I'm trying out [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

# Application
Simulates a web application for renting cars. The application has e REST API.

## Clean Architecture
![Clean Architecture](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

## Implementation

### Tech stack
* Spring Boot Web
* Kotlin
* Gradle

## Packages

The root packages are:

* [application](src/main/kotlin/nu/westlin/ca/carrental/application)
* [domain](src/main/kotlin/nu/westlin/ca/carrental/domain)
* [infrastructure](src/main/kotlin/nu/westlin/ca/carrental/infrastructure)
* [presentation](src/main/kotlin/nu/westlin/ca/carrental/presentation)

## Test data

If you want to start the application with some users, cars and bookings use the Spring profile `initwithdata`.