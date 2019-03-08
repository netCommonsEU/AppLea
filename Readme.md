# AppLea mobile app for communities

This is the official Repository for the developments made on the AppLea mobile app for the netCommons research project. 
AppLea is the evolution of what was earlier the CommonTasker app, an app that intended to ease crowdsourcing of tasks among community members in a CN. The shift of focus towards logging farming activities and data sharing occured after feedback from the local community in the Sarantaporo.gr CN, in the context of a highly participatory design process.

This repository hosts exclusively the code of the AppLea app. The link to the CommonTasker repository is https://github.com/netCommonsEU/CommonTasker

The application uses cellular network, WiFi and GPS technologies to get the location of meteorological stations in the vicinity of the user and show the weather where he or she actually stands. The location updates could be carried out periodically (e.g. hourly); alternatively, the app can use the accelerometer to detect movement.

## Features

### &nbsp;  Login , Register

* Register new account with email or mobile phone
* Login with existing account 
* Logout
* Firebase authentication for login and register

### &nbsp;  User profile and preferences
*  Fill in personal information (name, age group, residence)
*  Enter the fields/farms through a map interface
*  Determine which entries will be shared and which ones not
*  Access statistics about the logged activities

### &nbsp;  Simple event calendar, with agenda view.
* Insert entries By Date-Time
* Update entries in Agenda View 
* Delete events in Agenda View

### &nbsp;  Embedded weather module
* Extract info from local weather stations
* 7-day forecast in hourly intervals
      
### &nbsp;  Log history
 * Update-Delete existing entries 
 * Filter entries by activity
 * Filter entries by time
 * Generate .pdf file out of logs
   
### &nbsp; Statistics
 * by activity
  
### &nbsp; Social NetWork
 * Exchange photos

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
The executable .apk file can be found in the root directory 

