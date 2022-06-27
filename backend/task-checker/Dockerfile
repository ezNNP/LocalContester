FROM openjdk:8-alpine

COPY build/libs/*.jar checker.jar

EXPOSE 44881

ENTRYPOINT ["java", "-jar", "/checker.jar"]