##Base Image
#FROM eclipse-temurin:17-jre
#
##Working directory inside container
#WORKDIR /app
#
##COPY jar from target folder
#COPY target/coc-dashboard-0.0.1.jar app.jar
#
##Expose port
#EXPOSE 8080
#
##Run the application
#ENTRYPOINT ["java", "-jar","app.jar"]


# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/coc-dashboard-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]