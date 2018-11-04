# Payment Challenge

## Table of Contents

- [Introduction](#introduction)
- [Technical Info](#technical-info)
- [Requirements](#requirements)
- [Usage](#usage)
- [Test Coverage](#test-coverage)
- [Non-Tested Classes](#non-tested-classes)

## Introduction

This is a Spring Boot 2.1.0 REST API with Swagger Documentation that uses Transactions and H2 as Database.

The main focus of this application is:

- Maintain *Accounts* and *Transfer* money between then.

The relationship between Account and Transfer was mapped with 2 `@ManyToOne` annotations at the `source` and `target` accounts inside the Transfer.

There is a requirement, that the system need to be able to handle simultaneous transfer requests.

In order to process the simultaneous transfers, the system make use of transactions.

I just used the Optimistic Lock JPA feature, that controls the object `version` to avoid invalid states in a distributed system.

As we are dealing with financial information, I used another JPA Feature called `Auditing`, that maintain `createadAt` and `updatedAt` fields at the entities.

The application make use of `RuntimeException`s for business flux control, that affects also the API responses.

There is a difference between the Requests and the Responses that is handled by the `Controllers` and the `Service` layer. There are some data that sometimes we don't want or we can't expose for external world. 
Because of this, the application have different objects that comes in and out.

To convert the Objects, I have created a Functional Interface that can be implemented with the necessary objects.


## Technical Info

The software was built using the following tools/platforms:
- [Linux Mint 19](https://www.linuxmint.com/) - The Operational System that I use
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The Java IDE I Love S2
- [Java 8](https://www.java.com/pt_BR/) - **THE PROGRAMMING LANGUAGE**
- [Spring Boot 2.1.0](http://spring.io/projects/spring-boot) - The framework I use every day for building nice applications
- [H2 Database](http://www.h2database.com/html/main.html) - Database that can be used as a library
- [Lombok](https://projectlombok.org/) - Framework used to reduce boilerplate code
- [SpringFox](http://springfox.github.io/springfox/) - Library used to generate Swagger Documentation and UI
- [Maven](https://maven.apache.org/) - Just Maven!
- [JUnit](https://junit.org/) - Used for tests
- [AssertJ](http://joel-costigliola.github.io/assertj/) - assertThat() like a *PRO*
- [Mockito](https://site.mockito.org/) - Mocking dependencies
- [Jacoco](https://www.eclemma.org/jacoco/trunk/doc/maven.html) - Test Coverage Library

## Requirements

In order to run the application, you need to have installed at your environment:
1. [Java 8](https://www.java.com/pt_BR/)
2. [Maven](https://maven.apache.org/)

## Usage

First, you have to generate the `jar` with `mvn`:

```sh
$ mvn clean install
```

You are ready to run with the command:

```sh
$ java -jar target/payment-challenge-1.0.0-SNAPSHOT.jar 
```

The program will start embedded tomcat at port 8080, so you can now navigate to [Swagger UI](http://localhost:8080/swagger-ui.html)

Swagger Documentation could be reached [HERE](http://localhost:8080/v2/api-docs) and can be imported to Postman, for example.

## Test Coverage

I tried to focus on the application *CORE BUSINESS*, so not all the classes were not tested on purpose.

1. Test Coverage is 100% of Tested Classes
2. The Coverage is measured by Jacoco library that gives a HTML report that can be reached at `target/site/jacoco/index.html`

## Non-Tested Classes
1. `Exceptions` were not tested.
2. `PaymentChallengeApplication` was nos tested
3. `JpaConfiguration` and `SwaggerConfig` were not tested 
3. `model` package was not tested, but the `Account` class was, because of the business logic methods
4. `DTOs` inside of `request` and `response` packages were not tested
5. `@NotSameAccount` annotation was not tested

