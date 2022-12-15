# Decision service archives

This directory contains decision services archives that can be run in your Automation Decision Services runtime or using the execution api. 
They were built from the decision services that can be loaded in Designer using the 
samples wizard (see [Importing and exporting decision services](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=decisions-importing-exporting-decision-services)).

Here is the correspondance between the archive and the decision service:

|**Archive name** |**Sample name** |**Decision service name** |
|:---------------- |:-------------- |:--------------------- |
| `addressCleansing` | `Transportation` | `Address cleansing` | 
| `approvalWithML` | `Banking` | `Approval with ML` | 
| `approvalWithTasks` | `Banking` | `Approval with tasks` | 
| `baggage` | `Transportation` | `Baggage` | 
| `carRental` | `Transportation` | `Car rental` | 
| `commissions` | `Compensation` | `Commissions` | 
| `conseilDuJour` | `French` | `Conseil du jour` | 
| `consejoDiario` | `Spanish` | `Consejo diario` | 
| `conselhoDiario` | `Portuguese` | `Conselho diário` | 
| `consiglioQuotidiano` | `Italian` | `Consiglio quotidiano` | 
| `customerRetention` | `Telecom` | `Customer retention` | 
| `gettingStarted` | `Getting started` | `Getting started` | 
| `heutigeEmpfehlung` | `German` | `Heutige Empfehlung` | 
| `loanApproval` | `Banking` | `Loan Approval` | 
| `loyaltyProgram` | `Retail` | `Loyalty program` | 
| `panierDeE-commerce` | `French` | `Panier de e-commerce` | 
| `shipmentPricing` | `Pricing` | `Shipment Pricing` | 
| `shoppingCart` | `Retail` | `Shopping cart` | 
| `私のサービス` | `Japanese` | `私のサービス` | 
| `我的自动化服务` | `Chinese` | `我的自动化服务` | 


**Note:** The `loanApproval` archive is used by the following samples:
   * [Execution api sample](../samples/ExecutionApiSample),
   * [Explore execution trace sample](../samples/ExploreExecutionTraceSample),
   * [Loan application sample](../samples/LoanApplicationSample),
   * [Web micro service sample](../samples/WebMicroServiceSample).
   
The `approvalWithML` archive is used by the following samples:
   * [Execution api sample](../samples/ExecutionApiSample),
   * [Web micro service sample](../samples/WebMicroServiceSample).

# Executing an archive using the runtime

You use the Swagger UI of your runtime instance to execute the archives. 
For an archive containing a predictive model, you first deploy the machine learning model.
For more information, see [Executing decision services with  decision runtime documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-executing-decision-decision-runtime).

## Prerequisites
- Automation Decision Services: Your instance of the services must have a runtime. You must have the credentials allowing management and execution in this runtime. 

To use an archive containing a predictive model:
- **Automation Decision Services Machine Learning Service**: A Machine Learning service implementation based on IBM Open Prediction Service API. Ask your IT the URL to access it.

## Deploying an archive 

You use the Swagger UI of your runtime instance to deploy an archive to a deployment space. After you deploy the archive, you look at its operations.

1. Download the .jar file that you want to deploy to your computer, for example, `baggage-22.0.2.jar`.
2. Open the Swagger UI of your runtime instance.
3. Enter your security credentials to be able to manage archives and to execute.
4. Deploy the archive by expanding `POST /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/archive` in the **Decision storage management** part, entering the following parameters, and running the operation:
   * *deploymentSpaceId*: Use your space identifier, for example: `testArchive<Initials>`. It is created at the first call.
   * *decisionId*: Use the example `baggage-22.0.2.jar`.
   * Archive file: In the Request body, browse to the archive file downloaded to your computer. 
   
   The archive is loaded into your deployment space, and you get the return code 200 for a successfully action.
5. Check the operations in the decision archive by expanding `GET /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations` in the **Decision runtime** part, entering the following parameters, and running the operation:
   * *deploymentSpaceId*: Use your space identifier, for example, `testArchive<Initials>`.
   * *decisionId*: `baggage-22.0.2.jar`.
   
   You get the return code 200, and a list of the possible operations:
```    {
      "decisionId": "baggage-22.0.2.jar",
      "operations": [
        "compliance-and-fees",
      ]
    }
```
Next, you run the `compliance-and-fees` operation.

## Using an archive containing a predictive model

To run an archive using a Machine Learning model, you have first to deploy the corresponding model, then you give the required references to the decision metadata.

The ML archives require a deployment of the ML models they use. PMML files are provided in the `archives/models` directory. Download them to use the corresponding archives. 
The ML archives also embed a provider info key, you have to set it in the decision metadata. 
For more information about the Machine Learning metadata, see [Decision service metadata](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-decision-service-metadata).
Here are the required information for the archives:

| **Archive name** | **Model name** | **File containing the provider info id**                                                                                                  |
|:---------------- |:-------------- |:------------------------------------------------------------------------------------------------------------------------------------------|
| `approvalWithML` | `Approval-SGDClassifier-StandardScaler-pmml.xml`| `Banking.zip/Approval with ML/Loan risk score/resources/extannotations/<USERID>/banking/approval_with_ml/loanriskscore/providerInfo.json` |

The `ProviderInfoId` is the value of the `id` property in this json file.

Here is an example to deploy the model in the OPS instance your IT provided you:
   * Open the OPS URL in a browser.
   * In the `manage` section, expand `POST /upload`, click on `Try it out`.
   * Browse to the PMML file to be loaded `models/Approval-SGDClassifier-StandardScaler-pmml.xml`.
   * Click on Execute, the response code is 201. Browse into the response body to get the **id** of this deployment. In the next step, this id is refered as `ML_DEPLOYMENT_ID`.
   
Then you use the Swagger UI of your runtime instance to add the required information to the metadata:
   * Open the Swagger UI of your runtime instance.
   * Define the metadata by expanding `POST /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/metadata` in the **Decision storage management** part, entering the following parameters, and running the operation:
      * *deploymentSpaceId*: the space identifier used to upload the archive.
      * *decisionId*: the decision identifier used to upload the archive.
      * In the Request body, add the metadata describing the provider info:
```    
{"decisionMetadata": {
  "MyProvider": {"name": "MyProvider",
                 "kind": "ENCRYPTED",
                 "readOnly": false,
                 "value": {
                    "name": "MyProvider",
                    "type": "OPS",
                    "mlUrl": "<URL_TO_OPS>"
                    }
                 },
  "<ProviderInfoId>": {"name": "ProviderInfoId",
                        "kind": "ENCRYPTED",
                        "readOnly": false,
                        "value": {
                            "id" : "ProviderInfoKId",
                            "providerId" : "MyProvider",
                            "deploymentId" : "<ML_DEPLOYMENT_ID>"
                                 }
                        }
         }
}  
```   
   * Click on Execute, the response code is 200. You can now run the archive it will be able to invoke the Machine Learning model.
 

## Running an archive 
You use the Swagger UI of your runtime instance to run the decision service archive. 

1. Open the Swagger UI tool as described in the previous **Deploying an archive** section.
2. Get an example input payload by expanding `/deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/exampleInput` in the **Decision runtime** part, entering the following parameters, and running the operation:
   * deploymentSpaceId: Use your space identifier, for example, `testArchive<Initials>`.
   * decisionId: `baggage-22.0.2.jar`
   * operation: `compliance-and-fees`
   
   You get the return code 200 and the following response:
```
{
  "decisionId": "baggage-22.0.2.jar",
  "decisionOperation": "compliance-and-fees",
  "input": {
    "frequentFlyerStatus": "Bronze",
    "initialBooking": {
      "bags": 1000,
      "fareClass": "Business",
      "fareCode": "<fareCode>"
    },
    "checkedBaggage": {
      "bag": [
        {
          "depth": 1.5,
          "height": 1.5,
          "weight": 1.5,
          "width": 1.5
        }
      ]
    }
  }
}

```
Copy the contents of the input field and run the decision service in the following step.

3. Run the operation by expanding `POST /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/execute`, entering the following parameters, and running the operation:
   * *deploymentSpaceId*: Use your space identifier, for example, `testArchive<Initials>`.
   * *decisionId*: `baggage-22.0.2.jar`
   * *operation*: `compliance-and-fees`
   * *Request body*: Enter the input data from step 2, be careful to take only the input field content.
   
   You get the return code 200, and the following response:
```
    "output": {
      "compliant": false,
      "fees": 0,
      "messages": [
        "At least one baggage is too wide. The maximum allowed width is 100.0 cm or 39.0 inches",
        "At least one baggage is too deep. The maximum allowed depth is 90.0 cm or 35.0 inches."
      ]
    }
```

For more information, see  [Swagger UI documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=runtime-swagger-ui-decision-api).
