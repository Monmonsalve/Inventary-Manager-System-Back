# Inventary-Manager-System-Back

This repository contains the REST API responsible for managing users, products, inventory, sales and authentication.

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- MySQL
- Maven

## Description

Inventory Manager System is a web application designed to help businesses manage their inventory.

The system allows users to:

- Manage products and categories.
- Control inventory by store.
- Register Sales transactions.
- Manage users and roles.
- Authenticate users securely using JWT.

# DataBase Model

The system includes entities such as:

- User
- Role
- Product
- Category
- Store
- Inventory
- Sale
- Sale Details

In your application.properties file, add the following configuration to automatically update the database schema according to the application models:

```properties
spring.jpa.hibernate.ddl-auto=update