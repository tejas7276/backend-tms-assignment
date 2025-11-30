# Transport Management System (TMS) – Backend

Backend system built using Spring Boot and PostgreSQL to manage logistics workflow including loads, transporters, offers, and bookings. Includes rules for capacity validation, status transitions, and safe booking handling with optimistic locking.

## Tech Stack
- Java 17
- Spring Boot 3.2
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven

## How to Run

### 1. Clone project
git clone <your-repo-link>
cd tms-backend

### 2. Create PostgreSQL database
CREATE DATABASE tmsdb;

### 3. Configure database
Update src/main/resources/application.properties:
--------------------------------------------------
spring.datasource.url=jdbc:postgresql://localhost:5432/tmsdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
--------------------------------------------------

### 4. Run the application
mvn spring-boot:run

Application starts at:
http://localhost:8080


## API Endpoints

### Load APIs
POST    /load                       - create a load
GET     /load                       - list loads (with filters)
GET     /load/{loadId}              - load details with offers
PATCH   /load/{loadId}/cancel       - cancel a load
GET     /load/{loadId}/best-offers  - best offer suggestions

### Transporter APIs
POST    /transporter                - register transporter
GET     /transporter/{id}           - get transporter details
PUT     /transporter/{id}/trucks    - update available trucks

### Offer APIs
POST    /offer                      - create offer
GET     /offer                      - filter/search offers
GET     /offer/{id}                 - offer detail
PATCH   /offer/{id}/reject          - reject offer

### Booking APIs
POST    /booking                    - confirm booking
GET     /booking/{id}               - get booking details
PATCH   /booking/{id}/cancel        - cancel booking


## Sample Payloads

### Create Load
{
  "shipperId": "SHIP123",
  "loadingCity": "Mumbai",
  "unloadingCity": "Delhi",
  "loadingDate": "2025-12-05T10:00:00Z",
  "productType": "Steel",
  "weight": 100,
  "weightUnit": "TON",
  "truckType": "Open",
  "noOfTrucks": 4
}

### Create Offer
{
  "loadId": "07bbd7e2-bf06-4e93-b3f4-f861be36c18b",
  "transporterId": "e0d0183f-2a2a-4e17-8be3-c69afe49648f",
  "proposedRate": 35000,
  "trucksOffered": 2
}

### Confirm Booking
{
  "offerId": "1aa5f92c-fad5-462e-9d05-1fea8d9fee8d",
  "allocatedTrucks": 2,
  "finalRate": 34000
}


## Status Workflow
POSTED → OPEN_FOR_BIDS → BOOKED
                  ↘ CANCELLED

## Basic Schema Overview
Load (1) ---- (M) Offer ---- (1) Transporter
    \                          /
     \-------- (M) Booking ----

Constraints:
- Only one accepted offer per load
- Optimistic locking using @Version on Load
- Truck capacity validation and restore on cancellation


## Submission
Send email to: careers@cargopro.ai
Subject: Tejas_Backend_TMS_Assignment

Attach:
- Resume
- GitHub repo link
- Postman collection
