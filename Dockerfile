FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
VOLUME /main-app
ADD target/employer-docker.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
