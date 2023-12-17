package com.base;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;


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

    public static String getRandomEmail(){
        emailId = "Demo"+ System.currentTimeMillis() + "@gmail.com";
        return emailId;
    }
    @AfterTest
    public  void  tearDown(){
        plWright.close();
    }
}
