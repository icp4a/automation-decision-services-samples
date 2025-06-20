# Tutorial: Making an external library available in a decision service

## Description
This tutorial shows how to build and deploy an external library so that it can be used by a business user in Automation Decision Services. 
This library defines a `person` type in Java, which can be used in any decision service. 

In Automation Decision Services, external libraries can be used in decision services to extend rule authoring with custom data types and functions from Java libraries.
For more information on decision models and external libraries, see `Working with external libraries`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=data-working-external-libraries)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=data-working-external-libraries).

For another example of an external library, see [External library giving geo localization facilities](../ExternalLibGeoSample/README.md).

## Learning objectives
- Build an external library from a Java library.
- Customize the vocabulary that is used in a decision model.
- Deploy the library to make it usable in a decision service.

## Audience

This sample is intended for integrators who want to make a Java library available in Automation Decision Services.

## Time required

10 minutes

## Prerequisites

Prepare by completing the tutorial `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=resources-getting-started). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision services in Automation Decision Service. 
- **An Artifact Repository Manager**: Your IT developers must provide a repository manager containing the Automation Decision Services artifacts required to build an external library that can be used in a decision service. This repository manager is also used to deploy
the external library and its dependencies. For more information, see `Using an external Maven repository`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=environment-using-external-maven-repository)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=environment-using-external-maven-repository).

  **Note**: This repository manager can be the one of the custom CI/CD stack. 

- **Apache Maven**: You can download Maven from https://maven.apache.org. This sample requires Maven 3.9.x and was tested with version 3.9.10.
- Enable Maven to access remote repositories hosted on HTTPS servers. For more information, see ([more](https://maven.apache.org/guides/mini/guide-repository-ssl.html)). <br>
  **Note**: (For testing only) You can disable SSL validation by using the following option: 
  ```
  -Daether.connector.https.securityMode=insecure
  ```

Download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
Open this [link](/../../) and choose `Download Zip` in the `Code` menu.
The task refers to **automation-decision-services-samples**, which is the directory in which you decompress the repository.

# Task 1: Building an external library

You use an editor and Maven to build the external library.

**About this task**

In this task, you will:
- Explore the sample code.
- Configure Maven to build and deploy a decision library.
- Build an external library for use in Decision Designer.

## Step 1: Importing and exploring the code

You explore the sources.

1. Go to the **automation-decision-services-samples/samples/ExternalLibraryBuildTutorial** directory on your computer.
2. Explore the sources. There are two directories:
 - `sampleLibrary`: Contains the Java classes Person and Country. Browse through them to see how annotations are used to define the decision library.
For more information on the annotations, look into `Annotations`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=annotations-)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=annotations-).
 - `adsSampleLibrary`: Contains the reference files to build the library in the `reference/bom` folder. It includes:
       - Vocabulary files named with the pattern `model_<Locale>.voc`, such as `model_en_US.voc` for English. These files define, for example, the navigation sentence for the StringUtilities.capitalize static function and its precedence policy. For more information on the vocabulary, look into `Vocabulary`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=vocabulary-)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=vocabulary-).
 
Each repository contains a `pom.xml` file to build and deploy the library. Open the `adsSampleLibrary/pom.xml` file. The description field of this artifact will be used in  Decision Designer to describe the decision library. This library depends on the `sampleLibrary` artifact and can be used in many languages, including English.

## Step 2: Configuring to build and deploy your external library

You complete the provided template Maven settings file to access your artifact repository manager, where the Automation Decision Services artifacts required to build the external library are deployed.

1. Open **automation-decision-services-samples/samples/ExternalLibraryBuildTutorial/settings.xml**.
2. To access the **artifact repository manager** to build the sample external library, replace the following placeholders :
   * %MAVEN_RELEASES-REPOSITORY_TO_BE_SET% : The URL of the **artifact repository manager** release repository from where you get the Automation Decision Services artefacts required to build an external library.
   * %USER TO BE SET%: A username that can access this repository manager.
   * %PASSWORD TO BE SET%: The username's password to access this repository manager.
3. Save the `settings.xml` file.

## Step 3: Building and deploying the library

In this step, you build and deploy the external library to a repository manager.

To build and deploy the external library, run the following command in the **automation-decision-services-samples/samples/ExternalLibraryBuildTutorial** directory:
```
mvn clean deploy -s settings.xml
```
This command:

- Creates the `sampleLibrary.jar` file which contains the Java classes of your library.
- Runs unit tests on this JAR.
- Creates the `adsSampleLibrary.jar` file which encapsulates the Java library for use in Decision Designer. It uses the extensions and vocabulary files defined in `adsSampleLibrary/reference/bom`.
- Generates a description file of the library for each vocabulary in `adsSampleLibrary/target/` named in the format `adsSampleLibrary_LOCALE.md`. This file is used as a base for the description in Decision Designer.
- Deploys the two JAR files in the repository manager.
When you see the message BUILD SUCCESS, you can proceed to the next task to import the external library.

**Note**: if you don't get the BUILD SUCCESS message, it means the sample library has not been deployed to your repository manager. This could be because it was already deployed with the version number `2500.0.6`. To check, follow these steps:

1. Open your ***Artifact repository manager** release repository, as provided in the previous step.
2. Look for `ads/samples/adsSampleLibrary/2500.0.6`. If it exists, the sample library has already been deployed. 
3. If you want to deploy another version, change the version from `2500.0.6` to your desired version, for example, `1.myInitials`, in the following XML files:
- `automation-decision-services-samples/samples/ExternalLibraryBuildTutorial/pom.xml` 
- `automation-decision-services-samples/samples/ExternalLibraryBuildTutorial/sampleLibrary/pom.xml` 
- `automation-decision-services-samples/samples/ExternalLibraryBuildTutorial/adsSampleLibrary/pom.xml` 

Then, run the following command again:

```
mvn clean deploy -s settings.xml
```
**Note**: The pom file provided in this sample to build and deploy the external library can be generated using a maven command.
For more information about this feature see the `documentation`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=library-creating-pom-file-external)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=library-creating-pom-file-external).

# Task 2: Importing an external library

You ensure that a business user can import the external library in Decision Designer.

**About this task**

In this task, you... 
- Verify the credentials for the repository manager.
- Create a new project that can use the external library in Decision Designer.
- Explore the external library.

## Step 1: Checking the credentials for the repository manager
You check that Automation Decision Services is configured to search the repository manager where you deployed the external library. For more information about configuring credentials, see `Configuring credentials for a Maven repository manager`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.0?topic=administering-configuring-credentials-maven-repository-manager)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.0?topic=administering-configuring-credentials-maven-repository-manager)).

1. Open the admin-platform URL in a web browser.
**Note**: If you do not have access to this URL, contact your IT developer to request the `Administer platform for decision services` permission.
2. Select the `Maven configuration` tab.
3. Ensure that a value is defined to provide access to the repository where the external library was deployed.
4. If no value is defined, click the `New` button and add one. Provide the `Repository URL`, authentication type, and credentials.
  
 ## Step 2: Importing the external library
 In this step, you create a project to import the external library as a business user would.

1. Use the credentials provided for your instance to sign in to Decision Designer.
2. Create a new project named `Test Library`.
3. Click the `Settings` icon in the top-right bar to open the Settings page.
4. Open the **External libraries** tab and click **Import**.
5. Enter the following values:
  - Group ID: ads.samples
  - Artifact ID: adsSampleLibrary
  - Version: 2500.0.6 (This is the version number you deployed in the previous task. If you deployed a different version, use that version number instead.)
  
6. Click **OK** and wait for the library to be imported. You see the library description from the `adsSampleLibrary/pom.xml` file, and that the library is available in many languages.
7. Click **adsSampleLibrary** to open the library. You see the library types and verbalization.
8. Once the library is imported successfully, you can delete the `Test Library` project.

You have completed this tutorial. Provide the business user with the Group ID, Artifact ID, and Version used in the last step so they can import the external library into their decision services. For details on how the business user integrates the external library in a decision service, refer to the  [External library start tutorial](../ExternalLibraryStartTutorial/README.md).

