# Stock Auth API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)

API to handle user authentication.

## Stack

- Java 21
- Maven
- Spring Boot
- Spring Web
- Spring Validation
- Jakarta Validation
- Spring Data JPA
- MySQL Connector
- Spring Security
- JWT API
- JWT Implementation
- JWT Jackson
- Spring Dotenv
- Lombok
- Spring Test
- H2 Database
- MacOS DNS Resolver
- Docker

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
docker volume create stock_auth_volume
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

Requests can be made to perform the following actions:

- Register User
- Authenticate User

### Register User

#### Request

```

```

#### Response

```

```

### Authenticate User

#### Request

```

```

#### Response

```

```
