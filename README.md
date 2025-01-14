# Auth Globe API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)

API to handle user authentication.

## Stack

- Java 21
- Maven
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL Connector
- Docker
- Spring Dotenv
- Lombok
- Spring Test
- H2 Database
- MacOS DNS Resolver
- Spring Security
- Jackson Web Token (api, impl, jackson)
- Spring Validation
- Jakarta Validation

## Setup

- Install dependencies:

```
./mvnw clean install
```

- Pull Docker MySQL image for running database server:

```
docker pull mysql:latest
```

- Create an external volume for storing MySQL data:

```
docker volume create mysql_volume
```

- Run the container:

```
docker compose up -d
```

- Start the application:

```
./mvnw spring-boot:run
```

- Stop the container:

```
docker compose down
```

## Endpoints

A new user can be registered by sending a POST request to /register and an existing user can be authenticated by sending a POST request to /authenticate.

### Requests

- GET /:

```
curl -i -X GET http://localhost:8080/api/v1/auth/
```

- POST /register:

```
curl --location 'localhost:8080/api/v1/auth/register' --header 'Content-Type: application/json' --data-raw '{ "firstName": "Test", "lastName": "User", "email": "test5@example.com", "password": "password123" }'
```

- POST /authenticate:

```
curl --location 'localhost:8080/api/v1/auth/authenticate' --header 'Content-Type: application/json' --data-raw '{ "email": "test5@example.com", "password": "password123" }'
```
