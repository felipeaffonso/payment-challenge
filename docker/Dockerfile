FROM openjdk:8-jdk-alpine

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/payment-challenge-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} payment-challenge.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/payment-challenge.jar"]