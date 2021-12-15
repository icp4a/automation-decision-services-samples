# Tutorial: Testing a decision service at build time 

## Description
This tutorial shows you how to add unit tests to a decision service run at build time.

## Learning objectives
- Add tests to a decision service.
- Build a decision service archive and check the unit test results.
- Look at the test results using the embedded build service.
  
## Audience

This tutorial is for technical and business users who want to add unit tests to their decision service. Some knowledge of Java is required.

## Time required

15 minutes

## Prerequisites

Prepare by doing the [Getting started in Automation Decision Services](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.3?topic=resources-getting-started-tutorial) tutorial. It introduces you to Automation Decision Services.
Note that in this tutorial, the steps are not as detailed as in the Getting started, you can refer to it if you need more information.

You must have the following environment:
- **Decision Designer**: A web-based user interface for developing decision services in Business Automation Studio. You work with a sample decision service by importing it into a project and opening it in Decision Designer.
- **Apache Maven**: You can download it from https://maven.apache.org. This tutorial was tested with version 3.8.3.


# Task 1: Importing and sharing a decision service
**About this task**

In this task, you... 
- Explore the decision service to add unit tests on.
- Share your decision service.
  
 ## Step 1: Exploring a decision service
 In this step, you create a new project, import a sample, and explore a decision service to be ready to add unit tests to it. 
 This decision service is `Loan Approval` which validates loans by using data from the borrower, and computes insurance rates for the requested loan amount.
 If you need more details to follow the instructions please refer to the [Getting started in Automation Decision Services](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.3?topic=resources-getting-started-tutorial) tutorial.
 
 **Procedure**
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. Create a new project and import the `Banking` sample from the Business domains section of the samples wizard. It contains four decision services. 
3. Click **Loan Approval** to open this decision service. It contains a decision model, a data model, and a decision operation.
4. Open the **Decision Operations** tab, open the existing operation. The source model is `Approval decision model` and the function name is `Approval-decision-model`. The
operation ID to be used in the unit test is `ApprovalDecisionModel-Approval-decision-model`.
5. Open the **Approval decision model** model and explore its diagram.
6. Click the **Run** tab. Four data sets are defined. Open the `InvalidZipCode` data set, open the overflow menu and select `Edit as JSON` to get its json input to be used in the unit test definition.
7. Run the data set. You get the following response in the node named `Approval`:
```
{
  "approved": false,
  "message": "The borrower's Zip Code should have 5 digits"
}
```
The pointer to the approved value is `/approval/approved`, it has the schema `/<node name>/<value>`.

In the next task, you write a unit test to check the output of this data set.

## Step 2: Sharing your decision service

In this step, you connect your project to a Git repository and you share your decision service in your instance of Automation Decision Services.
If you need more details to follow the instructions please refer to the [Getting started in Automation Decision Services Task2](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.3?topic=resources-getting-started-tutorial) tutorial.
 
**Procedure**

1. Create a new empty Git repository, get its HTTP URL, and create a personal access token providing the full control to the repository. 
2. In Decision Designer, open the Settings page of your project to connect your project to the Git repository you created.
3. Open the **Share changes** tab and share your changes with the comment `Loan Approval first version`.
4. Refresh your Git repository to get the updated version of the project. Copy the URL of the Git repository to clone it to your machine in the next task.

In the next task, you get the files of your project and add unit tests inside.

# Task 2: Adding unit tests to a decision service
**About this task**

In this task, you... 
- Build your decision service.
- Add unit tests to your decision service.
- Run those unit tests locally to debug them.

You use Maven to build and test the project. Refer to the [Building and deploying from a CI/CD stack](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.3?topic=services-building-deploying-from-cicd-stack) documentation for more details.
                                              

## Step 1: Building your decision service

In this step, you first clone your Git repository, define your Maven settings file, then you build your decision service using Maven to create a first version of the decision archive without any test. You get the operation ID to be used in the unit test.

**Procedure**

1. Create a new empty directory on your machine and clone your Git repository by running the command `git clone <repository URL>` in this directory. 
2. Browse the files of your decision project, open the `Loan Approval` directory that contains the files of the `Loan Approval` decision service.
3. If your Maven settings file is configured to access Automation Decision Services artifacts, you can skip this point.
Otherwise, you must define a Maven settings file by completing the template settings.xml, which is provided in this sample.
   * Open **automation-decision-services-samples/samples/UseTestingTutorial/settings.xml**.
   *  Replace all the `TO BE SET` tags with appropriate values:
      * `LOCAL REPOSITORY TO BE SET`: A directory on your machine in which the artifacts are downloaded.
      * `MAVEN SNAPSHOT TO BE SET`: The URL of the repository manager snapshot repository.
      * `MAVEN RELEASE TO BE SET`: The URL of the repository manager release repository.
      * `MAVEN PUBLIC TO BE SET`: The URL of the repository manager public repository.
      * `USER TO BE SET`: A username that can access the repository manager.
      * `PASSWORD TO BE SET`: The username's password to access the repository manager.
   * Copy the `settings.xml` file in the `Loan Approval` directory opened in point 2.
3. Run the following command in the `Loan Approval` directory: 
```
mvn clean install -s settings.xml
```
Your decision archive named `loanApprovalDecisionService-1.0.0-SNAPSHOT.jar` is created in the `Loan Approval/.decisionservice/target/` directory.

4. Open the file `Loan Approval/Approval decision model/target/classes/META-INF/decisions/ApprovalDecisionModel-Approval-decision-model.json`. It contains:
```
{
  "contentVersion" : "1.0.0",
  "name" : "ApprovalDecisionModel-Approval-decision-model",
  ...
}
```
The name of this file which is also contained in the field `"name"` is the operation ID. It is built with the schema `<model name>-<function name>`. You use it in the next step for the unit test definition.

## Step 2: Adding unit tests

In this step, you add unit tests in the decision model. Those unit tests are described in JSON format giving the input and expected output of each run.
A Java file is also required to add a JUnit test browsing those JSON files. You can find detailed information about writing unit test in the [Unit testing documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.3?topic=services-unit-testing)

**Procedure**

1. Add a new package `src/test/java/adsSamples/` in `Loan Approval/Approval decision model`. You can choose any name for the package name. In this example, you use `adsSamples`.
2. Add a new file `src/test/java/adsSamples/TestLoanApproval.java` in `Loan Approval/Approval decision model`. You can choose any name for the java file. In this example, you use `TestLoanApproval.java`.
3. Enter the following contents for the file. The important part of this file is the decision operation ID. It is built with the schema `<model name>-<function name>`, you got it in Task2 Step1 4. This test opens all JSON files provided in the resources directory
and validates the model using the data it contains. Next you add a JSON file describing a specific test. 
```
package adsSamples;

import com.ibm.decision.run.test.junit5.DecisionTest;
import com.ibm.decision.run.test.junit5.JSONTestDirectoryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.Date;
import java.util.logging.*;
import java.util.stream.Stream;

@DecisionTest(decisionOperation = "ApprovalDecisionModel-Approval-decision-model")
@DisplayName("LoanApproval")
public class TestLoanApproval {
    @TestFactory
    public Stream<DynamicNode> decisionTests() {
        Logger logger = Logger.getLogger("com.ibm.decision.run.test");
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        logger.addHandler(handler);
        logger.setLevel(Level.FINEST);

        return JSONTestDirectoryFactory.createTests(this.getClass());
    }
}
```

4. Add a new directory `src/test/resources/adsSamples/ApprovalDecisionModel-Approval-decision-model/` in `Loan Approval/Approval decision model`. Note that the package has to be the same as the one for the java file and that it is followed by
a directory named with the decision operation ID. 
5. Add a new file `src/test/resources/adsSamples/ApprovalDecisionModel-Approval-decision-model/InvalidZipCode.json` in `Loan Approval/Approval decision model`. You can choose any name for the json file, the extension `.json` is the important part.
6. Enter the following contents for this file in JSON format. It describes:
   * the test name, `InvalidZipCode` in this example,
   * the test input: you can copy it from the data sets located in `Loan Approval/Approval decision model/resources/validate_dataset`, in this example it is copied from `InvalidZipCode.json`.
   * the assertions list to check on the decision service execution with the format:
      * label: the name for this assertion to be displayed when it fails.
      * pointer: the path to get the tested value in the response output with the schema `/<node name>/<value>`.
      * operator: the operator for the comparison, the default value it contains `(⊃)`.
      * an operator option for ignoring order (false, not ignoring by default).
      * expected: a JSON fragment that is the expected value.
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
         "expected": true
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
This file contains a wrong expected value for `approved`on purpose. Next you locally run the test, see it is failing and you fix it.

## Step 3: Locally running unit tests

In this step, you build again your decision service using Maven to run your unit test. You look at the output and you fix the unit test. Finally, you commit your changes to the Git repository.

**Procedure**

1. As in step 1, run the following command in the `Loan Approval` directory: 
```
mvn clean install -s settings.xml
```
This time, the command fails because the unit test has run and failed. You get this output:
```
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running adsSamples.TestLoanApproval
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.653 s <<< FAILURE! - in adsSamples.TestLoanApproval
[ERROR] decisionTests().InvalidZipCode.json  Time elapsed: 0.591 s  <<< FAILURE!
org.opentest4j.AssertionFailedError: Approved at JSON pointer "/approval/approved": failed: false = true

[INFO] 
[INFO] Results:
[INFO] 
[ERROR] Failures: 
[ERROR]   Approved at JSON pointer "/approval/approved": failed: false = true
[INFO] 
[ERROR] Tests run: 1, Failures: 1, Errors: 0, Skipped: 0
....
```
The test reports are created in the `Loan Approval/Approval decision model/target/surefire-reports` directory. 

2. You fix the test by changing the expected value from true to false in the assertion `Approved` in the `InvalidZipCode.json` file:
```
    "assertions": [
       {
         "label": "Approved",
         "pointer": "/approval/approved",
         "operator": "=",
         "expected": false
       },
```
3. Run again the test using: 
```
mvn clean install -s settings.xml
```
This time the test passes and the archive is generated. You get the following output:
```
...
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running adsSamples.TestLoanApproval
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.499 s - in adsSamples.TestLoanApproval
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
...
```

4. Since the tests are running fine, you commit your changes to your Git repository. For this, you add the new files using the `git add` command, then you commit using `git commit -m"add unit test"` and you push your changes.

In next step, you load your tests in Decision Designer and look at the test results using the embedded build.

# Task 3: Loading and using the test in Decision Designer
**About this task**

In this task, you load you changes in Decision Designer, you deploy the decision service using the embedded build service and look at the test result.
 
**Procedure**

1. Open the project you created in Task1 in Decision Designer.
2. Open the `Load changes` tab and load the changes you just committed to `Loan Approval`.
3. Open the Deploy tab, create a new version as proposed, and name it `UnitTest`.
4. Expand this version and click on Deploy at the end of the `Loan Approval`row. 
5. When the deployment is completed, click on the View logs button next to the successful status and search for the Tests section: your test has been successfully executed.
```
...
INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running adsSamples.TestLoanApproval
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.213 s - in adsSamples.TestLoanApproval
...
```
**Important note** 

**The unit tests of your model are executed each time you build and deploy your decision service. Those tests will fail in case of a regression in the behavior of your decision service.
In this case, `Deploy` will fail in Decision Designer, you will have to open the build log to identify the failing test and decide if it is the test or the service that should be updated.**

**Conclusion**

You've completed this tutorial. You can find more tests in the `project/BankingTested.zip` project.
You can import this zip in a new project in Decision Designer and connect it to a Git repository to look at the tests. The decision service `Approval with ML`
does not contain any test because the unit test feature is not yet supported for decision services using machine learning. You can find tests for the three others decision services. Note that the decision services available in `project/BankingTested.zip` are the same as the ones available in the `Banking` sample. You will need to rename the decision services before you can import the `Banking` sample.
