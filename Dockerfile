FROM openjdk:21-jdk-slim
LABEL authors="Reza"
RUN mkdir /demo
WORKDIR /demo
ARG JAR_FILE=./target/demo-0.0.1.jar
COPY ${JAR_FILE} /demo/demo.jar
COPY help.txt /demo/help.txt
ENTRYPOINT ["java", "-jar", "/demo/demo.jar"]