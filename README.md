# Bankwiz_server
Server of Bankwiz application

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=code_smells)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=coverage)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=ncloc)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=duplicated_lines_density)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=alert_status)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=jbwittner_bankwiz_server&metric=security_rating)](https://sonarcloud.io/summary/overall?id=jbwittner_bankwiz_server)

BankWiz Server is a banking-related server application. This README provides instructions for setting up and maintaining the project.

## Requirements
To work on this project, it is necessary to define three environment variables: `USER_GITHUB_LOGIN`, `USER_GITHUB_KEY`, and `SONAR_TOKEN`.

The `USER_GITHUB_LOGIN` variable should be set with your GitHub login, which is your GitHub username.

The `USER_GITHUB_KEY` variable should be set with a Personal Access Token that has the required `read:packages` scope. This token grants access to GitHub packages.

The `SONAR_TOKEN` variable should be set with your SonarCloud token. This will allow you to push analysis results from your local machine to SonarCloud.

Ensure to keep these tokens and your Personal Access Token confidential and avoid sharing them with unauthorized individuals. By correctly setting these environment variables, your development environment will be able to securely access GitHub and SonarCloud using your credentials.

**Important note:** These environment variables need to be set either on the host machine (in case of using DevContainer) or directly on the machine where you develop.

## Development environment
To ensure a controlled development environment, a DevContainer has been provided for the project. The DevContainer includes a preconfigured development environment with the necessary tools and dependencies. It helps to standardize the development environment across different machines and ensures that all developers have a consistent setup.

By using the provided DevContainer, you can be confident that your development environment is properly configured, including the required environment variables. This helps to streamline the development process and ensures that everyone working on the project has a consistent and controlled environment.

## Building with Makefile
The provided Makefile includes several tasks that can be used to build and manage the Bankwiz server. Here is an overview of these tasks:

- **compile**: Compiles the source code of the project using Maven.
- **package**: Packages the compiled code in its distributable format, such as a JAR, using Maven.
- **package-withouttest**: Similar to package, but skips the tests during the build.
- **spotless-apply**: Applies code formatting settings to the project's source code using the Spotless Maven plugin.
- **spotless-check**: Checks if the code formatting of the project matches the one defined by Spotless.
- **sonar-unittest**: Executes a 'clean verify' operation with Maven, runs the unit tests, and performs a SonarQube analysis on the code associated with the currently active Git branch suffixed with _UT.
- **sonar-integrationtest**: Executes a 'clean verify' operation with Maven, runs the integration tests, and performs a SonarQube analysis on the code associated with the currently active Git branch suffixed with _IT.
- **sonar**: Invokes both the sonar-unittest and sonar-integrationtest tasks.
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
