# Decision service archives

The archives in this repository contain sample decision services that you can run in your Automation Decision Services runtime. 

**Note:** The *loanValidationDecisionService* archive is used by the client web application that is defined in *samples/LoanApplication*.

The following steps show you how to use the archives. These steps use the `baggage-21.0.2.jar` archive as an example.

## Upload an archive 
You use the Swagger UI of your runtime instance to upload an archive to a deployment space. After you deploy the archive, you look at its operations.

1. Download the .jar file that you want to deploy to your computer, for example, `baggage-21.0.2.jar`.
2. Open the Swagger UI of your runtime instance.
3. Enter your security credentials to be able to manage archives and to execute.
4. Deploy the archive by expanding `POST /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/archive` in the **Decision storage management** part, entering the following parameters, and running the operation:
   * *deploymentSpaceId*: Use your space identifier, for example: `testArchive<Initials>`. It is created at the first call.
   * *decisionId*: Use the example `baggage-21.0.2.jar`.
   * Archive file: In the Request body, browse to the archive file downloaded to your computer. 
   
   The archive is loaded into your deployment space, and you get the return code 200 for a successfully action.
5. Check the operations in the decision archive by expanding `GET /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations` in the **Decision runtime** part, entering the following parameters, and running the operation:
   * *deploymentSpaceId*: Use your space identifier, for example, `testArchive<Initials>`.
   * *decisionId*: `baggage-21.0.2.jar`.
   
   You get the return code 200, and a list of the possible operations:
```    {
      "decisionId": "baggage-21.0.2.jar",
      "operations": [
        "compliance-and-fees",
      ]
    }
```
Next, you run the `compliance-and-fees` operation.

## Run an archive 
You use the Swagger UI of your runtime instance to run the decision service archive. 

1. Do the first three steps in the **Upload an archive** section to work in the Swagger UI.
2. Get an example input payload by expanding `/deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/exampleInput`, entering the following parameters, and running the operation:
   * deploymentSpaceId: Use your space identifier, for example, `testArchive<Initials>`.
   * decisionId: `baggage-21.0.2.jar`
   * operation: `compliance-and-fees`
   
   You get the return code 200 and the following response:
```
{
  "decisionId": "baggage-21.0.2.jar",
  "decisionOperation": "compliance-and-fees",
  "input": {
    "frequentFlyerStatus": "Bronze",
    "initialBooking": {
      "bags": 1000,
      "fare class": "Business",
      "fare code": "<fareCode>"
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
   * *decisionId*: `baggage-21.0.2.jar`
   * *operation*: `compliance-and-fees`
   * *Request body*: Enter the input data from step 2.
   
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

For more information, see  [Swagger UI documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.x?topic=services-swagger-ui).
