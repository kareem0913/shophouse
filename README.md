# ShopHouse

[![Docker Image Version](https://img.shields.io/badge/docker-latest-blue.svg)](https://hub.docker.com/repository/docker/kareemzaher/shophouse-app)

## About

**shophouse** — backend for an e‑commerce application with a clean code architecture and RESTful APIs.


**Technologies:** Java, Spring Boot, Spring Data JPA, Hibernate, Liquibase, Spring Security, MySQL

### Project Overview

* Built e-commerce backend with clean code architecture and RESTful APIs.
* Designed database schema with migration management using Liquibase.
* Implemented authentication and authorization using Spring Security.

---

## Features

* User authentication & authorization (Spring Security + JWT or session based).
* Products, categories, images, and uploads handling.
* Database migrations with Liquibase.
* RESTful API with Swagger UI for interactive docs.

---

## Quick Start (using Docker Hub)

This project provides a pre-built image on Docker Hub. To run the app on a new machine:

1. Download the `docker-compose.yml` from the repo:

```bash
wget https://raw.githubusercontent.com/kareem0913/shophouse/main/docker-compose.yml
```

2. Start the services with Docker Compose:

```bash
docker compose up -d
```

3. Open the Swagger UI to explore the API:

```
http://localhost:8080/api/swagger-ui/index.html
```
---

## Running locally (build from source)

If you prefer to build the image locally and run it with Compose (no Docker Hub required):

1. Build the application image from the project root (where the `Dockerfile` exists):

```bash
docker build -t kareemzaher/shophouse-app:latest .
```

2. Start services (build step not required now because image exists locally):

```bash
docker compose up -d
```

---

## Configuration

The `docker-compose.yml` included in the repo binds MySQL to host port `3307` and maps the application to port `8080`. Adjust ports and environment variables in the compose file as needed.

Example important parts from compose:

```yaml
ports:
  - "3307:3306"   # mysql
  - "8080:8080"   # app
volumes:
  - mysql_data:/var/lib/mysql
  - ./uploads:/app/uploads
```
---

## Database migrations

Liquibase is used to manage DB schema changes. Migrations are executed when the application starts (depending on your Spring Boot configuration). Check the `src/main/resources/db/changelog` folder for changeSets.

---

## API Docs

Swagger UI is available at:

```
http://localhost:8080/api/swagger-ui/index.html
```

---

## Common Troubleshooting

* **unauthorized: incorrect username or password** when pulling image: run `docker logout` and `docker login` on the machine.

---

## Contact

Kareem — <kareem.345@outlook.com>
