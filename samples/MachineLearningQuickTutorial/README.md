# Tutorial Quick: Using machine learning to make better decisions

## Description



This tutorial shows you how to connect an existing predictive model to a machine learning model and use it in a decision service that validates loans. The decision service uses a decision model to apply policies and incorporate a risk prediction. 
The machine learning model is described in a PMML file that is directly deployed in a Machine Learning Provider embedded in Automation Decision Service.
This tutorial tasks are illustrated in the following diagram.

![Image summarize the tutorial steps](images/MLQuickTutorial.png)

For more information on decision models and predictive models, see `Modeling decisions`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=services-developing-decision)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=services-developing-decision) and `Integrating machine learning`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=artifacts-creating-predictive-models)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=artifacts-creating-predictive-models).

For more tutorials about machine learning in Automation Decision Services see:
   - [Machine learning short tutorial](../MachineLearningShortTutorial/README.md) to learn how to connect a predictive model to a Watson Machine Learning Model. 
   - [Machine learning complete tutorial](../MachineLearningCompleteTutorial/README.md) to learn how to create predictive model, connect it to a Watson Machine Learning Model, deploy and execute the decision.
   - [Machine learning customer loyalty sample](../MachineLearningCustomerLoyaltySample/README.md) to get another example using two predictive models.
   
## Learning objectives

   - Define a machine learning provider using an embedded Machine Learning provider.
   - Import a PMML model directly in Decision Designer and connect it to an existing predictive model.
   - Run a decision model using this predictive model.

## Audience

This sample is for technical and business users who want to apply predictive analytics through machine learning in decision services in Automation Decision Services. It also shows data scientists and data engineers how Automation Decision Services can be used to apply machine learning models in decision-making applications.

## Time required

15 minutes

## Prerequisites

Prepare with the following resources:
- `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=resources-getting-started): This tutorial introduces you to Automation Decision Services.

You must have the following environment:
- **Decision Designer**: A web-based user interface for developing decision services. You work with the sample decision service by importing it into a project and opening it in Decision Designer.

In this tutorial, you...
- Define a machine learning provider in a project.
- Upload a machine learning model using a PMML file. 
- Connect a predictive model to a machine learning model.


# Task 1: Defining a machine learning provider

**About this task**

In this task, you...
- Import and explore a sample decision service.
- Define a machine learning provider of type embedded. 

## Step 1: Importing the sample decision service in Decision Designer

You import the sample decision service into a project. This decision service applies several criteria in determining a borrower’s eligibility for a loan.
One of the key factors is risk which is predicted by the machine learning model.

**Procedure**

1. Use the credentials provided for your instance to sign in to Decision Designer.
2. Create a new project and import the `Machine learning quick tutorial - Loan approval` sample from the list of Discovery tutorials. 
3. Click `Quick machine learning loan approval` to open the decision service.

**Discovery**

Take a moment to look at the imported decision service.

1. Open the `Loan Validation Data` data model in the `Data` tab to browse the defined types. <br>
   The `risk probabilities` type is used by the predictive model.
2. Click on the project name in the breadcrumbs and open the decision model `Loan Validation Decision Model`: it decides if a loan can be given to a borrower. One of the key decision is the risk computed in
the `Risk Score` node. It takes as input a prediction node corresponding to the `loan risk score` predictive model. 
3. Click **Quick machine learning loan approval** in the breadcrumbs and open the predictive model `loan risk score`. It is not yet connected, you'll connect it in the next steps.
4. Click on the `Input mapping` node and look at the input mapping rule: it defines the machine learning model input from a loan and a borrower.
5. Click on the `Output mapping` node and look at the output mapping rule: it defines a risk probability from the output of the machine learning model.

Next you will define a machine learning provider to connect the predictive model.


## Step 2: Defining a machine learning provider

You create a machine learning provider to get your model deployment into your project. You use the embedded provider: it allows you to import any type of Predictive Model Markup Language (PMML) file and run it directly in your automation. See `Managing machine learning providers`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=models-managing-local-machine-learning-providers)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=models-managing-local-machine-learning-providers) for more information.


**Procedure**

1. Go back to the diagram and click in the diagram outside any node or link.
2. Click on the **Configure** button. You see that no provider is defined.
3. By default, **Remote Machine Learning Model** is selected. Click on **Next**.
4. Click on **New provider** to define one.
5. Click on **New +** to open the Machine Learning provider wizard:
   * Select `Embedded machine learning` as the type.
   * Set `embedded-quick` as the name.
   * Enter the description: `Provider for the machine learning quick tutorial`.
   * Click on the `Test connection` button. It successfully connects.
   * Click on **Save** to add this provider. Wait for the Status to be Running to have this provider ready to be used in your project.
6. In the Navigation history click on `loan risk score` to go back to the predictive model configuration page.

Next you will connect your predictive model.

# Task 2: Uploading a PMML file and connecting a predictive model

**About this task**

In this task, you...
- Upload a PMML file into Decision Designer.
- Connect a predictive model to a machine learning model.

## Step 1: Uploading a PMML file 

First you explore the PMML file describing the Machine Learning model. Then you upload it to create a machine learning model deployment to be used in the predictive model.

**Procedure**

1. Browse the the PMML file provided in [`automation-decision-services-samples/samples/MachineLearningQuickTutorial/model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml`](model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml):
   * The input schema composed of 7 fields is described in the `DataDictionary` part.
   * The output schema composed of 3 fields is described in the `Output`part. Note that this part is optional. 
   * It uses a Regression model.
2. Back in Designer, following Task 1, you are in the **Configure predictive model** wizard. Click **Next** to go to the **Select provider and deployment** page.
3. Click on **Select provider** and select the `embedded-quick` provider you defined in Task 1. 
4. Click Upload and select the PMML file provided in [`automation-decision-services-samples/samples/MachineLearningQuickTutorial/model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml`](model/ML-Sample-SGDClassifier-StandardScaler-pmml.xml). 
5. Click on Upload. A new machine learning model deployment is added ready to be used to configure the predictive model.

Next you connect the predictive model to this machine learning model deployment.

## Step 2: Connecting a predictive model

You go on connecting the predictive model.

**Procedure**

1. Following Step 1, you are in the **Configure predictive model** wizard in the define input schema page.
2. The model input schema taken from the PMML file is complete, just browse it.
3. Click **Next** to check the test invocation. This step allows to invoke the model and get an output example that can be used to define the output schema. Since the output schema is defined in the PMML file you skip this step.
4. Click **Next** to check the model output schema. The model output schema taken from the PMML file is complete, just browse it.
5. Click **Apply** to connect the predictive model to this machine learning model deployment. The predictive model diagram opens, it is now configured with an embedded provider and the model deployment you just loaded.

Next you run your predictive model and the decision model that calls it.

# Task 3: Running a predictive model and a decision model

**About this task**

In this task, you...
- Run a predictive model.
- Run a decision model calling a prediction node.

## Step 1: Running a predictive model 

You run the predictive model you connected in Task2.

**Procedure**
1. Following Task2, you are in the `loan risk score` diagram editor. Click on the Run tab. Two data sets are defined.
2. Select `John good score` and click on **Run**. The result is
```
{
    "paymentDefault": 0,
    "probability": 1
}
```
There is no default payment risk with a probability of 1.

3. Browse the Run history to see the executed rules.

Next you run the decision model calling this predictive model.

## Step 2: Running a decision model 

You run the decision model using the predictive model you just defined. 

**Procedure**
1. Following Step1, you are in the `loan risk score` Run tab. 
2. In the Navigation history, select `Loan Validation Decision Model`. 
3. Click on the Run tab and run the dataset `John Good Score`. You get the following results:
![Results image](images/result.png)
4. Browse the run history: the loan risk score rule has been triggered, the predictive model called the machine learning model deployment.

You've completed this tutorial.

For more tutorials about machine learning in Automation Decision Services see:
   - [Machine learning short tutorial](../MachineLearningShortTutorial/README.md) to learn how to connect a predictive model to a Watson Machine Learning Model. 
   - [Machine learning complete tutorial](../MachineLearningCompleteTutorial/README.md) to learn how to create predictive model, connect it to a Watson Machine Learning Model, deploy and execute the decision.
   - [Machine learning sample](../MachineLearningCustomerLoyaltySample/README.md) to get another example using two predictive models.
