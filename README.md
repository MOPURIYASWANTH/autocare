# AutoCare – Vehicle Service Management System

AutoCare is a full-stack **Vehicle Service Management System** developed to manage vehicle servicing, service bookings, mechanics, invoices, and payments.

The application provides a simple interface for customers to register, add their vehicles, select services, book appointments, track service status, view invoices, and make payments.

## 🚗 Project Overview

AutoCare connects customers with vehicle service operations through a web-based application.

### Main Workflow

```text
Customer Registration/Login
          ↓
      Add Vehicle
          ↓
    View Services
          ↓
     Book Service
          ↓
   Assign Mechanic
          ↓
   Service Vehicle
          ↓
   Complete Service
          ↓
      Generate Invoice
          ↓
        Payment
          ↓
     Service History
```

## ✨ Features

### Customer Management

* Customer registration
* Customer login
* View customer details
* Update customer information
* Delete customer account

### Vehicle Management

* Add vehicle
* View customer's vehicles
* Update vehicle details
* Delete vehicle
* Associate vehicles with customers

### Service Management

* View available vehicle services
* Add service
* Update service
* Delete service
* Display service price and description

### Booking Management

* Book a vehicle service
* Select vehicle
* Select service
* Select mechanic
* Select booking date and time
* Track booking status
* Filter bookings by status

### Mechanic Management

* Add mechanic
* View mechanics
* Update mechanic details
* Delete mechanic
* Assign mechanics to bookings

### Invoice Management

* Generate service invoice
* Calculate service charges
* Add parts charges
* Calculate tax
* Calculate total invoice amount
* View invoice details
* Print invoice

### Payment Management

* View invoice amount
* Select payment method
* Make payment
* Track payment status
* Prevent duplicate payments
* Validate payment amount

## 🛠️ Technologies Used

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* REST APIs
* Maven

### Database

* MySQL 8

### Frontend

* HTML5
* CSS3
* JavaScript
* Fetch API

### Development Tools

* Eclipse IDE
* Visual Studio Code
* MySQL
* Postman
* Git
* GitHub

## 🏗️ Project Architecture

```text
Frontend
   │
   │ HTTP / REST API
   ↓
Spring Boot Backend
   │
   ├── Controller
   ├── Service
   ├── Repository
   ├── DTO
   ├── Entity
   └── Exception Handling
   │
   ↓
Hibernate / JPA
   │
   ↓
MySQL Database
```

## 📁 Project Structure

```text
autocare
│
├── src
│   └── main
│       ├── java
│       │   └── com.autocare.autocare
│       │       │
│       │       ├── config
│       │       ├── controller
│       │       ├── dto
│       │       ├── entity
│       │       ├── exception
│       │       ├── repository
│       │       ├── service
│       │       └── AutocareApplication.java
│       │
│       └── resources
│           └── application.properties
│
└── frontend
    ├── index.html
    ├── login.html
    ├── register.html
    ├── dashboard.html
    ├── vehicles.html
    ├── services.html
    ├── booking.html
    ├── bookings.html
    ├── invoice.html
    ├── payment.html
    ├── style.css
    └── app.js
```

## 🗄️ Database Design

The application uses the following main entities:

```text
Customer
   │
   ├── Vehicle
   │
   └── Booking
          │
          ├── AutoService
          ├── Mechanic
          └── Invoice
                  │
                  └── Payment
```

### Entity Relationships

| Relationship          | Type        |
| --------------------- | ----------- |
| Customer → Vehicle    | One-to-Many |
| Customer → Booking    | One-to-Many |
| Vehicle → Booking     | One-to-Many |
| AutoService → Booking | One-to-Many |
| Mechanic → Booking    | One-to-Many |
| Booking → Invoice     | One-to-One  |
| Invoice → Payment     | One-to-One  |

## 🔌 REST API Modules

### Customer APIs

```text
POST   /api/customers/register
POST   /api/customers/login
GET    /api/customers
GET    /api/customers/{customerId}
PUT    /api/customers/{customerId}
DELETE /api/customers/{customerId}
```

### Vehicle APIs

```text
POST   /api/vehicles
GET    /api/vehicles
GET    /api/vehicles/{vehicleId}
GET    /api/vehicles/customer/{customerId}
PUT    /api/vehicles/{vehicleId}
DELETE /api/vehicles/{vehicleId}
```

### Service APIs

```text
POST   /api/services
GET    /api/services
GET    /api/services/{serviceId}
PUT    /api/services/{serviceId}
DELETE /api/services/{serviceId}
```

### Mechanic APIs

```text
POST   /api/mechanics
GET    /api/mechanics
GET    /api/mechanics/{mechanicId}
PUT    /api/mechanics/{mechanicId}
DELETE /api/mechanics/{mechanicId}
```

### Booking APIs

```text
POST /api/bookings

PUT /api/bookings/{bookingId}/status

GET /api/bookings
GET /api/bookings/{bookingId}
GET /api/bookings/customer/{customerId}
GET /api/bookings/mechanic/{mechanicId}
GET /api/bookings/vehicle/{vehicleId}
```

### Invoice APIs

```text
POST /api/invoices
GET  /api/invoices
GET  /api/invoices/{invoiceId}
GET  /api/invoices/booking/{bookingId}
```

### Payment APIs

```text
POST /api/payments
GET  /api/payments
GET  /api/payments/{paymentId}
GET  /api/payments/invoice/{invoiceId}
```

## 📊 Booking Status

The booking system supports the following statuses:

```text
PENDING
CONFIRMED
IN_PROGRESS
COMPLETED
CANCELLED
```

## 💳 Payment Methods

The frontend currently supports:

```text
UPI
CARD
CASH
```

## 🧾 Invoice Calculation

The invoice calculates the final amount using:

```text
Total Amount = Service Charge + Parts Charge + Tax
```

The backend also validates invoice and payment amounts before saving them.

## 🔐 Validation and Exception Handling

The backend contains exception handling for common application errors, including:

* Customer not found
* Vehicle not found
* Service not found
* Mechanic not found
* Booking not found
* Invoice not found
* Payment not found
* Duplicate customer email
* Duplicate invoice
* Duplicate payment
* Invalid booking status
* Invalid invoice amount
* Invalid payment amount
* Vehicle ownership validation

A global exception handler provides appropriate error responses from the backend.

## ▶️ How to Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/MOPURIYASWANTH/autocare.git
```

### 2. Create MySQL Database

Open MySQL and create the database:

```sql
CREATE DATABASE autocare;
```

### 3. Configure Database

Update the database configuration in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/autocare
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Run the Spring Boot Application

Run:

```text
AutocareApplication.java
```

The backend will start on:

```text
http://localhost:8080
```

### 5. Run the Frontend

Open the `frontend` folder using Visual Studio Code.

Use **Live Server** to run the frontend.

Example:

```text
http://127.0.0.1:5500
```

Make sure the frontend API configuration points to:

```javascript
const API = "http://localhost:8080/api";
```

## 🖥️ Application Screenshots

Screenshots of the application can be added here.

### Login

*Add login page screenshot here.*

### Dashboard

*Add dashboard screenshot here.*

### My Vehicles

*Add vehicles page screenshot here.*

### Available Services

*Add services page screenshot here.*

### Book Service

*Add booking page screenshot here.*

### My Bookings

*Add bookings page screenshot here.*

### Invoice

*Add invoice screenshot here.*

### Payment

*Add payment screenshot here.*

## 🎯 Project Objective

The main objective of AutoCare is to build a practical web application that demonstrates how a vehicle service workflow can be managed using a Java-based backend and a web frontend.

The project demonstrates:

* REST API development
* CRUD operations
* Database relationships
* JPA and Hibernate
* Spring Boot layered architecture
* Frontend and backend integration
* Exception handling
* Data validation
* Invoice and payment workflow

## 📚 What I Learned

Through this project, I gained practical experience with:

* Building REST APIs using Spring Boot
* Working with Spring Data JPA
* Mapping entities using Hibernate
* Designing relational database relationships
* Connecting Java applications with MySQL
* Handling HTTP requests from JavaScript
* Connecting frontend applications with REST APIs
* Implementing CRUD operations
* Handling exceptions and validation
* Managing a complete service-booking workflow

## 👨‍💻 Author

**Mopuri Yaswanth**

B.Tech – Computer Science and Engineering

GitHub:
https://github.com/MOPURIYASWANTH

---

## 📌 Future Improvements

Possible future improvements include:

* Authentication using Spring Security and JWT
* Admin dashboard
* Online payment gateway integration
* Email notifications
* Service center management
* Mechanic availability tracking
* Deployment with cloud services

## ⭐ Project Status

**Completed**

The project is developed as a full-stack Java application using Spring Boot, Hibernate/JPA, MySQL, HTML, CSS and JavaScript.
