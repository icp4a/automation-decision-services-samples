# Tutorial Complete: Using machine learning to make better decisions

## Description


This tutorial shows you how to create a Predictive Model (PM) in Automation Decision Service, connect it to a Watson Machine Learning provider, use it in a decision service that validates loans, deploy the service and execute it in Swagger UI. The decision service uses a decision model to apply policies and incorporate a risk prediction. These tutorial tasks are illustrated in the following diagram.

![Image summarize the tutorial steps](images/MLCompleteTutorial.png)

For more information on decision models and machine learning, see [Modeling decisions](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/topics/con_modeling.html) and [Integrating machine learning](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/ml_topics/con_integrate_ml.html).

For more tutorials about machine learning in Automation Decision Services see:
   - [Machine learning quick tutorial](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningQuickTutorial) to learn how to import a Machine Learning Model as PMML file in an Automation Decision Services Machine Learning Service and how to connect it to a predictive model.
   - [Machine learning short tutorial](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningShortTutorial) to learn how to connect a predictive model to a Watson Machine Learning Model. 
   - [Machine learning sample](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MLDatasets) to get another example using two predictive models.
   - [Machine learning notebooks](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MLNotebooks) to get another example of a notebook ready to be connected to a predictive model.

## Learning objectives

   - Build a Watson Machine Learning (WML) model using a PMML file or a notebook, deploy this model to Watson Machine Learning.
   - Create a WML provider in Decision Designer.
   - Create a predictive model and connect it to a machine learning deployment.
   - Use this predictive model in a decision model.
   - Deploy a decision service using this predictive model.
   - Execute a decision using Swagger UI. 

## Audience

This sample is for technical and business users who want to apply predictive analytics through machine learning in decision projects in Automation Decision Services. It also shows data scientists and data engineers how Automation Decision Services can be used to apply machine learning models in decision-making applications.

## Time required

40 minutes

## Prerequisites

Prepare with the following resources:
- [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html): This tutorial introduces you to Automation Decision Services.
- [Watson Machine Learning](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/ml-overview.html?audience=wdp&context=wdp): This service lets you build analytical models and neural networks for use in applications. 

You must have the following environments:
- **Decision Designer**: A web-based user interface for developing decision services in Business Automation Studio. 
- **Watson Studio**: A web-based user interface for developing and deploying machine learning models. 

In this tutorial, you...
- Define a machine learning model using Watson Studio.
- Deploy a machine learning model in Watson Machine Learning.
- Define a machine learning provider using Watson Machine Learning.
- Define and connect a predictive model to a machine learning model deployed in Watson Machine Learning.
- Use this predictive model in a decision model.
- Build, deploy and execute a decision service archive.

# Task 1: Defining and deploying a machine learning model in Watson Machine Learning.

**About this task**

In this task, you...
- Define a machine learning model using a PMML file in Watson Studio.
- Deploy this model in Watson Machine learning.

Click the following image to watch a video where the first part covers this task:

[![Video covers the machine learning sample.](images/ml_sample.jpg)](https://www.youtube.com/watch?v=Ch78Qy-oUg8&ab_channel=IBMSupportandTraining)

**Procedure**
1. You define a machine learning model using:
   - either the PMML file provided in [`automation-decision-services-samples/samples/MachineLearningCompleteTutorial/model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml`](https://raw.githubusercontent.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningCompleteTutorial/model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml).
   - either the notebook provided in [`automation-decision-services-samples/samples/MachineLearningCompleteTutorial/model/Predict loan default with PMML in WML.ipynb`](https://raw.githubusercontent.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningCompleteTutorial/model/Predict%20loan%20default%20with%20PMML%20in%20WML.ipynb). 
2. You deploy this model in Watson Machine Learning, you can follow the documentation in [Deployment spaces](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/ml-spaces_local.html?audience=wdp).
3. When the model is deployed, you get the data from Watson Studio that is required to define a machine learning provider in your decision project in Task 2. You need:
* the space id which is defined in the deployment space settings.
* the URL where the model is deployed. For instance `https://<location>.ml.cloud.ibm.com/ml/v4`.
* the authentication URL. For instance `https://iam.bluemix.net/identity/token`.

You also require an API key that you take from the [cloud](https://cloud.ibm.com/iam/apikeys).

# Task 2: Defining a machine learning provider in Decision Designer

**About this task**

In this task, you...
- Import and explore a sample project.
- Define a machine learning provider using Watson Machine Learning.

## Step 1: Importing the sample decision service in Decision Designer

You import a sample decision service into a decision project. This decision service applies several criteria in determining a borrower’s eligibility for a loan.
One of the key factors is risk which is predicted by the machine learning model.

**Procedure**

1. Sign in to your instance of Business Automation Studio. Use the credentials provided for your instance.
2. Click on the navigation menu at the top left of the page, expand Design and select **Business automations**.
3. Click Decision to see the decision automations.
4. Click Create and select Decision automations to make a project.
5. Enter a name for the project. Use a unique name. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. For simplicity, we use **Machine learning complete** in this sample documentation. After entering your name for the decision project, enter the following description:
`Automated Decision Service complete tutorial integrating machine learning prediction in a Loan Validation decision`. 
6. Click **Create** to make your decision project.
7. Click **Browse samples** in the project, and then select **Machine learning complete tutorial - Loan approval** in the Discovery section. 
8. Click **Import**: a decision service named **Complete machine learning loan approval** is added to your project. Click on it.
9. Open the data model to browse the defined types. Select `risk probabilities` that is used by the predictive model.
10. Click on the project name in the breadcrumbs and open the decision model `Loan Validation Decision Model`: it decides if a loan can be given to a borrower. One of the key decision is the risk computed in
the `Risk Score` node. It takes a `Loan Risk Score ` value. You will change this part to use a predictive model predicting the risk.

Next you will define a machine learning provider to be able to access the machine learning model you deployed in Task 1.

### Step 2: Defining a machine learning provider

You associate to your project a new machine learning provider to get your model deployment. See [Managing machine learning providers](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/ml_topics/tsk_manage_providers.html) for more information.

**Procedure**

1. Click **Machine learning complete** in the breadcrumbs to go back to your project.
2. Click the three dots next to your project name to open the contextual menu and select **Settings**.
3. Open the Machine learning providers tab. 
4. Click on **New +** to define a provider:
   * Keep `Watson ML` as the type.
   * Set `wml-complete` as the name.
   * Enter the description: `Provider for the machine learning complete tutorial`.
   * Enter the following service credentials you obtained in Task 1 from Watson Studio to authenticate with your Watson Machine Learning service instance:
     * API key.
     * Space ID.
     * URL.
     * Authentication URL.
   * Click on **Save**. Wait for the Status to be Running to have this provider ready to be used in your project.
5. Click **Machine learning complete** in the breadcrumbs to go back to the project.

## Task 3: Defining and using a predictive model

You define a predictive model to use your machine learning model in your project.

**About this task**

In this task, you...
- Create a predictive model in your decision service.
- Define its input and output mapping to fit to the data model. 
- Run the predictive model.
- Use the predictive model in a decision model.

### Step 1: Creating and connecting a predictive model

You create a predictive model in your decision service connected to your machine learning model deployment.

**Procedure**

1. In your decision project, click the **Complete machine learning loan approval** decision service to open it.
2. In the Models tab, click on **Create**.
3. Select **Predictive model type**, give the name **Loan risk score** and click **Create** to make your predictive model. The predictive model opens.
4. On the left part, it is indicated **Not connected**. Click on **Connect** to connect it to the provider where your machine learning model is deployed. 
5. Click on **Select a provider** and choose `wml-complete`.
6. Select the machine learning model: expand the machine learning model name you chose in Task 1 and click on the deployment name you want to use. 
7. Click **Next** to define the model input schema: it is complete, just browse it.
8. Click **Next** to define the test invocation. Click **Run** at the right of the wizard to validate the model. You get the following output:
```json
{
    "fields": [
        "probability_0",
        "probability_1",
        "predicted_paymentDefault"
    ],
    "values": [
        [
            0.17825993285555752,
            0.8217400671444425,
            1
        ]
    ]
}
```
9. Click **Next** to define the model output schema. Select **Generate from test output**. Wait for the output schema to be generated and click on **OK**.
10. Click **Apply** to connect the predictive model to this provider.

Next you define the input and the output of the machine learning model.

### Step 2: Editing the input and output mapping.

In this step, you...
- provide the input schema expected by the machine learning model. For this you define rules to map the decision service data model to the expected input data.
- define the output of your predictive model from the output schema of the machine learning model.

**Procedure**

1. Define the input nodes:   
    a. Click the **Input type** node. Change its name to **Loan** and its output type to **loan**.  
    b. Hover over the **Loan** node and click on the `Copy node` icon to duplicate it. Change its name to **Borrower** and set its output type to **borrower**.
    
2. Define the input mapping rule:    
    a. Click the **Input mapping** node. Go to the **Logic** tab and add a new business rule. Name it **input mapping rule**. You do not need to select any criteria for this rule. Click **Create**.   
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
    c. Go back to the diagram.
    
3. Define the output mapping rule:       
    a. Select the **Output mapping** node. In the Details tab, set its type to **risk probabilities**.    
    b. Go to the **Logic** tab, and add a new business rule. Name it **output mapping rule**. Click **Create**.   
    c.  In the rule content, enter the following code:
    ```
   if 'ML model invocation' is in error
     then 
    set decision to a new risk probabilities where
        the payment default is 0,
            the probability is 1 ;
    else 
    set decision to a new risk probabilities where
        the payment default is the predicted paymentdefault of 'ML model invocation' , 
        the probability is max ( the probability0 of 'ML model invocation' , the probability1 of 'ML model invocation' );
    ```
    
    d. Go back to the diagram.
    ![Image shows the predictive model.](images/scrn_pmodel.png)

### Step 3: Running the predictive model.
You add and run data sets to validate your predictive model.

**Procedure**

1. Go to the **Run** tab.
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
 4. Click **Run** to execute your predictive model. The result shows that there is no risk.
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
        "amount": 520000,
        "rate": 0.7
      }
    }
    ```
6. Click **Run** to validate your predictive model. The result shows that there is a risk.

### Step 4: Using the predictive model in the decision model

You edit the decision model to replace a user input node with a prediction node calling your predictive model. The computed prediction value is stored in a variable so that it can be used in several rules. 

**Procedure**

1. In the Navigation history, select **Loan Validation Decision Model** to open the decision model.
2. Remove the **Loan Risk Score** node.
3. Hover on the `Risk score` node and click on  **Add prediction**. Click **Select a predictive model** to select `Loan risk score`.
4. Select the **Risk Score** node. Go to the **Logic** tab and edit the **loan risk score** rule. Enter the following rule:
    ```
    set decision to the loan risk score computed from 
    Borrower being Borrower , 
    Loan being Loan; 
    ```
 Here is the new model:
    ![Image shows the decision model.](images/scrn_dmodel.png)

6. Run your decision model. Go to the **Run** tab and run both the 'John Good Score' and 'Paul Bad Score' tests.

## Task 4: Deploying and invoking the decision service

You connect your project to a GitHub repository to be able to deploy a decision service archive. Then you invoke your decision using the Swagger UI tool. 

**About this task**

In this task, you...
- Connect the **Machine learning complete** project to a Git repository. 
- Create a new version of your project.
- Deploy your decision service archive.
- Invoke the decision service archive by using the Open Api generated by the runtime.
- Check the metadata defined for your archive.

### Step 1: Deploying the decision service

You deploy the decision service. Note that those steps are in more detail in Task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/tut_dd_t5_lsn.html).

**Procedure**

1. Click on the decision service name `Complete machine learning loan approval` in the breadcrumb to open it.
2. Open the **Decision operations** tab and note the name of the exposed operation: `loan-validation-decision-model`.
3. Create a new repository on GitHub, get its URI and the credentials to access it.
4. Connect your decision project to this Git repository. 
5. In the **Share changes** tab, select the **Complete machine learning loan approval** decision service.
6. Click **Share** to share your changes in the Git repository.
7. In the **Deploy** tab, click the suggested `create version` from the latest change to create your version.
8. Expand the created version and click **Deploy** at the end of the row for your decision service. Wait for the deployment to finish.
9. Copy the decision ID and click on `{...}` at the end of the row of your decision service deployment. The Swagger UI of Automation Decision Service runtime opens.

Next, you execute the decision service archive using Swagger UI.

### Step 2: Executing the decision service

You need to have credentials allowing execution in the Automation Decision Service runtime.

**Procedure**

1. In the Swagger UI of Automation Decision Service, click on **Authorize** and enter the credentials provided by your IT allowing execution in the Automation Decision Service runtime.
2. In the Decision runtime part, expand **GET /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/execute**. Click on **Try it out** at the end of the line.
3. Enter the following input values:
    - **deploymentSpaceId**: embedded
    - **decisionID**: the one you copied in the previous step.  
    - **operation**: `loan-validation-decision-model`, the one you got in the previous step.
       - Use the following schema in the request body:
 ``` 
{
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
```
7. Click on **Execute**. You get the following response body:
```
{
  "insurance": {
    "required": true,
    "rate": 0.02
  },
  "approval": {
    "approved": true,
    "message": "Congratulations! Your loan has been approved"
  }
}   
 
``` 
### Step 3: Checking the metadata associated to your decision

You verify the metadata containing the Machine Learning provider. Those metadata were generated by Decision Designer. You may have to change them if your provider credentials changes.
Note also that you have to define those metadata in case you deploy the decision service archive in another deployment space.
You need to have credentials allowing management in the Automation Decision Service runtime.
For more information about the metadata, see [Decision service metadata](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/topics/con_metadata.html).

**Procedure**
1. In the Swagger UI of Automation Decision Service, click on **Authorize** and enter the credentials provided by your IT allowing management in the Automation Decision Service runtime.
2. In the Decision storage management part, expand **GET /deploymentSpaces​/{deploymentSpaceId}​/decisions​/{decisionId}​/metadata**.
3. Enter the following input values and click on Execute:
    - **deploymentSpaceId**: embedded
    - **decisionID**: the one you copied in the Step 1.  
In the response body, you get the description of the machine learning provider:

```
{
 ...
    "decisions/Machine-learning-complete/wml-completeXXXXXX": {
      "name": "decisions/Machine-learning-complete/wml-completeXXXXXX",
      "kind": "ENCRYPTED",
      "readOnly": false,
      "value": {
        "name": "decisions/Machine-learning-complete/wml-completeXXXXXX",
        "type": "WML",
        "description": "Provider for the machine learning complete tutorial",
        "mlUrl": "XXXXXXXXXX",
        "authUrl": "XXXXXXXXXX",
        "instanceId": "XXXXXXXXXX",
        "apiKey": "XXXXXXXXXX"
      }
...
```
You've completed this tutorial.
For more tutorials about machine learning in Automation Decision Services see:
   - [Machine learning quick tutorial](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningQuickTutorial) to learn how to import a Machine Learning Model as PMML file in an Automation Decision Services Machine Learning Service and how to connect it to a predictive model.
   - [Machine learning short tutorial](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MachineLearningShortTutorial) to learn how to connect a predictive model to a Watson Machine Learning Model. 
   - [Machine learning sample](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MLDatasets) to get another example using two predictive models.
   - [Machine learning notebooks](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/MLNotebooks) to get another example of a notebook ready to be connected to a predictive model.
