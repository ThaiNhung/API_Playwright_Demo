package GET;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class GETAPICall {
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
    public  void getAPIOfSpecificUser(){
        APIResponse apiResponse = context.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setQueryParam("id",5213538)
                        .setQueryParam("gender", "male"));
        commonMethod(apiResponse);
    }
   public void commonMethod(APIResponse apiResponse){
     int StatusCode = apiResponse.status();

     String statusText =  apiResponse.statusText();
     System.out.println("Response status code is: "+ StatusCode);
     Assert.assertEquals(StatusCode, 200, "The Actual is: "+StatusCode+ " but expected is 200");
     System.out.println(statusText);

     System.out.println("Verify api response under boolean type");
     Assert.assertEquals(apiResponse.ok(), true);

     System.out.println(apiResponse.text());
     //Assert.assertEquals(apiResponse.text(), true);

     System.out.println("Print API Json body");
     //Because body show type byte array (byte[]) -> we cannot use toString or length, So we need to get the help of JACKSON API is the third party
     System.out.println(apiResponse.body());

 }
    @Test
    public void getAPIUser() throws IOException {

     APIResponse apiResponse = context.get("https://gorest.co.in/public/v2/users");

     int StatusCode = apiResponse.status();

      String statusText =  apiResponse.statusText();
      System.out.println("Status text is: " +statusText);

      System.out.println("Status code is: "+ StatusCode);
      Assert.assertEquals(StatusCode, 200, "The Actual is: "+StatusCode+ " but expected is 200");

      System.out.println("Verify api response successful or not with status range 200-299");
      Assert.assertEquals(apiResponse.ok(), true, "The expected is true but actual is "+apiResponse.ok());

      System.out.println("Returns the text representation of response body.");
      Assert.assertEquals(apiResponse.text(), "OK");

     System.out.println("Print API Json body");
     System.out.println(apiResponse.body());

     ObjectMapper objectMapper= new ObjectMapper();
     //Use the ObjectMapper.read try to read the byte array data type
      JsonNode JsonResponse = objectMapper.readTree(apiResponse.body());
      System.out.println( JsonResponse.toPrettyString());
      System.out.println("Print api URL "+ apiResponse.url());
      System.out.println(apiResponse.headers());

      Map<String, String> headersMap = apiResponse.headers();
      System.out.println(headersMap.get("content-type"));
      Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
  }

  @AfterTest
    public  void  tearDown(){
        plWright.close();
  }
}
