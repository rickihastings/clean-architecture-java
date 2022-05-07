# Clean Architecture

This project is a Java template for creating a Spring Boot application using the principles of [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).
This project also uses a pattern called CQRS. You can read more about that [here](https://martinfowler.com/bliki/CQRS.html).

## Why

I'm a big fan of the popular [.NET template by Jason Taylor](https://github.com/jasontaylordev/CleanArchitecture)
therefore this template takes a lot of inspiration from that template,
and aims to provide a similar experience in Spring and Java. Although, primarily, I have created this as more of a learning
exercise. Feel free to use this however you wish.

## Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data](https://spring.io/projects/spring-data)
- [Lombok](https://projectlombok.org/) and [Hibernate Validator](https://hibernate.org/validator/) for validation
- [ModelMapper](https://modelmapper.org/)
- [PipelinR](https://github.com/sizovs/PipelinR)
- [JUnit4](https://junit.org/junit4/), [Java Snapshot Testing](https://github.com/origin-energy/java-snapshot-testing), [Jackson](https://github.com/FasterXML/jackson)
- [NodeJS](https://nodejs.org/en/) and [PlopJS](https://plopjs.com/)

## Todo

- [x] Integration Tests
- [ ] Refactor domain layer to remove dependencies

## Getting Started

## Testing

This project uses [JUnit4](https://junit.org/junit4/), Spring Security Test and [Java Snapshot Testing](https://github.com/origin-energy/java-snapshot-testing) for unit tests.
I've chosen to use Snapshot Testing here as I believe this is quite a clean approach for validating the output of
commands and queries, which can often become complex in real-world applications.

## Generator

As there is quite a bit of boilerplate required to create new commands or queries, this project ships with a
small generator utility to quickly create the necessary files and test files.

To set up the generator, run the following (you will need NodeJS, I use [nvm](https://github.com/nvm-sh/nvm) to manage this easily):

```shell
cd generator
npm install
```

You can run the generator by running the following command. You will be presented with an interactive generator which will aim to do its best to get you started with new commands, queries, and events.

```shell
npm run generate
```

## Overview

### Domain

This layer contains all entities, events, enums, interfaces and logic specific to the domain layer.

### Application

This layer contains all application logic and is dependent on the domain layer but has no dependencies on any other layers.
This layer defines all the interfaces that are implemented in the infrastructure layer.
For example, if you needed to add a new service, you would define the interface here and implement it in the interface layer.

### Infrastructure

This layer contains classes for accessing external resources such as databases, file services, web services, etc.
These classes should implement the interfaces defined in the application layer.

### Web

This layer defines the entry point for the web service, which is a Spring Rest API. This layer handles configuring the
Spring application and all the dependencies required, such as Spring Security, OpenAPI, Logging, etc. This layer depends
on the application and infrastructure layers.

### License

This project is licensed with the MIT license.
