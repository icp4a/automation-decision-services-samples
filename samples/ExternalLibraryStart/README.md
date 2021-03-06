# Tutorial: Using an external library

## Description
This tutorial shows you how to use an external library in a decision service that gives advice about the weather. 
This decision service is based on the getting started tutorial result, but it includes an external library that defines a **Person** custom type.

In Automation Decision Services, you can use external libraries in decision services.
You use them to extend rule authoring to include custom data types and functions from Java libraries.
For more information on decision models and external libraries, see [Working with external libraries](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/21.0.x?topic=decisions-working-external-libraries).

For another example of an external library, see [External library giving geo localization facilities](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/ExternalLibraryGeo).

## Learning objectives
- Build an external library from a Java library.
- Customize the vocabulary that is used in a decision model.
- Integrate an external library into a decision service.
- Use the external types and functions in the decision model.

## Audience

This sample is for anyone who wants to use a Java library in Automation Decision Services.

## Time required

15 minutes

## Prerequisites

Prepare by doing the tutorial [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/dba_ddesigner_intro.html). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision projects in Business Automation Studio. You work with a sample decision service by importing it into a decision project and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a CI/CD stack that is connected to Decision Designer. They must give you the host name of a runtime archive repository where you can deploy the external library.
- **Apache Maven**: You can download it from https://maven.apache.org.

Download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
Open this [link](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2) and choose `Download Zip` in the `Code` menu.
The task refers to **automation-decision-services-samples**, which is the directory in which you decompress the repository.

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

1. Go to the **automation-decision-services-samples/samples/ExternalLibraryStart** directory on your computer.
2. Explore the sources. There are two directories:
 - `sampleLibrary`: Contains the Java classes StringUtilities, Person and Country. Browse through them to see the usage of annotations that define pure functions that are ready to be used in the decision models.
 The StringUtilities class defines two static functions to be used on string. To get more information on the annotations, look into [Annotations](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/external_lib_topics/con_annotation_api.html).
 - `adsSampleLibrary`: Contains the reference files to build the library. Several vocabulary files are provided in `reference/bom`, including the one for English. These files define, for example, the navigation sentence of the StringUtilities.capitalize static function and its precedence policy. To get more information on the vocabulary, look into [Vocabulary](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/external_lib_topics/con_voc_intro.html).
 
Each repository contains a `pom.xml` file to build and deploy the library. Open the `adsSampleLibrary/pom.xml` file. The description of this artifact is shown in the decision library. It has the packaging decision-library, which is defined by the Automation Decision Services mojo. The library depends on the sampleLibrary artifact. This library can be used in many languages including English.

## Step 2: Checking deployed artifacts

You check if the sample library has already been deployed to your repository manager.

1. Open your repository manager release repository.
2. Look for `ads/samples/adsSampleLibrary/1.0.0`. If it exists, the sample library is already deployed. You can go to Task 2 directly. If the file does not exist, you must deploy it.

**Note**: If you want to deploy another version, change the version from `1.0.0` to the version number that you want to use, for example, `1.0.myInitials`, in the following XML files:
- `automation-decision-services-samples/samples/ExternalLibraryStart/pom.xml` (one instance)
- `automation-decision-services-samples/samples/ExternalLibraryStart/sampleLibrary/pom.xml` (one instance)
- `automation-decision-services-samples/samples/ExternalLibraryStart/adsSampleLibrary/pom.xml` (two instances) 

## Step 3: Configuring the Maven settings to build your external library

If your Maven setting file is configured to access the Automation Decision Services artifacts, you can skip this step.
Otherwise, you must define a Maven setting file by completing the template settings.xml, which is provided in this sample.
1. Open **automation-decision-services-samples/samples/ExternalLibraryStart/settings.xml**.
2. Replace all `TO BE SET` tags by the appropriate values:
   * `LOCAL REPOSITORY TO BE SET`: A directory on your machine in which the artifacts are downloaded.
   * `MAVEN SNAPSHOT TO BE SET`: The URL of the repository manager snapshot repository.
   * `MAVEN RELEASE TO BE SET`: The URL of the repository manager release repository.
   * `MAVEN PUBLIC TO BE SET`: The URL of the repository manager public repository.
   * `USER TO BE SET`: A username that can access the repository manager.
   * `PASSWORD TO BE SET`: The username's password to access the repository manager.
3. Save the `settings.xml` file.

## Step 4: Setting the properties to deploy your external library

To be able to deploy the library, you must have the following information:
   * The snapshot URL of the repository manager in which you deploy your external library (for example, Nexus or Artifactory). 
   * The release URL of the repository manager in which you deploy your external library.

**Note**: The repository manager can be the one for the custom CI/CD stack.

1. Open **automation-decision-services-samples/samples/ExternalLibraryStart/pom.xml**.
2. Look at the property definitions:
    * `archive.repository.snapshot.url`: The URL in which you deploy snapshots. This value is used in the distribution management part. 
    * `archive.repository.release.url`: The URL in which you deploy releases. This value is used in the distribution management part. 
    * `ads-mojo.version` The version that is used to build the library. (**Note:** This sample was tested with Mojo 4.6.2.)

3. Replace the instances of `TO BE SET` by the appropriate values:

```
   <properties>
       <archive.repository.snapshot.url>SNAPSHOT URL TO BE SET</archive.repository.snapshot.url>
       <archive.repository.release.url>RELEASE URL TO BE SET</archive.repository.release.url>
        <ads-mojo.version>4.6.2</ads-mojo.version>
    </properties>
```
4. Save the `pom.xml` file. 

## Step 5: Building and deploying the library

In this step, you deploy the external library to a repository manager. 

Run the following command in the **automation-decision-services-samples/samples/ExternalLibraryStart** directory:
```
mvn clean deploy  -s settings.xml
```
This command...

- Creates the `sampleLibrary.jar` file. It contains the Java classes of your library.
- Runs the unit tests on this jar.
- Creates the `adsSampleLibrary.jar` file. It encapsulates the Java library for use in Decision Designer. It uses the vocabulary files defined into `adsSampleLibrary/reference/bom`.
- Creates a description file of the library for each vocabulary in `adsSampleLibrary/target/` with the form `adsSampleLibrary_LOCALE.md` file. It is used as a base for the description in Decision Designer.
- Deploys the two jars in the repository manager.
When you see the message BUILD SUCCESS, you can go to the next task to import the external library.

# Task 2: Importing an external library

You import the external library in Decision Designer.

**About this task**

In this task, you... 
- (Optional) Check the credentials to the repository manager.
- Create a new project that uses the external library in Decision Designer.
- Explore the external library.

## (Optional) Step 1: Checking the credentials for the repository manager
You search for the external library. The task is normally done by your IT developers (see [Configuring credentials for a Maven repository manager](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.install/op_topics/tsk_admin_add_maven.html).
1. Open the admin-platform URL in a web browser.
2. Filter the table by using the word *maven*. One value should be defined to give access to the repository in which you have deployed the external library.
3. If no value is defined, add a one by using the following information:
  - ID: The URL to the repository manager.
  - Authentication type: For example, USERNAME, and then provide the user and password.
  - Credential type: MAVEN
  
 ## Step 2: Creating a new project that uses the external library
 In this step, you create a project that uses the external library.
 
1. Sign in to your instance of Business Automation Studio to access Decision Designer. Use the credentials provided for your instance.
2. Click the navigation menu at the top left of the page, expand Design, and select **Business automations**.
3. Click **Decision** to see the decision automations.
4. Click **Create** and select Decision automations to make a project.
5. Enter a name for the project. Use a unique name. Do not reuse the name of a decision project that already exists in your instance of Business Automation Studio. We use `Library Sample` in this sample documentation. After entering your name for the decision project, enter the following description:
**Automated Decision Service sample integrating an external library in the daily advice project.**
6. Click **Create** to make your decision project.
7. Click the three dots (**...**) next to your project name to open the contextual menu and select **Settings**.
8. Open the **External libraries** tab and click **Import**.
9. Enter the following values:
  - Group ID: ads.samples
  - Artifact ID: adsSampleLibrary
  - Version: 1.0.0 (This is the version you want to use. It might be different if you deployed a new version.)
  
10. Click **OK** and wait for the library to be imported.

## Step 3: Exploring the external library
In this step, you look as a business user at the library that is loaded into Decision Designer.

1. Click **adsSampleLibrary** to open the library. You see the library types and verbalization in English.
2. Click **person**. You see the vocabulary that can be used with this new type.
Check the properties given to build a person: input name and country. Check the functions that you can use in your rules, for example, the greeting of a person.
3. Click **string**. You see the functions declared in the StringUtilities class by using the vocabulary you set up.
4. Click your project name in the breadcrumbs to continue by importing a decision service.

**Note** 
At the project level, you can see the library description in English. At the decision service level, you can see the library description in the locale of the current decision service.

# Task 3: Using an external library in a decision service
You import the external library into Decision Designer.

**About this task**

In this task, you... 
- Import the Getting Started project.
- Import the external library in this project.
- Use the external library in the Daily advice decision model.
- Validate your changes.


## Step 1: Importing sample projects
In this step, you import the decision project in which you use the external library. You import the answer to the getting started tutorial.

**Procedure**

1. Open the **Decision services** tab if it is not open.
2. Click **Browse samples**.
3. Choose **Getting started** in the Discovery part, click **Import**, and wait for the decision service to be imported.
4. Click **Getting Started** to open the decision service. It contains five decision models that correspond to different lessons in the Getting started tutorial. 
You work on the answer to the tutorial, which is named Daily advice. It also contains a data model defining custom types.

**Note** 
You can also import the **French** project and use the decision service named `Conseil du jour` to use this external library in French.

You can also import the **German** project and use the decision service named `Heutige Empfehlung` to use this external library in German.

You can also import the **Italian** project and use the decision service named `Consiglio quotidiano` to use this external library in Italian.

You can also import the **Spanish** project and use the decision service named `Consejo diario` to use this external library in Spanish.

You can also import the **Chinese** project and use the decision service named `我的自动化服务` to use this external library in Simplified Chinese.

## Step 2: Defining the dependency to the external library in the Getting Started decision service
In this step, you enable your decision service to use the external library.

**Procedure**

1. Click the **Data and libraries** tab. It show a data model and no dependency to an external library.
2. Click **Add library +**. 
3. Select `adsSampleLibrary` and click **OK**. Now, you can use the library in the decision service.
 
 **Note**: You can add an external library to a decision service that already has a data model. This is the case in the tutorial that you just imported.
 
## Step 3: Using the external library in the Daily Advice model
 You replace the String type that is used for the input name by the new person type. 
 
 **Procedure**
 
1. Click the **Models** tab.
2. Click **Daily advice** to open the decision model result of the getting started tutorial. It builds a daily advice decision that is based on a name and weather data.       
3. Click on the **Run** tab. Three data sets are provided. Run the Avery data set. It produces the following result:
`"Hello Avery! Cold day! Take a coat."`
Now, you change the beginning of this advice by replacing Hello by the greeting provided in the library and by the initials of the person.
4. Click **Modeling** and select the **Name** input node. Change the node name to **Person**. Change the node output type to the **person** custom type.

**Note:** In the custom types, you see the new types that are defined in your external libraries as well as the types that are defined in the data model of this decision service.

5. Click the **Daily advice** node. In the **Logic** tab, select **Advice rule**.
6. Change the rule to use the functions provided in the adsSampleLibrary:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the initials of Person + "! " + 'Weather advice';
```

**Note:** You can use the rule completion menu to select the functions. You can use another function from the library for the name of the Person such as the first name.

If you use the French project, the rule is
```
si 
     Personne est défini 
alors 
affecter à décision la manière de saluer de Personne en majuscules + " " + les initiales de Personne + "! " + 'Conseil lié aux prévisions' ;
```
If you use the German project, the rule is
```
wenn
	Person ist definiert
dann
	setze Entscheidung auf  die Anrede von Person in Großbuchstaben + " " + die Initialen von Person  + "! "  + Wetterempfehlung ;
```
If you use the Italian project, the rule is
```
se
	Persona è definito 
allora
	assegna decisione a il saluto di Persona maiuscola + " " + le iniziali di Persona + "! " + 'Consiglio meteo' ;
```
If you use the Spanish project, the rule is
```
si
	Persona está definido
entonces
	asignar a decisión el saludo de Persona en mayúsculas + " " + las iniciales de Persona + "! " + 'Recomendación meteorológica' ;
```
If you use the Simplified Chinese project, the rule is
```
如果
	'人' 是 已被定义的
那么
	设置 '决策' 为 '人' 的打招呼 大写 + " " + '人' 的姓名首字母  + "! " + '天气建议' ;
```
 
**Note**: The rest of this tutorial uses the English names, in another locale, you have to use the localized names.
 
## Step 4: Testing your changes
You run the modified model.

**Procedure**

1. Click the **Run** tab. The Avery data set shows an error because you changed one input data type.
2. Click the three dots (**...**) next to Person and select **Delete** in the menu.
Now, you can add a new value for the Person input value. 
3. Expand **Person** and set the following values: 
    - Country: France
    - Input name: `dominique dupont`
4. Click **Run**. You get the following output: `"Salut DD! Cold day! Take a coat."`
5. Click **Modeling** and edit **Advice rule** again to use the full name of the person instead of the initials. The rule in English becomes:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the full name of Person + "! " + 'Weather advice';
```
6. Back to the **Run** tab, run your data set. You get the following output: 
`"Salut Dominique Dupond! Cold day! Take a coat."`

# (Optional) Task 4: Executing a decision service using an external library
You run a decision service that includes an external library in the same way as any other decision service. These steps are more detailed than those in task 5 of [Getting started in Automation Decision Services](https://www.ibm.com/docs/SSYHZ8_21.0.x/com.ibm.dba.aid/gs_ddesigner_topics/tut_dd_t5_lsn.html).

**About this task**

In this task, you...
- Get the name of the operation to expose to the runtime.
- Connect the **Library sample** project to a Git repository. 
- Create a new version of your project.
- Deploy your decision service archive.
- Run the decision service archive by using a Swagger UI tool.

**Procedure**

1. Click on the decision service name **Getting started** in the breadcrumb to open it.
2. Open the **Decision operations** tab and note the name of the exposed operation: `daily-advice`.
3. Create a repository on GitHub, and get its URI and the credentials to access it.
4. Connect your decision project to the new Git repository. 
5. In the **Share changes** tab of your project, select the **Getting started** decision service.
6. Click **Share** to share your changes in the Git repository.
7. In the **Deploy** tab, click the suggested `create version` from the latest change to create your version.
8. Expand the created version and click **Deploy** at the end of the row for your decision service. Wait for the deployment to finish.
9. Run your decision service archive by copying the decision ID and clicking `{...}` at the end of the line of the decision service name to open the Swagger UI for the runtime.
10. Click **Authorize** and enter your credentials to be able to run the decision service.
11. In the Decision runtime part, expand `GET /deploymentSpaces/{deploymentSpaceId}/decisions/{decisionId}/operations/{operation}/execute`, enter the following input values:
   - deploymentSpaceId: embedded
   - decisionID: the one you copied from Decision Designer.
   - operation: `daily-advice`. 
   - Use the following schema in the request body:
 ``` 
{
  "person": {
    "country": "FRANCE",
    "inputName": "Dominique Dupont"
  },
  "weather": {
    "temperature": "cold",
    "rain forecast": 100,
    "storm alert": true
  }
}
``` 
12. Click Execute. You get the following response:
 
``` 
"Salut Dominique Dupont! It would be wise to stay home. There is a storm alert."
``` 
You've completed this tutorial. For another example of an external library, see [External library giving geo localization facilities](https://github.com/icp4a/automation-decision-services-samples/tree/21.0.2/samples/ExternalLibraryGeo).
