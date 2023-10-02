package POST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class createUserAPI {
    Playwright plWright;
    APIRequest request;
    APIRequestContext context;

    @BeforeTest
    public void setUp(){
        plWright =  Playwright.create();
        request = plWright.request();
        context = request.newContext();

    }
    @Test
    public  void createUser() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", "test4@yopmail.com");
        data.put("name", "nhungAuto");
        data.put("gender", "Female");
        data.put("status", "pending");


        //using the post have requestOptions because we need to add authentication, body, headers as well
    APIResponse responsePost = context.post("https://gorest.co.in/public/v2/users",
            RequestOptions.create()
                    .setHeader("content-type", "application/json")
                    .setHeader("authorization", "Bearer 067af0006458dd70bd2512dbd2d789f73870169869edbff114b03b406d7670da")
                    .setData(data)
    );
    System.out.println(responsePost.status());
    //Assert.assertEquals(responsePost.statusText(), "201");
    System.out.println(responsePost.statusText());

    ObjectMapper objectMapper= new ObjectMapper();
    JsonNode JsonResponse = objectMapper.readTree(responsePost.body());
    System.out.println( JsonResponse.toPrettyString());

    }
    @AfterTest
    public  void  tearDown(){
        plWright.close();
    }
}
