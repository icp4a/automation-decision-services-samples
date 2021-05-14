# Decision service archives

This directory contains archives of sample decision services that you can run in your Automation Decision Services runtime. Your runtime might already be configured to point to a repository that contains these archives. This is done by default for installations that are made by using the demo pattern.

**Note:** The *loanValidationDecisionService* archive is used by the client web application that is defined in *samples/LoanApplication*.

Follow these steps to use the archives.

## Upload an archive into a Nexus archive repository
To upload the archive `baggage-1.0.0.jar`:

1. Log in to your instance of Nexus.
2. Upload the JAR file to the repository `maven-releases`:

   a. Browse to the archive file `baggage-1.0.0.jar`.

   b. Enter `ads.samples` for the group ID.

   c. Enter the name of the JAR file, for example, `baggage` for the artifact ID.

   d. Enter `1.0.0` for the version number.

   e. Click the `Upload` button.
   
3. View the uploaded file, and copy its path to use it as the decision ID in running the sample, for example, `ads/samples/baggage/1.0.0/baggage-1.0.0.jar`.

## Run the sample by using the Swagger UI tool 
To run the sample by using the Swagger UI tool:

1. Enter the URL for your Swagger UI tool into your browser, and then open the tool.
2. Click `Authorize` in the tool, and enter your login credentials.
3. Expand `GET /decision/{decisionId}/operations`. 
4. Enter the decision ID of the sample. In this example, the path is `ads/samples/baggage/1.0.0/baggage-1.0.0.jar`.
You get a list of possible operations. In this exercise, you execute the operation `complianceAndFees`.
5. Expand `GET ​/decision​/{decisionId}​/operations​/{operation}​/payload`, and enter the decision ID and the operation `complianceAndFees`.
You get the payload of this operation, for example:
```
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
```

6. Expand `POST /decision/{decisionId}/operations/{operation}`, and enter the decision ID and the operation `complianceAndFees`. Enter in the input the payload previously got.
You get the following results because the request is not compliant with the limits:
```
     "At least one baggage is too wide. The maximum allowed width is 100.0 cm or 39.0 inches",
     "At least one baggage is too deep. The maximum allowed depth is 90.0 cm or 35.0 inches."
```

For more information, see  [Swagger UI documentation](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/topics/con_swagger_ui.html).
