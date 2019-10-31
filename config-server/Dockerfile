FROM openjdk:8-jdk-alpine
EXPOSE 8888
VOLUME /tmp
ADD ./target/config-server.jar config-server.jar
RUN sh -c 'touch /config-server.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/config-server.jar"]