FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ENV JAR_FILE=${JAR_FILE}
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]