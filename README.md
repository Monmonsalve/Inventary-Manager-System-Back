# Inventory Manager System - Backend

REST API for managing products, categories, inventory, sales, users, roles and authentication.

## 🚀 Technologies

| Technology          | Description                      |
| ------------------- | -------------------------------- |
| ☕ Java 17           | Programming language             |
| 🌱 Spring Boot      | Backend framework                |
| 🗄️ Spring Data JPA | Data persistence                 |
| 🔄 Hibernate        | ORM                              |
| 🔐 Spring Security  | Authentication and authorization |
| 🎫 JWT              | Token-based authentication       |
| 🐬 MySQL            | Relational database              |
| 📦 Maven            | Dependency management            |

## 📋 Description

**Inventory Manager System** is a backend REST API designed to help businesses manage their inventory and sales operations.

The system provides endpoints for managing products, categories, suppliers, inventory, sales, users and roles.

It also implements secure authentication using **Spring Security and JWT**, allowing protected endpoints to be accessed only by authenticated users.

## ✨ Features

* 👤 User management
* 🔐 Authentication using JWT
* 🎫 JWT token generation and validation
* 🛡️ Protected API endpoints
* 👥 Role management
* 📦 Product management
* 🏷️ Category management
* 🏪 Store management
* 📊 Inventory management
* 🛒 Sales management
* 🧾 Sale details management
* 🗄️ MySQL database integration
* 🔄 Automatic database schema updates with Hibernate

## 🔐 Authentication

The application uses **Spring Security and JSON Web Tokens (JWT)** for authentication.

### Authentication flow

```text
Client
   │
   │ POST /auth/login
   │ Email + Password
   ▼
Authentication Service
   │
   │ Verify credentials
   ▼
Spring Security
   │
   │ Generate JWT
   ▼
Client
   │
   │ Authorization: Bearer <token>
   ▼
JWT Authentication Filter
   │
   │ Validate token
   ▼
Protected Endpoint
```

The authentication system includes:

* Secure password verification using `BCryptPasswordEncoder`.
* JWT token generation after successful login.
* JWT token validation on protected requests.
* Authentication through the `Authorization` header.
* Protected endpoints using Spring Security.
* Automatic rejection of requests with invalid or expired tokens.

### Example request

Login:

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

After successful authentication, the API returns a JWT token that can be used to access protected endpoints.

```http
Authorization: Bearer <your-jwt-token>
```

## 🗃️ Database Model

The system currently includes the following main entities:

```text
User
 │
 └── Role

Product
 ├── Category
 └── Supplier

Store
 │
 └── Inventory
       └── Product

Sale
 └── Sale Details
       └── Product
```

### Main entities

* **User** — Application users and their credentials.
* **Role** — User roles and permissions.
* **Product** — Products available in the inventory.
* **Category** — Product categorization.
* **Supplier** — Product suppliers.
* **Store** — Stores managed by the system.
* **Inventory** — Product stock by store.
* **Sale** — Registered sales transactions.
* **Sale Details** — Products and quantities associated with each sale.

## ⚙️ Configuration

Create or update the `application.properties` file with your database configuration.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_manager
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### JWT configuration

The JWT secret and expiration time should also be configured in `application.properties`.

```properties
jwt.secret=your_secret_key
jwt.expiration=86400000
```

> ⚠️ For production environments, avoid storing sensitive credentials directly in `application.properties`. Use environment variables or a secure secrets management solution.

## ▶️ Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/Monmonsalve/Inventary-Manager-System-Back.git
```

### 2. Navigate to the project

```bash
cd Inventary-Manager-System-Back
```

### 3. Configure the database

Create a MySQL database and configure the connection in `application.properties`.

### 4. Run the application

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The API will be available at:

```text
http://localhost:8080
```

## 📁 Project Structure

```text
src/
└── main/
    └── java/
        └── com.api.manager/
            ├── controllers/
            ├── models/
            ├── repositories/
            ├── services/
            ├── security/
            └── ...
```

## 🛡️ Security

Security is implemented using **Spring Security**, **BCrypt** and **JWT**.

Passwords are not stored as plain text. They are encrypted using BCrypt before being persisted in the database.

Protected endpoints require a valid JWT token:

```http
Authorization: Bearer <JWT_TOKEN>
```

Requests containing invalid, expired or missing tokens are rejected by the security layer.

## 🧪 API Testing

The API can be tested using tools such as:

* Postman
* Insomnia
* cURL

Example:

```bash
curl -X GET http://localhost:8080/category \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📌 Project Status

🚧 **In development**

The project is being developed with the goal of implementing a complete backend solution for inventory and sales management.

Future improvements may include:

* Refresh tokens
* Email verification
* Password recovery
* Role-based endpoint permissions
* Request rate limiting
* Auditing and logging
* Caching
* Soft delete
* Docker support
* Automated testing

## 👨‍💻 Author

**Isaac Monsalve Marin**

Backend / Full Stack Developer

* GitHub: [Monmonsalve](https://github.com/Monmonsalve)
* LinkedIn: [Isaac Monsalve](https://www.linkedin.com/in/isaacmonsalve/)
