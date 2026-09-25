# Spring Boot DevOps Starter

A compact production-style Spring Boot REST service demonstrating backend and DevOps fundamentals.

## Demonstrates

- Java 21 + Spring Boot
- REST API and request validation
- Centralized validation error handling
- Spring Actuator health endpoint
- MockMvc controller tests
- Multi-stage Docker build
- Docker Compose
- GitHub Actions CI with Maven caching

## Run locally

Prerequisites: Java 21 and Maven 3.9+.

```bash
mvn spring-boot:run
```

Health check:

```bash
curl http://localhost:8080/actuator/health
```

Create a task:

```bash
curl -X POST http://localhost:8080/api/tasks -H 'Content-Type: application/json' -d '{"title":"ship the container"}'
```

List tasks:

```bash
curl http://localhost:8080/api/tasks
```

## Docker

```bash
docker compose up --build
```

## Test

```bash
mvn test
```

## API

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/tasks | List tasks |
| POST | /api/tasks | Create a task |
| GET | /actuator/health | Health check |

## Portfolio use

This repo is intentionally small and is meant to be a clean public demonstration of Spring Boot, Docker, testing, and CI/CD fundamentals.
