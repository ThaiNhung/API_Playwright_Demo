package putPatch;

import base.baseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.Test.appConstants.AUTHORIZATION;

public class putDemo extends baseTest {
    @Test
    public void updateUserInfo() throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("email", baseTest.getRandomEmail());
        data.put("name", "Updated");
        data.put("gender", "Female");
        data.put("status", "active");

        //Update the user with jsonBody info
        APIResponse apiCreateUser = context.post("https://gorest.co.in/public/v2/users/", RequestOptions.create()
                .setHeader("Authorization", AUTHORIZATION)
                .setHeader("Content-Type", "application/json")
                .setData(data)
        );
        //Response body
        ObjectMapper obMap = new ObjectMapper();
        JsonNode jsonNodeResponse = obMap.readTree(apiCreateUser.body());
        System.out.println(jsonNodeResponse.toPrettyString());

        //capture id from the post json response:
        String userId = jsonNodeResponse.get("id").asText();
        System.out.println("user id : " + userId);

        //update user info
        data.put("name", "Demo_Updated");

        //Update the user with jsonBody info
        APIResponse apiUpdate = context.put("https://gorest.co.in/public/v2/users/"+userId, RequestOptions.create()
                .setHeader("Authorization", AUTHORIZATION)
                .setHeader("Content-Type", "application/json")
                .setData(data)
        );

        //verify status code & Text
        verifyStatusCode(apiUpdate);
        //get body information
        getBodyResponseInfo(apiUpdate);
        Assert.assertTrue(apiUpdate.text().contains("Demo_Updated"));

    }
}
