package get;

import base.baseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;


public class demoClass extends baseTest {
    @Test
    public void apiGetSpecificUser() throws IOException {

        //get Json file
        byte[] fileBytes = null;
        File file = new File("./src/test/data/userDemo.json");
        fileBytes = Files.readAllBytes(file.toPath());

        //Call API
        APIResponse apiResponse = context.get("https://api.genderize.io/", RequestOptions.create()
                .setQueryParam("name", "luc"));

        int StatusCode = apiResponse.status();

        System.out.println("---verify status text---");
        Assert.assertEquals(apiResponse.ok(), true);

        System.out.println("---verify status code---"+ StatusCode);
        Assert.assertEquals(StatusCode, 200, "The Actual is: "+StatusCode+ " but expected is 200");

        System.out.println("----print api json body response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println("---Print Response JSON----");
        System.out.println(jsonPrettyResponse);

        System.out.println("---Print JSON array before----");
        System.out.println(objectMapper.readTree(fileBytes));

        System.out.println("---Verify Json file and Json Response body---");
        Assert.assertEquals(objectMapper.readTree(jsonPrettyResponse), objectMapper.readTree(fileBytes),  "Failed to verify data");

        //Using Map:
        Map<String, String> headers = apiResponse.headers();
        // pass lambda expression to forEach()
        headers.forEach((k,v) -> System.out.println(k +":" + v));
        //Print header size
        System.out.println("Header size: "+ headers.size());
        Assert.assertEquals(headers.size(), 15, "Failed to verify header size");

        //verify Some points in header response
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8", "The expected is application/json but actual is "+headers.get("content-type"));
        System.out.println("access-control-allow-credentials "+ headers.get("access-control-allow-credentials"));
        Assert.assertEquals(headers.get("access-control-allow-credentials"), "true");
        Assert.assertEquals(headers.get("content-length"), "63");
        Assert.assertEquals(headers.get("access-control-expose-headers"), "x-rate-limit-limit,x-rate-limit-remaining,x-rate-limit-reset");

        //Using List to read header value
        List<HttpHeader> headerlist = apiResponse.headersArray();
        for (HttpHeader e : headerlist){
            System.out.println("print name and value corresponding to " +e.name + ":" + e.value);

        }
    }
}
