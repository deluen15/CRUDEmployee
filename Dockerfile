# Use the eclipse-temurin:17_35-jdk-alpine as the base image
FROM eclipse-temurin:17_35-jdk-alpine

WORKDIR /app

COPY . /app

CMD ["java", "-jar", "service.jar"]
