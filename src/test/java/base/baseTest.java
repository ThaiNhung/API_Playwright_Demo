package base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;


public class baseTest {
    Playwright plWright;
    APIRequest request;
    public APIRequestContext context;
    public static String emailId;

    @BeforeTest
    public void setUp(){
        //Setup environment - new context
        plWright =  Playwright.create();
        request = plWright.request();
        context = request.newContext();

    }

    public void verifyStatusCode(APIResponse apiResponse){
        int statusCode = apiResponse.status();
        String statusText = apiResponse.statusText();
        if (statusCode == 200){
            Assert.assertEquals(statusText, "OK", "The Actual is: "+statusText+ " but expected is OK");
        }else if (statusCode == 201){
            Assert.assertEquals(statusText, "Created", "The Actual is: "+statusText+ " but expected is Created");
        }
        System.out.println("---print status code---" +statusCode);
        System.out.println("---print status text---" +statusText);
    }
    public void getBodyResponseInfo(APIResponse apiResponse) throws IOException {

        System.out.println("Print API Json body");
        //Because body show type byte array (byte[]) -> we cannot use toString or length, So we need to get the help of JACKSON API is the third party
        System.out.println(apiResponse.body());

        System.out.println("----print api json body response----");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyResponse);
    }
    public static String getRandomEmail(){
        emailId = "Demo"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }
    @AfterTest
    public  void  tearDown(){
        plWright.close();
    }
}
