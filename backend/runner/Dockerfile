FROM openjdk:8-alpine

COPY build/libs/*.jar app.jar

EXPOSE 44880

ENTRYPOINT ["java", "-jar", "/app.jar"]