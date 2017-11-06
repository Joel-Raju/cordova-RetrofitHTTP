# cordova-RetrofitHTTP
A Cordova/ PhoneGap Plugin (for Android only) which helps to perform HTTP requests using the awesome Retrofit 2 library under the hood.

This project is inspired by https://github.com/wymsee/cordova-HTTP

## Usage

### AngularJS

This plugin creates a cordovaHTTP service inside of a cordovaHTTP module.  You must load the module when you create your app's module.

    var app = angular.module('myApp', ['ngRoute', 'ngAnimate', 'cordovaRetrofitHTTP']);
    
You can then inject the cordovaRetrofitHTTP service into your controllers. Make sure that you load cordova.js or phonegap.js after AngularJS is loaded.

### Not AngularJS

This plugin registers a `cordovaRetrofitHTTP` global on window



## Async Functions
These functions all take success and error callbacks as their last 2 arguments.

### get
Execute a GET request.  Takes a URL, parameters, and headers.  See the [post](#post) documentation for details on what is returned on success and failure.

    cordovaRetrofitHTTP.get("https://example.com/", {
        id: 101,
        message: "test message"
    }, { Authorization: "OAuth2: token" }, function(response) {
        console.log(response.status);
    }, function(response) {
        console.error(response.error);
    });

### post<a name="post"></a>
Execute a POST request.  Takes a URL, parameters, and headers.

#### success
The success function receives a response object with 3 properties: status, data, and headers.  Status is the HTTP response code. Data is the response from the server as a string. Headers is an object with the headers.  Here's a quick example:

    {
        status: 200,
        data: "{'id': 12, 'message': 'test'}",
        headers: {
            "Content-Length": "247"
        }
    }
    
Most apis will return JSON meaning you'll want to parse the data like in the example below:

    cordovaRetrofitHTTP.post("https://example.com/", {
        id: 12,
        message: "test"
    }, { Authorization: "OAuth2: token" }, function(response) {
        // prints 200
        console.log(response.status);
        try {
            response.data = JSON.parse(response.data);
            // prints test
            console.log(response.data.message);
        } catch(e) {
            console.error("JSON parsing error");
        }
    }, function(response) {
        // prints 403
        console.log(response.status);
        
        //prints Permission denied 
        console.log(response.error);
    });
    
    
#### failure
The error function receives a response object with 3 properties: status, error and headers.  Status is the HTTP response code.  Error is the error response from the server as a string.  Headers is an object with the headers.  Here's a quick example:

    {
        status: 403,
        error: "Permission denied",
        headers: {
            "Content-Length": "247"
        }
    }

Project Dependencies
--------

JAR 
```
-commons-io-2.5.jar
-converter-gson-2.3.0.jar
-gson-2.8.1.jar
-okio-1.13.0.jar
-retrofit-2.3.0.jar
```

Cordova Plugin 
```
-cordova-plugin-file
```
