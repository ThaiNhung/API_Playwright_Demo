package POST;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class createUserNJsonString {
    Playwright plWright;
    APIRequest request;
    APIRequestContext context;
    static String emailId;

    @BeforeTest
    public void setUp() {
        plWright = Playwright.create();
        request = plWright.request();
        context = request.newContext();
    }

    public static String getRandomEmail() {
        emailId = "Demo" + System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }

    @Test
    public void createUserWithJsonString() throws IOException {
        String jsonBody = "{\n" +
                "\"email\": \"demo9@gmail.com\",\n" +
                "\"name\": \"Demo\",\n" +
                "\"gender\": \"Male\", \n" +
                "\"status\": \"Active\" \n" +
                "}";
        //Create a new User
        APIResponse apiResponse = context.post("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setHeader("Authorization", "Bearer 067af0006458dd70bd2512dbd2d789f73870169869edbff114b03b406d7670da")
                .setHeader("content-type", "application/json")
                .setData(jsonBody)
        );
        System.out.println("Print the code and status corresponding: " + apiResponse.status() + " " + apiResponse.statusText());
        Assert.assertEquals(apiResponse.status(), 201, "Failed to verify status code");
        Assert.assertEquals(apiResponse.statusText(), "Created", "Failed to verify status text");

        //Response body
        ObjectMapper obMap = new ObjectMapper();
        JsonNode jsonNodeResponse = obMap.readTree(apiResponse.body());
        System.out.println(jsonNodeResponse.toPrettyString());

        //capture id from the post json response:
        String userId = jsonNodeResponse.get("id").asText();
        System.out.println("user id : " + userId);

        //Get the response
        APIResponse responseAPIPost = context.get("https://gorest.co.in/public/v2/users?" + userId + "&", RequestOptions.create()
//                .setHeader("content-type", "application/json")
                        .setHeader("Authorization", "Bearer 067af0006458dd70bd2512dbd2d789f73870169869edbff114b03b406d7670da")
        );
        System.out.println("Print status of Get method: " + responseAPIPost.status());
//        Assert.assertEquals(responseAPIPost.status(), 200, "Failed to verification code");

        System.out.println("Print status text of Get method: " + responseAPIPost.statusText());
        Assert.assertEquals(responseAPIPost.statusText(), "OK", "Failed to verification status text");

        System.out.println("Get Text: " + responseAPIPost.text());
        Assert.assertTrue(responseAPIPost.text().contains(userId));

    }
}
