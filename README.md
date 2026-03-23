# 👤 User Management API

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=flat-square&logo=postgresql)
![JUnit 5](https://img.shields.io/badge/JUnit-5-blue?style=flat-square&logo=junit5)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=flat-square&logo=swagger)

A RESTful API built with **Spring Boot** for managing users, demonstrating clean backend practices such as layered architecture, DTO usage, input validation, custom exception handling, and unit testing.

---

## 🚀 Getting Started

**Requirements:** Java 17+ and Maven 3.8+
```bash
git clone https://github.com/Math713/user-management-api.git
cd user-management-api
mvn spring-boot:run
```

Swagger UI → `http://localhost:8080/swagger-ui.html`
```bash
# Run tests
mvn test
```

---

## ⚙️ Configuration

Set the following environment variables before running the application:

| Variable | Description |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/user_management` |
| `DB_USERNAME` | Your PostgreSQL username |
| `DB_PASSWORD` | Your PostgreSQL password |

In IntelliJ: Run > Edit Configurations > Modify Options > Environment Variables

---

## 📡 Endpoints

### Create User
```
POST /api/users
```
```json
// Request
{ "name": "John Doe", "email": "john@email.com" }

// Response 201
{ "id": 1, "name": "John Doe", "email": "john@email.com" }
```

### Update User
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

## 🛠️ Tech Stack

Java · Spring Boot · Maven · PostgreSQL · JUnit 5 · Mockito · Swagger/OpenAPI · Jakarta Validation

---

## 🗺️ Roadmap

- [ ] Deploy
