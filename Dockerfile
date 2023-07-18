FROM eclipse-temurin:17_35-jdk-alpine AS builder
COPY --from=builder /workspace/service/target/service.jar service.jar
ENTRYPOINT ["java","-jar","service.jar"]
