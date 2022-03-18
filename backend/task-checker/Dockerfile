FROM openjdk:8-alpine

COPY build/libs/*.jar app.jar

EXPOSE 44881

ENTRYPOINT ["java", "-jar", "/app.jar"]