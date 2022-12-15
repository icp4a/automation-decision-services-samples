# Sample: Creating a web microservice for executing a decision service

## Description
This sample shows how to create a web microservice to execute a decision service that might include a machine learning model.
The web microservice embeds a decision service archive. When it is launched, it can execute the decision service. You use the web microservice in a Kubernetes container.

This sample uses the execution Java API to execute decisions and Quarkus to build a web microservice.
For more information, see the [execution Java API](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-executing-decision-execution-java-api) documentation.

## Learning objectives

- Create a web microservice that embeds a decision service archive.
- Define required metadata to execute the decision service archive that uses machine learning.
- Run the web microservice and execute the decision service archive.

## Audience

This sample is for anyone who wants to create a web microservice for executing a decision service.

## Time required

15 minutes

## Prerequisites
- Linux: This sample is used on Linux.
- **Apache Maven**: A software project management tool that you can download from [Welcome to Apache Maven](https://maven.apache.org). 
- **OpenJDK 11**: The web microservice is built by using the latest Quarkus version that requires Java SDK 11.
- **Kubernetes**: A Kubernetes cluster where License Service for tracking usage is installed. For more information about tracking usage, see the [documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=api-tracking-usage).
This Kubernetes cluster is not necessary the same one that Automation Decision Services is installed. This sample was tested by using an OpenShift cluster with the Kubernetes version v1.20.11+e880017.
- **Automation Decision Services machine learning service**: A machine learning service implementation based on IBM Open Prediction Service API. Contact your IT to get a URL to access it.

# Setting up the sample
In this section, you download the source files, and prepare the configuration file.

## Getting the source files and configuring the Maven settings file
You download the repository to be used, and you prepare the Maven settings file.

1. Download a compressed file of the `automation-decision-services-samples` Git repository. Unzip it in a new directory. From now on, this documentation refers `WebMicroServiceSample` as the `automation-decision-services-samples/samples/WebMicroServiceSample` directory.
If your Maven settings file is already configured to access the decision artifacts, you can skip the remaining steps.

Otherwise, you must define a Maven settings file by completing the template `settings.xml` file that is provided in this sample.

2. Open `WebMicroServiceSample/settings.xml`.
3. Replace all `TO BE SET` tags with the appropriate values:
   * `ADS_MAVEN_REPOSITORY_TO_BE_SET`: The URL of your Maven repository.
   * `API_KEY_TO_BE_SET`: The Zen API key to access Decision Designer. For more information about getting the API key, see this [documentation](https://www.ibm.com/docs/en/cloud-paks/1.0?topic=users-generating-api-keys-authentication). 
   You must encode this key by using `base64` as described in this [documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=administering-authorizing-http-requests-by-using-zen-api-key).
4. Save the `settings.xml` file.

_Note_: To access Decision Designer, you might need to add the appropriate certificate to your key store.

## Deploying the decision service archives
You deploy decision service archives to your local Maven repository. Decision service archives are delivered in the `automation-decision-services-samples/archives` directory. 
You deploy two archives: `loanApproval` and `approvalWithML`, and they are used by two web microservices.

**Note**
   * The script deploys to your local Maven repository for a quick test. In a production mode, they are in a Maven repository, and you can access with the settings file.
  
Follow these steps to deploy the decision service archives:
   
1. Browse your local Maven repository, and check the installed versions of `ads/samples/loanApproval` and `ads/samples/approvalWithML`. If the version 1.0.0 is already installed, increase the version number in the script.
3. Run the following command in the `WebMicroServiceSample/archive` directory:
   ```sh
   ./install-archives.sh
   ```
    
This command installs the two decision service archives with the version specified in `SERVICE_VERSION` to your local Maven repository. You can go to the next step when you see the message ``` BUILD SUCCESS```.

# Building, running, and testing a web microservice without machine learning
In this section, you build by using a Quarkus docker image that contains a web microservice. This web micoservice embeds the decision service archive `loanApproval`. 
This decision service archive does not contain any predictive model.
You deploy it to a Kubernetes container, and then, you test it by using cURL.

### Building and running the web microservice 

1. Open the `WebMicroServiceSample/pom.xml` file to check the used versions. They are defined as properties at the beginning of the file:
```
    <ads.samples.version>1.0.0</ads.samples.version>
    <ads.execution-api.version>2.1.1</ads.execution-api.version> 
```
`ads.samples.version` is the version of the decision service archive `loanApproval` that you previously installed. 
This sample was tested with the version `ads.execution-api.version` of the artifact `execution-api`.<br> 
The POM file excludes compiling the classes that contain `withML` in their package. They are used in the web microservice that uses machine learning.

_Note_: The name of the web microservice is the artifact ID given in this POM file. The decision service archive is embedded in the web microservice because this POM file contains a dependency for the `loanApproval` artifact.

2. Run the following command in the `WebMicroServiceSample/` directory to create the docker image, and create a Kubernetes descriptor for the
   web microservice.
```
  mvn clean package -Dquarkus.container-image.build=true -s settings.xml 
```
The image is ready to be pushed when you see the message ``` BUILD SUCCESS```.

3. Manually push the docker image to your docker registry of your Kubernetes
   cluster, and use the generated descriptor `target/kubernetes/kubernetes.yml` to create
   the container on your Kubernetes cluster.
   
### Optional: Automating the deployment by using Quarkus 
You can also use the Quarkus plug-in to automate the deployment to a Kubernetes cluster. You can use the following commands.

   * To push the image to the docker image registry:
  ```sh
mvn clean package -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=<IMAGE_REGISTRY_URL> -s settings.xml
```
   * To create the containers in your Kubernetes cluster, use the `-Dquarkus.kubernetes.deploy=true` option:
   ```sh
mvn clean package -Dquarkus.kubernetes.deploy=true -Dquarkus.container-image.push=true -Dquarkus.container-image.registry=<IMAGE_REGISTRY_URL> -s settings.xml
 ```

_Note_: the `kubectl` client must be installed and configured to access your Kubernetes cluster.

For more information about the Quarkus usage, see the [Quarkus documentation](https://quarkus.io/guides/deploying-to-kubernetes).

   * To forward the service port to your machine to test it when the web microservice is deployed to the Kubernetes container:
   ```sh
kubectl port-forward  service/ads-sample-micro-service 8080:80 &
   ```

### Testing the web microservice

Call the web microservice by posting the following HTTP requests with cURL.
   * To get the available operations:
```sh
curl -s -H "Content-Type: application/json" http://<HOST_NAME>:<PORT_NAME>/decision/operations|jq
```
One operation is defined as follows:
   ```
[
  "approval"
]
   ```
   * To get an input example of the `approval` operation:
```sh
curl http://<HOST_NAME>:<PORT_NAME>/decision/input-sample?operation=approval
```
   * To execute the decision `approval` with input data, use the JSON files provided in the `test/approval` directory. For example:
```sh
cat test/approval/approved.json | curl -X POST -d @- -H "Content-Type: application/json" http://<HOST_NAME>:<PORT_NAME>/decision?operation=approval
```

You can also use the scripts defined in the test directory:
   * `test/approval/test-approval.sh` to execute the decision on two input data.
   * `test/approval/test-approval-input-sample.sh` to get an input example of the decision.
   * `test/test-operations.sh` to get the list of the operations that can be used.
   
You might need to change the hostname and port in the scripts to run them. They use `localhost:8080` by default.  

# Building, running, and testing the web microservice with machine learning

In this section, you build another web microservice that embeds the decision service archives `loanApproval` and `approvalWithML`. These decision service archives  contain a predictive model. 
To prepare the web microservice, you need to:
   * deploy the machine learning model,
   * complete the machine learning provider information,
   * make sure you have a trusted certificate to call the machine learning provider or add one in the code.

For more information about the metadata for machine learning, see [Decision service metadata](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-decision-service-metadata).

### Building and running the web microservice with approvalWithML

1. Open the `WebMicroServiceSample/pomML.xml` file to check the used versions. They are defined as properties at the beginning of the file:
```
    <ads.samples.version>1.0.0</ads.samples.version>
    <ads.execution-api.version>2.1.1</ads.execution-api.version> 
```
`ads.samples.version` is the version of the decision service archive `approvalWithML` that you previously installed. 
This sample was tested with the artifact  `execution-api` with the version `ads.execution-api.version`.<br>
The POM file excludes compiling the classes that contain `simple` in their package. They were used for the web microservice that does not use machine learning.

**Note**: The two decision service archives are embedded in the web microservice because this POM file contains dependencies for the `loanApproval` and  `approvalWithML` artifacts.

2. Deploy the machine learning model described in this [PMML file](../../archives/models/Approval-SGDClassifier-StandardScaler-pmml.xml), as described in this [procedure](../../archives/README.md#using-an-archive-containing-a-predictive-model).
You use `ML_DEPLOYMENT_ID` in the next step.

3. Complete the provider information in the source file.
`WebMicroServiceSample/src/main/java/com/ibm/ads/samples/quarkus/withML/DecisionResourceWithML.java`:
   * `ML_PROVIDER_INFO_KEY` is given in this [README](../../archives/README.md) file about the archive `approvalWithML`.
   * `ML_DEPLOYMENT_ID` is obtained in the previous step.
   * `ML_PROVIDER_URL` is the URL that your IT provided for you for the installed instance of OPS.
   
Save your changes.     
                   
4. If the trusted certificate of your machine learning provider is already added in the keystore, set the constant `ML_CODE_CERTIFICATE_REQUIRED` to `false`, and skip the remaining steps.

To add the certificate:
   * keep the constant `ML_CODE_CERTIFICATE_REQUIRED` set to `true`, 
   * get the certificate to call the machine learning model that is deployed in OPS. Open the `ML_PROVIDER_URL` in your browser to view and download the pem certificate. Copy this file to 
`WebMicroServiceSample/src/main/resources/adsSamplesQuarkus/ads-ml.pem`. It is used at execution time to validate the certificate.

5. Run the following command in the `WebMicroServiceSample/` directory to create the docker image and a Kubernetes descriptor for the
      web microservice. You use the `pomML.xml` configuration file.
      ```sh
     mvn clean package -Dquarkus.container-image.build=true -s settings.xml -f pomML.xml
      ```
       
   The docker image is ready to be deployed when you see the message ``` BUILD SUCCESS```.
6. Manually push the docker image to your docker registry of your Kubernetes
   cluster, and use the generated descriptor `target/kubernetes/kubernetes.yml` to create
      the container on your Kubernetes cluster. Get the hostname and port number for testing.

You can also follow the previous section to use the Quarkus plug-in to automate the deployment.
In this case, add the parameter `-f pomML.xml` when calling Maven to use the image embedding the two archives.

### Testing the web microservice

Call the web microservice by posting the following HTTP requests with cURL:
   * To get the operations list:
   ```sh
curl -s -H "Content-Type: application/json" http://<HOST_NAME>:<PORT_NAME>/decision/operations|jq
   ```
Three operations are defined:
   ```
[
  "approval",
  "approvalWithML",
  "loan-risk-score"
]
   ```
   * To get an input example of the `approvalWithML` operation:
```sh
curl http://<HOST_NAME>:<PORT_NAME>/decision/input-sample?operation=approvalWithML
```
   * To execute the decision `approvalWithML` with input data, use the JSON files provided in the `test/approvalWithML` directory. For example:
```sh
cat test/approvalWithML/badScore.json | curl -X POST -d @- -H "Content-Type: application/json" http://<HOST_NAME>:<PORT_NAME>/decision?operation=approvalWithML
```

You can also use the scripts defined in the test directory:
   * `test/approvalWithML/test-approvalWithML.sh` to execute the decision on two input data.`
   * `test/approvalWithML/test-approvalWithML-input-sample.sh` to get an input example of the decision.
   * `test/test-operations.sh` to get the list of the operations that can be used.
   
You might need to change the hostname and port in the scripts to run them.   

# Sample details

## Browsing the code for the web microservice without machine learning

Open the file `WebMicroServiceSample/src/main/java/com/ibm/ads/samples/quarkus/simple/DecisionResource.java`. 
The endpoints are defined as public functions with Quarkus annotations to declare:
   * their type (GET or POST) 
   * their path
   * their input and output type
   
The docker image description is defined in `src/main/docker/Dockerfile.jvm`.

The annotations for using the decision execution are in `src/main/resources/application.properties`. They are for a non-production environment.
You get the ones for a production environment in the [documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=api-tracking-usage).

## Browsing the code for the web microservice with machine learning
 
Open the file `WebMicroServiceSample/src/main/java/com/ibm/ads/samples/quarkus/withML/DecisionResourceWithML.java`. 
* The endpoints are defined as public functions with Quarkus annotations, as in the previous file.
* The `getSSLContext` function defines the SSL context by reading the certificate that you previously put in the resources.
* The `execute` endpoint function begins by defining an execution context where:
    * the execution ID is set
    * the metadata for the machine learning provider are defined
    * the SSL context is set
    
The docker image description is defined in `docker/Dockerfile.jvm`.

The annotations for using the decision execution are in `src/main/resources/application.properties`. They are for a non-production environment.
You get the ones for a production environnement in the [documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=api-tracking-usage).
   
# Getting more information
   * about the [Execution API documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-executing-decision-execution-java-api).
   * about Quarkus in [this documentation](https://quarkus.io/get-started/).
   * about the machine learning metadata in [Decision service metadata](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/22.0.2?topic=services-decision-service-metadata).
