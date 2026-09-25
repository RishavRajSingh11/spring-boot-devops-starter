# Spring Boot DevOps Starter

A portfolio-ready Spring Boot REST API demonstrating backend engineering and practical DevOps: PostgreSQL persistence, containerization, automated tests, CI/CD, and cloud deployment.

## Live Demo

- **Base URL:** https://spring-boot-devops-starter.onrender.com
- **Health:** https://spring-boot-devops-starter.onrender.com/actuator/health
- **Tasks API:** https://spring-boot-devops-starter.onrender.com/api/tasks
- **GitHub Actions:** https://github.com/RishavRajSingh11/spring-boot-devops-starter/actions

The deployed health endpoint has returned `UP`. A task was created through the public API, retrieved successfully, and remained available after a service restart, as verified during the demo.

> This is a demonstration project, not a production-hardened service. The hosted instance may sleep or be subject to hosting-provider free-tier limits.

## Highlights

- REST API with request validation and centralized error responses
- PostgreSQL persistence using Spring Data JPA
- Health and readiness/liveness endpoints through Spring Boot Actuator
- Structured JSON application logs
- Java 21 and Spring Boot 3
- Multi-stage Docker build with a non-root runtime user
- Docker Compose for local API + PostgreSQL development
- JUnit 5 and Testcontainers integration tests using PostgreSQL
- GitHub Actions CI and container smoke test; GHCR image publishing
- Render Blueprint configuration for deployable web service and PostgreSQL database

## Architecture

```text
Client
  |
  v
Spring Boot REST API (Docker)
  |  Spring Data JPA
  v
PostgreSQL

GitHub Actions -> Build + tests + container smoke test + image publishing
Render Blueprint -> Web service + managed PostgreSQL
```

## API

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/tasks` | List tasks ordered by ID |
| `POST` | `/api/tasks` | Create a task (title required; max 100 characters) |
| `GET` | `/actuator/health` | Application health status |

### Create a task

```bash
curl -X POST https://spring-boot-devops-starter.onrender.com/api/tasks \\
  -H 'Content-Type: application/json' \\
  -d '{"title":"My first live deployment"}'
```

Example response:

```json
{
  "id": 1,
  "title": "My first live deployment",
  "completed": false
}
```

### List tasks

```bash
curl https://spring-boot-devops-starter.onrender.com/api/tasks
```

### Check health

```bash
curl https://spring-boot-devops-starter.onrender.com/actuator/health
```

## Run Locally

Requirements: Java 21, Maven 3.9+, Docker Engine, and Docker Compose.

Start the API and PostgreSQL:

```bash
docker compose up --build
```

The API listens at `http://localhost:8080`.

```bash
curl http://localhost:8080/actuator/health
curl -X POST http://localhost:8080/api/tasks \\
  -H 'Content-Type: application/json' \\
  -d '{"title":"ship the container"}'
curl http://localhost:8080/api/tasks
```

Stop the stack:

```bash
docker compose down
```

## Run Tests

Requires Java 21, Maven 3.9+, and Docker (Testcontainers starts PostgreSQL).

```bash
mvn -B verify
```

## CI/CD

GitHub Actions workflows automate build/test verification, container smoke testing, and container image publishing. See [workflow runs](https://github.com/RishavRajSingh11/spring-boot-devops-starter/actions).

The repository also includes a SonarCloud workflow that requires project configuration and a `SONAR_TOKEN` secret before analysis can run.

## Deployment on Render

The repository includes [`render.yaml`](./render.yaml), which defines the Docker web service and PostgreSQL database. To deploy:

1. Sign in to [Render](https://render.com/) and connect the GitHub repository.
2. Create a new Blueprint and select this repository's `main` branch.
3. Review the service and database plans, then apply the Blueprint.
4. Confirm the service becomes Live and check `/actuator/health`.

Hosting and database availability, persistence duration, and pricing depend on the selected Render plan. Check Render's current plan limits before using this demo for important data.

## Configuration

| Variable | Purpose |
|---|---|
| `SPRING_DATASOURCE_URL` | JDBC URL; overrides the host/database fallback |
| `DB_HOST` | Database hostname fallback |
| `DB_NAME` | Database name fallback |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |

Local Compose credentials are for demo use only. Use managed secrets and versioned database migrations (Flyway or Liquibase) before production use.

## Tech Stack

Java 21 · Spring Boot 3 · Spring Data JPA · PostgreSQL 16 · Bean Validation · Actuator · Logback JSON · Docker · Docker Compose · GitHub Actions · Testcontainers · Render
