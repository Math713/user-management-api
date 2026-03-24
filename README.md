# User Management API

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=flat-square&logo=docker)
![JUnit 5](https://img.shields.io/badge/JUnit-5-blue?style=flat-square&logo=junit5)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=flat-square&logo=swagger)

A RESTful API built with Spring Boot for managing users, demonstrating clean backend practices such as layered architecture, DTO usage, input validation, custom exception handling, and unit testing.

---

## Getting Started

### Running with Docker (recommended)

**Requirements:** Docker and Docker Compose

```bash
git clone https://github.com/Math713/user-management-api.git
cd user-management-api
```

Create a `.env` file in the project root:

```env
DB_URL=jdbc:postgresql://db:5432/user_management
DB_USERNAME=postgres
DB_PASSWORD=your_password
```

```bash
docker-compose up
```

No Java, no PostgreSQL installation needed. Everything runs inside containers.

Swagger UI → `http://localhost:8080/swagger-ui.html`

---

### Running locally

**Requirements:** Java 17+ and Maven 3.8+

```bash
git clone https://github.com/Math713/user-management-api.git
cd user-management-api
mvn spring-boot:run
```

Set the following environment variables before running:

| Variable | Description |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/user_management` |
| `DB_USERNAME` | Your PostgreSQL username |
| `DB_PASSWORD` | Your PostgreSQL password |

In IntelliJ: Run > Edit Configurations > Modify Options > Environment Variables

```bash
# Run tests
mvn test
```

---

## Docker

The project includes a multi-stage Dockerfile and a docker-compose setup that orchestrates two services:

- **app** — Spring Boot application built with Amazon Corretto 17 and Maven
- **db** — PostgreSQL 16

The Dockerfile uses a multi-stage build: the first stage compiles the project with Maven, the second runs only the final `.jar`, keeping the image lean.

```bash
docker-compose up       # start both services
docker-compose down     # stop and remove containers
docker-compose up --build  # rebuild image after code changes
```

---

## Endpoints

### Create user

```
POST /api/users
```

```json
// Request
{ "name": "John Doe", "email": "john@email.com" }

// Response 201
{ "id": 1, "name": "John Doe", "email": "john@email.com" }
```

### Update user

```
PUT /api/users/{id}
```

```json
// Request
{ "name": "John Smith" }

// Response 200
{ "id": 1, "name": "John Smith", "email": "john@email.com" }
```

| Status | Description |
|---|---|
| `400` | Invalid input |
| `404` | User not found |
| `409` | Email already in use |

---

## Tech stack

Java · Spring Boot · Maven · PostgreSQL · JPA/Hibernate · JUnit 5 · Mockito · Swagger/OpenAPI · Jakarta Validation · Docker

---

## Roadmap

- [ ] Deploy
