<div align="center">

# 📦 Inventory Manager System — Backend

### Secure REST API for inventory, purchases and sales management

[![CI](https://github.com/Monmonsalve/Inventary-Manager-System-Back/actions/workflows/ci.yml/badge.svg)](https://github.com/Monmonsalve/Inventary-Manager-System-Back/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker&logoColor=white)

Built with Java and Spring Boot to demonstrate secure authentication, role-based authorization, transactional business logic, automated tests and containerized deployment.

[Features](#-features) · [Architecture](#-architecture) · [API](#-main-api-endpoints) · [Quick start](#-quick-start-with-docker) · [Author](#-author)

</div>

---

## 📋 About the project

**Inventory Manager System** is a backend REST API designed for businesses that need to control products, suppliers, stores, purchases, sales and stock.

Unlike a basic CRUD project, purchase and sale operations contain real transactional logic:

- A **purchase** increases the available inventory in the selected store.
- A **sale** checks available stock, calculates the total and reduces inventory.
- If any step fails, the complete operation is rolled back.

This project is especially focused on backend security, data consistency and maintainable API design.

## ✨ Features

| Area | Capabilities |
|---|---|
| 🔐 Authentication | Login and registration with JWT |
| 🛡️ Authorization | Role-based access control with USER and ADMIN |
| 👤 Users | Safe DTO responses without password hashes |
| 📦 Products | Product, category and supplier management |
| 🏪 Stores | Multiple stores with independent inventory |
| 📊 Inventory | Unique stock record for each product and store |
| 🛒 Sales | Stock validation, automatic totals and inventory reduction |
| 🚚 Purchases | Automatic totals and inventory replenishment |
| ✅ Validation | Jakarta Validation and consistent JSON errors |
| 📚 Documentation | OpenAPI specification and Swagger UI |
| 🧪 Quality | JUnit, Mockito, H2 and GitHub Actions CI |
| 🐳 Deployment | Dockerfile and Docker Compose with MySQL |

## 🚀 Technologies

| Technology | Purpose |
|---|---|
| ☕ Java 17 | Programming language |
| 🌱 Spring Boot | Application framework |
| 🌐 Spring MVC | REST API layer |
| 🗄️ Spring Data JPA | Data persistence |
| 🔄 Hibernate | Object-relational mapping |
| 🔐 Spring Security | Authentication and authorization |
| 🎫 JSON Web Token | Stateless authentication |
| ✅ Jakarta Validation | Request validation |
| 🐬 MySQL 8 | Production database |
| 🧪 H2 | In-memory test database |
| 📖 Springdoc OpenAPI | Swagger documentation |
| 📦 Maven | Build and dependency management |
| 🐳 Docker | Reproducible local deployment |
| ⚙️ GitHub Actions | Continuous integration |

## 🏗️ Architecture

~~~mermaid
flowchart TB
    Client[Client or frontend] --> Controller[REST controllers]
    Controller --> DTO[DTO and validation]
    DTO --> Service[Business services]
    Service --> Security[Spring Security and JWT]
    Service --> Repository[JPA repositories]
    Repository --> Database[(MySQL)]
~~~

The project follows a layered architecture:

~~~text
HTTP request
    ↓
Controller
    ↓
DTO and validation
    ↓
Service and business rules
    ↓
Repository
    ↓
MySQL
~~~

## 🔐 Authentication and security

Passwords are hashed with **BCrypt** and are never included in API responses. Authentication is stateless and every protected request must include a valid JWT.

### Authentication flow

~~~mermaid
sequenceDiagram
    participant Client
    participant API
    participant Security
    participant Database

    Client->>API: POST /auth/login
    API->>Database: Find user by email
    API->>Security: Verify BCrypt password
    Security-->>Client: Signed JWT
    Client->>API: Request with Bearer token
    API->>Security: Validate token and role
    Security-->>Client: Protected resource
~~~

### Access rules

| Resource | Public | USER | ADMIN |
|---|:---:|:---:|:---:|
| Register and login | ✅ | ✅ | ✅ |
| Products and categories | ❌ | ✅ | ✅ |
| Inventory, purchases and sales | ❌ | ✅ | ✅ |
| User administration | ❌ | ❌ | ✅ |
| Role administration | ❌ | ❌ | ✅ |
| Swagger UI | ✅ | ✅ | ✅ |

Public registration always assigns the **USER** role in the backend. A client cannot register itself as an administrator.

## 🗃️ Database model

~~~mermaid
erDiagram
    ROLE ||--o{ USER : assigns
    CATEGORY ||--o{ PRODUCT : groups
    SUPPLIER ||--o{ PRODUCT : supplies
    STORE ||--o{ INVENTORY : contains
    PRODUCT ||--o{ INVENTORY : stocked
    USER ||--o{ SALE : registers
    STORE ||--o{ SALE : receives
    SALE ||--|{ SALE_DETAILS : contains
    PRODUCT ||--o{ SALE_DETAILS : sold
    USER ||--o{ PURCHASE : registers
    STORE ||--o{ PURCHASE : receives
    SUPPLIER ||--o{ PURCHASE : provides
    PURCHASE ||--|{ PURCHASE_DETAILS : contains
    PRODUCT ||--o{ PURCHASE_DETAILS : purchased
~~~

Stock belongs to the combination **product + store**. A database constraint prevents duplicate inventory rows for the same combination.

## 🌐 Main API endpoints

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | /auth/register | Public | Register a new USER |
| POST | /auth/login | Public | Authenticate and obtain a JWT |
| GET | /products | Authenticated | List products |
| POST | /products | Authenticated | Create a product |
| PUT | /products/{id} | Authenticated | Update a product |
| DELETE | /products/{id} | Authenticated | Delete a product |
| GET | /inventory | Authenticated | List inventory |
| POST | /purchases | Authenticated | Register a purchase and increase stock |
| POST | /sales | Authenticated | Register a sale and reduce stock |
| GET | /users | ADMIN | List users safely |
| PUT | /users/{id} | ADMIN | Update a user and role |
| GET/POST/DELETE | /role/** | ADMIN | Manage roles |

After starting the application, the complete interactive documentation is available at:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## ⚡ Quick start with Docker

### Requirements

- Docker
- Docker Compose

### 1. Clone the repository

~~~bash
git clone https://github.com/Monmonsalve/Inventary-Manager-System-Back.git
cd Inventary-Manager-System-Back
~~~

### 2. Create the environment file

~~~bash
cp .env.example .env
~~~

Replace the example passwords and JWT secret in the new .env file.

### 3. Start the complete environment

~~~bash
docker compose up --build
~~~

This starts:

- Spring Boot API on port **8080**
- MySQL on port **3306**
- Automatic USER and ADMIN role initialization
- Optional initial administrator configured through environment variables

## 🖥️ Run locally

### Requirements

- Java 17
- MySQL 8

Configure application.yml

Before starting the project, open:

src/main/resources/application.yml

Update the MySQL username, password and JWT secret according to your local environment:

server:
  port: 8080

spring:
  application:
    name: manager

  datasource:
    url: jdbc:mysql://localhost:3306/inventory_manager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
    username: root
    password: "YOUR_MYSQL_PASSWORD"

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    open-in-view: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true

jwt:
  secret: "YOUR_JWT_SECRET_WITH_AT_LEAST_32_CHARACTERS"
  expiration: 3600000

app:
  bootstrap-admin:
    email: ""
    password: ""

springdoc:
  swagger-ui:
    path: /swagger-ui.html

The database can be created automatically when the configured MySQL user has the required permissions. The JWT secret must contain at least 32 characters.

Configure these environment variables:

| Variable | Description | Example |
|---|---|---|
| DB_URL | JDBC database connection | jdbc:mysql://localhost:3306/inventory_manager |
| DB_USERNAME | Database user | root |
| DB_PASSWORD | Database password | your-password |
| JWT_SECRET | Secret with at least 32 characters | replace-with-a-secure-random-value |
| JWT_EXPIRATION | Token duration in milliseconds | 86400000 |
| ADMIN_EMAIL | Optional initial administrator | admin@example.com |
| ADMIN_PASSWORD | Optional administrator password | strong-password |

Run the application:

~~~bash
./mvnw spring-boot:run
~~~

On Windows:

~~~powershell
./mvnw.cmd spring-boot:run
~~~

## 🧑‍💻 Usage examples

### Register

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

### Login

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

Use the returned token:

~~~http
Authorization: Bearer <your-jwt-token>
~~~

### Register a sale

The API obtains the official product price, verifies the selected store inventory and calculates the total automatically.

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

## 🧪 Testing and continuous integration

Run all tests:

~~~bash
./mvnw verify
~~~

The test environment uses H2, so it does not require a local MySQL installation.

Every push and pull request is automatically validated by GitHub Actions:

- Project compilation
- Application context startup
- Authentication service tests
- User registration and role assignment tests
- Transactional sales and inventory tests

## 📁 Project structure

~~~text
.
├── .github/workflows/     # Continuous integration
├── src/
│   ├── main/
│   │   ├── java/com/api/manager/
│   │   │   ├── controllers/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   └── services/
│   │   └── resources/
│   └── test/              # Unit and context tests
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
~~~

## 🗺️ Roadmap

- [x] JWT authentication
- [x] Role-based authorization
- [x] Transactional inventory updates
- [x] Request validation and error handling
- [x] Swagger/OpenAPI documentation
- [x] Docker Compose environment
- [x] Automated tests and CI
- [ ] Refresh tokens
- [ ] Email verification
- [ ] Password recovery
- [ ] Audit logs
- [ ] Pagination and filtering
- [ ] Rate limiting
- [ ] Cloud deployment and public demo

## 👨‍💻 Author

**Isaac Monsalve Marin**<br>
Backend / Full Stack Developer — Santiago, Chile

- GitHub: [Monmonsalve](https://github.com/Monmonsalve)
- LinkedIn: [Isaac Monsalve](https://www.linkedin.com/in/isaacmonsalve/)

---

<div align="center">

If this project helped you or you found it interesting, consider giving it a ⭐.

</div>
