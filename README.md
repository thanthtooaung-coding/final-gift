# final-gift
final gift for my love

# Spring Boot Monthly Sales Report System

This is a comprehensive web application built with Spring Boot 3 for a small business to manage products, customers, and sales, with a focus on generating insightful monthly and daily sales reports.

## 🧱 Features

- **User Management**: Role-based access control (OWNER, ADMIN, CASHIER).
- **Security**: JWT-based authentication and authorization.
- **Product & Category Management**: Full CRUD operations and stock tracking.
- **Customer Management**: Store customer details for invoicing.
- **Sales & Invoicing**: Create sales, auto-calculate totals, and generate invoice numbers. Stock is automatically updated.
- **Advanced Reporting**:
    - Monthly Sales Summary (Total revenue, items sold, etc.)
    - Daily Sales Breakdown for a given month.
    - Category-wise Sales Performance.
    - Top Customer Reports.
- **API Documentation**: Integrated Swagger UI for easy API exploration.

## ⚙️ Technology Stack

- **Backend**: Spring Boot 3.2.5
- **Language**: Java 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA (Hibernate)
- **Security**: Spring Security, JSON Web Tokens (JWT)
- **Build Tool**: Maven
- **API Docs**: SpringDoc OpenAPI (Swagger UI)
- **Utilities**: Lombok, ModelMapper

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven
- Docker (for PostgreSQL)

### 1️⃣ Run the Spring Boot Application

Navigate to the project's root directory and run the application using the Maven wrapper:

```bash
# On macOS / Linux
./mvnw spring-boot:run

# On Windows
./mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:1500`.

### 2️⃣ Access API Documentation

Once the application is running, you can access the interactive Swagger UI to explore and test the API endpoints:

[http://localhost:8080/swagger-ui.html](https://www.google.com/search?q=http://localhost:8080/swagger-ui.html)

## 🧰 Sample API Endpoints

  - **`POST /api/auth/login`**: Authenticate to get a JWT token. Use `owner` / `password123` with the provided sample data.
  - **`GET /api/products`**: List all available products.
  - **`POST /api/sales`**: Record a new sale (CASHIER role required).
  - **`GET /api/reports/monthly?year=2025`**: Get the monthly sales report for a specific year (ADMIN/OWNER role required).
  - **`GET /api/reports/daily?month=2025-10-01`**: Get daily sales summary for October 2025 (ADMIN/OWNER role required).
