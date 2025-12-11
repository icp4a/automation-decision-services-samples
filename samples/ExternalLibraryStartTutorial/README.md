# Tutorial: Using an external library

## Description
This tutorial shows you how to use an external library in a decision service. 
This decision service is obtained by following the Getting Started tutorial and has been customized to use an external library that defines a **Person** custom type.

In Automation Decision Services, external libraries can be used in decision services to extend rule authoring with custom data types and functions from Java libraries.
For more information on decision models and external libraries, see `Working with external libraries`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=data-working-external-libraries)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=data-working-external-libraries).

For another example of an external library, see [External library giving geo localization facilities](../ExternalLibGeoSample/README.md).

## Learning objectives
- Import and explore an external library in **Decision Designer**.
- Use the external types and functions in a decision service.

## Audience

This sample is intended for a business user who wants to use a Java library in Automation Decision Services, with the library made available by an integrator.

## Time required

10 minutes

## Prerequisites

Prepare by completing the tutorial `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=resources-getting-started). It introduces you to Automation Decision Services.

Ensure that the external library has been built and deployed by an integrator. 
For details on how the integrator proceeds,  refer to the [External library build tutorial](../ExternalLibraryBuildTutorial/README.md).
Get the deployed version number from the integrator. This tutorial uses the version 2501.0.1.

You must have the following environment:
- **Decision Designer**: The web-based user interface for developing decision services in Automation Decision Services. You work with a sample decision service by importing it into a project and opening it in Decision Designer.


# Task 1: Importing and exploring an external library

You import the external library in Decision Designer.

**About this task**

In this task, you will: 
- Create a new project that uses the external library in Decision Designer.
- Explore the external library.
  
 ## Step 1: Creating a new project that uses the external library

 In this step, you create a project that uses the external library. If you need more details to follow the instructions, please refer to the `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=resources-getting-started) tutorial.

1. Use the credentials provided for your instance to sign in to Decision Designer.
2. Create a new project. In this documentation, we use `Library Sample` as the project name.
3. Click the `Settings` icon in the top-right bar to open the Settings page.
4. Open the **External libraries** tab and click **Import**.
5. Enter the following values:
  - Group ID: ads.samples
  - Artifact ID: adsSampleLibrary
  - Version: 2501.0.1 (This is the version you want to use. It might be different if you deployed a new version.)
  
6. Click **OK** and wait for the library to be imported. You see the library description and that it is available in multiple languages.

**Note** 
If you encounter the error "Unable to add the external library," verify that you have entered the correct values for the Group ID, Artifact ID, and Version. If the issue persists, ask your integrator to confirm the availability of the library.


## Step 2: Exploring the external library
In this step, you will explore the library that is loaded into Decision Designer.

1. Click **adsSampleLibrary** to open the library. You see the library types and verbalization.
2. Click **person**. You see the vocabulary that can be used with this new type.
Review the properties used to build a person, such as input name and country. Explore the functions available for use in your rules, such as the greeting function for a person.
3. Click your project name in the breadcrumbs to return and continue by importing a decision service.

**Note** 
At the project level, you will see the library description in English. At the decision service level, the library description will appear in the locale of the current decision service.

# Task 2: Using an external library in a decision service
In this task, you will import a decision service and use your external library within it.

**Note**: This tutorial can use a localized sample since the library is defined in many languages.
The following steps describe how to use either the English or the localized sample. While most terms are in English, if you're using a different locale, you should use the localized names.
 
**About this task**

In this task, you will: 
- Import the Getting Started decision service.
- Import the external library into this decision service.
- Use the external library in the Daily advice decision model.
- Run your changes.


## Step 1: Importing sample decision services
In this step, you import the decision service that uses the external library. This decision service corresponds to the answer from the Getting Started tutorial.

**Procedure**

1. Open the **Decision services** tab if it is not already open.
2. Click **New decision**.
3. In the left panel, choose **Discovery tutorials**. Then, select **Getting started** and click **Import** to import the decision service.
4. Click **Getting Started** to open the decision service. It includes several decision models corresponding to different lessons from the Getting Started tutorial.
You will work on the answer to the tutorial, named `Daily advice`. It also includes a data model defining custom types.

**Note**  
You can use a localized sample since the library is defined in multiple languages.

To use the external library in Brazilian Portuguese, import the **Brazilian Portuguese** sample and use the decision service named `conselho diário`.

To use the external library in French, import the **French** sample and use the decision service named`Conseil du jour`.

To use the external library in German , import the **German** sample and use the decision service named `Heutige Empfehlung`.

To use the external library in Italian, import the **Italian** sample and use the decision service named `Consiglio quotidiano`.

To use the external library in Japanese, import the **Japanese** sample and use the decision service named `私のサービス`.

To use the external library in Simplified Chinese, import the **Simplified Chinese** sample and use the decision service named `我的自动化服务`.

To use the external library in Spanish, import the **Spanish** sample and use the decision service named `Consejo diario`.


## Step 2: Defining the dependency to the external library in the Getting Started decision service
In this step, you enable your decision service to use the external library. 

**Procedure**

1. Open the `Daily advice` decision model.
2. Click the **Dependencies** tab. You see the data model but no dependency to an external library.
2. Click **Add +**. 
3. Select `adsSampleLibrary` and click **OK**. Now, you can use the library in the decision service.
 
 **Note**: You can add an external library to an existing decision service that already includes a data model, as demonstrated with the `Daily advice` decision model.
 
## Step 3: Using the external library in the Daily Advice model
In this step, you replace the String type used for the input name with the new `person` type.
 
 **Procedure**
 
1. Click on the **Run** tab. <br>Three data sets are provided. Run the `Avery` data set. It produces the following results:
`"Hello Avery! Cold day! Take a coat."`<br>
2. Now, you will customize this advice with a personalized greeting and initials provided by the external library.<br>Click **Modeling** and select the **Name** input node. <br>Change the node name to `Person`.<br>Change the node output type to the `person` custom type.

**Note:** In the custom types, you see the new types that are defined in your external libraries, as well as the types that are defined in the data model of this decision service.

3. Click the **Daily advice** node. In the **Logic** tab, select **Advice rule**.
4. Modify the rule to use the functions provided in the adsSampleLibrary:
```
if
    Person is defined
then
    set decision to the greeting of Person + " " + the initials of Person + "! " + 'Weather advice';
```

**Note:** You can use the rule completion menu to select the functions. You can choose a different function from the library, like one that returns the person's first name.

If you are using the Brazilian Portuguese project, the rule will be:

```
se
	Pessoa é definido 
então
	atribuir à decisão o valor a saudação de Pessoa + " " + as iniciais de Pessoa + "! " + 'Recomendação meteorológica';
```

If you are using the French project, the rule will be:
```
si 
     Personne est défini 
alors 
affecter à décision la manière de saluer de Personne + " " + les initiales de Personne + "! " + 'Conseil lié aux prévisions' ;
```
If you are using the German project, the rule will be:
```
wenn
	Person ist definiert
dann
	setze Entscheidung auf  die Anrede von Person + " " + die Initialen von Person  + "! "  + Wetterempfehlung ;
```
If you are using the Italian project, the rule will be:
```
se
	Persona è definito 
allora
	assegna decisione a il saluto di Persona + " " + le iniziali di Persona + "! " + 'Consiglio meteo' ;
```

If you are using the Japanese project, the rule will be:
```
仮定条件
	'人' は定義されている 
その場合

	'意思決定' を '人' の挨拶 + " " + '人' のイニシャル + "、" +'天気のアドバイス' とする 。 
```

If you are using the Simplified Chinese project, the rule will be:
```
如果
	'人' 是 已被定义的
那么
	设置 '决策' 为 '人' 的打招呼 + " " + '人' 的姓名首字母  + "! " + '天气建议' ;
```

If you are using the Spanish project, the rule will be:

```
si
	Persona está definido
entonces
	asignar a decisión el saludo de Persona + " " + las iniciales de Persona + "! " + 'Recomendación meteorológica' ;
```
 
## Step 3: Testing your changes
In this step, you run the modified model.

**Procedure**

1. Click the **Run** tab.
2. Since the input data type has been changed, the `Avery` data set will show an error.<br>Click the vertical three dots (**...**) next to the `name` attribute and select **Delete** from the menu.
3. Now, add the Person input value.<br>Click the **person +** button and set the following values: 
    - Input name: `dominique dupont`
    - Country: France
5. Click **Run**. The following output is generated: `"Salut DD! Cold day! Take a coat."`
6. Click **Modeling** and edit **Advice rule** again to use the full name of the person instead of the initials. The rule in English becomes:
```
if
    Person is defined
then
    set decision to the greeting of Person + " " + the full name of Person + "! " + 'Weather advice';
```
6. Back in the **Run** tab, run your data set again. You get the following output: 
`"Salut Dominique Dupond! Cold day! Take a coat."`

# (Optional) Task 3: Executing a decision service using an external library
In this task you run a decision service that includes an external library in the same way as any other decision service. These steps are more detailed than those in task 6 of `Getting started in Automation Decision Services`[![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/25.0.1?topic=resources-getting-started)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/25.0.1?topic=resources-getting-started).

**About this task**

In this task, you will:
- Share the changes of your decision service.
- Create a new version of your decision service.
- Deploy your decision service archive.
- Run the decision service archive using the OpenAPI of your decision service in the Swagger UI tool.

**Procedure**

1. In the **Share changes** tab of your project, select the **Getting started** decision service.
2. Click **Share** to share your changes with your collaborators.
3. In the **Deploy** tab, click the suggested `create version` from the latest change to create your version.
4. Expand the created version and click **Deploy** at the end of the row for your decision service. Wait for the deployment to finish.
5. To run your decision service archive, click the **Test** icon <img src="/resources/Test-icon.png" width="26" height="23">
 at the end of the line of the decision service name to open the Swagger UI for the your decision service.
6. In the Swagger UI, expand `POST /daily-advice/execute`,click on `Try it out` and enter the following request body:
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
"Salut Dominique Dupont! It would be wise to stay home. There is a storm alert."
``` 
You have completed this tutorial. For another example of an external library, see [External library giving geo localization facilities](../ExternalLibGeoSample/README.md).
