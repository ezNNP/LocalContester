FROM openjdk:8-alpine

COPY build/libs/*.jar gateway.jar

EXPOSE 44881

ENTRYPOINT ["java", "-jar", "/gateway.jar"]