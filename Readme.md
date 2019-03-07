# AppLea mobile app for communities

This is the official Repository for the developments made on the AppLea mobile app for the netCommons research project. 
AppLea is the evolution of what was earlier the CommonTasker app, an app that intended to ease crowdsourcing of tasks among community members in a CN. The shift of focus towards logging farming activities and data sharing occured after feedback from the local community in the Sarantaporo.gr CN, in the context of a highly participatory design process.

This repository hosts exclusively the code of the AppLea app. The link to the CommonTasker repository is https://github.com/netCommonsEU/CommonTasker

Application uses cell network, WIFI and GPS to get your location from metterological stations and show you weather on the place where you are actually.Update of location could be used by some specific period (ex. hourly) or application can use accelerometer to detect movement.

## Features

### &nbsp;  Login , Register

* Register new account
* Login with existing account
* Logout
* Firebase authentication for login and register

### &nbsp;  Simple event calendar, with agenda view.
 
### &nbsp;  Current weather
 
*  7-day forecast
* Support different measuring units 
* Forecasting
      
### &nbsp;  History Logs Entry
 * Update-Delete Logs
 * Filtering Logs By Activity
 * Filtering Logs By Time
 * Pdf Logs
   
### &nbsp; Statistics
 * By Activity
  
### &nbsp; Social NetWork
   

#### &nbsp; Chat With Friends
* Send Messages
* Send Images
* Delete Messages
* Last seen 
* Friend is Online or not

## Requirements

AppLea requires the following libraries for in order to compile:

```
  //Jackson Databind
implementation 'com.fasterxml.jackson.core:jackson-databind:2.7.2'
```

  ```
   // FirebaseUI
implementation 'com.firebaseui:firebase-ui:0.6.0'
```


 ```
  // Firebase
    implementation 'com.google.firebase:firebase-messaging:11.6.0'
    implementation 'com.github.bluejamesbond:textjustify-android:2.1.0'
    implementation 'com.google.firebase:firebase-config:11.6.0'
    implementation 'com.google.firebase:firebase-storage:11.6.0'
    implementation 'com.google.firebase:firebase-database:11.6.0'
    implementation 'com.google.firebase:firebase-auth:11.6.0'
    implementation 'com.google.firebase:firebase-core:11.6.0'
 ```
 
 
## Installation
The executable .apk file can be found in the TBD directory 

## Usage

Work in progress
