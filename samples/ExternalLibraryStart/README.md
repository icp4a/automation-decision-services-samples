# Tutorial: Using an external library

## Description
This tutorial shows you how to use an external library in a decision project that gives advice about the weather. 
The project is based on the getting started tutorial, but is enhanced by using an external library that defines a **Person** custom type.

In IBM Automation Decision Services, you can use external libraries in decision projects.
They allow you to extend rule authoring by using custom data types and functions from Java libraries.
For more information on decision models and external libraries, see [Working with external libraries](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/external_lib_topics/con_ext_lib_intro.html).

## Learning objectives
- Build an external library from a Java library.
- Customize the vocabulary that is used in the decision model.
- Integrate an external library in a decision project.
- Use the external types and functions in a decision model.

## Audience

This sample is for anyone who wants to use a Java library in Automation Decision Services.

## Time required

15 minutes

## Prerequisites

Prepare by doing the tutorial [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision projects in IBM Business Automation Studio. You work with a sample decision project by importing it into a decision solution and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a CI/CD stack that is connected to Decision Designer. They must give you the host name of a runtime archive repository where you can deploy the external library.
- **Apache Maven**: You can download it from https://maven.apache.org.

Download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
Open this [link](https://github.com/icp4a/automation-decision-services-samples) and choose `Download Zip` in the `Code` menu.
The task refers to **automation-decision-services-samples** as the directory where you decompress the repository.

# Task 1: Building an external library

You use an editor and Maven to build the external library.

**About this task**

In this task, you...
- Explore the sample code.
- Check if the sample library has already been deployed.
- Configure the Maven settings.
- Edit the properties for deployment.
- Build an external library for use in Decision Designer.

This task should be done by an integrator.

## Step 1: Importing and exploring the code

You explore the sources.

1. Go to the **automation-decision-services-samples/samples/ExternalLibraryStart** directory on your machine.
2. Explore the sources. There are two directories:
 - `sampleLibrary`: Contains the Java classes StringUtilities, Person and Country. Browse through them to see the usage of annotations that define pure functions that are ready to be used in the decision models.
 The StringUtilities class defines two static functions to be used on string. To get more information on the annotations, look into [Annotations](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/external_lib_topics/con_annotation_api.html).
 - `adsSampleLibrary`: Contains the custom vocabulary in `reference/bom/model_en_US.voc`. This file defines, for example, the navigation sentence of the StringUtilities.capitalize static function and its precedence policy. To get more information on the vocabulary, look into [Vocabulary](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/external_lib_topics/con_voc_intro.html).
 
Each repository contains a `pom.xml` file to build and deploy the library. Open the `adsSampleLibrary/pom.xml` file. The description of this artifact is shown in the decision library. It has the packaging decision-library, which is defined by the Automation Decision Services mojo. The library depends on the sampleLibrary artifact.

## Step 2: Checking deployed artefacts

You check if the sample library has already been deployed in your repository manager.

1. Browse your repository manager release repository.
2. Look for `ads/samples/adsSampleLibrary/1.0.0`. If it exists, the sample library has already been deployed, you can directly go to Task2. If it does not exist, you have to deploy the sample library.

Note: if you wish to deploy another version, change the version from `1.0.0` to the version you want, for instance `1.0.myInitials`, in `automation-decision-services-samples/samples/ExternalLibraryStart/pom.xml`, 
`automation-decision-services-samples/samples/ExternalLibraryStart/sampleLibrary/pom.xml` and `automation-decision-services-samples/samples/ExternalLibraryStart/adsSampleLibrary/pom.xml`. In the last pom file, there are two occurences of the version number, one for the parent and one for the dependency.

## Step 3: Configure the Maven settings to build your external library

If your Maven settings file is configured to access the Automation Decision Services artefacts, you can skip this step.
Else you have to define a Maven settings file by completing the template settings.xml file provided in this sample:
1. Open **automation-decision-services-samples/samples/ExternalLibraryStart/settings.xml**.
2. Replace all `TO BE SET` tags by the appropriate values:
   * `LOCAL REPOSITORY TO BE SET`: a directory on your machine where the artefacts will be downloaded.
   * `MAVEN SNAPSHOT TO BE SET`: the URL of the repository manager snapshot repository.
   * `MAVEN RELEASE TO BE SET`: the URL of the repository manager release repository.
   * `MAVEN PUBLIC TO BE SET`: the URL of the repository manager public repository.
   * `USER TO BE SET`: a user name that can access the repository manager.
   * `PASSWORD TO BE SET`: the corresponding password to access the repository manager.
3. Save the `settings.xml` file.

## Step 4: Setting the properties to deploy your external library

To be able to deploy the library, you have to know:
   * the snapshot URL of the repository manager where you deploy your external library (for example, Nexus or Artifactory). 
   * the release URL of the repository manager where you deploy your external library.

**Note**: It can be the same repository manager where decision services are deployed.

1. Open **automation-decision-services-samples/samples/ExternalLibraryStart/pom.xml**.
2. Look at the property definitions:
    * `archive.repository.snapshot.url` is the URL where you deploy snapshots. This value is used in the distribution management part. 
    * `archive.repository.release.url` is the URL where you deploy snapshots. This value is used in the distribution management part. 
    * `ads-mojo.version` is the version used to build the library. This sample was tested with the version 2.3.3.

The properties look as follows:
```
   <properties>
       <archive.repository.snapshot.url>SNAPSHOT URL TO BE SET</archive.repository.snapshot.url>
       <archive.repository.release.url>RELEASE URL TO BE SET</archive.repository.release.url>
        <ads-mojo.version>2.3.3</ads-mojo.version>
    </properties>
```
3. Replace the `TO BE SET` by the appropriate values.
4. Save the `pom.xml` file. 

## Step 5: Building and deploying the library

In this step, you deploy the external library to a repository manager. 

Run the following command in the **automation-decision-services-samples/samples/ExternalLibraryStart** directory:
```
mvn clean deploy  -s settings.xml
```
This command...

- Creates the `sampleLibrary.jar` file: it contains the Java classes of your library.
- Run the unit tests on this jar.
- Creates the `adsSampleLibrary.jar` file: it encapsulates the Java library to be able to use it into Decision Designer.
- Deploys the two jars in the repository manager.
You can go to the next task to import the external library when you see the message BUILD SUCCESS.

# Task 2: Importing an external library

You import the external library in Decision Designer.

**About this task**

In this task, you... 
- Optionaly check the credentials to the repository manager.
- Create a new solution that uses the external library in Decision Designer.
- Explore the external library.

## (Optional) Step 1: Checking the credentials for the repository manager
This step lets you understand where the external library is searched for. It is normally done by your IT. For more details, see [Configuring credentials for a Maven repository manager](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.install/op_topics/tsk_admin_add_maven.html).
1. Open the admin-platform URL in a web browser.
2. Filter the table by using the word *Maven*. One value should be defined, giving access to the repository where you have deployed the external library.
3. If no value is defined, add a new one with the following information:
  - ID: The URL to the repository manager.
  - Authentication type: For example, USERNAME then provide the user and password.
  - Credential type: MAVEN.
  
 ## Step 2: Creating a new solution that uses the external library
 In this step, you create a new solution that uses the external library.
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. Click the **Decision services** part.
3. Click **Create** to create a decision solution.
4. Enter a name for the solution. Use a unique name. Do not reuse the name of a decision solution that already exists in your instance of Business Automation Studio. For simplicity, we use `Library Sample` in this sample documentation. After entering your name for the decision solution, enter the following description:
**Automated Decision Service sample integrating an external library in the daily advice project.**
6. Keep **Open in Designer** selected, and then click **Create** to make your decision solution.
7. Click the three dots next to your solution name to open the contextual menu and select **Settings**.
8. Open the **External libraries** tab and click **Import**.
9. Enter the following values:
  - Group ID: ads.samples
  - Artifact ID: adsSampleLibrary
  - Version: 1.0.0 (this should be the version you want to use, it may be different if you deployed a new version)
  
 Click **OK** and wait for the library to be imported.

## Step 3: Exploring the external library
In this step, you look at the external library that is loaded into Decision Designer as a usual business user.

1. Click **adsSampleLibrary** to open the library. You see all the types.
2. Click **person**. You see the vocabulary that can be used with this new type.
Check the properties given to build a person:  an input name and a country. Check the functions that you can use in your rules, for example, the greeting of a person.
3. Click **string**. You see the functions declared in the StringUtilities class using the vocabulary you set up.
4. Click your solution name in the breadcrumbs to continue by importing a decision project.

# Task 3: Using an external library in a decision project
You import the external library in Decision Designer.

**About this task**

In this task, you... 
- Import the Getting Started answer project.
- Declare this project is using the external library.
- Use the external library in the Daily advice decision model.
- Validate your changes.


## Step 1: Importing sample projects
In this step, you import the decision project where you will use the external library. You import the getting started tutorial answer.

1. In your solution, be sure to be in the **Explore projects** tab.
2. Click **Import** and choose **Import sample projects**.
3. Select **GettingStarted** in the menu, click on Import and wait for the project to be imported.
4. Click **Getting Started** to open the project. It contains five decision models that correspond to the Getting started tutorial. 
You work on the final answer, which is named Daily advice.

## Step 2: Defining the dependency to  the external library in the Getting Started project
In this step, you enable your decision project to use the external library.

1. Click the **External libraries** tab. Currently there is no dependency to external library.
2. Click **Add**.
3. Select `adsSampleLibrary` and click **OK**. Now you can use the library in the decision project.
 
 ## Step 3 Use the external library in the Daily Advice model
 You replace the String  type used for the input name by the new person type.
 
1. Click the **Decision models** tab.
2. Click **Daily advice** to open the decision model solution of the getting started tutorial. It builds a daily advice decision that is based on weather data and a name.       
3. Click on the **Validation** tab. Three data sets are provided. Run the Avery data set. It gives the following output:
"Hello Avery! Cold day! Take a coat."
You change the beginning of this advice by replacing Hello by the greeting provided in the library and by the initials of the person.
4. Click **Modeling** and select the **Name** input node. Change the node name to **Person**. Change the node output type to the **person** custom type.

**Note:** In the custom types, you see the new types that are defined in your external libraries as well as the type that are defined in the data model of this decision project.

5. Click the **Daily advice** node. In the **Logic** tab, select **Advice rule**.
6. Change the rule to use the functions provided in the adsSampleLibrary:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the initials of Person + "! " + 'Weather advice';
```

**Note:** You can use the rule completion menu to select the functions. You can use another function from the library for the name of the Person such as the first name.

## Step 4: Validating your changes
1. Click the **Validation** tab. The Avery data set shows an error because you changed one input data type.
2. Click the three dots next to Person and select **Delete** in the menu.
Now, you can enter a new value.
3. Expand **Person**: 
    - Set the country value to FRANCE.
    - Set `dominique dupont` as the input name.
4. Click **Run**. You get the following output: `"Salut DD! Cold day! Take a coat."`
5. Click **Modeling** and edit **Advice rule** again to use the full name of the person:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the full name of Person + "! " + 'Weather advice';
```
6. Back to the **Validation** tab, run your data set. You get the following output: 
`"Salut Dominique Dupond! Cold day! Take a coat."`

**Note:** You can use the CI/CD stack that is associated with Decision Designer to publish, build, and deploy your decision service. Follow the instructions in the task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_20.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html).
