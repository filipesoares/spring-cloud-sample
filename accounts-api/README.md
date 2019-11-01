# Cataloger
A sample RESTful api built-in NodeJs + Express

## Overview
This api is a example of RESTful api in NodeJS + Express. The scenario of a system resource was used to show this.

The persistence is done in mongo database.

The CRUD operations are possible in api through HTTP methods call.

## Enviroinment
The DockerFile provide the build enviroinment of api and docker-compose.yml file provide the simple stack used.

You need create a .env file in root path containning the following properties:

```properties
DB_URL=mongodb://mongodb:27017/cataloger
```

## Running in Docker

To run just run the cli **`$ docker-compose up`**

## Running Standalone

You can run without docker container, to this just do the foolowing steps:

- Modify the propertie of mongo url in **`.env`** file;
- Run **`npm start`** in your CLI;

## Resources

Just access the address **http://localhost:3000** in your rest client. (e.g. Postman)

### Endpoints

| HTTP Method | Endpoint | Description |
| ----------- | -------- | ----------- |
| `GET` |  /systems | List all systems |
| `GET` |  /systems/:id | Fetch a system |
| `POST` |  /systems | Create a new system |
| `PUT` |  /systems/:id | Update a system |
| `DELETE` |  /systems/:id | Delete a system |

### Model

#### System
```json
{
    "_id": "654sdf654sdf4sdf654sdf",
    "_name": "System",
    "_version": "1.0.0",
    "_context": "/_api",
    "tags": "API RESTFul NodeJS"
}
```

## Tests

Just run

```bash
$ npm test
```

### See

- Mocha => [https://mochajs.org/](https://mochajs.org/)
- Chai  => [https://www.chaijs.com](https://www.chaijs.com)

## Style Guide Formatter (Linter)

- Prettier => [https://prettier.io/](https://prettier.io/)

### Run

```bash
$ npx prettier --write "src/**/*.js"
```

## Author

Filipe Oliveira - [https://github.com/FilipeSoares](https://github.com/FilipeSoares)
