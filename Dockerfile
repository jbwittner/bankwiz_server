# Stage 1: Build OpenApi
FROM maven:3.8.7-eclipse-temurin-17-alpine AS openapi

# Set the working directory in the container
WORKDIR /app

# Copy the openapi folder
COPY openapi openapi

# Run the Maven command to compile the project
RUN mvn clean install -f openapi/pom.xml

# Stage 2: Build the project
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Copy the dependencies from the previous stage
COPY --from=openapi  /root/.m2/repository /root/.m2/repository

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml pom.xml

# Copy the project source code
COPY infrastructure infrastructure
COPY domain domain

# Run the Maven command to compile the project
RUN mvn clean package -DskipTests -Dcheckstyle.skip

# Stage 2: Run the application
FROM openjdk:17-bullseye

# Set the working directory in the container
WORKDIR /app

# Set the JVM options environment variable
ENV JVM_OPTS="-Xms512m -Xmx1024m"

# Set the Spring Boot profile environment variable
ENV PROFILES="production"

# Copy the compiled JAR file from the previous stage
COPY --from=build /app/infrastructure/target/infrastructure-*.jar bankwiz-server.jar

# Expose the port on which your application listens
EXPOSE 8080

# Set the entrypoint to run the JVM with the specified JVM options and Spring Boot parameters
ENTRYPOINT java $JVM_OPTS -Dspring.profiles.active=$PROFILES -jar bankwiz-server.jar