# Claims Platform - Project Scope

## Objective

Build a backend platform for insurance claims management, focused on modern Java architecture, API design, persistence, security, testing, documentation, and DevOps practices.

The project is designed as a portfolio-grade backend system that demonstrates enterprise software engineering practices without overcomplicating the first version.

## MVP Scope

The MVP includes customer, policy, claim, status history, and audit management.

The first version will be implemented as a modular monolith. This keeps the domain cohesive while leaving a clear path to split modules into microservices later if needed.

## Users

- CUSTOMER: Creates claims and checks claim status.
- ANALYST: Reviews claims and updates claim status.
- ADMIN: Manages master data and reviews audit logs.

## Main Flow

1. A customer is registered.
2. A policy is registered and linked to the customer.
3. A claim is created for a policy.
4. The claim starts as REGISTERED.
5. An analyst moves the claim to IN_REVIEW.
6. The analyst approves or rejects the claim.
7. The system stores status history and audit events.

## Claim Statuses

- REGISTERED
- IN_REVIEW
- APPROVED
- REJECTED
- CANCELLED

## Main Entities

### Customer

Represents a policy holder or insured person.

Initial fields:

- id
- documentType
- documentNumber
- firstName
- lastName
- email
- phone
- createdAt
- updatedAt

### Policy

Represents an insurance policy associated with a customer.

Initial fields:

- id
- policyNumber
- customerId
- productType
- startDate
- endDate
- status
- createdAt
- updatedAt

### Claim

Represents an insurance claim created for a policy.

Initial fields:

- id
- claimNumber
- policyId
- description
- status
- createdAt
- updatedAt

### ClaimStatusHistory

Stores every status transition for a claim.

Initial fields:

- id
- claimId
- previousStatus
- newStatus
- changedBy
- reason
- changedAt

### AuditLog

Stores critical business operations.

Initial fields:

- id
- actor
- action
- resourceType
- resourceId
- details
- createdAt

## MVP Endpoints

### Customers

- POST /api/customers
- GET /api/customers/{id}

### Policies

- POST /api/policies
- GET /api/policies/{id}

### Claims

- POST /api/claims
- GET /api/claims/{id}
- GET /api/claims?status=REGISTERED
- PATCH /api/claims/{id}/status
- GET /api/claims/{id}/history

## Business Rules

- A claim must be linked to an existing policy.
- A policy must belong to an existing customer.
- A new claim always starts as REGISTERED.
- A claim can only move to IN_REVIEW from REGISTERED.
- A claim can only be approved or rejected from IN_REVIEW.
- A CANCELLED claim cannot be modified.
- An APPROVED or REJECTED claim cannot be modified.
- Every status change must create a history entry.
- Every critical operation must create an audit entry.
- Customer document numbers must be unique per document type.
- Policy numbers must be unique.
- Claim numbers must be unique.

## Initial Architecture

The MVP will use a modular monolith structure:

- customer
- policy
- claim
- audit
- shared

Each business module should keep its own application services, domain model, persistence adapters, and API contracts where practical.

## Suggested Technology Stack

- Java 21
- Spring Boot 3
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- OpenAPI / Swagger
- JUnit 5
- Testcontainers
- Docker Compose
- GitHub Actions

## Quality Goals

- Clear API contracts.
- Validation at request boundaries.
- Business rules covered by tests.
- Database schema versioned with Flyway.
- Local environment reproducible with Docker Compose.
- Build validated in GitHub Actions.
- README with setup instructions, architecture notes, and API documentation.

## Out of Scope for MVP

- Real payments.
- Real document upload.
- External insurance integrations.
- Real email/SMS notifications.
- Full frontend.
- Separate microservices.
- Advanced OAuth2 authentication.
- Cloud deployment.

## Future Enhancements

- JWT-based authentication and authorization.
- Role-based access control.
- Document upload simulation.
- Notification simulation with async events.
- Kafka or RabbitMQ for claim lifecycle events.
- Observability with metrics, tracing, and structured logs.
- Deployment to Azure.
- Frontend dashboard.

