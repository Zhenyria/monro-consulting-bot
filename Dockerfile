# Stage 1: Build the application
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY . ./project
WORKDIR /app/project
RUN mvn clean package

# Stage 2: Create the image
FROM tomcat:11.0-jdk17
ENV TZ=UTC
COPY --from=build /app/project/target/*.war /usr/local/tomcat/webapps/ROOT.war
