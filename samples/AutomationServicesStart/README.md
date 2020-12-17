# Tutorial: Using a decision service in an automation service 

## Description

This tutorial shows you how to use a decision service in an automation service.

## Learning objectives
- Deploy a decision service archive to the embedded runtime.
- Deploy an automation service.
- Build and run an application that calls a decision service archive.
 
## Audience

This tutorial is for business users who want to run an application that uses a decision service built in Automation Decision Services.
Some knowledge of IBM Business Automation Studio is required.

## Time required

10 minutes

## Prerequisites

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision services in IBM Business Automation Studio. You work with a sample decision service that you import into a decision project and open in Decision Designer.
- **Application Designer**:  A web-based user interface for developing applications in Business Automation Studio. 

# Task 1: Creating and deploying a decision service
**About this task**

In this task, you... 
- Create a new project.
- Import a sample.
- Deploy your project.
  
 ## Step 1: Creating a new project
 In this step, you create a new project.
 
 **Procedure**
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the security credentials that were given to you for this instance.
2. Click the **Automations** tile.
3. Click **Create** and select **Decision automations** to make a project.
4. Enter a unique name for the project. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. For simplicity, we use `My Project` in this tutorial. After entering your name for the decision project, enter the following description:
```
Automated Decision Services sample to use in an application.
```
5. Click **Create** to make your decision project.

## Step 2: Importing a sample decision service
In this step, you import the decision service that you later deploy. You import the Getting started decision service, which provides advice based on the weather.

**Procedure**

1. In the **Decision services** tab, click **Browse samples**.
2. Choose **Getting started** in the Discovery part, and click **Import** to import the decision service.
3. Click **Getting started** to open the decision service after it is imported. It contains a data model and five decision models. Each decision model corresponds to a task in the getting started tutorial.
4. Click the **Daily advice** decision model to open it, and explore the diagram. It is the final version of the getting started tutorial.
5. Click the **Run** tab and run the provided test data to see how the decision service works.

## Step 3: Deploying your decision service

In this step, you deploy your decision service within your instance of Business Automation Studio. The instructions are summarized, but you can find more detailed instructions in the 
[Getting started in Automation Decision Services Task5 Step 1 to Step5](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/tut_dd_t5_lsn.html).

**Procedure**

1. Create a repository in GitHub, and get its URL and the credentials to access it.
2. In Decision Designer, click the name of your project in the breadcrumbs.
3. Open the **Share changes** tab. You see the changes that you can share. The Getting started decision service is selected.
4. Click **Share**. Enter the following comment, and then click **Share**:
```
Getting started first version`
```
5. Open the **View history** tab. It shows the commit that you just made. To the right of it, click **Version +**.
Enter the name `FirstVersion` and click the **Create** button. 
6. Click **Connect** in the upper right corner of Decision Designer. Then, enter the GitHub repository URI and credentials, and click **Connect**.
7. Click the name of your project in the breadcrumbs, open the **Deploy** tab, and expand FirstVersion. Click **Deploy** to the right of the Getting started decision service.
Wait for the decision service to be deployed to the embedded runtime archive repository. Now, you make the decision service available as an automation service.
8. Click **Projects** in the breadcrumbs to go back to the list of projects.

# Task 2: Publishing and using a decision service in an application
**About this task**

In this task, you... 
- Publish your version of the decision service.
- Create a new application.
- Add a form to call your decision service in the application.
- Test the application.


## Step 1: Publishing a version of the decision service

In this step, you publish the first version of the decision service archive to use it in an application.

**Procedure**

1. In the list of projects in Business Automation Studio, click inside the box of your project, `My Project`. Do not open it in Decision Designer.
2. In the **Versions** tab, click the three dots at the end of the row for FirstVersion and select **Publish**. 
3. Close the window that confirms the deployment of FirstVersion to the runtime environment. 
4. Open the **Automation services** tab. It shows the first version of the getting started tutorial.

## Step 2: Creating an application

In this step, you create an application. 

**Procedure**

1. In Business Automation Studio, click the menu in the top left corner and select **Applications**.
2. Click **Create** and select **Application**.
3. Enter a unique name such as `My Application`. Then, enter the following purpose and click **Create**:
```
Application for the Daily advice decision service
```
The new application opens in Application Designer.

## Step 3: Adding a form to run your decision service archive

In this step, you add a generated form to your application to run your decision service archive.

**Procedure**

1. On the right of Application Designer, in the `Drag a component to your page` section, click **All views** and select **Automation service** in the menu.
2. Click the **Add +** button and wait for the `Add an automation service` wizard to be filled with all the published decision services.
3. Enter **Getting started** in the search field and look for the decision service that you just deployed. There might be several decision services with the same name if your collaborators have already used this tutorial.
4. Select **Getting started** in the list. You see the First Version of the list of operations that can be used.
5. Select **dailyAdvice** and click **Next**.
6. A component named `Getting started` is added to the list. Select it and drag it into the middle page. Note that operation dailyAdvice is selected, and that a whole form is proposed.
7. Click **Done**. The application now contains a form ready for running your decision service archive.

## Step 4: Running the decision service archive in your application

In this step, you run the decision service archive with input data that is entered in the form.

**Procedure**

1. Click **Preview** in the top right of the view.
2. In the Preview window, enter the following data:
- Name: Jamie
- Rain forecast: 90
- Check the Storm Alert box.
- Temperature: cold.
3. Click the **Launch service** button. You get the following message from running of the decision service archive:
```
Hello Jamie! It would be wise to stay home. There is a storm alert.
```
