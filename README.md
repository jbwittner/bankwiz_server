# Bankwiz_server
Server of Bankwiz application

# Sonar status

## Develop

### Domain


[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=bugs)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_domain&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_domain)

### Infrastructure

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=bugs)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=coverage)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server_infrastructure&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=jbwittner_bankwiz_server_infrastructure)

## Description
Bankwiz Server is the server component of a bank account management application. Developed in Java 17 and managed with Maven, this server is designed to provide a robust and secure interface for managing banking transactions and user interactions. It uses a hexagonal architecture, integrates with a database, and utilizes Auth0 for OAuth2 authentication.

This is crucial for retrieving the server code generation from the OpenAPI specification at [Bankwiz OpenAPI](https://github.com/jbwittner/bankwiz_openapi).

## Requirements
* Java 17
* Maven 3.8.8

## Architecture
The project uses a parent pom with two submodules:

Domain Module: Contains the business logic.
Architecture Module: Implements the domain and manages interactions with external systems.

## Authentication
Spring Boot's spring-boot-starter-oauth2-resource-server is used for user authentication, utilizing JWT access tokens provided by Auth0.

## Building with Makefile
The provided Makefile includes several tasks that can be used to build and manage the Bankwiz server. Here is an overview of these tasks:

- **clean-openapi**: Removes the openapi directory, thus cleaning any previous OpenAPI generation.
- **generate-openapi**: Utilizes Docker to run openapitools/openapi-generator-cli:v7.2.0 and generate Spring source code from the OpenAPI specification (openapi.yaml). This task creates specified packages for APIs and models in the openapi directory.
- **install-openapi**: Executes a mvn clean install on the pom.xml located in the openapi directory, thus installing the generated Maven project.
- **all**: Executes the tasks clean-openapi, generate-openapi, and install-openapi in sequence, providing a way to fully automate the setup of the development environment after any changes to the OpenAPI specification.
- **spotless-apply**: Applies code formatting settings to the project's source code using the Spotless Maven plugin.
- **spotless-check**: Checks if the code formatting of the project matches the one defined by Spotless.
- **docker-build**: Builds a Docker image of the application, tagging it as bankwiz_server. This task may require specific environment variables or adjustments based on your Docker configuration.
- **http-coverage-domain**: Starts a simple HTTP server with Python to serve the Jacoco code coverage reports generated in the domain target/site/jacoco on port 8001.
- **http-coverage-infrastructure**: Similar to http-coverage-domain, but for the infrastructure, serving the Jacoco code coverage reports on port 8002.

## Running the Container

To run the container, use the following commands:

```bash
docker run -p 8080:8080 bankwiz_server
```

```bash
docker run -p 8080:8080 -e JVM_OPTS="-Xms256m -Xmx512m" -e PROFILE="development" bankwiz_server
```
