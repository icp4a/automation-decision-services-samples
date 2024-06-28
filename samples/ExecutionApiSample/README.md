# Sample: Executing a decision service in Java

## Description
This sample shows Java applications that execute decision services by using the **execution Java API** (see `documentation`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=services-executing-decision-execution-java-api)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=services-executing-decision-execution-java-api)). <br><br>
These Java applications must be run in a Kubernetes cluster where License Service for tracking usage is installed. (see [Tracking usage](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=api-metering-tracking-usage-execution-java)).

## Learning objectives
- Create a Java application that executes a decision service
- Define metadata to execute decision services that rely on machine learning
- Display trace to understand rule executions.

## Audience
This sample is for anyone who wants to use the execution Java API to run a decision service.

## Time
15 minutes

## Prerequisites
- Apache Maven project management tool (see [Welcome to Apache Maven](https://maven.apache.org)).
- `Access to Decision Designer as a Maven repository`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=environment-using-decision-designer-as-maven-repository)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=environment-using-decision-designer-as-maven-repository).
- (Optional) Implementation of a Machine Learning service based on IBM Open Prediction Service (OPS) API. <br>Ask your IT for an access (see `Integrating machine learning`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=artifacts-integrating-machine-learning)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=artifacts-integrating-machine-learning)).

# Setting up the sample
In this section, you download the source files, and fill in the placeholders of the configuration files.

## Getting the source files
- Download a compressed file of the `automation-decision-services-samples` in the Git repository.
- Unzip it in a new directory. From now on, this documentation refers `ExecutionApiSample` as the `automation-decision-services-samples/samples/ExecutionApiSample` directory.

## Configuring Maven
- Replace the following placeholders in `ExecutionApiSample/settings.xml`:
  - %ADS_MAVEN_REPOSITORY_TO_BE_SET% : the URL of your maven repository (`more`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=environment-using-decision-designer-as-maven-repository)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=environment-using-decision-designer-as-maven-repository)).
  - %YOUR_API_KEY_TO_BE_SET% : your ZEN API key ([more](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=administering-authorizing-http-requests-by-using-zen-api-key))
- Enable Maven to use the remote repository that is stored in HTTPS servers ([more](https://maven.apache.org/guides/mini/guide-repository-ssl.html)). <br>
  Note: (For testing only) Alternatively, SSL validation can be disabled by using: 
  ```
  -Dmaven.wagon.http.ssl.insecure=true
  ```

## Building
- Run the following command in the `ExecutionApiSample` directory:
  ```
  mvn -s settings.xml clean compile
  ```
  
# LoanValidation application
## Discovering
- The application is located at `ExecutionApiSample/src/main/java/com/ibm/ads/samples/LoanApproval.java`.
- It submits all JSON input that is found in the directory `ExecutionApiSample/../WebMicroServiceSample/test/approval`.
- It uses the decision service named **Loan Approval** that is defined in the **Banking** sample project
- The generated decision service archive is located at `ExecutionApiSample../../archives/loanApproval-XXX.jar`.

## Executing
- Run the following command in `ExecutionApiSample` directory:
  ```
  mvn -s settings.xml exec:exec
  ```
- The output should be something similar to the following example:
  ```
...
juin 03, 2024 4:50:32 PM com.ibm.ads.samples.LoanApproval main
INFOS: Running refused.json
juin 03, 2024 4:50:32 PM com.ibm.ads.samples.LoanApproval main
INFOS: Result {"insurance":{"rate":0.0025,"required":true},"approval":{"approved":false,"message":"Too big Debt/Income ratio: 0.3943594113786697"}}
juin 03, 2024 4:50:32 PM com.ibm.ads.samples.LoanApproval main
INFOS: Running approved.json
juin 03, 2024 4:50:32 PM com.ibm.ads.samples.LoanApproval main
INFOS: Result {"insurance":{"rate":0.006,"required":true},"approval":{"approved":true,"message":"Congratulations! Your loan has been approved"}}
```

# LoanValidationPOJO application
## Discovering
- The application is located at `ExecutionApiSample/src/main/java/com/ibm/ads/samples/LoanApprovalPOJO.java`.
- It submits hardcoded inputs written in Java
- It uses the decision service named **Loan Approval** that is defined in the **Banking** sample project
- The generated decision service archive is located at `ExecutionApiSample../../archives/loanApproval-XXX.jar`.

## Executing
- Run the following command in `ExecutionApiSample` directory:
  ```
  mvn -s settings.xml exec:exec -Ppojo
  ```
- The output should be something similar to the following example:
  ```
...
juin 03, 2024 4:54:40 PM com.ibm.ads.samples.LoanApprovalPOJO main
INFOS: Approved input result: {"insurance":{"rate":0.006,"required":true},"approval":{"approved":true,"message":"Congratulations! Your loan has been approved"}}
juin 03, 2024 4:54:40 PM com.ibm.ads.samples.LoanApprovalPOJO main
INFOS: Refusal input result: {"insurance":{"rate":0.0025,"required":true},"approval":{"approved":false,"message":"Too big Debt/Income ratio: 0.3943594113786697"}}
  ```
  
# LoanValidationWithML application
## Discovering
- The application is located at `ExecutionApiSample/src/main/java/com/ibm/ads/samples/LoanApprovalWithML.java`.
- It submits all JSON input that is found in the directory `ExecutionApiSample/../WebMicroServiceSample/test/approvalWithML`.
- It uses the decision service named **Approval with ML** that is defined in the **Banking** sample project.
- The generated decision service archive is located at `ExecutionApiSample/../../archives/approvalWithML-XXX.jar`.

## Executing
- The following steps must be completed before running this sample:
  - Get the **URL of your OPS server** (for the placeholder *<OPS_URL>*)
  - Deploy this machine learning model [PMML file](../../archives/models/Approval-SGDClassifier-StandardScaler-pmml.xml) (as described in this [procedure](../../archives/README.md#using-an-archive-containing-a-predictive-model)).<br/>
    and keep the **ID of the deployment** (for the placeholder *<DEPLOYMENT_ID>*).
  - Retrieve the **SSL certificate in PEM format of your OPS server** (for the placeholder *<PEM_CERTIFICAT_PATH>*)
- Run the following command in the `ExecutionApiSample` directory:
  ```
  mvn -s settings.xml exec:exec -PwithML \
           -Dml.provider.url=<OPS_URL> \
           -Dml.deployment.id=<DEPLOYMENT_ID> \
           -Dml.certificat=<PEM_CERTIFICAT_PATH>
  ```
- The output should be something similar to the following example:
  ```
  ...
juin 03, 2024 5:43:06 PM com.ibm.ads.samples.LoanApprovalWithML main
INFOS: Running badScore.json
juin 03, 2024 5:43:08 PM com.ibm.ads.samples.LoanApprovalWithML main
INFOS: Result {"insurance":{"rate":0.02,"required":true},"approval":{"approved":false,"message":"The loan is rejected because the risk score is too high."}}
juin 03, 2024 5:43:08 PM com.ibm.ads.samples.LoanApprovalWithML main
INFOS: Running goodScore.json
juin 03, 2024 5:43:09 PM com.ibm.ads.samples.LoanApprovalWithML main
INFOS: Result {"insurance":{"rate":0.02,"required":true},"approval":{"approved":true,"message":"Congratulations! Your loan has been approved"}}
```

# Getting more information
* about the `Execution API documentation`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=services-executing-decision-execution-java-api)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=services-executing-decision-execution-java-api).
* about the machine learning metadata in `Decision service metadata`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=services-decision-service-metadata)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=services-decision-service-metadata).
* about the [sample of the execution API in a web microservice](../WebMicroServiceSample/README.md)
* about the [sample of the Loan Validation application](../LoanApplicationSample/README.md) to illustrate decision execution using **decision runtime**.
