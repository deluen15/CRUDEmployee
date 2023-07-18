FROM eclipse-temurin:17_35-jdk-alpine AS build
COPY . /usr/app
WORKDIR /usr/app
RUN chmod +x mvnw \
    && ./mvnw --version \
    && ./mvnw clean package

FROM eclipse-temurin:17_35-jdk-alpine
COPY --from=build /usr/app/target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
