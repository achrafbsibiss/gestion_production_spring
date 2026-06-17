# Gestion Product Spring

Spring Boot web app for managing products. Server-rendered CRUD (Thymeleaf) with role-based access control via Spring Security.

## Features

- List products with case-insensitive search by name
- Create, edit, delete products (admin only)
- Bean validation on product fields (name, price, quantity)
- Role-based authorization: `USER` is read-only, `ADMIN` has full CRUD
- Form-based login/logout
- H2 in-memory database with web console

## Tech Stack

- Java 21
- Spring Boot 4.1.0 (Web MVC, Data JPA, Security, Validation, Thymeleaf)
- Thymeleaf + thymeleaf-extras-springsecurity6
- H2 (runtime, in-memory) — MySQL connector also bundled
- Lombok
- Maven

## Getting Started

### Prerequisites

- JDK 21+
- Maven (or use the bundled `./mvnw` wrapper)

### Run

```bash
./mvnw spring-boot:run
```

App starts on **http://localhost:8085**.

### Build

```bash
./mvnw clean package
java -jar target/gestion_product_spring-0.0.1-SNAPSHOT.jar
```

## Login

Two in-memory users (password `1234` for both):

| Username | Password | Roles        | Access          |
|----------|----------|--------------|-----------------|
| `user`   | `1234`   | USER         | read-only       |
| `admin`  | `1234`   | USER, ADMIN  | full CRUD       |

## Routes

| Method | Path             | Access      | Description                  |
|--------|------------------|-------------|------------------------------|
| GET    | `/`              | authenticated | redirects to `/products`   |
| GET    | `/products`      | USER, ADMIN | list / search products       |
| GET    | `/newProduct`    | ADMIN       | new product form             |
| POST   | `/saveProduct`   | ADMIN       | save new product             |
| GET    | `/editProduct`   | ADMIN       | edit product form (`?id=`)   |
| POST   | `/updateProduct` | ADMIN       | update product               |
| GET    | `/deleteProduct` | ADMIN       | delete product (`?id=`)      |
| GET    | `/login`         | public      | login page                  |
| GET    | `/notAuthorized` | public      | access-denied page           |

## H2 Console

Enabled at **http://localhost:8085/h2-console**.

- JDBC URL: `jdbc:h2:mem:product-db`
- User: `sa`
- Password: *(empty)*

> In-memory DB: data resets on every restart. Three sample products (Computer, Printer, Smartphone) are seeded at startup via `CommandLineRunner`.

## Project Structure

```
src/main/java/org/example/gestion_product_spring/
├── GestionProductSpringApplication.java   # entry point + data seeder
├── entities/Product.java                  # JPA entity (+ validation)
├── repositories/ProductRepository.java    # Spring Data JPA repo
├── security/SecurityConfig.java           # auth + role rules
└── web/
    ├── ProductController.java             # product CRUD endpoints
    └── SecurityController.java            # login / notAuthorized views
src/main/resources/templates/              # Thymeleaf views
```
