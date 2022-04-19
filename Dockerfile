FROM openjdk:16-alpine3.13
LABEL maintainer="endri.zeqo@gmail.com"
VOLUME /main-app
ADD target/employer-docker.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
