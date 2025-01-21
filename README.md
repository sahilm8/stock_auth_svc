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
curl --location 'localhost:8080/api/v2/auth/register-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Test",
    "lastName": "User",
    "email": "tu@example.com",
    "password": "password1234"
}'
```

#### Response

```
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3Mzc1MDEyNTAsImV4cCI6MTczODEwNjA1MH0.7Yj96BvdTv-4b05jPBhZ_QdpzWD8kosxyzlyoWsvqXk",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dTJAZXhhbXBsZS5jb20iLCJpYXQiOjE3Mzc1MDEyNTAsImV4cCI6MTc0MDA5MzI1MH0.FAaKVZuRkJ4lUVdo8cnAd6kFfbn0JzQtT5roa4n8THs"
}
```

### Authenticate User

#### Request

```
curl --location 'localhost:8080/api/v2/auth/authenticate-user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "tu@example.com",
    "password": "password1234"
}'
```

#### Response

```
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dUBleGFtcGxlLmNvbSIsImlhdCI6MTczNzUwMTAzOSwiZXhwIjoxNzM4MTA1ODM5fQ.Spi1W0gRCULRwLoU3Jz8sW4_zL0XnGi0xME3PTbEFK4",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dUBleGFtcGxlLmNvbSIsImlhdCI6MTczNzUwMTAzOSwiZXhwIjoxNzQwMDkzMDM5fQ._WEcXnNyyCngS6gmJ4F3ho_Vn6Vo2DyzrmNFu-i6VjY"
}
```
