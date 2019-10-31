FROM openjdk:8-jdk-alpine
EXPOSE 8082
VOLUME /tmp
ADD ./target/users-api.jar users-api.jar
RUN sh -c 'touch /users-api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/users-api.jar"]