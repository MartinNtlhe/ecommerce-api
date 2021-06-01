# ECommerce-Api

This project provide end point for generating user bills for an online e-commerce store
Here is an overview of the project's features: 
- Leverage Spring Boot framework to build a Monolithic application. 
- Uses Spring Data JPA to persist data to h2 database. 
- Uses H2, an in-memory database for spring application.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Pre-requisite](#pre-requisite)
* [Setup](#setup)

## General info
This project is a simple spring boot, unauthenticated api for a general online e-commerce store.
## Technologies
Project is created with:
* [Spring boot](https://spring.io/)
* [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven 3](https://maven.apache.org/)

#### Pre-requisites:
- Install Java JDK 1.8 and ensure it is available in your PATH
- _(Optional)_ Apache Maven is used for an alternate build system.  [Click for instructions](https://maven.apache.org/install.html).

## Setup
In this section run the Spring Boot application on your local workstation.
If this works you are in business:

Clone project from its repository into your local machine and navigate into its directory
```
$ git clone git@github.com:MartinNtlhe/ecommerce-api.git
$ cd ecommerce-api
```
#####Build
To build the project one must run the following commands on the project root folder:
`mvn clean package`

#####Run
Run the application on localhost:

```
mvn spring-boot:run
```
Validate. You should be able to read `swagger documentation` of the application which list all apis of the system.

Open [swagger-document](http://localhost:8081/swagger-ui.html#/) to view the api documentation

That's it...

##Testing
To run application's unit tests, run in the terminal `mvn clean verify -DskipITs=false`.
