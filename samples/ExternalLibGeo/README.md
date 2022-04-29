# Sample: External library of geolocalization functions

## Description
This sample defines an external library that provides geolocalization functions that are ready for use in Automation Decision Services.
For more information about decision models and external libraries, see [Working with external libraries](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.x?topic=decisions-working-external-libraries).

## Learning objectives
- Build an external library from a Java library.
- Customize the vocabulary that is used in a decision model.
- Integrate an external library into a decision service.
- Use the external types and functions in a decision model.

## Audience

This sample is for anyone who wants to use a Java library in Automation Decision Services.

## Time required

10 minutes

## Prerequisites

To become familiar with the development environment, do the tutorial [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision projects in Automation Decision Service. You work with a sample decision service by importing it into a decision project and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a CI/CD stack that is connected to Decision Designer. They must give you the host name of a runtime repository into which you can deploy the external library.
- **Apache Maven**: You can download Maven from https://maven.apache.org.

Download a compressed file of the `automation-decision-services-samples` Git repository to your computer.
Open [Automation Decision Services samples](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2), click `Code` and select `Download Zip` in the menu.
The sample refers to **automation-decision-services-samples** as the directory where you decompress the repository.

# Setting up the sample

- Follow the instructions in Task 1: Building an external library of the tutorial [Using an external library](../ExternalLibraryStart/README.md) to build and deploy the external library.
- Check the credentials for the repository manager as described in [Task2, Step1: Checking the credentials for the repository manager](../ExternalLibraryStart#optional-step-1-checking-the-credentials-for-the-repository-manager).
- Create a new project in Decision Designer. Import the compressed file for this sample: `automation-decision-services-samples/samples/ExternalLibGeo/project/PricingWithGeo.zip`.
- Open the settings of your project to define the external library:
  - Group ID: ads.samples
  - Artifact ID: adsGeoLibrary
  - Version: 1.0.15
  
# Sample details

- Open the `Capitals shipment pricing` decision service. It contains three decision models:
   - `Location`: Uses the external library to find the capital or country of the entered name.
   - `Distance`: Uses the external library to calculate the distance between two locations.
   - `Shipping price`: Determines a shipping price that is based on the distance and the shipping policies.
- Run the models by using the provided data sets.

