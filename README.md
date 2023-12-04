# Playwright_Java_API_Automation
##Introduction
This source describes how to automate API using playwright API. Combine with TestNG for users easy to run and get reports. use Maven to manage the library in Source easy

### Setup
1: Install IDE (Intellij or VSCode): https://www.jetbrains.com/idea/promo/
2: Install python then add environment: https://www.python.org/downloads/
3: Install Playwright:
Open Terminal then run below commands:
npm i -D @playwright
4: Install TestNG: https://www.jetbrains.com/help/idea/testng.html

#### Structure
A: AppConstants - This file contain all constants of the project
B: data - This file is where the usage data for the POST API (e.g: userDemo.json)
C: Base/ baseTest - This file contain all common method using for project
+ including setup environment and common method (like verifyStatusCode,..)
D: get/ post/ put/ delete - this folder to contain test case (each of child class corresspond to each of API method request)


##### IN the Intellij
- Open the Intellij use TestNG to run Test Case
- Open get folder, run API request on demoClass.java 
  + by click on button Run Test to run class or run any method inside class
+ To debug, Set breakpoints in the code then Select the debug options to debug code
+ Output: After running successfully, go to the folder named target/Out-put then check the report file using the TestNG sample


