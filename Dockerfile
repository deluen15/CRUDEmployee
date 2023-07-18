FROM eclipse-temurin:17_35-jdk-alpine
VOLUME /tmp
ADD service.jar service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/service.jar"]
