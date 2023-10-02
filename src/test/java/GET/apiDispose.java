package com.qa.api.tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class apiDispose {
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
    public void testDeposit(){

        //Request 1
        APIResponse apiResponse = context.get("https://gorest.co.in/public/v2/users");
        int StatusCode = apiResponse.status();

        String statusText =  apiResponse.statusText();
        System.out.println("Response status code is: "+ StatusCode);
        Assert.assertEquals(StatusCode, 200, "The Actual is: "+StatusCode+ " but expected is 200");
        System.out.println(statusText);

        System.out.println("verify api response under boolean type");
        Assert.assertEquals(apiResponse.ok(), true);

        System.out.println("---Print api response with plain text---");
        System.out.println(apiResponse.text());

        //Disposes the body of this response. If not called then the body will stay in memory until the context closes.
        apiResponse.dispose();

        try {
            System.out.println("print api after dispose");
            System.out.println(apiResponse.text());
        }catch(PlaywrightException e){
            System.out.println("API response body is disposed");
        }
        int StatusCode2 = apiResponse.status();
        System.out.println("print new api after dispose " +StatusCode2);

    }
    @AfterTest
    public  void  tearDown(){
        plWright.close();
    }
}
