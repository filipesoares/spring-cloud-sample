FROM openjdk:8-jdk-alpine
EXPOSE 8083
VOLUME /tmp
ADD ./target/cards-api.jar cards-api.jar
RUN sh -c 'touch /cards-api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cards-api.jar"]