FROM openjdk:8-alpine

COPY build/libs/*.jar eureka.jar

EXPOSE 44881

ENTRYPOINT ["java", "-jar", "/eureka.jar"]