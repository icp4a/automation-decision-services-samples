# Tutorial: Using a decision service in an automation service 

## Description

This tutorial shows you how to use a decision service in an automation service. It requires an installation of Automation Decision Services on the platform IBM Cloud Pak for Business Automation.

## Learning objectives
- Deploy a decision service archive to the embedded runtime.
- Publish an automation service ready to be used in Business Automation components.
- Build and run in Application Designer an application  executing an automation service.
 
## Audience

This tutorial is for business users who want to run an application that uses a decision service built in Automation Decision Services.
Some knowledge of Business Automation Studio is required.

## Time required

15 minutes

## Prerequisites

You must have access to  the following environments in Business Automation Studio:
- **Decision Designer**: A web-based user interface for developing decision services in Business Automation Studio. You work with a sample decision service that you import into a project and open in Decision Designer.
- **Application Designer**:  A web-based user interface for developing applications in Business Automation Studio.
 
If you do not have access to these environments, see [Managing access to Business Automation Studio projects documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/23.0.1?topic=services-managing-access-decision-automations).
For more information about Business Automation Studio and the designers it integrates, see the [Business Automation Studio documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/23.0.1?topic=cpbaf-business-automation-studio).
# Task 1: Creating and deploying a decision service
**About this task**

In this task, you... 
- Create a new project.
- Import a sample.
- Deploy your decision service archive.
  
 ## Step 1: Creating a new project
 In this step, you create a new project.
 
 **Procedure**
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the security credentials that were given to you for this instance.
2. Click on the navigation menu at the top left of the page, expand Design and select **Business automations**.
3. Select Decision in the list.
4. Click **Create** and select **Decision automations** to create a project.
5. Enter a unique name for the project. Do not reuse the name of a project that already exists in your instance of Business Automation Studio. For simplicity, we use `My Project` in this tutorial. After entering your name for the project, enter the following description:
```
Automated Decision Services sample to use in an application.
```
5. Click **Create** to create your project.

## Step 2: Importing a sample decision service
In this step, you import the decision service that you later deploy. You import the Getting started decision service, which provides advice based on the weather.

**Procedure**

1. In the **Decision services** tab, click **New decision**.
2. Choose **Discovery tutorials** in the left panel. Then, select **Getting started** and click **Import** to import the decision service.
3. Click **Getting started** to open the decision service after it is imported. It contains a data model and six decision models. Each decision model corresponds to a task in the getting started tutorial.
4. Click the **Daily advice** decision model to open it, and explore the diagram. It is the final version of the getting started tutorial.
5. Click the **Run** tab and run the provided test data to see how the decision service works.
6. Click on **Getting started** in the breadcrumbs and select the **Decision operations** tab. There is one operation `daily-advice`. Click on its name to explore its parameters.

## Step 3: Deploying your decision service

In this step, you deploy your decision service within your instance of Automation Decision Services. The instructions are summarized, but you can find more detailed instructions in the
[Getting started in Automation Decision Services Task 2](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/23.0.1?topic=gs-task-2-connecting-git-repository-sharing-decision-service).
If Decision Designer is configured to automatically create a Git repository to be connected to, you can use this git repo and skip points 1,2 and 3 in the following procedure. See [Connecting to a remote repository automatically](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/23.0.1?topic=administering-connecting-remote-repository-automatically).

**Procedure**

1. Create a repository in GitHub, and get its URL and the credentials to access it.
2. In Decision Designer, click the name of your project in the breadcrumbs.
3. Click **Connect** in the upper right corner of Decision Designer. Then, enter the GitHub repository URI and credentials, and click **Connect**.
4. Click the name of your project in the breadcrumbs.
5. Open the **Share changes** tab. You see the changes that you can share. The Getting started decision service is selected.
6. Click **Share**. Enter the following comment, and then click **Share**:
```
Getting started first version
```

7. Open the **Deploy** tab.
8. Click on Create version. Enter the name FirstVersion and click the Create button.
9. Expand FirstVersion. Click **Deploy** to the right of the Getting started decision service.
Click Deploy in the confirmation window, and wait for the decision service to be deployed to the embedded runtime archive repository. 
10. Click **Business Automations** in the breadcrumbs to go back to the list of projects.

The next step is to make the decision service archive available as an automation service.

# Task 2: Publishing and using an automation service in an application
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
3. Click on **Publish** in the **Publish automation services** view. Wait for the service to be published.
4. Click on the arrow at the top left of the screen to go back in the **Business automations** page. Click **Published automation services** tab. It shows the list of automation services.
5. Click on **Getting started** to see the service you just deployed. You see the available operation `daily-advice`.

## Step 2: Creating an application

In this step, you create an application. 

**Procedure**

1. In Business Automation Studio, click the menu in the top left corner, expand Design and select **Business applications**. Discover the explore and go menu.
2. Click **Create** and select **Application**.
3. Enter a unique name such as `My Application`. Do not select any template. Then, enter the following purpose and click **Create**:
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
4. Select **Getting started** in the list, the one that has just been deployed. Click on **Add(1)**. Note that the number `1` in parenthesis is the number of available operations in the decision service.
5. A component named `Getting started` is added to the list. Select it and drag it into the middle page. Note that operation dailyAdvice is selected, and that a whole form is proposed.
7. Click **Done**. The application now contains a form ready for running your decision service archive.

## Step 4: Running the decision service archive in your application

In this step, you run the decision service archive with input data that you enter in the form.

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
