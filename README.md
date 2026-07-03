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

Create your local environment file:

```powershell
Copy-Item .env.example .env
```

Then edit `.env` and set your local database password.

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

## MVP API

### Customers

- `POST /api/customers`
- `GET /api/customers/{id}`

### Policies

- `POST /api/policies`
- `GET /api/policies/{id}`

### Claims

- `POST /api/claims`
- `GET /api/claims/{id}`
- `GET /api/claims?status=REGISTERED`
- `PATCH /api/claims/{id}/status`
- `GET /api/claims/{id}/history`

## Claim Lifecycle

Supported statuses:

- `REGISTERED`
- `IN_REVIEW`
- `APPROVED`
- `REJECTED`
- `CANCELLED`

Initial transition rules:

- A new claim starts as `REGISTERED`.
- `REGISTERED` can move to `IN_REVIEW` or `CANCELLED`.
- `IN_REVIEW` can move to `APPROVED`, `REJECTED`, or `CANCELLED`.
- `APPROVED`, `REJECTED`, and `CANCELLED` are terminal states.

## Database Defaults

The project does not store real database credentials in `application.yml`.

Local database values are loaded from `.env`, which is ignored by Git. Use `.env.example` as a template:

- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `SERVER_PORT`

For CI or cloud deployment, configure these values as environment variables or repository/environment secrets.
