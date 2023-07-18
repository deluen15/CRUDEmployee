FROM eclipse-temurin:17_35-jdk-alpine
VOLUME /main-app
ADD service.jar service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service.jar"]
