# Tutorial: Using an external library

## Description
This tutorial shows you how to use an external library in a decision service that gives advice about the weather. 
This decision service is based on the getting started tutorial result, but it includes an external library that defines a **Person** custom type.

In Automation Decision Services, you can use external libraries in decision services.
You use them to extend rule authoring to include custom data types and functions from Java libraries.
For more information on decision models and external libraries, see `Working with external libraries`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=data-working-external-libraries)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=data-working-external-libraries).

For another example of an external library, see [External library giving geo localization facilities](../ExternalLibGeoSample/README.md).

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

Prepare by doing the tutorial `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=resources-getting-started). It introduces you to Automation Decision Services.

Some basic knowledge of Java is required.

You must have the following environments:
- **Decision Designer**: The web-based user interface for developing decision services in Automation Decision Service. You work with a sample decision service by importing it into a project and opening it in Decision Designer.
- **Deployment services**: Your IT developers must provide a repository manager where you can deploy the external library. We will call it **sample-repository-manager** in this sample.
- **Apache Maven**: You can download Maven from https://maven.apache.org.

Download a compressed file of the `automation-decision-services-samples` Git repository to your machine.
Open this [link](/../../) and choose `Download Zip` in the `Code` menu.
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

1. Go to the **automation-decision-services-samples/samples/ExternalLibraryStartTutorial** directory on your computer.
2. Explore the sources. There are two directories:
 - `sampleLibrary`: Contains the Java classes StringUtilities, Person and Country. Browse through them to see the usage of annotations that define pure functions that are ready to be used in the decision models.
 The StringUtilities class defines two static functions to be used on string. To get more information on the annotations, look into `Annotations`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=annotations-)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=annotations-).
 - `adsSampleLibrary`: Contains the reference files to build the library. Several vocabulary files are provided in `reference/bom`, including the one for English. These files define, for example, the navigation sentence of the StringUtilities.capitalize static function and its precedence policy. To get more information on the vocabulary, look into `Vocabulary`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=vocabulary-)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=vocabulary-).
 
Each repository contains a `pom.xml` file to build and deploy the library. Open the `adsSampleLibrary/pom.xml` file. The description of this artifact is shown in the decision library. The library depends on the sampleLibrary artifact. This library can be used in many languages including English.

## Step 2: Checking deployed artifacts

You check if the sample library has already been deployed to your repository manager.

1. Open your **sample-repository-manager** release repository where the external library will be stored.
2. Look for `ads/samples/adsSampleLibrary/2400.0.1`. If it exists, the sample library is already deployed. You can go to Task 2 directly. If the file does not exist, you must deploy it.

**Note**: If you want to deploy another version, change the version from `2400.0.1` to the version number that you want to use, for example, `1.myInitials`, in the following XML files:
- `automation-decision-services-samples/samples/ExternalLibraryStartTutorial/pom.xml` 
- `automation-decision-services-samples/samples/ExternalLibraryStartTutorial/sampleLibrary/pom.xml` 
- `automation-decision-services-samples/samples/ExternalLibraryStartTutorial/adsSampleLibrary/pom.xml` 

## Step 3: Configuring the Maven settings to build your external library

Your Maven setting files should be configured:
  * to access the Automation Decision Services artifacts to build the sample external library,
  * to access to the sample-repository-manager to deploy the sample external library.
If your Maven setting files is already configured like this, you can skip this step.
Otherwise, you must define a Maven setting file by completing the template settings.xml, which is provided in this sample.
1. Enable Maven to use Automation Decision Services remote repository stored in HTTPS servers ([more](https://maven.apache.org/guides/mini/guide-repository-ssl.html)). <br>
  Note: Alternatively, for test only, SSL validation can be disabled using, to use at step 5 :
  ```
  -Dmaven.wagon.http.ssl.insecure=true
  ```
2. Open **automation-decision-services-samples/samples/ExternalLibraryStartTutorial/settings.xml**.
3. To access the Automation Decision Services artifacts to build the sample external library, replace the following placeholders :
   * %ADS_MAVEN_REPOSITORY_TO_BE_SET% : the URL of your ADS maven repository (`more`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=environment-using-decision-designer-as-maven-repository)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=environment-using-decision-designer-as-maven-repository)).
   * %YOUR_API_KEY_TO_BE_SET% : your encoded ZEN api key (For more information about getting the API key, see this [documentation](https://www.ibm.com/docs/en/cloud-paks/1.0?topic=users-generating-api-keys-authentication). You must encode this key as described in this [documentation](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=administering-authorizing-http-requests-by-using-zen-api-key)).
4. To access the sample-repository-manager to deploy the sample external library, replace the following placeholders :
   * %MAVEN_RELEASES-REPOSITORY_TO_BE_SET% : The URL of the **sample-repository-manager** release repository where you deploy the external library.
   * %USER TO BE SET%: A username that can access the repository manager where you deploy the external library.
   * %PASSWORD TO BE SET%: The username's password to access the repository manager where you deploy the external library.
5. Save the `settings.xml` file.

## Step 4: Setting the properties to deploy your external library

To be able to deploy the library, you must have the following information:
   * The snapshot URL of the repository manager in which you deploy your external library (for example, Nexus or Artifactory). 
   * The release URL of the repository manager in which you deploy your external library.

**Note**: The repository manager can be the one of the custom CI/CD stack. 

1. Open **automation-decision-services-samples/samples/ExternalLibraryStartTutorial/pom.xml**.
2. Look at the property definitions:
    * `archive.repository.snapshot.url`: The URL in which you deploy snapshots. This value is used in the distribution management part. 
    * `archive.repository.release.url`: The URL in which you deploy releases. This value is used in the distribution management part. 
    * `ads-mojo.version` The version that is used to build the library. (**Note:** This sample was tested with Mojo 14.1.10)
    * `ads-annotations.version` The version that is used to build the library. (**Note:** This sample was tested with annotations 2.0.12)

3. Replace the instances of `TO BE SET` by the appropriate values:

```
   <properties>
       <archive.repository.snapshot.url>SNAPSHOT URL TO BE SET</archive.repository.snapshot.url>
       <archive.repository.release.url>RELEASE URL TO BE SET</archive.repository.release.url>
       <ads-mojo.version>14.1.10</ads-mojo.version>
       <ads-annotations.version>2.0.12</ads-annotations.version>
   </properties>
```
4. Save the `pom.xml` file. 

## Step 5: Building and deploying the library

In this step, you build and deploy the external library to a repository manager.

**Note**: The pom file to build an external library can be generated using a maven command, 
for more information about this feature see the `documentation`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=library-creating-pom-file)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=library-creating-pom-file).
This sample provides this pom file in `automation-decision-services-samples/samples/ExternalLibraryStartTutorial/adsSampleLibrary/pom.xml`, it can be generated with the following command
````
mvn archetype:generate -DarchetypeGroupId=com.ibm.decision -DarchetypeArtifactId=maven-archetype-external-library \
-DarchetypeVersion=ADS-MOJO.VERSION -DgroupId=ads.samples \
-DartifactId=adsSampleLibrary \
-Dversion=SAMPLE.VERSION \
-Ddependencies=ads.samples:sampleLibrary:SAMPLE.VERSION \
-Dlocales=en_US,fr_FR,de_DE,it_IT,es_ES,pt_BR,zh_CN,ja_JP \
-DimportMethods=true \
-B -s settings.xml 
````
where `ADS-MOJO.VERSION` is the version that is used to build the library, and `SAMPLE.VERSION` is the version of the sample.


To build and deploy the external library, run the following command in the **automation-decision-services-samples/samples/ExternalLibraryStartTutorial** directory:
```
mvn clean deploy -s settings.xml
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
You search for the external library. The task is normally done by your IT developers (see `Configuring credentials for a Maven repository manager`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=administering-configuring-credentials-maven-repository-manager)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=administering-configuring-credentials-maven-repository-manager)).
To look at the credentials, you should have the permission `Administer platform for decision services` enabled. If you don't, you can skip this step.
1. Open the admin-platform URL in a web browser.
2. Select the `Maven configuration` tab.
3. One value should be defined to give access to the repository in which you have deployed the external library.
4. If no value is defined, add one using the `New` button.
  
 ## Step 2: Creating a new project that uses the external library
 In this step, you create a project that uses the external library. If you need more details to follow the instructions please refer to the `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=resources-getting-started) tutorial.

1. Use the credentials provided for your instance to sign in to Decision Designer.
2. Create a new project. We use `Library Sample` in this sample documentation for the project name.
3. Click the `Settings` icon in the right top bar to open the Settings page.
4. Open the **External libraries** tab and click **Import**.
5. Enter the following values:
  - Group ID: ads.samples
  - Artifact ID: adsSampleLibrary
  - Version: 2400.0.1 (This is the version you want to use. It might be different if you deployed a new version.)
  
6. Click **OK** and wait for the library to be imported. You see the library description and that it is defined in many languages.

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
You import a decision service and use your external library in it.

**About this task**

In this task, you... 
- Import the Getting Started decision service.
- Import the external library in this decision service.
- Use the external library in the Daily advice decision model.
- Run your changes.


## Step 1: Importing sample decision services
In this step, you import the decision service in which you use the external library. You import the answer to the getting started tutorial.

**Procedure**

1. Open the **Decision services** tab if it is not open.
2. Click **New decision**.
3. Choose **Discovery tutorials** in the left panel. Then, select **Getting started** and click **Import** to import the decision service.
4. Click **Getting Started** to open the decision service. It contains several decision models that correspond to different lessons in the Getting started tutorial. 
You work on the answer to the tutorial, which is named `Daily advice`. It also contains a data model defining custom types.

**Note**  
You can also import the **Brazilian Portuguese** sample and use the decision service named `conselho diário` to use this external library in Brazilian Portuguese.

You can also import the **French** sample and use the decision service named `Conseil du jour` to use this external library in French.

You can also import the **German** sample and use the decision service named `Heutige Empfehlung` to use this external library in German.

You can also import the **Italian** sample and use the decision service named `Consiglio quotidiano` to use this external library in Italian.

You can also import the **Japanese** sample and use the decision service named `私のサービス` to use this external library in Japanese.

You can also import the **Simplified Chinese** sample and use the decision service named `我的自动化服务` to use this external library in Simplified Chinese.

You can also import the **Spanish** sample and use the decision service named `Consejo diario` to use this external library in Spanish.


## Step 2: Defining the dependency to the external library in the Getting Started decision service
In this step, you enable your decision service to use the external library.

**Procedure**

1. Open the `Daily advice` decision model.
2. Click the **Dependencies** tab. It shows a data model and no dependency to an external library.
2. Click **Add +**. 
3. Select `adsSampleLibrary` and click **OK**. Now, you can use the library in the decision service.
 
 **Note**: You can add an external library to a decision service that already has a data model. This is the case in the tutorial that you just imported.
 
## Step 3: Using the external library in the Daily Advice model
 You replace the String type that is used for the input name by the new person type. 
 
 **Procedure**
 
1. Click on the **Run** tab. <br>Three data sets are provided. Run the `Avery` data set. It produces the following results:
`"Hello Avery! Cold day! Take a coat."`<br>
2. Now, you will change this advice by a capitalized greeting and initials provided in the external library.<br>Click **Modeling** and select the **Name** input node. <br>Change the node name to `Person`.<br>Change the node output type to the `person` custom type.

**Note:** In the custom types, you see the new types that are defined in your external libraries as well as the types that are defined in the data model of this decision service.

3. Click the **Daily advice** node. In the **Logic** tab, select **Advice rule**.
4. Change the rule to use the functions provided in the adsSampleLibrary:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the initials of Person + "! " + 'Weather advice';
```

**Note:** You can use the rule completion menu to select the functions. You can use another function from the library for the name of the Person such as the first name.

If you use the Brazilian Portuguese project, the rule is

```
se
	Pessoa é definido 
então
	atribuir à decisão o valor a saudação de Pessoa em maiúsculas + " " + as iniciais de Pessoa + "! " + 'Recomendação meteorológica';
```

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

If you use the Japanese project, the rule is
```
仮定条件
	'人' は定義されている 
その場合

	'意思決定' を '人' の挨拶 を大文字にする + " " + '人' のイニシャル + "、" +'天気のアドバイス' とする 。 
```

If you use the Simplified Chinese project, the rule is

```
如果
	'人' 是 已被定义的
那么
	设置 '决策' 为 '人' 的打招呼 大写 + " " + '人' 的姓名首字母  + "! " + '天气建议' ;
```

If you use the Spanish project, the rule is

```
si
	Persona está definido
entonces
	asignar a decisión el saludo de Persona en mayúsculas + " " + las iniciales de Persona + "! " + 'Recomendación meteorológica' ;
```

**Note**: The rest of this tutorial uses the English names, in another locale, you have to use the localized names.
 
## Step 4: Testing your changes
You run the modified model.

**Procedure**

1. Click the **Run** tab.
2. The `Avery` data set shows an error as input data type has been changed.<br>Click the vertical three dots (**...**) at the right of the `name` attribute and select **Delete** in the menu.
3. Now, you can add Person input value.<br>Click the **person +** button and set the following values: 
    - Country: France
    - Input name: `dominique dupont`
5. Click **Run**. You get the following output: `"SALUT DD! Cold day! Take a coat."`
6. Click **Modeling** and edit **Advice rule** again to use the full name of the person instead of the initials. The rule in English becomes:
```
if
    Person is defined
then
    set decision to the greeting of Person capitalized + " " + the full name of Person + "! " + 'Weather advice';
```
6. Back to the **Run** tab, run your data set. You get the following output: 
`"SALUT Dominique Dupond! Cold day! Take a coat."`

# (Optional) Task 4: Executing a decision service using an external library
You run a decision service that includes an external library in the same way as any other decision service. These steps are more detailed than those in task 2 and task 6 of `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=resources-getting-started).

**About this task**

In this task, you...
- Connect the **Library sample** project to a Git repository. 
- Create a new version of your project.
- Deploy your decision service archive.
- Run the decision service archive by using the OpenAPI of your decision service in the Swagger UI tool.

If Decision Designer is configured to automatically create a Git repository to be connected to, you can use this git repo and skip points 1,2 and 3 in the following procedure. See `Connecting to a remote repository automatically`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.0?topic=administering-connecting-remote-repository-automatically)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.0?topic=administering-connecting-remote-repository-automatically).

**Procedure**

1. Create a GIT repository, and get its URI and credentials.
2. Click on the project name **Library Sample** in the breadcrumb to open it.
3. Connect the project to the GIT repository using the button of the right top bar. 
4. In the **Share changes** tab of your project, select the **Getting started** decision service.
5. Click **Share** to share your changes in the Git repository.
6. In the **Deploy** tab, click the suggested `create version` from the latest change to create your version.
7. Expand the created version and click **Deploy** at the end of the row for your decision service. Wait for the deployment to finish.
8. Run your decision service archive by clicking on the **Test** icon <img src="/resources/Test-icon.png" width="26" height="23">
 at the end of the line of the decision service name to open the Swagger UI for the your decision service.
9. Expand `POST /daily-advice/execute`, click on `Try it out` and enter the following request body:
 ``` 
{
  "person": {
    "country": "FRANCE",
    "inputName": "Dominique Dupont"
  },
  "weather": {
    "temperature": "cold",
    "rainForecast": 100,
    "stormAlert": true
  }
}
``` 
10. Click Execute. You get the following response:
 
``` 
"SALUT Dominique Dupont! It would be wise to stay home. There is a storm alert."
``` 
You've completed this tutorial. For another example of an external library, see [External library giving geo localization facilities](../ExternalLibGeoSample/README.md).
