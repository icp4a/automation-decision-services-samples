# Tutorial: Using a CI/CD stack to build and deploy a decision service

## Description

This tutorial shows you how to use the custom CI/CD stack to build and deploy a decision service archive.

## Learning objectives
- Connect a decision project to a Gitea repository.
- Build a decision service archive by using Maven.
- Deploy a decision service archive to the Automation Decision Services runtime by using a curl command.
- Generate a decision by using a Swagger UI tool.

  
## Audience

This tutorial is for technical and business users who want to understand the usage of the custom CI/CD stack in Automation Decision Services.
Some knowledge of the CI/CD stack is required.

## Time required

15 minutes

## Prerequisites

Prepare by doing the [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html) tutorial. It introduces you to Automation Decision Services.

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision services in Business Automation Studio. You work with a sample decision service by importing it into a decision project and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a CI/CD stack to run the decision service. They must give you the URLs and credentials for both the Gitea repository and the Swagger UI of the Automation Decision Services runtime service.


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
2. Click the navigation menu at the top left of the page. Expand **Design** and select **Business automations**.
3. Click **Decision** to see the decision automations.
4. Click **Create** and select **Decision automations** to make a project.
5. Enter a unique name for the project. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. In this tutorial we use `My Project` as the name of the project. After entering your name for the decision project, enter the following description:
```
Automated Decision Service sample deployed by using the CI/CD stack.
```
6. Click **Create** to make your decision project.

## Step 2: Importing sample projects
In this step, you import the decision project that you deploy later in this tutorial. You import the Loan Validation decision service, which validates loans by using data from the borrower, and computes insurance rates for the requested loan amount.

**Procedure**

1. In the **Decision services** tab, click **Browse samples**.
2. Choose **Banking** in the Business domains section, and click **Import** to import the decision service.
3. Click **Loan Validation** to open the decision service. It contains a decision model, a data model, and a decision operation.
4. Click the **Decision operations** tab. One operation is defined. Copy its name, `loan-validation-decision-model`, to use it to run the decision service archive.
5. Click the **Modeling** tab, and click **Loan Validation Decision Model** to open the model and explore its diagram.
6. Click the **Run** tab. Four data sets are defined. Open the `MrDoeLoan` data set and edit it as .json code to copy its contents:
```
{
  "loan": {
    "number of monthly payments": 72,
    "start date": "2005-06-01T00:00:00Z",
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
  "currentTime": "2020-02-03T16:43:04.609Z"
}
```
7. Run the data set. You get the following response:
```
{
  "approved": true,
  "message": "Congratulations! Your loan has been approved"
}
```
## Step 3: Sharing your decision service

In this step, you share your decision service in your instance of Automation Decision Services.

**Procedure**

1. Click the name of your project in the breadcrumbs.
2. Open the **Share changes** tab. It shows the changes that you can share. 
3. Click **Share**, and enter the comment `Loan Validation first version`.
4. Click **Share**. Now, your collaborators in your instance of Automation Decision Services can see your decision service.

# Task 2: Connecting to a  Gitea repository
**About this task**

In this task, you... 
- Create a Gitea repository.
- Connect your decision project to the repository.

You must have the URI and security credentials for the Git. 

## Step 1: Creating a Gitea repository

In this step, you create a Gitea repository. Your IT developers must give you a URI and security credentials for the repository. 

**Procedure**

1. Log in to Gitea by using your security credentials.
2. Click the **+** button, and select **New Repository** in the menu that opens.
3. Enter a unique name and a description. For this example, we use `My Project` and the description `ADS project using the custom CI/CD stack`.
4. Click the **Create Repository** button.
5. If you are logged in to Gitea as the user of the Decision Designer specified at installation time, you can skip the following steps:<br /> 
    a. Click **Settings**, and select the **Collaborators** tab.<br />
    b. Enter the user's name as a Git user, and then click the **Add Collaborator** button.
6. In the **Code** tab, click the **Copy** button to get the URL of the Git repository, which you use in the next step.

## Step 2: Connecting your project to the Git repository

In this step, you connect your project to a Git repository in Decision Designer. 

**Procedure**

1. Check the status of your Git repository connection in the upper right corner of Decision Designer. The broken link icon shows that the project is not connected to a remote Git repository.
2. Click **Connect** to make a connection.
3. Enter the Git URI and security credentials from step 1, and then click **Connect**. A message tells you when the connection completes successfully. Your decision service is written to the repository when you connect to it.
4. Click **My Project** in the breadcrumbs to return to your service. The link icon shows that your project is now connected to the Git repository.
5. Refresh your Gitea page to see the Loan Validation decision service in your repository.


# Task 3: Building the decision service archive and deploying it
**About this task**

In this task, you...
- Build the decision service archive with Maven.
- Deploy the decision service archive to the Automation Decision Services runtime.

## Step 1: Configuring the Maven settings to build your decision service archive

If your Maven settings are configured to access the Automation Decision Services artifacts, you can skip this step.
Otherwise, you must define a file of Maven settings by completing the template *settings.xml*, which is included in this sample:
1. Open **automation-decision-services-samples/samples/UseCICDStack/settings.xml**.
2. Replace all the `TO BE SET` tags by entering the appropriate values:
   * `LOCAL REPOSITORY TO BE SET`: The directory on your computer to which the artifacts are downloaded.

   * `MAVEN SNAPSHOT TO BE SET`: The URL of the repository for manager snapshots.
   * `MAVEN RELEASE TO BE SET`: The URL of the manager release repository.
   * `MAVEN PUBLIC TO BE SET`: The URL of the manager public repository.
   * `USER TO BE SET`: A username that can access the repository manager.
   * `PASSWORD TO BE SET`: The password that corresponds with the username.
3. Save the `settings.xml` file.

## Step 2: Building the decision service archive

In this step, you build the decision service archive with Maven. 

**Procedure**

1. Clone the Gitea repository defined in Task 2 on your computer.
2. Copy the file `settings.xml` defined in Step 1 in the `Loan Validation` directory.
3. Run the following command in the `Loan Validation` directory: 
```
mvn clean install -s settings.xml
```
This command builds a decision service archive that is ready to run your decision service. Named `loanValidationDecisionService-1.0.0-SNAPSHOT.jar`, the archive is created in the `Loan Validation/.decisionservice/target/` directory.

## Step 3: Deploying the archive to your space ID.

In this step, you deploy the decision service archive to the Automation Decision Services runtime in a deployment space that is dedicated to samples. The runtime manages several deployment spaces, which are created the first time they are used.
You use a curl command instead of the Swagger UI tool because it can be easily integrated into a script.
Your IT developers must give you a URI for the runtime, and security credentials to manage the runtime. 

**Procedure**

In the following curl command, replace all the variables with the appropriate values:<br />
   * `USERNAME` and `PASSWORD`: The credentials for accessing the runtime.<br />
   * `FILEPATH`: The full path to the generated archive.<br />
   * `URL`: The URL to transfer the archive. It has the following pattern: `https://HOSTNAME/ads/runtime/api/v1/deploymentSpaces/ADSsample/decisions/loanValidationDecisionModel/archive`.
The URL uses the following variables:
      * `HOSTNAME`: Replace this placeholder with the name of the host that is running Automation Decision Services runtime.
      * `ADSsample`: Used to give the name of the deployment space. You can use a different name. The runtime manages several deployment spaces that are created the first time they are used.
      * `loanValidationDecisionModel`: Shows the decision ID of the decision service archive.
      
The command looks as follows:

```
curl -u USERNAME:PASSWORD -vi -k -X POST -H "accept: */*" -H "Content-Type: application/octet-stream" \
     --data-binary @FILEPATH \
     URL
```

After modifying the command, run it. When you do, you get the following response:
```
...
* We are completely uploaded and fine
< HTTP/1.1 200 OK
...
```
The archive has been deployed. In the next task, you run it by using the Swagger UI tool.

# Task 4: Running the decision service archive
**About this task**

In this task, you use the Swagger REST API tool to run your decision service archive. You must have its URL and the credentials to the runtime for running a decision service archive. For more information, see [User roles and authentication modes](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/topics/con_user_roles.html).

**Procedure**

1. Open the Swagger UI for the runtime.
2. Click **Authorize** and enter your credentials to be able to run the decision service.
3. In the Decision runtime part, expand `GET /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/execute`, enter the following input values:
   - deploymentSpaceId: embedded
   - decisionID: `loanValidationDecisionModel`.
   - operation: `loan-validation-decision-model`: you got it in Task 1 Step 2.
   - Use the schema you copied in Task 1 in the request body:
```
{
  "loan": {
    "number of monthly payments": 72,
    "start date": "2005-06-01T00:00:00Z",
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
  "currentTime": "2020-02-03T16:43:04.609Z"
}
```
4. Click **Execute**. 
The server returns the 200 response code, and the response body shows the following output:

```
{
  "insurance": {
    "rate": 0.006,
    "required": true
  },
  "approval": {
    "approved": true,
    "message": "Congratulations! Your loan has been approved"
  }
}
```
