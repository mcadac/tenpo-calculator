FROM openjdk:8-jdk-alpine
RUN addgroup -S tenpo && adduser -S tenpo -G tenpo
USER tenpo:tenpo
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]