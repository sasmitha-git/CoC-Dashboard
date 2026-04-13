#Base Image
FROM eclipse-temurin:17-jre

#Working directory inside container
WORKDIR /app

#COPY jar from target folder
COPY target/coc-dashboard-0.0.1.jar app.jar

#Expose port
EXPOSE 8080

#Run the application
ENTRYPOINT ["java", "-jar","app.jar"]