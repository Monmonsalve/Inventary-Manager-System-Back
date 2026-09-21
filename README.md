# Inventory Management API

[![CI](https://github.com/Monmonsalve/Inventary-Manager-System-Back/actions/workflows/ci.yml/badge.svg)](https://github.com/Monmonsalve/Inventary-Manager-System-Back/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-17-ED8B00.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F.svg)](https://spring.io/projects/spring-boot)

Secure REST API for managing products, inventory by store, purchases and sales. It uses JWT authentication, role-based authorization and transactional stock updates.

## Highlights

- JWT authentication with BCrypt password hashing.
- Role-based access control: user and role administration is restricted to ADMIN.
- Registration always assigns the USER role on the server.
- DTOs prevent password hashes from being exposed through the API.
- Purchases increase stock and sales reduce stock in one database transaction.
- Stock is stored only in Inventory for each product/store pair.
- Consistent validation and JSON error responses.
- OpenAPI/Swagger documentation.
- Unit tests, Docker Compose and GitHub Actions CI.

## Stack

- Java 17
- Spring Boot, Spring MVC and Spring Data JPA
- Spring Security and JWT
- MySQL 8
- Maven
- JUnit 5 and Mockito
- Docker

## Run with Docker

1. Copy the environment template:

~~~bash
cp .env.example .env
~~~

2. Replace the example passwords and JWT secret in .env. ADMIN_EMAIL and ADMIN_PASSWORD create the initial administrator only when that email does not exist.

3. Start the API and MySQL:

~~~bash
docker compose up --build
~~~

The API runs at http://localhost:8080. Swagger UI is available at http://localhost:8080/swagger-ui.html.

## Run locally

Requirements: Java 17 and MySQL 8.

~~~bash
git clone https://github.com/Monmonsalve/Inventary-Manager-System-Back.git
cd Inventary-Manager-System-Back
~~~

Configure environment variables:

~~~bash
export DB_URL='jdbc:mysql://localhost:3306/inventory_manager?createDatabaseIfNotExist=true&serverTimezone=UTC'
export DB_USERNAME='root'
export DB_PASSWORD='your-password'
export JWT_SECRET='replace-with-a-random-secret-of-at-least-32-characters'
~~~

Then run:

~~~bash
./mvnw spring-boot:run
~~~

On Windows:

~~~powershell
./mvnw.cmd spring-boot:run
~~~

## Authentication

Register:

~~~http
POST /auth/register
Content-Type: application/json
~~~

~~~json
{
  "firstName": "Isaac",
  "lastName": "Monsalve",
  "email": "isaac@example.com",
  "password": "strong-password"
}
~~~

Login:

~~~http
POST /auth/login
Content-Type: application/json
~~~

~~~json
{
  "email": "isaac@example.com",
  "password": "strong-password"
}
~~~

Use the returned token on protected requests:

~~~http
Authorization: Bearer <token>
~~~

## Main endpoints

| Method | Endpoint | Access | Purpose |
|---|---|---|---|
| POST | /auth/register | Public | Register a user with role USER |
| POST | /auth/login | Public | Obtain a JWT |
| GET/PUT/DELETE | /users/** | ADMIN | Manage users |
| GET/POST/DELETE | /role/** | ADMIN | Manage roles |
| GET/POST/PUT/DELETE | /products/** | Authenticated | Manage products |
| GET/POST/PUT/DELETE | /inventory/** | Authenticated | Manage stock by store |
| GET/POST/PUT | /purchases/** | Authenticated | Register purchases and increase stock |
| GET/POST/PUT | /sales/** | Authenticated | Register sales and decrease stock |

The full contract is generated at /v3/api-docs and displayed in Swagger UI.

## Example sale

The API ignores client-provided totals and prices. It obtains the product price, validates the store inventory, calculates totals and reduces stock transactionally.

~~~json
{
  "store": { "id": 1 },
  "user": { "id": 1 },
  "saleDetails": [
    {
      "product": { "id": 2 },
      "quantity": 3
    }
  ]
}
~~~

## Tests

~~~bash
./mvnw verify
~~~

The test profile uses an in-memory H2 database, so tests do not require a local MySQL instance.

## Project structure

~~~text
src/
├── main/java/com/api/manager/
│   ├── controllers/
│   ├── dto/
│   ├── exception/
│   ├── models/
│   ├── repositories/
│   └── services/
└── test/
~~~

## Important model rule

Stock belongs to the combination product + store; therefore, Inventory.quantity is the only stock source. A unique database constraint prevents duplicate inventory rows for the same product and store.
