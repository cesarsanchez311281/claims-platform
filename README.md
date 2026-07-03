# Claims Platform

Backend platform for insurance claims management, focused on modern Java architecture, API design, persistence, security, testing, documentation, and DevOps practices.

## Project Status

Phase 2: Spring Boot foundation.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Maven Wrapper
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- OpenAPI / Swagger UI
- Spring Boot Actuator
- Testcontainers
- Docker Compose

## Documentation

- [Project Scope](docs/project-scope.md)

## Local Setup

### Requirements

- Java 21
- Docker Desktop

Maven does not need to be installed globally because the project includes Maven Wrapper.

### Start PostgreSQL

```powershell
docker compose up -d
```

### Run the Application

```powershell
.\mvnw.cmd spring-boot:run
```

### Run Tests

```powershell
.\mvnw.cmd test
```

### Build

```powershell
.\mvnw.cmd verify
```

## Local URLs

- API health: http://localhost:8080/api/health
- Actuator health: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Database Defaults

The local Docker Compose database uses:

- Database: `claims_platform`
- Username: `claims_user`
- Password: `claims_password`
- Port: `5432`

These values can be overridden with:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `SERVER_PORT`
