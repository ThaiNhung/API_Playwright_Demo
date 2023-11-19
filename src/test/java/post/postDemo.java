package post;

import base.baseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.Test.appConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class postDemo extends baseTest {

    @Test
    public void createUserWithJsonString() throws IOException {
        //get Json file
        byte[] fileBytes = null;
        File file = new File("./src/test/data/user.json");
        fileBytes = Files.readAllBytes(file.toPath());

        //Create a new User
        APIResponse apiResponse = context.post(appConstants.URL, RequestOptions.create()
                .setHeader("Authorization", appConstants.AUTHORIZATION)
                .setHeader("content-type", appConstants.CONTENT_TYPE)
                .setData(fileBytes)
        );
        //Verify Status code & status text after created
        System.out.println("---print status code & text---" + apiResponse.status() + " " + apiResponse.statusText());
        Assert.assertEquals(apiResponse.status(), 201, "Failed to verify status code");
        Assert.assertEquals(apiResponse.statusText(), "Created", "Failed to verify status text");

        //Response body
        ObjectMapper obMap = new ObjectMapper();
        JsonNode jsonNodeResponse = obMap.readTree(apiResponse.body());
        System.out.println(jsonNodeResponse.toPrettyString());

        //capture id from the post json response:
        String userId = jsonNodeResponse.get("id").asText();
        System.out.println("user id : " + userId);


        //Get the response information newly created above
        APIResponse responseAPIPost = context.get(appConstants.URL, RequestOptions.create()
                        .setHeader("Authorization", appConstants.AUTHORIZATION)
                        .setQueryParam("id",userId)
        );

        System.out.println("Print status of Get method: " + responseAPIPost.status());
        Assert.assertEquals(responseAPIPost.status(), 200, "Failed to verification code");

        System.out.println("Print status text of Get method: " + responseAPIPost.statusText());
        Assert.assertEquals(responseAPIPost.statusText(), "OK", "Failed to verification status text");

        System.out.println("Get Text: " + responseAPIPost.text());
        //verify ID correct
        Assert.assertTrue(responseAPIPost.text().contains(userId));

    }
}
