Say less — here’s **short, clean, copy-paste README** (no extra text):

---

# **Transport Management System (TMS) – Backend**

Backend system built using Spring Boot & PostgreSQL to manage loads, transporters, offers, and bookings with validation rules and status workflow.

## **Tech Stack**

* Java 17
* Spring Boot 3.2
* Spring Data JPA / Hibernate
* PostgreSQL
* Maven

## **Run Setup**

```bash
git clone <your-repo-url>
cd tms-backend
```

Create DB:

```sql
CREATE DATABASE tmsdb;
```

Configure `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/tmsdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Run:

```bash
mvn spring-boot:run
```

Base URL:

```
http://localhost:8080
```

## **API Endpoints**

### **Load**

```
POST   /load
GET    /load
GET    /load/{id}
PATCH  /load/{id}/cancel
GET    /load/{id}/best-offers
```

### **Transporter**

```
POST   /transporter
GET    /transporter/{id}
PUT    /transporter/{id}/trucks
```

### **Offer**

```
POST   /offer
GET    /offer
GET    /offer/{id}
PATCH  /offer/{id}/reject
```

### **Booking**

```
POST   /booking
GET    /booking/{id}
PATCH  /booking/{id}/cancel
```

## **Sample Payloads**

### Create Load

```json
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
```

### Create Offer

```json
{
  "loadId": "07bbd7e2-bf06-4e93-b3f4-f861be36c18b",
  "transporterId": "e0d0183f-2a2a-4e17-8be3-c69afe49648f",
  "proposedRate": 35000,
  "trucksOffered": 2
}
```

### Confirm Booking

```json
{
  "offerId": "1aa5f92c-fad5-462e-9d05-1fea8d9fee8d",
  "allocatedTrucks": 2,
  "finalRate": 34000
}
```

## **Status Flow**

```
POSTED → OPEN_FOR_BIDS → BOOKED
               ↘ CANCELLED
```

## **Schema**

Diagram file is included:

```
/schema.pdf
```

Relations:

```
Load (1) -- (M) Offer -- (1) Transporter
Load (1) -- (M) Booking -- (1) Transporter
Offer (1) -- (1) Booking
```

## **Submission**

```
Email: careers@cargopro.ai
Subject: Tejas_Backend_TMS_Assignment
Attach: Resume, GitHub Link, Postman Collection, schema.pdf
```

---

Done.
Let me know if you want **submission email message** also. ✉
