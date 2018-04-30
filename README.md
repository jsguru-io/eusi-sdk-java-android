
# eusi-sdk-java-android
Android SDK for [**EUSI**](https://eusi.io) Delivery API.

> EUSI is an API-first CMS that is user-friendly, beautifully designed and easy to use.

## Table of content

- [Setup](#setup)
- [Simple usage](#simple-usage)
- [Configuration](#configuration)
- [Authorization/Authentication](#authorizationauthentication)
  - [authorize](#authorize)
  - [login](#login)
  - [register](#register)
- [Fetching content](#fetching-content)
  - [All content](#all-content)
  - [Filtered content](#filtered-content)
  - [Pagination](#pagination)
- [Handling forms](#handling-forms)
  - [Get Form](#get-form)
  - [Submit Form](#submit-form)
- [Fetching taxonomy](#fetching-taxonomy)
- [More examples](#more-examples)


## Setup

Gradle:
```
dependencies {
    ...
    implementation '...'
}
```

The SDK requires at minimum Android 4.4.


## Simple usage
```
String TAG = "Eusi";
String host = "https://delivery.eusi.cloud";
String bucketId = "...";
String bucketSecret = "...";

try {
    Eusi eusi = new Eusi(host, Eusi.ApiVersion.V1);
    EusiClient eusiClient = eusi.createClient(bucketId, bucketSecret);
    eusiClient.authorize();
    EusiContentResponse response = eusiClient.getContent(false);

    // Get list of all contents in the bucket
    ArrayList<EusiContent> contentList = response.getContentList();
    for(int i = 0; i < contentList.size(); i++){
        EusiContent content = contentList.get(i);
        // Log title of the content
        Log.d(TAG, i + ". content: " + content.getTitle());
        ...
        }

    // or raw response string
    String responseString = response.getResponseString();
} catch (EusiException e) {
    // Thrown in case of
    e.printStackTrace();
} catch (IOException e) {
    // Thrown in case of networking problem
    e.printStackTrace();
}
```

## Configuration
To get up and going you will need the following parameters:

 - **host** - represents a target URL of Eusi Delivery API
 - **apiVersion** - represents API version of Eusi Delivery API. All supported API versions can be found as constants at Eusi.ApiVersion.class
 - **bucketId** - represents unique identificator of the bucket you are accessing
 - **bucketSecret** - the secret token that you are given

First step is to create ```Eusi``` object with Delivery API URL and API Version:
```
Eusi eusi = new Eusi(host, apiVersion);
```
```eusi``` is used to create ```EusiClient``` objects and hold references to them so it should be used as singleton in application if we have multiple buckets on same host and API version.


Second step is to create EusiClient from ```eusi```:
```
EusiClient eusiClient = eusi.createClient(bucketId, bucketSecret);
```
```eusiClient``` is reference to one Bucket (based on bucketId). It's used to ... :D

If ```eusiClient``` is already created in application it can be accessed from ```eusi``` by **bucketId**:
```
eusi.getClient(bucketId);
```


## Authorization/Authentication
Eusi uses two step authorization system.

First step covers **bucketId** and **bucketSecret**.

Second step is acquiring an **access token** and there are a few ways of doing this as described below. 


### authorize
To access resources as a guest user

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.authorize(); // returns authToken
```

Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.authorizeAsync(new EusiAuthorizeCallback() {
    @Override
    public void onSuccess(String authToken) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```

### login
To access resources as registered user

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.logIn(email, password);  // returns userToken
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.logInAsync(email, password, new EusiLogInCallback() {
    @Override
    public void onSuccess(String userToken) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```

### register
To create a new user

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.register(firstName, lastName, email, password);  // returns userToken
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.registerAsync(firstName, lastName, email, password, new EusiRegisterCallback() {
    @Override
    public void onSuccess(String userToken) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```
**NOTE: Methods register and registerAsync will automatically login newly created user.**


All listed methods will save received token in ```eusiClient``` as **authToken** ot **userToken** and this token can be accessed with:

 ```
eusiClient.getToken();      // returns userToken if set, authToken otherwise
eusiClient.getAuthToken();  // returns authToken
eusiClient.getUserToken();  // returns userToken
 ```

## Fetching content

### All content
Receive all content in the bucket

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.getContent(withUserContent)  // returns EusiContentResponse
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.getContentAsync(withUserContent, new EusiContentCallback() {
    @Override
    public void onSuccess(EusiContentResponse response) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```


Sample:
```
String TAG = "Eusi";
boolean withUserContent = true; 
EusiContentResponse contentResponse = eusiClient.getContent(withUserContent);
for(int i = 0; i < contentResponse.getContentList().size(); i++){
    // One content
    EusiContent content = contentResponse.getContentList().get(i);
    for(int j = 0; j < content.getContent().size(); j++){
        // One picker inside content
        EusiContentTypePicker type = content.getContent().get(j);
        if(type.getType().equals(EusiContentTextPicker.class)){
            // Downcast to EusiContentTextPicker type
            EusiContentTextPicker picker = (EusiContentTextPicker) type;
            // Use data from picker
            Log.d(TAG, picker.getName() + ": " + picker.getText());
            ...
        } else if (type.getType().equals(EusiContentNumberPicker.class)){
            // Downcast to EusiContentNumberPicker type
            EusiContentNumberPicker picker = (EusiContentNumberPicker) type;
            // Use data from picker
            Log.d(TAG, picker.getName() + ": " + picker.getNumber());
            ...
        } else {
            ...
        }
    }
}
```


### Filtered content
Content can be filtered using ```EusiQuery```. It supports filtering by :
```
id
key
title
model
taxonomy
elements
```
or raw query for more advanced filtering.

Sample:
```
// Create query
EusiQuery query = new EusiQuery.Builder()
    .withId(String contentId)
    .withKey(String contentKey)
    .withTitle(contentTitle)
    .withContentModel(contentModel)
    .withTaxonomy(taxonomy)
    .withTaxonomyPath(taxonomyPath)
    .withElement(elementName, elementValue)
    .withRawQuery(rawQuery)
    .build();
    
// Pass query to getContent 
eusiClient.getContent(query, withUserContent);

// or getContentAsync
eusiClient.getContentAsync(query, withUserContent, eusiContentCallback);
```

***NOTE: Eusi currently supports searching only by textual fields***


### Pagination
Pagination is available for content in ```EusiContentResponse``` as ```EusiPagination``` object with properties:
```
page
count
total
totalPages
```
To access it simply call ```eusiContentResponse.getPagination();``` 


## Handling forms

Eusi exposes an API for managing web forms.

### Get Form
Requests form metadata

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.getForm(formId);  // returns EusiFormResponse
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.getFormAsync(formId, new EusiFormCallback() {
    @Override
    public void onSuccess(EusiFormResponse response) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```

Sample:
```
String TAG = "Eusi";
EusiFormResponse formResponse = eusiClient.getForm(formId);
Log.d(TAG, "Form name: " + formResponse.getName());
for(int i = 0; i < formResponse.getFields().size(); i++){
    EusiFormField field = formResponse.getFields().get(i);
    Log.d(TAG, "Field key: " + field.getKey());
    ...
}
```

### Submit Form

Synchronous (network operations will be conducted on caller thread):
```
JSONObject formBody = new JSONObject();
formBody.put(key, value);
...
eusiClient.submitForm(formId, formBody);  // returns true if submit is successful
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.submitFormAsync(formId, formBody, new EusiSubmitFormCallback() {
    @Override
    public void onSuccess(String formId) {
        //
    }

    @Override
    public void onFailure(String message, String validation) {
        //
    }
});
```


## Fetching taxonomy

Synchronous (network operations will be conducted on caller thread):
```
eusiClient.getTaxonomy(taxonomyId);  // returns EusiTaxonomyResponse
```
Asynchronous (network operations will be conducted on a separate thread):
```
eusiClient.submitFormAsync(taxonomyId, new EusiTaxonomyCallback() {
    @Override
    public void onSuccess(EusiTaxonomyResponse response) {
        //
    }

    @Override
    public void onFailure(String message) {
        //
    }
});
```


## More examples
For more examples please make sure you refer to [sample](https://github.com/jsguru-io/eusi-sdk-java-android/tree/master/sample). 