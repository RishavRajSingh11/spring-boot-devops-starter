# Spring Boot DevOps Starter

A practical backend/DevOps demo: Spring Boot REST API, PostgreSQL persistence, structured JSON logs, containerized integration tests, and automated CI.

## Stack
- Java 21, Spring Boot 3
- Spring Data JPA + PostgreSQL 16
- Bean Validation and centralized API error responses
- Actuator health endpoint
- Logback JSON logging (Logstash encoder)
- Docker multi-stage build and Docker Compose
- JUnit 5 + Testcontainers (real PostgreSQL for integration tests)
- GitHub Actions Maven CI

## Run the stack
Requires Docker Engine and Docker Compose.

```bash
docker compose up --build
```

API listens on http://localhost:8080. Health check:

```bash
curl http://localhost:8080/actuator/health
```

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{"title":"ship the container"}'
```

List tasks:

```bash
curl http://localhost:8080/api/tasks
```

## Run tests
Requires Java 21, Maven 3.9+, and Docker (Testcontainers starts PostgreSQL).

```bash
mvn -B verify
```

## API
| Method | Path | Behavior |
|---|---|---|
| GET | /api/tasks | List tasks ordered by ID |
| POST | /api/tasks | Create a task (title required, max 100 chars) |
| GET | /actuator/health | Application health |

Tasks persist in PostgreSQL with Compose. Hibernate schema auto-update is enabled for this demo; use versioned migrations such as Flyway or Liquibase before production deployment.

## CI/CD
- CI runs on pushes and pull requests to main, executes Maven verification, and runs PostgreSQL integration tests with Testcontainers.
- Compose starts API and PostgreSQL with a database health check.
- Docker image uses a multi-stage build and runs as a non-root user.

## SonarQube / SonarCloud
A Sonar analysis job can be enabled after configuring a Sonar project and repository secret named SONAR_TOKEN. The default CI does not require this secret.

## Configuration
| Variable | Default |
|---|---|
| SPRING_DATASOURCE_URL | jdbc:postgresql://localhost:5432/taskdb |
| SPRING_DATASOURCE_USERNAME | taskuser |
| SPRING_DATASOURCE_PASSWORD | taskpass |

Compose credentials are demo-only. Use secrets or a local untracked environment file for real deployments.
