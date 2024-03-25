# Bankwiz_server
Server of Bankwiz application

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=code_smells)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=coverage)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=ncloc)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=duplicated_lines_density)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=alert_status)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=fr.bankwiz.server%3Aparent&metric=security_rating)](https://sonarcloud.io/summary/overall?id=fr.bankwiz.server%3Aparent)

## Description
Bankwiz Server is the server component of a bank account management application. Developed in Java 17 and managed with Maven, this server is designed to provide a robust and secure interface for managing banking transactions and user interactions. It uses a hexagonal architecture, integrates with a MySQL database, and utilizes Auth0 for OAuth2 authentication.

This is crucial for retrieving the server code generation from the OpenAPI specification at [Bankwiz OpenAPI](https://github.com/jbwittner/bankwiz_openapi).

## Requirements
* Java 17
* Maven

To work on this project, it is necessary to define three environment variables: `USER_GITHUB_LOGIN`, `USER_GITHUB_KEY`, and `SONAR_TOKEN`.

The `USER_GITHUB_LOGIN` variable should be set with your GitHub login, which is your GitHub username.

The `USER_GITHUB_KEY` variable should be set with a Personal Access Token that has the required `read:packages` scope. This token grants access to GitHub packages.

The `SONAR_TOKEN` variable should be set with your SonarCloud token. This will allow you to push analysis results from your local machine to SonarCloud. (only to run scans)

Ensure to keep these tokens and your Personal Access Token confidential and avoid sharing them with unauthorized individuals. By correctly setting these environment variables, your development environment will be able to securely access GitHub and SonarCloud using your credentials.

**Important note:** These environment variables need to be set either on the host machine (in case of using DevContainer) or directly on the machine where you develop.

Example settings.xml configuration for Maven:

```xml
<server>
    <id>github</id>
    <username>${env.USER_GITHUB_LOGIN}</username>
    <password>${env.USER_GITHUB_KEY}</password>
</server>
```

## Architecture
The project uses a parent pom with two submodules:

Domain Module: Contains the business logic.
Architecture Module: Implements the domain and manages interactions with external systems.

## Authentication
Spring Boot's spring-boot-starter-oauth2-resource-server is used for user authentication, utilizing JWT access tokens provided by Auth0.

## Building with Makefile
The provided Makefile includes several tasks that can be used to build and manage the Bankwiz server. Here is an overview of these tasks:

- **compile**: Compiles the source code of the project using Maven.
- **package**: Packages the compiled code in its distributable format, such as a JAR, using Maven.
- **package-withouttest**: Similar to package, but skips the tests during the build.
- **spotless-apply**: Applies code formatting settings to the project's source code using the Spotless Maven plugin.
- **spotless-check**: Checks if the code formatting of the project matches the one defined by Spotless.
- **sonar**: Executes a 'clean verify' operation with Maven, runs the unit tests, and performs a SonarQube analysis on the code associated with the currently active Git branch.
- **docker-build**: Builds a Docker image of the application. The built image is tagged as bankwiz_server. This task requires two environment variables to be set: USER_GITHUB_LOGIN and USER_GITHUB_KEY, which are used as secrets during the build process.

To execute a Makefile task, use the make command followed by the task name. For example, to compile the source code, run:

```shell
make compile
```

For packaging the compiled code without running tests, run:

```shell
make package-withouttest
```

Ensure that the `USER_GITHUB_LOGIN` and `USER_GITHUB_KEY` environment variables are set before running any Makefile tasks that require them.


## Building without Makefile

The following are the commands you can run to build and manage the Bankwiz server:

Compile the source code:

```shell
mvn compile
```

Package the compiled code into its distributable format, such as a JAR:

```shell
mvn package
```

Package the compiled code without running the tests:

```shell
mvn package -DskipTests
```

Apply code formatting settings to the project's source code using the Spotless Maven plugin:

```shell
mvn spotless:apply
```

Check if the code formatting of the project matches the one defined by Spotless:

```shell
mvn spotless:check
```

Perform a 'clean verify' operation with Maven, run the unit tests, and execute a SonarQube analysis on the code:

```shell
mvn clean verify sonar:sonar -Dsonar.branch.name=`git rev-parse --abbrev-ref HEAD`
```

Build a Docker image of the application. The built image is tagged as bankwiz_server:

```shell
DOCKER_BUILDKIT=1 docker build --secret id=USER_GITHUB_LOGIN --secret id=USER_GITHUB_KEY -t bankwiz_server .
```

Ensure that the USER_GITHUB_LOGIN and USER_GITHUB_KEY environment variables are set before running any commands that require them.

## Running the Container

To run the container, use the following commands:

```bash
docker run -p 8080:8080 bankwiz_server
```

```bash
docker run -p 8080:8080 -e JVM_OPTS="-Xms256m -Xmx512m" -e PROFILE="development" bankwiz_server
```
