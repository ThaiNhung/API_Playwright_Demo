package com.post;

import com.base.baseTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;
import org.Test.userPOJO;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import static org.Test.appConstants.*;

public class postDemo extends baseTest {

    @Test(description="DM_01-create new user then verify response information")
    public void createNewUser() throws IOException {

        //get json file:
        byte[] fileBytes = null;
        File file = new File("./src/test/data/user.json");
        fileBytes = Files.readAllBytes(file.toPath());

        //Update the user with jsonBody info
        APIResponse apiPostResponse = context.post(URL, RequestOptions.create()
                .setHeader("Authorization", AUTHORIZATION)
                .setHeader("Content-Type", CONTENT_TYPE)
                .setData(fileBytes)
        );
        System.out.println(apiPostResponse.url());
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(),SC_CREATED_CODE);
        Assert.assertEquals(apiPostResponse.statusText(), SC_CREATED_TEXT);

        String responseText = apiPostResponse.text();
        System.out.println(responseText);

        //convert response text/json to POJO -- deserialization
        ObjectMapper objectMapper = new ObjectMapper();
        userPOJO actUser = objectMapper.readValue(responseText, userPOJO.class);
        System.out.println("actual user from the response---->");
        System.out.println(actUser);

        Assert.assertEquals(actUser.getName(), "Demo", "Failed to verify name");
        Assert.assertEquals(actUser.getEmail(), "Demo2@gmail.com", "Failed to verify email");
        Assert.assertEquals(actUser.getStatus(), "active", "Failed to verify status");
        Assert.assertEquals(actUser.getGender(), "male", "Failed to verify gender");
        Assert.assertNotNull(actUser.getId());

        //Using Map:
        Map<String, String> headers = apiPostResponse.headers();
        // pass lambda expression to forEach()
        headers.forEach((k,v) -> System.out.println(k +":" + v));
        //Print header size - Assume size of header in requirement equal to 25
        System.out.println("Header size: "+ headers.size());
        Assert.assertEquals(headers.size(), 25, "Failed to verify header size");

        //verify Some points in header response
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8", "The expected is application/json but actual is "+headers.get("content-type"));
        Assert.assertEquals(headers.get("x-download-options"), "noopen");

        //Using List to read header value
        List<HttpHeader> headerlist = apiPostResponse.headersArray();
        for (HttpHeader e : headerlist){
            System.out.println("print name and value corresponding to " +e.name + ":" + e.value);

        }
    }
}
