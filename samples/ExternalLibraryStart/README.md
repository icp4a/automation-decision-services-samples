# Tutorial: Using an external library

## Description
This tutorial shows you how to use an external library in a decision service that gives advice about the weather. 
This decision service is based on the getting started tutorial result, but is enhanced by using an external library that defines a **Person** custom type.

In Automation Decision Services, you can use external libraries in decision services.
They allow you to extend rule authoring by using custom data types and functions from Java libraries.
For more information on decision models and external libraries, see [Working with external libraries](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/external_lib_topics/con_ext_lib_intro.html).

Look at the sample [External library giving geo localization facilities](https://github.com/icp4a/automation-decision-services-samples/tree/master/samples/ExternalLibraryGeo) to get another example of an external library.

## Learning objectives
- Build an external library from a Java library.
- Customize the vocabulary that is used in the decision model.
- Integrate an external library in a decision service.
- Use the external types and functions in a decision model.

## Audience

This sample is for anyone who wants to use a Java library in Automation Decision Services.

## Time required

15 minutes

## Prerequisites

Prepare by doing the tutorial [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision projects in Business Automation Studio. You work with a sample decision service by importing it into a decision project and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a CI/CD stack that is connected to Decision Designer. They must give you the host name of a runtime archive repository where you can deploy the external library.
- **Apache Maven**: You can download it from https://maven.apache.org.

Download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
Open this [link](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.1) and choose `Download Zip` in the `Code` menu.
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
 The StringUtilities class defines two static functions to be used on string. To get more information on the annotations, look into [Annotations](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/external_lib_topics/con_annotation_api.html).
 - `adsSampleLibrary`: Contains the reference files to build the library. Vocabulary files for English, French, German and Italian are provided in `reference/bom`. This file defines, for example, the navigation sentence of the StringUtilities.capitalize static function and its precedence policy. To get more information on the vocabulary, look into [Vocabulary](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/external_lib_topics/con_voc_intro.html).
 
Each repository contains a `pom.xml` file to build and deploy the library. Open the `adsSampleLibrary/pom.xml` file. The description of this artifact is shown in the decision library. It has the packaging decision-library, which is defined by the Automation Decision Services mojo. The library depends on the sampleLibrary artifact. This library can be used in English, in French or in German.

## Step 2: Checking deployed artifacts

You check if the sample library has already been deployed in your repository manager.

1. Browse your repository manager release repository.
2. Look for `ads/samples/adsSampleLibrary/1.0.0`. If it exists, the sample library has already been deployed, you can directly go to Task2. If it does not exist, you have to deploy the sample library.

**Note**: if you wish to deploy another version, change the version from `1.0.0` to the version you want, for instance `1.0.myInitials`, in:
- one occurence in `automation-decision-services-samples/samples/ExternalLibraryStart/pom.xml`, 
- one occurence in `automation-decision-services-samples/samples/ExternalLibraryStart/sampleLibrary/pom.xml`,
- two occurences in `automation-decision-services-samples/samples/ExternalLibraryStart/adsSampleLibrary/pom.xml`. 

## Step 3: Configure the Maven settings to build your external library

If your Maven settings file is configured to access the Automation Decision Services artifacts, you can skip this step.
Else you have to define a Maven settings file by completing the template settings.xml file provided in this sample:
1. Open **automation-decision-services-samples/samples/ExternalLibraryStart/settings.xml**.
2. Replace all `TO BE SET` tags by the appropriate values:
   * `LOCAL REPOSITORY TO BE SET`: a directory on your machine where the artifacts will be downloaded.
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
    * `ads-mojo.version` is the version used to build the library. This sample was tested with the version 4.2.0.

The properties look as follows:
```
   <properties>
       <archive.repository.snapshot.url>SNAPSHOT URL TO BE SET</archive.repository.snapshot.url>
       <archive.repository.release.url>RELEASE URL TO BE SET</archive.repository.release.url>
        <ads-mojo.version>4.2.0</ads-mojo.version>
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
- Creates the `adsSampleLibrary.jar` file: it encapsulates the Java library to be able to use it into Decision Designer. It uses the vocabulary files defined into `adsSampleLibrary/reference/bom`.
- Creates a description file of the library for each vocabulary in `adsSampleLibrary/target/` with the form `adsSampleLibrary_LOCALE.md` file: it will be used as a base of the description into Decision Designer.
- Deploys the two jars in the repository manager.
You can go to the next task to import the external library when you see the message BUILD SUCCESS.

# Task 2: Importing an external library

You import the external library in Decision Designer.

**About this task**

In this task, you... 
- Optionaly check the credentials to the repository manager.
- Create a new project that uses the external library in Decision Designer.
- Explore the external library.

## (Optional) Step 1: Checking the credentials for the repository manager
This step lets you understand where the external library is searched for. It is normally done by your IT. For more details, see [Configuring credentials for a Maven repository manager](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.install/op_topics/tsk_admin_add_maven.html).
1. Open the admin-platform URL in a web browser.
2. Filter the table by using the word *Maven*. One value should be defined, giving access to the repository where you have deployed the external library.
3. If no value is defined, add a new one with the following information:
  - ID: The URL to the repository manager.
  - Authentication type: For example, USERNAME then provide the user and password.
  - Credential type: MAVEN.
  
 ## Step 2: Creating a new project that uses the external library
 In this step, you create a new project that uses the external library.
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. Click on the navigation menu at the top left of the page, expand Design and select **Business automations**.
3. Click Decision to see the decision automations.
4. Click Create and select Decision automations to make a project.
5. Enter a name for the project. Use a unique name. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. For simplicity, we use `Library Sample` in this sample documentation. After entering your name for the decision project, enter the following description:
**Automated Decision Service sample integrating an external library in the daily advice project.**
6. Click **Create** to make your decision project.
7. Click the three dots next to your project name to open the contextual menu and select **Settings**.
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
4. Click your project name in the breadcrumbs to continue by importing a decision service.

# Task 3: Using an external library in a decision service
You import the external library in Decision Designer.

**About this task**

In this task, you... 
- Import the Getting Started answer project.
- Declare this project is using the external library.
- Use the external library in the Daily advice decision model.
- Validate your changes.


## Step 1: Importing sample projects
In this step, you import the decision project where you will use the external library. You import the getting started tutorial answer.

**Procedure**

1. In your project, be sure to be in the **Decision services** tab.
2. Click **Browse samples**.
3. Choose **Getting started** in the Discovery part, click on Import and wait for the decision service to be imported.
4. Click **Getting Started** to open the decision service. It contains five decision models that correspond to the Getting started tutorial. 
You work on the final answer, which is named Daily advice. It also contains a data model defining custom types.

**Note** 
You can also import the **French** project and use the decision service named `Conseil du jour` because this external library is also defined for French.

You can also import the **German** project and use the decision service named `Heutige Empfehlung` because this external library is also defined for German.

You can also import the **Italian** project and use the decision service named `Consiglio quotidiano` because this external library is also defined for Italian.

## Step 2: Defining the dependency to  the external library in the Getting Started decision service
In this step, you enable your decision service to use the external library.

**Procedure**

1. Click the **External libraries** tab. Currently there is no dependency to external library.
2. Click **Add**. 
3. Select `adsSampleLibrary` and click **OK**. Now you can use the library in the decision service.
 
 **Note**: You can add an external library to a decision service when it already has a data model. This is the case of the Getting started you just imported.
 ## Step 3 Use the external library in the Daily Advice model
 You replace the String  type used for the input name by the new person type.
 
 **Procedure**
 
1. Click the **Decision models** tab.
2. Click **Daily advice** to open the decision model result of the getting started tutorial. It builds a daily advice decision that is based on weather data and a name.       
3. Click on the **Validation** tab. Three data sets are provided. Run the Avery data set. It gives the following output:
"Hello Avery! Cold day! Take a coat."
You change the beginning of this advice by replacing Hello by the greeting provided in the library and by the initials of the person.
4. Click **Modeling** and select the **Name** input node. Change the node name to **Person**. Change the node output type to the **person** custom type.

**Note:** In the custom types, you see the new types that are defined in your external libraries as well as the type that are defined in the data model of this decision service.

5. Click the **Daily advice** node. In the **Logic** tab, select **Advice rule**.
6. Change the rule to use the functions provided in the adsSampleLibrary:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the initials of Person + "! " + 'Weather advice';
```

**Note:** You can use the rule completion menu to select the functions. You can use another function from the library for the name of the Person such as the first name.

If you used the French project, the rule is
```
si 
     Personne est défini 
alors 
affecter à décision la manière de saluer de Personne en majuscules + " " + les initiales de Personne + "! " + 'Conseil lié aux prévisions' ;
```
If you used the German project, the rule is
```
wenn
	Person ist definiert
dann
	setze Entscheidung auf  die Anrede von Person in Großbuchstaben + " " + die Initialen von Person  + "! "  + Wetterempfehlung ;
```
If you used the Italian project, the rule is
```
se
	Persona è definito 
allora
	assegna decisione a il saluto di Persona maiuscola + " " + le iniziali di Persona + "! " + 'Consiglio meteo' ;
```
## Step 4: Validating your changes

**Procedure**

1. Click the **Run** tab. The Avery data set shows an error because you changed one input data type.
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
6. Back to the **Run** tab, run your data set. You get the following output: 
`"Salut Dominique Dupond! Cold day! Take a coat."`

# (Optional) Task 4: Executing a decision service using an external library
You execute a decision service including an external library in the same way as any other decision service. The steps described here are more detailed in task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/support/knowledgecenter/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html).

**About this task**

In this task, you...
- Connect the **Library sample** project to a Git repository. 
- Create a new version of your project.
- Deploy your decision service archive.
- Run the decision service archive using a curl command.

**Procedure**

1. Create a new repository on GitHub, get its URI and the credentials to access it.
3. Connect your decision project to this Git repository. 
4. In the **Share changes** tab, select the **Getting started** decision service.
5. Click **Share** to share your changes in the Git repository.
6. In the **View history** tab, create a new version with the name FirstVersion.
7. In the **Deploy** tab, deploy this version. Wait for the deployment to be completed.
8.  If you want to run your decision service archive, copy the decision id by clicking on the Copy icon.
Encode the decision Id by replacing slash characters (/) with %2F to use it in the next curl instruction.
9. Get an access token to be able to call the runtime:
      - In the deployment page, open the Developers tools,
      - Open the Storage tab
      - Expand Local storage, and select the URL of your ADS instance note the value of the access_token.
10. In a terminal, call the following command where you replace `<ACCESS TOKEN>`, `<ADS embedded runtime URL>` and `<Encoded decision Id>` by the appropriates values
   
  ``` 
  curl --insecure -v -H "Authorization: Bearer <ACCESS TOKEN>" \
   -X POST "https://<ADS embedded runtime HOST><Encoded decision Id>/operations/loanValidationDecisionModel" \
   -H "accept: application/json" -H "Content-Type: application/json" \
   -d "{\"input\":{\"person\": {\"country\": \"FRANCE\", \"inputName\": \"Dominique Dupond\"}, \
                    \"weather\":{\"temperature\":\"cold\",\"rainForecast\":99,\"stormAlert\":false}}}" -k
```
   
 You get the following response
 
``` 
...
"output":"Salut DD! It would be wise to stay home. There is a storm alert."
...
``` 
