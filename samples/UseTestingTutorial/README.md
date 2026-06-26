# Tutorial: Testing a decision service at build time 

## Description
This tutorial shows you how to add unit tests to a decision service run at build time.

## Learning objectives
- Add tests to a decision service.
- Build a decision service archive and check the unit test results.
- Look at the test results using the embedded build service.
  
## Audience

This tutorial is for technical and business users who want to learn how to add unit tests to their decision services. 

## Time required

15 minutes

## Prerequisites

To get more familiar with Decision Intelligence Client Managed Software (DICMS), we recommend you to start by following the `Getting started in Decision Intelligence`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/26.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Decision Intelligence Client Managed Software")](https://www.ibm.com/docs/en/dicms/26.0.0?topic=resources-getting-started) tutorial. 

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision services. You work with a sample decision service by importing it into a project and opening it in Decision Designer.

# Task 1: Importing and sharing a decision service
**About this task**

In this task, you... 
 - Import and explore a sample decision service.
 - Create a Git repository and connect it to your decision service.
 - Share your changes.

  ## Step 1: Exploring a decision service
  In this step, you create a new automation, and import and explore a sample decision service to be ready to add unit tests to it. 
  You import the `Loan Approval` sample, which validates loans by using data from a borrower, and computes insurance rates for the requested loan amount.
  If you need more guidance to follow the instructions below, you can refer to the `Getting started in Decision Intelligence`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/26.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Decision Intelligence Client Managed Software")](https://www.ibm.com/docs/en/dicms/26.0.0?topic=resources-getting-started) tutorial.
 
 **Procedure**
 
1. Use the credentials provided for your instance to sign in to Decision Designer.
2. Create a new automation and import the `Banking` sample from the list of **Industry samples**. This sample contains four decision services. 
3. Open the **Loan Approval** decision service. It contains a decision model, a data model, and a decision operation.
4. Go to the **Decision Operations** tab and open the existing operation. The source model is `Approval decision model` and the function name is `Approval-decision-model`. The operation ID to be used in the unit test is `ApprovalDecisionModel-Approval-decision-model`.
5. Navigate to the **Approval decision model** model and explore its diagram.
6. Open the **Run** tab. Four data sets are defined. Open the `InvalidZipCode` data set and click on `Edit as JSON` to get its JSON input. This input is going to be used in the unit test definition.
7. Run the data set. You get the following response in the node named `Approval`:
```
{
  "approved": false,
  "message": "The borrower's Zip Code should have 5 digits"
}
```
The pointer to the approved value is `/approval/approved`. It uses the schema `/<node name>/<value>`.

In the next task, you write a unit test to check the output of this data set.

## Step 2: Creating a Git repository

In this step, you create an empty Git repository and get the data required to connect to it in Designer.

**Procedure**

1. Open [GitHub][https://github.com/] in your browser, and use your GitHub credentials to log in.
2. Click the `+` icon in the upper-right corner of the page and select `New repository`.
3. Give the repository a unique name, and add the following description:
```
Git repository for the Decision Intelligence getting started decision service.
```
4. Click `Create repository` to finish setting it up.

**Note**: The repository must be empty. Do not add a README file, .gitignore, or license file.
5. Click the Copy button to copy the HTTPS URI. It has the following format:
```
https://github.com/<yourAccountName>/<yourRepoName>.git
```
6. Click your profile icon in the upper-right corner of the page and select `Settings`.
7. Navigate to `Developer settings > Personal access tokens > Tokens (classic)`.
8. Click `Generate new token`. Then, select `Generate new token (classic)`.
9. Enter a note to identify the token, and select `repo` to grant full access of the repository to Decision Intelligence.
10. Click `Generate token` at the end of the page. Copy the generated access token before you close the page. 

 ## Step 3: Connecting and sharing a decision service

In this step, you connect your automation to a Git repository and you share your decision service in your instance of Decision Intelligence.

Note: Ignore the repository that is connected to your decision service by default.
 
**Procedure**

1. In Decision Designer, click the connection icon to define or update a connection.
2. Click the `Disconnect`button to disconnect the current repository and connect to the one you created in the previous step. 
3. Ensure that `Create or update credentials` is selected for the project.
4. Enter the Git URI, and leave the Username and password option selected.
5. Enter your GitHub username and the personal access token that you generated in step 2.
6. Click `Connect`. When your decision automation connects to your GitHub repository, Decision Designer displays the following message:
```
    The remote Git repository is connected successfully.
```
7.  Click on your automation name in the breadcrumbs to return to your decision service. 
8. Open the **Share changes** tab and share your changes with the comment `Loan Approval first version`.
4. Refresh your Git repository to get the updated version of the automation. Copy the URL of the Git repository. You will use it in the next task to clone the repository to your machine.

 In the next task, you retrieve your automation files and add unit tests inside.

# Task 2: Adding unit tests to a decision service
**About this task**

In this task, you... 
- Get the source files of your decision service.
- Add unit tests to your decision service.
- Commit your changes.

## Step 1: Getting the source files of your decision service

In this step, you clone your Git repository and explore your decision service.

**Procedure**

1. Create a new directory on your machine and clone your Git repository by running the command `git clone <repository URL>` in this directory. 
2. Browse the files of your decision automation and open the `Loan Approval` directory. It contains the files of the `Loan Approval` decision service.

 ## Step 2: Adding unit tests

 In this step, you add unit tests in the decision model. Those unit tests are described in JSON format, providing the input and expected output of each run.
You can find detailed information about writing unit tests in the `Unit testing documentation` [![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/26.0.0?topic=services-unit-testing)[![ADS](/resources/ads.svg "IBM Decision Intelligence Client Managed Software")](https://www.ibm.com/docs/en/dicms/26.0.0?topic=services-unit-testing)

**Procedure**

1. In `Loan Approval/Approval decision model`, create a directory based on the decision operation ID name: `src/test/resources/ApprovalDecisionModel-Approval-decision-model/`.
2. Create a JSON test file `src/test/resources/adsSamples/ApprovalDecisionModel-Approval-decision-model/InvalidZipCode.json` in the newly created directory. You can use any descriptive name for your JSON file, as long as it has the  with the `.json` extension.
3. Add the following content to the file:
```
   {
     "name": "InvalidZipCode",
     "input": {
                "loan": {
                  "amount": 185000,
                  "numberOfMonthlyPayments": 72,
                  "startDate": "2005-06-01T00:00:00Z",
                  "loanToValue": 0.7
                },
                "borrower": {
                  "SSN": {
                    "areaNumber": "123",
                    "groupCode": "45",
                    "serialNumber": "6789"
                  },
                  "firstName": "John",
                  "lastName": "Doe",
                  "birthDate": "1968-05-12T00:00:00Z",
                  "yearlyIncome": 100000,
                  "zipCode": "9132",
                  "creditScore": 700
                },
               "currentTime" : "2021-12-04T08:41:21.242Z"
      },
     "assertions": [
       {
         "label": "Approved",
         "pointer": "/approval/approved",
         "operator": "=",
         "expected": false
       },
       {
         "label": "Message",
         "pointer": "/approval/message",
         "operator": "⊃",
         "expected": "Zip Code"
       }
     ]
   }
   ```
It describes:
   * The test name: `InvalidZipCode` in this example.
   * The test input: You can copy it from the data sets located in `Loan Approval/Approval decision model/resources/validate_dataset`. In this example, it is copied from `InvalidZipCode.json`.
   * The list of assertions to check when executing the decision service. Each assertion consists of five items:
      * `label`: Defines the name of the assertion. It is displayed if it fails.
      * `pointer`: Represents the path to the tested values in the output. It follows a `/<node name>/<value>` schema.
      * `operator`: Defines the type of comparison that you want to perform. The default value for the operator is `(⊃)`.
      * `ignoringOrder` (optional): Makes an order agnostic comparison of collections if set to true. It is set to false by default.
      * `expected`: Contains all expected values.


### Step 3: Committing your changes

In this step, you commit your changes to the Git repository.

**Procedure**
1. Stage the new files by using the `git add` command.
2. Commit your changes by using the `git commit -m "add unit test"` command.
3. Push your changes to the remote repository.

# Task 3: Using the testing feature in Decision Designer
In this task, you use and modify a decision service with unit tests defined.

  # Step 1: Loading changes and checking test results

 In this step, you load your changes in Decision Designer. Then, you deploy the decision service using the embedded build service and look at the test report. 

 **Procedure**

 1. Open the automation you created in Task1 in Decision Designer.
 2. Open the `Load changes` tab and load the changes you just committed to `Loan Approval`.
 3. Open the `View History` tab. Then, create a new version named `1.0.0` from the latest commit "add unit test".
 4. Open the `Deploy` tab. Expand the `1.0.0` version and click on **Deploy** at the end of the `Loan Approval` row. 
 5. Once the deployment is completed, click on the **View report** button next to **Test succeeded**. You get the following output:
 ```
Test result
Approval decision model
 1 tests ran for 0.316 seconds.
Success
InvalidZipCode.json (decisionTests()): 0.234 seconds
```

 # Step 2: Updating a decision service

 In this step, you update the decision model to change the number of digits for ZIP codes.

 **Procedure**

 1. Use the navigation history to open the `Approval decision model`.
 2. Edit the `wrong zip format`rule in the `Approval` node to change the ZIP length from 5 to 4:
 ```set 'zip length' to 4 ;```
 3. Open the `Run` tab. Select the `InvalidZipCode` data set and change the ZIP code value to 913.
 4. Run the data set. You get the following results:
```
 Test result
 Approval decision model
 1 tests ran for 0.275 seconds.
 1 failure(s)
 InvalidZipCode.json (decisionTests): 0.269 seconds
     Assertion Failure(s)
         Approved at JSON pointer "/approval/approved": failed: true = false
         Message at JSON pointer "/approval/message": failed: <actual> ⊃ <expected>
         actual: "Congratulations! Your loan has been approved"
         expected: "Zip Code"
 ```
 5. Share you changes as described in Task1 Step2. Then, create a new version named `1.0.1` and deploy it as explained in the previous step.
 6. Because the results are different from the ones defined in your unit test, the deployment fails. You get the following output in the log report:
 ``` Error in 1 assertions. Check the test report for details. The deployment is stopped ```
 and the test report
 ```
Test result
Approval decision model
1 tests ran for 0.275 seconds.
1 failure(s)
InvalidZipCode.json (decisionTests): 0.269 seconds
    Assertion Failure(s)
        Approved at JSON pointer "/approval/approved": failed: true = false
        Message at JSON pointer "/approval/message": failed: <actual> ⊃ <expected>
         actual: "Congratulations! Your loan has been approved"
         expected: "Zip Code"
 ```
 # Step 3: Maintaining a unit test

 In this step, you update the unit test to reflect the changes you made to the decision service.

 1. Load changes in the duplicate version of your decision service.
 2. Update the `zipCode` value to `913` in `src/test/resources/adsSamples/ApprovalDecisionModel-Approval-decision-model/InvalidZipCode.json`. 
 3. Commit and push your changes. Then, load the changes into Decision Designer as explained in Task3 Step1.
 4. In Decision Designer, create a new version named `1.0.2` and deploy it. This time, the deployment is successful.
 
 **Conclusion**

You have completed this tutorial. You can find more tests in the `project/BankingTested.zip` sample.
You can import this ZIP file into a new automation in Decision Designer and connect it to a Git repository to browse the tests it contains. The decision service `Approval with ML`
does not contain any test because the unit test feature is not yet supported for decision services using machine learning. You can find tests for the three other decision services. The decision services available in `project/BankingTested.zip` are the same as the ones available in the `Banking` sample. You will need to rename the decision services before you can import the `Banking` sample.

