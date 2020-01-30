# spring-cloud-sample

A sample microservice application to demonstrate some concepts and tools of spring cloud

## Microservices

Two apis are used to demonstrate **springboot** and **springcloud** and can run with followed profiles:

- local => Run locally with h2 database and no service register;
- development => Run locally with h2 database and local service register; (depends on eureka server localy)
- dev => Run locally but config is on the cloud; (depends config-server)

### users-api

A RESTful api to users operations (CRUD)

URL: [http://host:8082/users](http://host:8082/users)

### cards-api

A RESTful api to cards operations (CRUD)

URL: [http://host:8083/cards](http://host:8083/cards)

### eureka-server

A [Springcloud Netflix Eureka]([https://spring.io/projects/spring-cloud-netflix]) server example to service register

URL: [http://host:8761/eureka](http://host:8761/eureka)

### config-server

A [Springcloud Config]([https://spring.io/projects/spring-cloud-config]) server example to  register configurations and decouple applications

URL: [http://host:8888/configserver](http://host:8888/configserver)

## Running

## Standalone (Maven)

```bash
mvn -U -Pprofile clean spring-boot:run
```

## Stack (Docker - Recommended)

```bash
docker-compose up [-d]
```