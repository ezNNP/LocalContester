FROM openjdk:8-alpine

COPY build/libs/*.jar runner.jar

EXPOSE 44880

ENTRYPOINT ["java", "-jar", "/runner.jar"]