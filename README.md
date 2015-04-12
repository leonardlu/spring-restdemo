# CRUD Rest Services Demo with Spring Boot, Maven &amp; Mockito

##Introduction:
This is a basic demo of a set of CRUD Rest APIs (secured by token-based authentication) which has the following functionality:
    Receive a JSON String from a web page and store it in a mongoDB collection
    Displays all stored strings 
    Delete one of the stored strings
    Edit one of stored strings

##Technologies used:
1) Build: Maven
2) Web Services Framework: Spring Boot
3) Service Authentication: Spring Security OAuth2
4) MongoDB Interface: Spring Boot Starter Data MongoDB
5) Testing: Mockito, JUnit
Requirements: Java 8, Maven, MongoDB

##Steps to run:
1) Install and run MongoDB (http://docs.mongodb.org/manual/tutorial/getting-started/)

2) mvn clean package (in project folder)

3) java -jar target/octus-restdemo-0.0.1-SNAPSHOT.jar

4) To run specific curl commands from terminal:

Do this before any other commands:
####1) Get Auth Token
curl -H "Accept: application/json" my-client-with-secret:secret@localhost:8080/oauth/token -d grant_type=client_credentials
This will return an access token which looks like this
{"access_token":"5f9f9f99-0811-4d3e-95a1-a0abab1c0679","token_type":"bearer","expires_in":43199,"scope":"read write"}

####2) Set Auth Token
TOKEN="5f9f9f99-0811-4d3e-95a1-a0abab1c0679"
Use this command to set the TOKEN variable in your session to whatever token is presented above.

####3) Run the following commands for various CRUD operations:
Substitute {id} with the id of the object you are interested in.

#####Top Level Service
curl -H "Authorization: Bearer $TOKEN" localhost:8080

#####GET (returns all objects)
curl -H "Authorization: Bearer $TOKEN" localhost:8080/api

#####PUT
curl -H "Authorization: Bearer $TOKEN" -H "Content-type: text/plain" -X PUT -d '{ “name”: “value”, “name2”: “value2” }' http://localhost:8080/api/{id}

#####DELETE
curl -X DELETE -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/{id}

#####POST
curl -H "Authorization: Bearer $TOKEN" -H "Accept: application/json" -H "Content-type: text/plain" -X POST -d '{ "name": "value" }' http://localhost:8080/api/


##Incremental Approach/Thought Process:

1) Set up skeleton Maven project with Spring Boot (MVC) and MongoDB dependencies, Spring Security. Project Structure as following:
src/main/java
	restdemo
	restdemo/controller
	restdemo/model
	restdemo/objects (normally we would split this into BO and DTO for clear separation of logic, but unnecessary in this case so I’ve condensed into one package)
	restdemo/service
src/test/java

2) Create Service and Model Interfaces

3) Create our RestObject class

4) Set up test framework dependencies and write the following test classes for services and authentication tests:
AuthenticationTest
DataServiceTestImpl

5) Write Service, Model, and Controller classes and wire them together with Spring Boot annotations

6) Perform Unit Testing and packaging 



##Improvements/Future work:

-Unit Test class for Authentication flow
-Integration Test for end-to-end flow, i.e. client request <-> Controller handling <-> Backend services
-Provide more validation checks, error and format checking, and error-handling for service calls
-Use a config file to inject RestObjects into test classes



##Justifications:

These are specific concerns I addressed via my implementation:

1) Spring Boot as a framework for prototyping the Rest services

For the purposes of this demo I have chosen Spring Boot and here’s why:

-Provides out-of-the-box implementation for Restful web services and highly recommended by Spring (thus no need for a lot of boilerplate code)
-I don’t have to configure web.xml as defaults are good enough for us
-Test scope dependencies: Provides support for Spring Test, JUnit, Mockito, Hamcrest
-If more granular control is required, we can easily override the default beans instantiated by Boot
-Can be easily extended, if necessary, to run Spring Boot Actuator which provides several interfaces for the following:

    /health – returns “ok” as text/plain content which is useful for simple service monitoring
    /env – check environment configuration, property file and command line argument overrides, active profiles
    /metrics – basic statistics on your service endpoints (e.g. hit count, error count)
    /dump – thread dump
    /trace – the latest HTTP request/response pairs

2) Objects
-As there are no objects or variables specified, there is only one Java object created called RestObject. It contains two variables: id for unique identification, and contents, which is merely the whole JSON dump of the object.

