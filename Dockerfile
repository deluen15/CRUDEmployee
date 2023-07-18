FROM eclipse-temurin:17_35-jdk-alpine
WORKDIR /app
COPY . /app
ADD service.jar service.jar
EXPOSE 8080
CMD ["java", "-jar", "service.jar"]
