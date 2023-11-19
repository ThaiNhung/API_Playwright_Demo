package get;

import base.baseTest;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.Test.appConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class getDemo extends baseTest {


    @Test
    public  void getSpecificUserApiTest() throws IOException {
        //API GET Specific user with gender as male and status as active.
        APIResponse apiResponse = context.get(appConstants.URL, RequestOptions.create()
                .setQueryParam("gender", "male")
                .setQueryParam("status", "active"));

        //Get Response information (including status code & text, body)
        verifyStatusCode(apiResponse);
        getBodyResponseInfo(apiResponse);

    }

    @Test
    public void getAllAPIUsersTest() throws IOException {
        APIResponse apiResponse = context.get(appConstants.URL);

        //get status and body of API response
        verifyStatusCode(apiResponse);
        getBodyResponseInfo(apiResponse);

        System.out.println("----print api url----");
        System.out.println(apiResponse.url());

        System.out.println("----print response headers----");
        Map<String, String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        Assert.assertEquals(headersMap.get("x-download-options"), "noopen");

    }
}
