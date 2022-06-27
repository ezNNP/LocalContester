FROM openjdk:8-alpine

COPY build/libs/*.jar store.jar

EXPOSE 44881

ENTRYPOINT ["java", "-jar", "/store.jar"]