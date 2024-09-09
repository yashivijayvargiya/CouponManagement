# Coupon Management Project

## Project Overview
This project is a **Coupon Management System** built using **Spring Boot** and **Maven**. The system enables users to create, retrieve, update, and delete coupons, as well as apply applicable coupons to a shopping cart. The service includes logging features and maintains records of coupon-related operations.

## Features
- **Create, retrieve, update, and delete coupons**
- **Fetch and apply applicable coupons to a shopping cart**
- **Logging for key events, such as coupon creation, updates, and deletions**

## Application Properties
The following configurations are specified in the `application.properties` file:

```properties
# Application name (optional)
# spring.application.name=CouponManagement

# Server configuration
server.port=9091

# Data source configuration for MySQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/coupon
spring.datasource.username=root
spring.datasource.password=First#1234

# Hibernate settings for MySQL dialect
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Logging configuration
logging.level.root=INFO
logging.level.com.mockcommerce=DEBUG
logging.file.name=logs/application.log
```

### Key Configuration Details:
1. **Database**: MySQL is used as the database, with the required credentials (username and password) set in the `application.properties`.
2. **Port**: The application runs on port `9091`.
3. **Logging**: Logs are generated in the `logs/application.log` file. The log folder is automatically created within the service directory, storing all generated logs.

## API Endpoints
Below are the main API endpoints exposed by the Coupon Management system:

1. **POST /coupons**
   - **Description**: Create a new coupon.
   - **Request Body**: `CouponRequest` containing coupon details.

2. **GET /coupons**
   - **Description**: Retrieve all coupons.
   
3. **GET /coupons/{id}**
   - **Description**: Retrieve a specific coupon by its ID.

4. **PUT /coupons/{id}**
   - **Description**: Update a specific coupon by its ID.
   - **Request Body**: Updated `Coupon` entity.

5. **DELETE /coupons/{id}**
   - **Description**: Delete a specific coupon by its ID.
   - **Note**: If the coupon is associated with a product, it cannot be deleted. A log entry is generated to capture this event.

6. **POST /applicable-coupons**
   - **Description**: Fetch all applicable coupons for a given cart and calculate the total discount.

7. **POST /apply-coupon/{id}**
   - **Description**: Apply a specific coupon to the cart and return the updated cart with discounted prices for each item.

## Logging
Logging has been configured to provide detailed information about coupon operations. Logs are generated for:
- Coupon creation
- Fetching coupons
- Updating coupons
- Deleting coupons
  - A warning is logged if a coupon cannot be deleted because it is associated with a product.

Logs are stored in the `logs/application.log` file, which is automatically created in the log folder at runtime.

## Controller
The `CouponController` handles API requests related to coupons. The controller logs important information about each operation, including:
- Coupon creation
- Coupon fetching (all or by ID)
- Coupon updates
- Coupon deletions (including warnings if deletion is not allowed due to associations)

## How to Run the Project

### Prerequisites:
- Java 8 or higher
- Maven
- MySQL database

### Steps:
1. Clone the repository.
2. Navigate to the project directory.
3. Set up the MySQL database and update the `application.properties` file with the appropriate credentials.
4. Run the following command to build the project:
   ```bash
   mvn clean install
   ```
5. Start the application by running:
   ```bash
   mvn spring-boot:run
   ```
6. The application will be accessible on `http://localhost:9091`.

### Testing the APIs:
You can use tools like **Postman** or **cURL** to test the various endpoints.
