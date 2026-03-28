# Hotel Booking System

A microservices-based application designed to simulate a hotel booking process. Currently, the services communicate synchronously via **REST**, with plans to introduce Kafka in future iterations.

---

## Modules

The system is currently divided into three main microservices:

* **Generator Module:** Acts as a load/traffic generator (simulates incoming user requests for booking reservations).
* **Registrator Module:** The core orchestrator. It receives booking requests, checks room availability, communicates with the Payment service, finalizes the booking registration once payment is confirmed, and handles sending responses back to the users.
* **Payment Module:** A mock service responsible for simulating payment processing and verification.

---

## Tech Stack

* **Language:** Java 21
* **Database:** PostgreSQL
* **Containerization:** Docker & Docker Compose
* **Communication:** REST (HTTP)

---

## How to Run

### Start the Application

```bash
docker-compose up -d --build
```

### Stop the Application

```bash
docker-compose down
```

* GENERATOR: http://localhost:8080/generator
* REGISTRATOR: http://localhost:8081/registrator
* PAYMENT: http://localhost:8082/payment