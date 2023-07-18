FROM eclipse-temurin:17_35-jdk-alpine
COPY . /usr/app
WORKDIR /usr/app
ADD --from=build /usr/app/target/*.jar service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/service.jar"]
