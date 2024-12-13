# Tutorial : Using Generative AI to support decisions

## Description
This tutorial shows you how to use generative AI within decisions. It guides you through the configuration of a generative AI provider and the creation of a generative AI model in Decision Designer.
## Learning objectives
- Configure a generative AI provider to use it in Automation Decision Services.
- Build a generative AI model in Decision Designer.
- Integrate generative AI into a task model.

## Audience
This sample is for technical and business users who are eager to learn more about how generative AI can enhance their decisions.

## Time required

20 minutes

## Prerequisites

You must have access to the following environments:
- [IBM Watsonx](https://dataplatform.cloud.ibm.com/docs/content/wsj/getting-started/welcome-main.html?context=wx&audience=wdp): a secure and collaborative environment where you can manage foundation and machine learning models with watsonx.ai and watsonx.governance.
- **Decision Designer**: A web-based user interface for developing decision services. 

You must activate the generative AI feature in Automation Decision Services. More information about activating this feature is given in **Task 1** below.


# Task 1: Configure a Generative AI provider
**About this task**

In this task, you configure a generative AI provider to use it in Automation Decision Services

**Procedure**

To activate the Generative AI feature in Automation Decision Services, you must create a Kubernetes secret and reference it in spec.genai_secret_name. For more information, see `Configuring a generative AI secret` [![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.1?topic=services-configuring-generative-ai-secret)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.1?topic=preparing-configuring-generative-ai-secret). You  retrieve the <watsonx_ai_api_key> and <project_id> from your IBM Watsonx account as follows.


1. Create an IBM Cloud API key:
    - Log in to [IBM Cloud](https://cloud.ibm.com/login) and select **Manage** > **Access (IAM)** > **API keys**.
    - Create an API key for your own personal identity.
    - Copy the key value, and save it in a secure place 
2. [Create an IBM Cloud service](https://dataplatform.cloud.ibm.com/docs/content/wsj/admin/create-services.html?context=wx&audience=wdp)
3. Create a new project
4. [Associate your IBM Cloud service to the project](https://dataplatform.cloud.ibm.com/docs/content/wsj/getting-started/assoc-services.html?context=wx&audience=wdp)
3. Go to **Project** > <project_name> > **Manage** and copy the Project ID.

# Task 2: Use Generative AI model in a decision service

**About this task**

In this task, you import and explore a sample decision service. 

## Step 1: Import a sample decision service in Decision Designer
You import the sample decision service into an automation. The decision service uses a large language model (LLM) to extract data from customer emails, such as a product name or a quantity of items. It then runs business rules to compute a price.

**Procedure**

1. Sign in to access Decision Designer. Use the security credentials that were given to you for your instance.
2. Create a new automation and import the `AssistedPricing.zip` sample. This sample can be found under [./project/AssistedPricing.zip](./project/AssistedPricing.zip).
3. Click **Assisted pricing** to open the decision service.

## Step 2: Explore the decision service
In this step, you take a moment to explore the imported sample decision service.

**Procedure**
1. Go to the **Data** tab and open the **Pricing Data** data model. Browse the data types: the Detection type is used by the generative AI model to store data extracted from customer emails.
2. Click on the decision service name in the breadcrumbs and open the **Email analysis** generative AI model. It uses the Mixtral-8x7B LLM to analyze content from customer emails and extract data such as product names, item quantities, and company names.
For more details about prompt fields see `Creating prompts` [![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.1?topic=models-creating-prompts)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.1?topic=models-creating-prompts).
3. Click **Assisted Pricing** in the breadcrumbs and open **Part number** decision model. Click on **Part number** node then click on **Logic** and open **Part number table**. It defines the part number of a product based on its name. If the product is not in the decision table than the **output-default-setting** rule is applied. Open the Run tab and test with the **Laptop** dataset. The output is **"OF-028"**. Update the product value to **laptop** and click **Run**. The output is **"N/A"**.
4. Click **Assisted Pricing** in the breadcrumbs and open **[Main] Pricing** task model. 
Click the **[Main] Pricing** ruleflow in the Artifacts list to open it. It contains three subflows:
   - **Analyse email**: It calls the **Email analysis** generative AI model to extract data from customer emails and get product names.
   - **Build product list**: It builds a list of product names and quantities by parsing the text returned by the generative AI model. It also calls the **Part number** decision model to get the part number of products.
   - **Price products**: It calls the **Unit price** decision model to get the unit price for specific products and calculate the total price for all detected products.
5. Open the Run tab to test the **[Main] Pricing** ruleflow.There are four functions, one for the **[Main] Princing** ruleflow and the three others for the subflows. Select **Pricing** function and click **Run**. Switch to **Realistic email** test data and click Run.

Depending on the email used as input, some products might show a price of 0 because their part number could not be identified. If the product name does not exactly match the spelling used in the **Part number** decision table, the part number is set to to N/A and a price of 0 is returned.
 This limitation can be avoided by using a generative AI model instead of a decision table to get the part number of a product.
  
Next you create a generative AI model.

## Step 3: Create a generative AI model

**About this task**

In this task, you create a generative AI model to replace the **Part number** decision model and call it from a task model. For more information about generative AI models, see `Integrating generative AI models` [![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.1?topic=artifacts-integrating-generative-ai-models)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.1?topic=artifacts-integrating-generative-ai-models).

**Procedure**

1. Click **Assisted Pricing** in the breadcrumbs.
2. Click the overflow menu for the **Part number** decision model and click **Delete**.
3. Click **Create**. Then, select Generative AI and enter **Part number** for the name of the model.
4. Click **Create**. The model automatically opens.
5. In the **Model** drop-down menu, select **mistralai/mistral-large** foundation model. You can explore and test other foundation models to see which one consistenly returns the result you expect. 
6. Clear the Context pane and enter:
        <details>
        <summary>Context</summary>

        Your goal is to find the part number of a product, based on its name, which can be singular or plural. 
        If you don't find it, just answer N/A. Write only one part number.      
        Here is the list of products and part numbers:
        Part Number,Product Name (English)
        OF-001,Ergonomic Office Chair
        OF-002,Executive Desk
        OF-003,Conference Table
        OF-004,Filing Cabinet
        OF-005,Office Cubicle
        OF-006,Desk Lamp
        OF-007,Standing Desk
        OF-008,Office Sofa
        OF-009,Reception Desk
        OF-010,Bookcase
        OF-011,Meeting Room Chair
        OF-012,Office Partition
        OF-013,Monitor Stand
        OF-014,Keyboard Tray
        OF-015,Desk Organizer
        OF-016,Office Pod
        OF-017,Printer Stand
        OF-018,Office Stool
        OF-019,Whiteboard
        OF-020,Bulletin Board
        OF-021,Coat Rack
        OF-022,Office Plant
        OF-023,Laptop Stand
        OF-024,Side Table
        OF-025,Office Chair Mat
        OF-026,Footrest
        OF-027,Desktop Computer
        OF-028,Laptop
        OF-029,Monitor
        OF-030,Mouse,
        OF-031,Keyboard
        OF-032,Printer
        OF-033,Scanner
        OF-034,Fax Machine
        OF-035,Shredder
        OF-036,Phone
        OF-037,Projector
        OF-038,Projector Screen
        OF-039,Server Rack,
        OF-040,Network Switch
        OF-041,Office Safe
        OF-042,File Sorter
        OF-043,Pen Holder
        OF-044,Paper Tray
        OF-045,Waste Bin
        OF-046,Recycle Bin
        OF-047,Coffee Maker
        OF-048,Water Dispenser
        OF-049,Mini Fridge
        OF-050,Microwave
        OF-051,Desk Fan
        OF-052,Air Purifier
        OF-053,Heater
        OF-054,Electric Kettle
        OF-055,Clock
        OF-056,Picture Frame
        OF-057,Desk Drawer Organizer
        OF-058,Cushion
        OF-059,Blanket
        OF-060,Desk Calendar
        OF-061,Planner
        OF-062,Note Pad
        OF-063,Sticky Notes
        OF-064,Paper Clips
        OF-065,Stapler
        OF-066,Staples
        OF-067,Tape Dispenser
        OF-068,Scissors
        OF-069,Ruler
        OF-070,Hole Punch
        OF-071,Label Maker
        OF-072,Calculator
        OF-073,Binder
        OF-074,File Folder
        OF-075,Clipboard
        OF-076,Desk Pad
        OF-077,Cable Organizer
        OF-078,Extension Cord
        OF-079,Power Strip
        OF-080,USB Hub
        OF-081,Charging Station
        OF-082,Pen
        OF-083,Pencil
        OF-084,Highlighter
        OF-085,Marker
        OF-086,Eraser
        OF-087,Whiteboard Marker
        OF-088,Dry Erase Eraser
        OF-089,Rubber Bands
        OF-090,Thumbtacks
        OF-091,Push Pins
        OF-092,Desk Mirror
        OF-093,Desk Clock
        OF-094,Table Lamp
        OF-095,Magazine Rack
        OF-096,Office Signage
        OF-097,Paperweight
        OF-098,Visitor Chair
        OF-099,Guest Sofa
        OF-100,Desk Lamp
     </details>
7. Clear the default prompt input and enter: `what is the part number of {{product}}?`. A warning is displayed because you haven't defined the product variable yet.
8. Edit the default variable:
    - Double click the Variable name and set it to `product`.
    - Set its default value to `laptop`.
9. Click Generate to test your model. The generated output is `OF-028`.
You can edit the default value for the product (as explained in step 8. above) and test the model again. Try `laptops` or `Laptop` for example. The generated output is `OF-028`.

## Step 4: Use generative AI model in a task model

**About this task**

In this task, you replace the **Part number** decision model by the generative AI model created in **Step3** in the **Assisted Pricing** task model .

**Procedure**

1. Click **Assisted Pricing** in the breadcrumbs. Then, open the **[Main] Pricing** task model. It is in error because it uses the **Part number** decision model, you deleted in **Step3**. To fix the error: 
2. Open the **Dependencies** tab. 
3. Create a new dependency:
   - Click **Add +**.
   - Select **Part number**.
   - Click Add.
4. Open the **Artifacts** tab.
5. Expand the **Build list of products** package and click the **3 Build part numbers** rule.
6. Update the rule to call the generative AI model instead of the decision model. Enter the following rule:  
    ```
    definitions
    set p to a Product in 'the products';
    then 
    set partNumber to generated_text of the part number computed from 
      product being the name of p;
    set the part number of p to partNumber; 
    print "P is: " + partNumber ;
    ```
    Model is now error-free and can be tested.
7. Open the **Run** tab and test the **[Main] Pricing** ruleflow with the **Realistic email** data set. Compare with the results you got when running the task model in **Step 2-4**. Prices are well computed with both datasets.


## Learn more
- `Integrating generative AI models` in Decision Designer [![CP4BA](/resources/cloudpak4ba.svg "IBM Cloud Pak for Business Automation")](https://www.ibm.com/docs/en/cloud-paks/cp-biz-automation/24.0.1?topic=artifacts-integrating-generative-ai-models)[![ADS](/resources/ads.svg "IBM Automation Decision Services")](https://www.ibm.com/docs/en/ads/24.0.1?topic=artifacts-integrating-generative-ai-models).
- [Working with generative AI](https://dataplatform.cloud.ibm.com/docs/content/wsj/getting-started/quickstart-tutorials.html?context=wx&audience=wdp#prompt) in watsonx.ai
- [Choosing a foundation model in watsonx.ai](https://dataplatform.cloud.ibm.com/docs/content/wsj/analyze-data/fm-model-choose.html?context=wx&audience=wdp)



