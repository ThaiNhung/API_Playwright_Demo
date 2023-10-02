package GET;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class APIResponseHeader {
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
    public void apiResponseHeader(){
        APIResponse apiResponse = context.get("https://gorest.co.in/public/v2/users");

        int StatusCode = apiResponse.status();

        String statusText =  apiResponse.statusText();
        System.out.println("Status text is: " +statusText);

        System.out.println("Status code is: "+ StatusCode);
        Assert.assertEquals(StatusCode, 200, "The Actual is: "+StatusCode+ " but expected is 200");

        //Using Map:
        Map<String, String> headers = apiResponse.headers();
        // pass lambda expression to forEach()
        headers.forEach((k,v) -> System.out.println(k +":" + v));
        System.out.println("Header size: "+ headers.size());
        Assert.assertEquals(headers.get("content-type"), "application/json; charset=utf-8", "The expected is application/json but actual is "+headers.get("content-type"));

        //Using List:
        List<HttpHeader> headerlist = apiResponse.headersArray();
        for (HttpHeader e : headerlist){
            System.out.println("print name and value corresponding to " +e.name + ":" + e.value);

        }
    }

    @AfterTest
    public  void  tearDown(){
        plWright.close();
    }
}
