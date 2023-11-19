# Playwright_Java_API_Automation

## Setup
1: Install IDE (Intellij or VSCode): https://www.jetbrains.com/idea/promo/
2: Install python then add environment: https://www.python.org/downloads/
3: Install Playwright:
Open Terminal then run below commands:
npm i -D @playwright
4: Install TestNG: https://www.jetbrains.com/help/idea/testng.html

### Structure
A: AppConstants - This file contain all constants of the project
B: data - This file is where the usage data for the POST API
B: Base/ baseTest - This file contain all common method using for project
+ including setup environment and common method (like verifyStatusCode,..)
C: get/ post/ put/ delete - this folder to contain test case (each of child class corresspond to each of API method request)


### IN the Intellij
- Open the Intellij use TestNG to run Test Case
- Open get/ post/ put/ delete folder, run API request by click on button Run Test
+ To debug, Set breakpoints in the code then Select the debug options to debug code


