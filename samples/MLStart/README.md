# Tutorial: Using machine learning to make better decisions

## Description

This tutorial shows you how to use a machine learning model in a decision project that validates loans. The project uses a decision model to apply policies and incorporate a risk prediction.

In IBM Automation Decision Services, you can create predictive models and use them in decision projects. A predictive model can combine machine learning and data from your company's past decisions to determine the possible results of new decisions.  

Each decision project has at least one decision model to map a process for applying policies and data to produce a business decision. By adding a predictive model to a decision model, you can produce more accurate and comprehensive business decisions. 

For more information on decision models and machine learning, see [Modeling decisions](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/topics/con_modeling.html) and [Integrating machine learning](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/ml_topics/con_integrate_ml.html).

## Learning objectives

   - Import a decision project into Decision Designer, the decision project editor in Automation Decision Services.
   - Create and validate a predictive model that uses a machine learning model.
   - Explore and validate the usage of the predictive model in a decision model.
   - (Optional) Build a decision service archive and run it with a CI/CD stack.

## Audience

This sample is for technical and business users who want to apply predictive analytics through machine learning in decision projects in Automation Decision Services. It also shows data scientists and data engineers how Automation Decision Services can be used to apply machine learning models in decision-making applications.

## Time required

20 minutes

## Prerequisites

Prepare with the following resources:
- [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html): This tutorial introduces you to Automation Decision Services.
- [Watson Machine Learning](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/ml-overview.html?audience=wdp&context=wdp): This service lets you build analytical models and neural networks for use in applications. 

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision projects in IBM Business Automation Studio. You work with the sample decision project by importing it into a decision solution and opening it in Decision Designer.
- **Watson Machine Learning provider**: To deploy the pmml file that is included in this sample, you must have access to a Watson Machine Learning provider on IBM Cloud. Your IT developers must add the provider to your Automation Decision Services installation (see [Managing machine learning providers](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/ml_topics/tsk_manage_providers.html)).
- **(Optional) Deployment services**: For the optional part of this sample, your IT developers must provide a CI/CD stack to run the decision service. They must give you the URL to a Git repository in which you can store the decision project. They must also provide a build plan to create a decision service archive, and make the plan available to the runtime environment that uses the decision service.

# Task 1 : Setting up the sample

You use Watson Studio and Decision Designer to set up the sample.

**About this task**

In this task, you...
- Deploy a Watson Machine Learning model.
- Define the machine learning provider in an Automation Decision Services installation.
- Import the sample files into a decision solution.

First, download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
This tutorial refers to **automation-decision-services-samples** as the directory where you decompress the repository.

## Step 1: Deploying the Watson Machine Learning model in Watson Studio
You create and deploy the machine learning model, and your IT developers add a machine learning provider. Knowledge of Watson Studio is helpful.

**Procedure**

1. Log in to Watson Studio and create a new project.
2. Add a new Watson Machine Learning model to your project:

   a. Name it **Loan Risk Score**, and select your **Machine Learning Service**. 
   
   b. Select **From file** in the model type and browse to **automation-decision-services-samples/samples/MLStart/model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml**.
   
3. Deploy the new model with the name **loan_risk_score**. For more information, see [Move your Machine Learning insights into production](https://developer.ibm.com/tutorials/ml-into-production/).

## Step 2: Adding a new machine learning provider to Automation Decision Services

Ask your IT developers to add a new machine learning provider named,for example, **ml-sample** (see [Managing machine learning providers](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/ml_topics/tsk_manage_providers.html)).

## Step 3: Importing the sample decision project in Decision Designer

The decision project in this sample applies several criteria in determining a borrower’s eligibility for a loan. One of the key factors is risk, and to assess it, the project uses a predictive model. The deployed machine learning model, **loan_risk_score**, is called by the predictive model, and returns a prediction as to the riskiness of a loan. When a loan is too risky, the decision project rejects it. However, when a loan is accepted, its risk assessment from the predictive model is used in computing the annual interest rate for the loan. 

You import the sample decision project into a decision solution. 

**Procedure**

1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. In Decision services, click **View all**.
3. Click **Create** to create a decision solution.
4. Enter a name for the solution. Use a unique name. Do not reuse the name of a decision solution that already exists in your instance of Business Automation Studio. For simplicity, we use **ML Loan Sample** in this sample documentation. After entering your name for the decision solution, enter the following description:
**Automated Decision Service sample integrating ML prediction in a Loan Validation decision.**
5. Keep **Open in Designer** selected, and then click **Create** to make your decision solution.
6. Click **Import** in the solution, and then select **Import from your file system**. Browse to the downloaded file **automation-decision-services-samples/samples/MLStart/project/ml-sample.zip** and select it.
7. Click **Import** to import the file into your decision solution.
8. In your decision solution, click the **ML-based Loan Approval** sample decision project to open it in Decision Designer.
Take some time to browse through the data model and the decision model. The decision model decides if a loan can be given to a borrower. It already takes as input a loan risk score, which is later replaced by the prediction of the machine learning model.
9. Click **ML-based Loan Approval** in the breadcrumbs to go back to the decision project.


# Task 2: Adding the predictive Watson Machine Learning model to the sample decision project

You work in Decision Designer to define a predictive model.

**About this task**

In this task, you...
- Create a predictive model in your decision solution.
- Edit its input and output mapping to fit to the data model. 
- Validate the results.

## Step 1: Creating a predictive model

You create a predictive model in your decision project to encapsulate your machine learning model.

**Procedure**

1. Open the **Predictive models** tab, and then click **Create** to make your predictive model.
2. Select the provider where your model deployment is stored. It should be **ml-sample**.
3. Select the deployment that you created to generate the predictive model. It should be **loan_risk_score** under the machine learning model name **Loan Risk Score**.
4. Click **Next** to define the model input schema. It should already be complete, so no action is required.
5. Click **Next** to define the test invocation. Click **Run** at the bottom of the wizard to validate the model. You get the following output:
```json
{
    "fields": [
        "probability_no_paymentDefault",
        "probability_paymentDefault"
    ],
    "values": [
        [
            0.986491163897098,
            0.013508836102901989
        ]
    ]
}
```
6. Click **Next** to define the model output schema. It should already be complete, so no action is required.
7. Click **Create** to create the predictive model.

## Step 2: Editing the input and output mapping.

You edit the input and output mapping of your predictive model to use the decision project data model.

**Procedure**

1. Define the input nodes:
    
    a. Click the **Input type**. Change its name to **Loan** and its output type to **loan**.
    
    b. Hover over the **Loan** node and duplicate it. Change its name to **Borrower** and set its output type to **borrower**.
    
2. Define the input mapping rule:
    
    a. Click the **Input Mapping Node**. Go to the **Logic** tab and add a new rule. Name it **input mapping rule**. You do not need to select any criteria for this rule. Click **Create**.
    
    b. In the rule content, enter the following code:
    ```
    definitions
    set 'duration' to the number of monthly payments of Loan ;
    set 'rate' to the rate of Loan ;
    set 'yearlyreimbursement' to  'rate'  * the amount of Loan  / (1 - pow ( 1 + 'rate' , -duration));
    then
        set decision to a new ML model input where
            the creditscore is the credit score of Borrower , 
            the income is the yearly income of Borrower , 
            the loanamount is the amount of Loan , 
            the monthduration is the number of monthly payments of Loan , 
            the rate is 'rate' , 
            the yearlyreimbursement is 'yearlyreimbursement' ;
    ```
    
    c. Close the editor.
    
3. Define the output mapping rule:    
    
    a. Select the **Output Mapping Node**. In the Details tab, set its type to **risk probabilities**.
    
    b. Go to the **Logic** tab, and add a new rule. Name it **output mapping rule**. You do not need to select any criteria for this rule. Click **Create**.
    
    c.  In the rule content, enter the following code:
    ```
    set decision to a new risk probabilities where
        the no risk is the probability no paymentdefault of 'ML model invocation' ,
        the risk is the probability paymentdefault of 'ML model invocation' ;
    ```
    
    d. Close the editor.
    ![Image shows the predictive model.](images/scrn_pmodel.png)

## Step 3: Validating the predictive model.
You add and run data sets to validate your predictive model.

**Procedure**

1. Go to the **Validation** tab.
2. Click the **+** button to add a new test scenario. Change its name to **John Good Score**.
3. Edit its content as JSON. Enter the following data:
    ```json
    {
      "loan": {
        "rate": 0.7,
        "number of monthly payments": 72,
        "amount": 185000
      },
      "borrower": {
        "credit score": 750,
        "yearly income": 1000000,
        "first name": "John"
      }
    }
    ```
 4. Click **Run** to execute your predictive model. The Result shows that there is no risk.
 5. Click the **+** button to add another test scenario. You can change the name to **Paul Bad Score** and enter the data as JSON:
    ```json
    {
      "borrower": {
        "credit score": 100,
        "yearly income": 100000,
        "first name": "Paul"
      },
      "loan": {
        "number of monthly payments": 72,
        "amount": 185000,
        "rate": 0.7
      }
    }
    ```
6. Click **Run** to validate your predictive model. The Result shows that there is a risk.

# Task 3: Using the predictive model in the decision model

You work in Decision Designer to modify the decision model.

**About this task**

In this task, you...
- Edit the decision model to replace a user input node with a function node that calls your predictive model.
The computed prediction value is stored in a variable so that it can be used in several rules. 
- Validate your decision model.

**Procedure**

1. In the breadcrumbs, click **ML-based Loan Approval** to go back to the project view. Click the decision model **Loan Validation Decision Model**.
2. Remove the **Loan Risk Score** node.
3. Add a new function node. Click **Select a model** to select the **Loan Risk Score** predictive model.
4. Connect the new function node to the **Risk score** node.
5. Select the **Risk Score Node**. Go to the **Logic** tab and edit the **loan risk score** rule. Enter the following rule:
    ```
    set decision to the loan risk score computed from 
    Borrower being Borrower , 
    Loan being Loan; 
    ```
 Here is the new model:
    ![Image shows the decision model.](images/scrn_dmodel.png)

6. Validate your decision model. Go to the **Validation** tab and run both the 'John Good Score' and 'Paul Bad Score' tests.

# (Optional) Task 4: Publishing the project and running the decision service

You use a CI/CD stack to build and deploy your decision service. Your IT developers must provide a URL for an empty Git repository to which the user in the Automation Decision Services configuration has access rights. For more information, see task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html).

**About this task**

In this task, you...
- Publish the **ML-based Loan Approval** decision project to a Git repository. 
- Ask your IT developers to build and deploy your decision service.
- Ask your IT developers to configure the machine learning provider in the decision runtime.
- Run the decision service in the Swagger UI tool.

**Procedure**

1. Ask your IT developers to configure the machine learning provider in the decision runtime (see [Configuring the decision runtime](http://cisdev01w.fr.eurolabs.ibm.com:9190/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.install/op_topics/tsk_adsconfig_dr.html)).
2.	In Decision Designer, click the name of your solution, **ML Loan Sample**, in the breadcrumbs to edit your solution. 
3. Connect your decision solution to the Git repository that is provided by your IT developers. 
4.	In the **Publish changes** tab, select the **ML-based Loan Approval** decision project.
5. Click **Publish** to publish your changes to the Git repository.
6.	 Ask your IT developers to make a build plan to build and deploy your decision service as described in task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html).
7.	After building and deploying the decision service, run it in the Swagger UI tool (see [Executing decision services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/topics/con_execute.html)): 
    
    a. Get the decision ID of your decision service from the runtime archive repository.
    
    b. Enter **loanValidationDecisionModel** as the operation name.
    
    c. Enter the following input parameters for an approved loan:
```json
{
    "input": {
        "loan": {
            "number of monthly payments": 72,
            "start date": "2020-06-01",
            "amount": 185000,
            "rate": 0.7
        },
        "borrower": {
            "first name": "John",
            "last name": "Doe",
            "birth date": "1968-05-12",
            "SSN": {
                "area number": "123",
                "group code": "45",
                "serial number": "6789"
            },
            "yearly income": 1000000,
            "zip code": "91320",
            "credit score": 750
        },
        "currentTime": "2020-02-03"
    }
}
```

You get the following response:

```json
"output": {
    "insurance": {
      "required": true,
      "rate": 0.0025
    },
    "approval": {
      "approved": true,
      "message": "Congratulations! Your loan has been approved"
    }
  }
  ```
