FROM eclipse-temurin:17_35-jdk-alpine
VOLUME /main-app
ADD target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
