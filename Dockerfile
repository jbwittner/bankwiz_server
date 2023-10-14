# Stage 1: Resolve dependencies
FROM maven:3.8.7-eclipse-temurin-17-alpine AS dependencies

# Set build arguments for environment variables
ARG USER_GITHUB_LOGIN
ARG USER_GITHUB_KEY

# Set environment variables for the build
ENV USER_GITHUB_LOGIN=$USER_GITHUB_LOGIN
ENV USER_GITHUB_KEY=$USER_GITHUB_KEY

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml pom.xml
COPY infrastructure/pom.xml infrastructure/pom.xml
COPY domain/pom.xml domain/pom.xml

# Copy the settings.xml file from the "maven" directory in the project
COPY maven/settings.xml /root/.m2/settings.xml

# Run the Maven command to get all dependencies
RUN --mount=type=secret,id=USER_GITHUB_LOGIN,dst=/etc/secrets/USER_GITHUB_LOGIN --mount=type=secret,id=USER_GITHUB_KEY,dst=/etc/secrets/USER_GITHUB_KEY USER_GITHUB_KEY="$(cat /etc/secrets/USER_GITHUB_KEY)" && USER_GITHUB_LOGIN="$(cat /etc/secrets/USER_GITHUB_LOGIN)" && mvn dependency:go-offline --settings /root/.m2/settings.xml

# Stage 2: Build the project
FROM maven:3.8.7-eclipse-temurin-17-alpine AS build

# Copy the dependencies from the previous stage
COPY --from=dependencies  /root/.m2/repository /root/.m2/repository

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml pom.xml

# Copy the project source code
COPY infrastructure infrastructure
COPY domain domain

# Run the Maven command to compile the project
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17.0.2-slim-buster

# Set the working directory in the container
WORKDIR /app

# Set the JVM options environment variable
ENV JVM_OPTS="-Xms512m -Xmx1024m"

# Set the Spring Boot profile environment variable
ENV PROFILE="production"

# Copy the compiled JAR file from the previous stage
COPY --from=build /app/infrastructure/target/infrastructure-*.jar bankwiz-server.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the entrypoint to run the JVM with the specified JVM options and Spring Boot parameters
ENTRYPOINT java $JVM_OPTS -Dspring.profiles.active=$PROFILE -jar bankwiz-server.jar