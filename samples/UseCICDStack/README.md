# Tutorial: Using a CI/CD stack to build and deploy a decision service

## Description

This tutorial shows you how to use the custom CI/CD stack to build and deploy a decision service archive.

## Learning objectives
- Connect a decision project to a Gitea repository.
- Add the required instructions to deploy the archive on Nexus.
- Make a build plan in Jenkins to build a decision service.
- Execute the decision service archive with the Swagger UI.

  
## Audience

This sample is for technical and business users who want to understand the usage of the custom CI/CD stack in Automation Decision Services.
Some knowledge of the CI/CD stack is required.

## Time required

20 minutes

## Prerequisites

Prepare with the following resources:
- [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html): This tutorial introduces you to Automation Decision Services.

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision services in Business Automation Studio. You work with the sample decision service by importing it into a decision project and opening it in Decision Designer.
- **Deployment services**:  Your IT developers must provide a CI/CD stack to run the decision service. They must provide you the URLs and credentials to the instances of Gitea, Jenkins, Nexus, and the instance of the Swagger UI for the Automation Decision Services runtime service.


# Task 1: Creating and sharing a decision service
**About this task**

In this task, you... 
- Create a new project.
- Import a sample.
- Share your project.
  
 ## Step 1: Creating a new project
 In this step, you create a new project.
 
 **Procedure**
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. Click on the navigation menu at the top left of the page, expand Design and select **Business automations**.
3. Click Decision to see the decision automations.
4. Click **Create** and select **Decision automations** to make a project.
5. Enter a unique name for the project. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. For simplicity, we use `My Project` in this sample documentation. After entering your name for the decision project, enter the following description:
```
Automated Decision Service sample deployed using the CI/CD stack.
```
6. Click **Create** to make your decision project.

## Step 2: Importing sample projects
In this step, you import the decision project that you deploy later in this tutorial. You import the Loan Validation decision service, which validates loans based on data from the borrower and computes insurance rates for the requested loan amount.

**Procedure**

1. In the **Decision services** tab, click **Browse samples**.
2. Choose **Banking** in the Business domains section, and click **Import** to import the decision service.
3. Click **Loan Validation** to open the decision service. It contains a data model and a decision model. 
4. Click **Loan Validation Decision Model** to open it and explore the diagram.
5. Click the **Run** tab to run the model with the provided test data.

## Step 3: Sharing your decision service

In this step, you share your decision service in your instance of Automation Decision Services.

**Procedure**

1. Click the name of your project in the breadcrumbs.
2. Open the **Share changes** tab. It shows the changes that you can share. The Loan Validation decision service is selected.
3. Click **Share**, and enter the comment `Loan Validation first version`.
4. Click **Share**. Now, your collaborators in your instance of Automation Decision Services can see your decision service.

# Task 2: Connecting to a  Gitea repository
**About this task**

In this task, you... 
- Create a Gitea repository.
- Connect your decision project to it.
- Prepare the decision service for deployment.
- Load the changes you made in Designer.

You must have the URI and security credentials for the Git. You must also have the URL to the Nexus where you can deploy decision service archives.

## Step 1: Creating a Gitea repository

In this step, you create a Gitea repository. Your IT developers must give you a URI and security credentials for it. 

**Procedure**

1. Log in to Gitea using your security credentials.
2. Click the **+** button, and select **New Repository** in the menu that opens.
3. Enter a unique name and a description. For this example, we use `My Project` and the description `ADS project using the custom CI/CD stack`.
4. Click the **Create Repository** button.
5. If you are logged in to Gitea as the user of the Decision Designer specified at installation time, you can skip the following steps: 
a. Click **Settings**, and select the **Collaborators** tab.
b. Enters the user's name as a Git user, and then click the **Add Collaborator** button.
6. In the **Code** tab, click the **Copy** button to get the URL of the Git repository, which you use in next step.

## Step 2: Connecting your project to the Git repository

In this step, you connect your project to a Git repository in Decision Designer. 

**Procedure**

1. Check the status of your Git repository connection in the upper right corner of Decision Designer. The broken link icon shows that the project is not connected to a remote Git repository.
2. Click **Connect** to make a connection.
3. Enter the Git URI and security credentials from Step 1, and then click **Connect**. A message tells you that the connection completed successfully. Your decision service is written to the repository when you connect to it.
4. Click **My Project** in the breadcrumbs to return to your service. The link icon shows that your project is now connected to the Git repository.
5. Refresh your Gitea page to see the Loan Validation decision service in your repository.

## Step 3: Preparing your decision service for deployment

In this step, you add the instructions to deploy a decision service archive. This step references the Nexus, archive repository tool in the sample CI/CD stack.

**Procedure**

1. Open your decision service in the Git repository.
2. Edit the `pom.xml` file in the repository. Before the `end` tag, add the following instructions for deploying your project. 
Replace `<Nexus URL>` by entering the value that corresponds to the Maven repository server:
```
  <distributionManagement>
        <snapshotRepository>
            <id>maven-snapshots</id>
            <url><Nexus URL>/repository/maven-snapshots/</url>
        </snapshotRepository>
        <repository>
            <id>maven-releases</id>
            <url><Nexus URL>/repository/maven-releases/</url>
        </repository>
    </distributionManagement>
```
3. Commit the changes, adding the following message: `Added a distribution management part.`

## Step 4: Loading your changes in Designer

In this step, you load the changes you made in Step 3 in your Designer project to get the latest version. Then, you will be able to go on changing the project as described in Task 4.
**Procedure**

1. Open `My Project` in Decision designer.
2. Click on the **Load changes** button in the **Load changes** tab to retrieve the changes. A line appears for the `Loan Validation` decision service.
3. Click on the **View details** link: you see that the maven build files have been modified. Close the details window.
4. Click on the **Load changes** button to get those changes. Click on the **Load** button. There are no more incoming changes. You can go on working on your project in Designer.

# Task 3: Building and deploying the decision service archive
**About this task**

In this task, you... 
- Create a build plan in Jenkins to build and deploy your decision service archive.
- Execute this build plan.
- Look at the deployed decision service archive.

## Step 1: Creating a Jenkins build plan

In this step, you create a plan in Jenkins to build and deploy an archive that is based on your decision service. 
You must have the URL and security credentials for Jenkins.

**Procedure**

1. Log in to Jenkins with your credentials.
2. Click **New Item** to create a build plan.
3. Enter `Loan Validation` as the name, and the following description:
`Build plan to build and deploy the Loan Validation decision service archive.`
5. In the Source Code Management part, select **Git**, and enter the URI and your security credentials for the Git repository.
6. In the Build Triggers part, choose the appropriate trigger policy. For example, select **Poll SCM** and enter `* * * * *` in the Schedule part. This policy produces a check for modifications in the Git repository every minute. This one-minute delay is for this example. A different delay time can be selected for a real application.
7. Update the Build part by adding the following information:
- Root POM: `Loan Validation/pom.xml`
- Goals and options: `clean deploy -U`
8. Click **Save** to save the plan.

## Step 2: Building the Jenkins build plan

In this step, you run the Jenkins build plan to build and deploy the decision service archive.

**Procedure**

1. In Jenkins, select the Maven project **Loan Validation**.
2. Run the plan by clicking **Build now** in the left part. When the build finishes, the log shows that the decision service archive is deployed:
```
[INFO] Uploaded to maven-snapshots: <archiverepo URL>/repository/maven-snapshots/decisions/my_project/loan_validation/loanValidationDecisionService/maven-metadata.xml
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] loanValidation 1.0.0-SNAPSHOT ...................... SUCCESS [ 29.653 s]
[INFO] loanValidationData LATEST-SNAPSHOT ................. SUCCESS [ 50.416 s]
[INFO] loanValidationDecisionModel LATEST-SNAPSHOT ........ SUCCESS [ 51.337 s]
[INFO] loanValidationDecisionService 1.0.0-SNAPSHOT ....... SUCCESS [ 55.626 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
... 
Finished: SUCCESS 
```

## Step 3: Looking at the decision service archive

In this step, you get the decision ID of your decision service archive from the runtime archive repository.
You need to have the URL of your Nexus archive repository.

**Procedure**

1. Open the runtime archive repository.
2. Click **Browse** in the left part and select **maven-snaphots**.
2. Expand **decisions** to find the deployed decision service archive. Its path name starts with the name of the decision project, for instance, `my_project`.
3. Expand all the subfolders in the decision service archive, for example:
```
decisions/my_project/loan_validation/loanValidationDecisionService/1.0.0-SNAPSHOT/loanValidationDecisionService-1.0.0-<timestamp>-1.jar
```

This path is called the decision ID, and it is used to run the decision service archive. Copy the part beginning with  `decisions`  to run the decision service archive in the next step.

# Task 4: Executing the decision service archive
**About this task**

In this task, you... 
- Use the Swagger REST API tool to validate the decision service archive.
- Modify the decision service in Decision Designer.
- Uses the Swagger REST API tool to validate the decision service archive changes.

## Step 1: Validating the decision service archive in Swagger

In this step, you use the Swagger REST API tool to validate the decision service archive.  For more information, see [Swagger UI](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/topics/con_swagger_ui.html).
To run the archive, you must have the URL and security credentials for the Swagger REST API tool.

**Procedure**

1. Open the Swagger REST API tool.
2. In the Decision runtime part, open the `POST /decision/{decisionId}/operations/{operation} Execute decision` method.
3. Click **Try it out**, and enter the decision ID from the previous step.
4. Enter `loanValidationDecisionModel` for the operation.
5. Edit the Request body to enter the following input data:
```
  "input": {
             "loan": {
               "number of monthly payments": 72,
               "start date": "2020-12-20T00:00:00Z",
               "amount": 185000,
               "loan to value": 0.7
             },
             "borrower": {
               "first name": "John",
               "last name": "Doe",
               "birth date": "1968-05-12T00:00:00Z",
               "SSN": {
                 "area number": "123",
                 "group code": "45",
                 "serial number": "6789"
               },
               "yearly income": 100000,
               "zip code": "91320",
               "credit score": 500
             },
             "currentTime": "2020-12-07T16:43:04.609Z"
           }
```

**Tip:**
You can get the input as JSON data in Decision Designer. In the **Run** tab in the decision model, click the three points next to the name of the data set and select **Edit as JSON**. Then, copy the JSON code.

6. Click **Execute** to run the archive. Security authentication might be required to complete this operation.
The return code is `200`, and the response body shows the following output:
`Congratulations! Your loan has been approved`

## Step 2: Adding changes to a shared decision service
In this step, you update a rule in your model. Then, you share the changes and check the build of the new version of the decision service archive.

**Procedure**

1. In Decision Designer, open **Loan Validation Decision Model**.
2. Select the Approval node, and open the `age not valid` rule in the Logic tab. In the definitions part, change minAge to 18 and maxAge to 100.
```
definitions
    set 'minAge' to 18;
    set 'maxAge' to 100;
```
3. Open the **Run** tab and select the dataset `MrDoeLoan`. Change the year of birth to 1910.
4. Click **Run**. You get the following answer:
`The borrower's age is not valid.`

## Step 3: Sharing and validating the changes 
In this step, you  share the changes and check the build of the new version of the decision service archive.

**Procedure**

1. Click **My Project** in the breadcrumbs to open your project.
2. Open the **Share changes** tab. You see the changes that you can share. The Loan Validation decision service is selected.
3. Click **Share**. Enter the following comment, and then click **Share**.
`Update limits in the age not valid rule.`
4. Open Jenkins and check that the `Loan Validation` plan runs. Changes are checked every minute by the poll policy set earlier in for the tutorial.
5. Open the recent changes. You see your commit message, and the details in the files for the changed rule and the data set.
6. Wait for the build finish and look for the new version of the decision service archive in Nexus. Copy the new decision ID.
7. Open the Swagger REST API tool as shown in Step 1, and run the following input data:
```
  "input": {
             "loan": {
               "number of monthly payments": 72,
               "start date": "2020-12-20T00:00:00Z",
               "amount": 185000,
               "loan to value": 0.7
             },
             "borrower": {
               "first name": "John",
               "last name": "Doe",
               "birth date": "1910-05-12T00:00:00Z",
               "SSN": {
                 "area number": "123",
                 "group code": "45",
                 "serial number": "6789"
               },
               "yearly income": 100000,
               "zip code": "91320",
               "credit score": 500
             },
             "currentTime": "2020-12-07T16:43:04.609Z"
           }
```

The return code is `200`, and the response body shows the following output:
`The borrower's age is not valid.`
