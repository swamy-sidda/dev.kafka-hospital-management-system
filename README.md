# Hospital Management System - Microservices with Kafka

A hospital management system built using **Spring Boot Microservices**, **Spring Cloud Eureka**, **OpenFeign**, **Apache Kafka**, **MySQL**, and **REST APIs**.

The project manages patients, doctors, appointments, medicines, billing, payments, and notifications using independent microservices.

---

## Project Architecture

The application consists of the following microservices:

| Service | Port | Purpose |
|---|---:|---|
| Eureka Server | 8761 | Service discovery |
| Patient Service | 8081 | Patient management |
| Doctor Service | 8082 | Doctor management |
| Appointment Service | 8083 | Appointment management |
| Billing Service | 8084 | Billing management |
| Payment Service | 8085 | Payment processing |
| Medicine Service | 8086 | Medicine and prescription management |
| Notification Service | 8087 | Patient notifications |

---

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Spring Cloud Eureka
- Spring Cloud OpenFeign
- Apache Kafka
- MySQL
- REST APIs
- Maven
- Lombok
- Bean Validation
- Git & GitHub
- Postman

---

## Communication Between Services

The project uses two types of communication.

### Synchronous Communication

**OpenFeign** is used when one service needs an immediate response from another service.

Example:

<div align="center">

```text
Payment Service
       |
       | OpenFeign
       v
Billing Service
```
</div> 

Payment Service validates the billing information before processing a payment.

Asynchronous Communication

Apache Kafka is used for event-based communication.

The important Kafka events are:

Appointment Created
        |
        v
appointment-created
        |
        v
Billing Service
Medicine Prescribed
        |
        v
medicine-prescribed
        |
        v
Billing Service
Payment Completed
        |
        v
payment-completed
        |
        v
Notification Service
Payment Failed
        |
        v
payment-failed
        |
        v
Notification Service
Kafka Topics

The project uses the following Kafka topics:

Topic	Producer	Consumer
appointment-created	Appointment Service	Billing Service, Notification Service
medicine-prescribed	Medicine Service	Billing Service, Notification Service
payment-completed	Payment Service	Notification Service
payment-failed	Payment Service	Notification Service
Main Hospital Flow

The major business flow is:

Patient
   |
   v
Appointment
   |
   v
Billing
   |
   +---- Medicine Prescription
   |          |
   |          v
   |     Medicine Charge
   |
   v
Payment
   |
   +---- Payment Completed
   |          |
   |          v
   |     Notification
   |
   +---- Payment Failed
              |
              v
         Notification
Payment Flow

The system supports an advance payment during an appointment.

For example:

Consultation Fee = ₹500
Medicine Charges = ₹50

Total Bill = ₹550

The patient can make an advance payment of:

₹200

The remaining amount can be paid after treatment:

₹550 - ₹200 = ₹350

After the complete amount is paid, the billing status becomes:

PAID
Billing

Billing contains:

Consultation Fee
Medicine Fee
Test Fee
Other Charges
Total Amount
Billing Status
Billing Date

Example:

Consultation Fee : ₹500
Medicine Fee     : ₹50
Test Fee         : ₹0
Other Charges    : ₹0
----------------------
Total Amount     : ₹550
Medicine Management

Medicine Service manages:

Medicine details
Price
Stock quantity
Availability
Medicine prescription

When a medicine is prescribed, a medicine-prescribed Kafka event is published.

Billing Service consumes the event and updates the medicine charges.

Notification Management

Notification Service consumes important Kafka events and stores notifications in its database.

Notifications are generated for:

Appointment Created
Medicine Prescribed
Payment Completed
Payment Failed

Notifications are persisted in the Notification database.

Service Discovery

The project uses Spring Cloud Netflix Eureka for service discovery.

Eureka Server runs on:

http://localhost:8761

All hospital microservices register themselves with Eureka.

Databases

Each microservice uses its own MySQL database.

Examples:

hospital_patient_db
hospital_doctor_db
hospital_appointment_db
hospital_billing_db
hospital_payment_db
hospital_medicine_db
hospital_notification_db

This keeps the services independently manageable.

Running the Project
1. Start MySQL

Make sure MySQL is running.

2. Start Kafka

Kafka is configured to run on:

localhost:9092

Kafka uses KRaft mode.

The Kafka installation used for this project is:

C:\kafka\kafka
3. Start Eureka Server

Start:

eureka-server

Open:

http://localhost:8761
4. Start the Microservices

Start the services in IntelliJ IDEA or STS:

patient-service
doctor-service
appointment-service
billing-service
payment-service
medicine-service
notification-service
API Base URLs
Patient Service
http://localhost:8081/api/patients
Doctor Service
http://localhost:8082/api/doctors
Appointment Service
http://localhost:8083/api/appointments
Billing Service
http://localhost:8084/api/billings
Payment Service
http://localhost:8085/api/payments
Medicine Service
http://localhost:8086/api/medicines
Notification Service
http://localhost:8087/api/notifications
Example Kafka Event Flow
Appointment
POST Appointment
       |
       v
Appointment Service
       |
       v
appointment-created
       |
       +------------------+
       |                  |
       v                  v
Billing Service    Notification Service
Medicine
POST Prescription
       |
       v
Medicine Service
       |
       v
medicine-prescribed
       |
       +------------------+
       |                  |
       v                  v
Billing Service    Notification Service
Payment
POST Payment
       |
       v
Payment Service
       |
       +----------------------+
       |                      |
       v                      v
payment-completed       payment-failed
       |                      |
       v                      v
Notification Service   Notification Service
Project Structure
workspace-spring
│
├── appointment-service
├── billing-service
├── doctor-service
├── eureka-server
├── medicine-service
├── notification-service
├── patient-service
├── payment-service
│
├── .gitignore
└── README.md
Testing

The APIs can be tested using Postman.

The complete hospital flow can be tested in this order:

1. Create Patient
2. Create/Select Doctor
3. Create Appointment
4. Verify Billing
5. Prescribe Medicine
6. Verify Updated Billing
7. Complete Appointment
8. Make Advance Payment
9. Make Remaining Payment
10. Verify Billing Status
11. Verify Notifications
GitHub Repository

GitHub:

https://github.com/swamy-sidda/dev.kafka-hospital-management-system


### Now create the file

In IntelliJ:

**Right-click `workspace-spring` → New → File →**

```text
README.md

Paste the content above and save it.

Don't commit/push it yet. Once you have created the file, tell me “created”, and I'll give you the next Git command.

I need complete file for this 

Absolutely. Here is the complete README.md file ready to copy into your project.

# Hospital Management System - Microservices with Apache Kafka

A hospital management system developed using **Spring Boot Microservices**, **Spring Cloud Netflix Eureka**, **Spring Cloud OpenFeign**, **Apache Kafka**, **MySQL**, and **REST APIs**.

The system manages patients, doctors, appointments, medicines, billing, payments, and notifications through independently running microservices.

---

## 1. Project Overview

This project follows a **microservices architecture**, where each major hospital functionality is implemented as an independent service.

The services communicate using:

- **REST APIs** for external API access
- **OpenFeign** for synchronous communication between microservices
- **Apache Kafka** for asynchronous event-driven communication
- **Eureka Server** for service discovery
- **MySQL** for service-specific databases

The main objective of the project is to demonstrate how multiple Spring Boot microservices can work together to implement a real-world hospital management workflow.

---

# 2. Architecture

The project contains the following services:

| Service | Port | Responsibility |
|---|---:|---|
| Eureka Server | 8761 | Service discovery |
| Patient Service | 8081 | Patient management |
| Doctor Service | 8082 | Doctor management |
| Appointment Service | 8083 | Appointment management |
| Billing Service | 8084 | Billing management |
| Payment Service | 8085 | Payment management |
| Medicine Service | 8086 | Medicine and prescription management |
| Notification Service | 8087 | Notification management |

---

# 3. Technology Stack

## Backend

- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- Spring Validation
- Spring Cloud Netflix Eureka
- Spring Cloud OpenFeign
- Spring Kafka
- Lombok

## Database

- MySQL

## Messaging

- Apache Kafka
- Kafka KRaft mode

## Tools

- IntelliJ IDEA
- Maven
- Postman
- Git
- GitHub

---

# 4. Microservices

## 4.1 Eureka Server

**Port:** `8761`

The Eureka Server acts as the service registry.

All hospital microservices register themselves with Eureka.

Eureka Dashboard:

```text
http://localhost:8761

Registered services include:

PATIENT-SERVICE
DOCTOR-SERVICE
APPOINTMENT-SERVICE
BILLING-SERVICE
PAYMENT-SERVICE
MEDICINE-SERVICE
NOTIFICATION-SERVICE
5. Patient Service

Port: 8081

Patient Service manages patient information.

Main responsibilities
Create patient
Get all patients
Get patient by ID
Update patient
Delete patient
Base URL
http://localhost:8081/api/patients
Example Patient
{
    "firstName": "Swamy",
    "lastName": "Siddarapu",
    "dateOfBirth": "2003-05-15",
    "gender": "MALE",
    "phone": "9876543210",
    "email": "swamy@gmail.com",
    "address": "Bengaluru",
    "bloodGroup": "O_POSITIVE"
}
6. Doctor Service

Port: 8082

Doctor Service manages doctors and their availability.

Main responsibilities
Create doctor
Get doctors
Get doctor by ID
Update doctor
Delete doctor
Manage doctor availability
Base URL
http://localhost:8082/api/doctors
Doctor information includes
First name
Last name
Gender
Phone
Email
Qualification
Specialization
Experience
Availability status
7. Appointment Service

Port: 8083

Appointment Service manages patient appointments with doctors.

Main responsibilities
Create appointment
Get appointment
Update appointment
Delete appointment
Change appointment status
Base URL
http://localhost:8083/api/appointments
Appointment contains
Patient ID
Doctor ID
Appointment date
Appointment time
Reason
Status

Example:

{
    "patientId": 1,
    "doctorId": 2,
    "appointmentDate": "2026-10-10",
    "appointmentTime": "10:00:00",
    "reason": "General health consultation",
    "status": "SCHEDULED"
}
8. Billing Service

Port: 8084

Billing Service manages the financial details associated with an appointment.

A billing record contains:

Consultation fee
Medicine fee
Test fee
Other charges
Total amount
Billing status
Billing date
Base URL
http://localhost:8084/api/billings
Billing calculation

The total amount is calculated as:

Total Amount =
Consultation Fee
+ Medicine Fee
+ Test Fee
+ Other Charges

Example:

Consultation Fee : ₹500
Medicine Fee     : ₹50
Test Fee         : ₹0
Other Charges    : ₹0
--------------------------------
Total Amount     : ₹550
9. Payment Service

Port: 8085

Payment Service handles payments against billing records.

Base URL
http://localhost:8085/api/payments
Main responsibilities
Create payment
Get all payments
Get payment by ID
Get payments by bill ID
Get payments by patient ID
Update payment
Delete payment
Payment information
Payment ID
Bill ID
Patient ID
Amount
Payment method
Payment status
Payment date
10. Payment Business Flow

The project supports an advance payment model.

For example:

Consultation Fee = ₹500
Medicine Fee     = ₹50

Total Bill = ₹550

The patient can initially pay:

Advance Payment = ₹200

Remaining amount:

₹550 - ₹200 = ₹350

The remaining amount can then be paid after treatment.

After the complete amount has been paid:

Billing Status = PAID
11. Medicine Service

Port: 8086

Medicine Service manages medicines and prescriptions.

Base URL
http://localhost:8086/api/medicines
Main responsibilities
Create medicine
Get medicines
Get medicine by ID
Update medicine
Delete medicine
Prescribe medicine
Manage stock
Check medicine availability

Medicine information includes:

Medicine name
Description
Price
Stock quantity
Availability
12. Medicine Prescription

A medicine can be prescribed to a patient through an appointment billing record.

Example request:

POST http://localhost:8086/api/medicines/prescribe
{
    "medicineId": 1,
    "quantity": 2,
    "billId": 1
}

If the medicine price is:

₹25

and quantity is:

2

the medicine charge becomes:

₹25 × 2 = ₹50

The medicine stock is also reduced.

13. Notification Service

Port: 8087

Notification Service receives important events from Kafka and stores notifications in its database.

Base URL
http://localhost:8087/api/notifications

Notifications are generated for:

Appointment Created
Medicine Prescribed
Payment Completed
Payment Failed
14. Synchronous Communication

The project uses Spring Cloud OpenFeign for synchronous communication.

OpenFeign allows one microservice to call another microservice without manually creating HTTP client code.

For example:

Payment Service
       |
       | OpenFeign
       v
Billing Service

Payment Service can validate billing information before processing a payment.

Another example:

Payment Service
       |
       | OpenFeign
       v
Appointment Service

The Payment Service can verify the appointment status before applying payment rules.

15. Apache Kafka

Apache Kafka is used for asynchronous communication between services.

Kafka allows one service to publish an event without directly waiting for another service to process it.

The project uses Kafka for important hospital events.

16. Kafka Topics

The project uses the following Kafka topics:

Kafka Topic	Producer	Consumer
appointment-created	Appointment Service	Billing Service, Notification Service
medicine-prescribed	Medicine Service	Billing Service, Notification Service
payment-completed	Payment Service	Notification Service
payment-failed	Payment Service	Notification Service
17. Appointment Created Event

When an appointment is successfully created:

Appointment Service
        |
        v
appointment-created
        |
        +--------------------+
        |                    |
        v                    v
Billing Service     Notification Service

The Appointment Service publishes an AppointmentCreatedEvent.

The event contains:

appointmentId
patientId
doctorId

Billing Service consumes the event and creates the corresponding billing record.

Notification Service consumes the same event and creates an appointment notification.

18. Medicine Prescribed Event

When medicine is prescribed:

Medicine Service
        |
        v
medicine-prescribed
        |
        +--------------------+
        |                    |
        v                    v
Billing Service     Notification Service

The event contains:

medicineId
billId
quantity
medicineCharge

Billing Service consumes the event and updates the medicine fee.

Notification Service stores a medicine prescription notification.

19. Payment Completed Event

When a payment is successfully completed:

Payment Service
        |
        v
payment-completed
        |
        v
Notification Service

The payment event contains information such as:

paymentId
billId
patientId
amount
paymentMethod
paymentDate

Notification Service consumes the event and stores a payment-success notification.

20. Payment Failed Event

If a payment fails:

Payment Service
        |
        v
payment-failed
        |
        v
Notification Service

Notification Service stores a payment-failed notification.

21. Complete Hospital Workflow

The main workflow of the project is:

Patient
   |
   v
Appointment
   |
   v
Billing
   |
   +----------------------+
   |                      |
   v                      v
Medicine              Payment
   |                      |
   v                      |
Billing                    |
                            |
                            v
                       Notification

A more detailed event flow:

Create Appointment
        |
        v
Appointment Service
        |
        v
Kafka: appointment-created
        |
        +--------------------------+
        |                          |
        v                          v
Billing Service           Notification Service
        |
        v
Billing Created
        |
        |
Prescribe Medicine
        |
        v
Medicine Service
        |
        v
Kafka: medicine-prescribed
        |
        +--------------------------+
        |                          |
        v                          v
Billing Service           Notification Service
        |
        v
Billing Updated
        |
        |
Make Payment
        |
        v
Payment Service
        |
        +--------------------------+
        |                          |
        v                          v
payment-completed            payment-failed
        |                          |
        +------------+-------------+
                     |
                     v
             Notification Service
22. Database Architecture

Each microservice has its own database.

The project uses separate databases instead of one shared database.

Example:

hospital_patient_db
hospital_doctor_db
hospital_appointment_db
hospital_billing_db
hospital_payment_db
hospital_medicine_db
hospital_notification_db

This allows each microservice to manage its own data independently.

23. Database Responsibilities
Database	Service
hospital_patient_db	Patient Service
hospital_doctor_db	Doctor Service
hospital_appointment_db	Appointment Service
hospital_billing_db	Billing Service
hospital_payment_db	Payment Service
hospital_medicine_db	Medicine Service
hospital_notification_db	Notification Service
24. Project Structure
workspace-spring
│
├── appointment-service
│
├── billing-service
│
├── doctor-service
│
├── eureka-server
│
├── medicine-service
│
├── notification-service
│
├── patient-service
│
├── payment-service
│
├── .gitignore
│
└── README.md
25. Service Ports
Eureka Server          8761
Patient Service        8081
Doctor Service         8082
Appointment Service    8083
Billing Service        8084
Payment Service        8085
Medicine Service       8086
Notification Service   8087
26. API Base URLs
Eureka Server
http://localhost:8761
Patient Service
http://localhost:8081/api/patients
Doctor Service
http://localhost:8082/api/doctors
Appointment Service
http://localhost:8083/api/appointments
Billing Service
http://localhost:8084/api/billings
Payment Service
http://localhost:8085/api/payments
Medicine Service
http://localhost:8086/api/medicines
Notification Service
http://localhost:8087/api/notifications
27. Kafka Configuration

Kafka runs on:

localhost:9092

The project uses Kafka in KRaft mode.

Kafka installation:

C:\kafka\kafka

Kafka logs:

C:\tmp\kraft-combined-logs

Kafka topics used by the project:

appointment-created
medicine-prescribed
payment-completed
payment-failed
28. Running Kafka

Kafka can be started using the configured Windows batch file:

C:\Users\home\Desktop\start-kafka.bat

Kafka should be running before starting services that produce or consume Kafka events.

29. Running the Project
Step 1 - Start MySQL

Make sure MySQL is running.

The required databases should be available.

Step 2 - Start Kafka

Start Kafka using:

C:\Users\home\Desktop\start-kafka.bat

Kafka should be available at:

localhost:9092
Step 3 - Start Eureka Server

Start:

eureka-server

Open:

http://localhost:8761

Verify that Eureka Server is running.

Step 4 - Start Patient Service

Start:

patient-service

Port:

8081
Step 5 - Start Doctor Service

Start:

doctor-service

Port:

8082
Step 6 - Start Appointment Service

Start:

appointment-service

Port:

8083
Step 7 - Start Billing Service

Start:

billing-service

Port:

8084
Step 8 - Start Payment Service

Start:

payment-service

Port:

8085
Step 9 - Start Medicine Service

Start:

medicine-service

Port:

8086
Step 10 - Start Notification Service

Start:

notification-service

Port:

8087
30. Testing With Postman

The complete business flow can be tested using Postman.

Recommended testing order:

1. Create Patient
2. Create/Verify Doctor
3. Create Appointment
4. Verify Billing
5. Prescribe Medicine
6. Verify Medicine Stock
7. Verify Updated Billing
8. Complete Appointment
9. Make Advance Payment
10. Make Remaining Payment
11. Verify Final Billing
12. Verify Notifications
31. Example Complete Flow

Example:

Step 1 - Appointment
Patient ID = 1
Doctor ID = 2

Create the appointment.

Step 2 - Billing

The Appointment Service publishes:

appointment-created

Billing Service consumes the event and creates a bill.

Example:

Consultation Fee = ₹500
Step 3 - Medicine

Prescribe:

Paracetamol
Quantity = 2
Price = ₹25

Medicine charge:

₹25 × 2 = ₹50

The Medicine Service publishes:

medicine-prescribed

Billing becomes:

Consultation Fee = ₹500
Medicine Fee     = ₹50

Total = ₹550
Step 4 - Appointment Completion

The appointment status becomes:

COMPLETED
Step 5 - Advance Payment

The patient pays:

₹200

Billing status:

PARTIALLY_PAID
Step 6 - Remaining Payment

Remaining:

₹550 - ₹200 = ₹350

The patient pays:

₹350

Final billing status:

PAID
Step 7 - Notifications

Notification Service receives Kafka events and stores notifications such as:

Appointment booked successfully
Medicine prescribed successfully
Payment of ₹200.00 completed successfully
Payment of ₹350.00 completed successfully
32. Error Handling

The services use exception handling for invalid operations.

Examples include:

Resource not found
Invalid payment
Invalid billing information
Insufficient medicine stock
Medicine unavailable
Invalid request data

Validation is implemented using Jakarta Bean Validation annotations such as:

@NotNull
@NotBlank
@Positive
@PositiveOrZero
33. Medicine Stock Management

The Medicine Service validates stock before prescription.

For example:

Available Stock = 100
Requested Quantity = 2

After successful prescription:

Remaining Stock = 98

If requested quantity is greater than available stock, the prescription is rejected.

34. Payment Validation

Payment Service validates:

Bill existence
Patient and bill relationship
Appointment status
Payment amount
Payment status
Remaining bill amount

The system prevents payment amounts from exceeding the remaining bill amount.

35. Billing Status

Billing can move through different states based on payments.

Example:

GENERATED
    |
    v
PARTIALLY_PAID
    |
    v
PAID
36. Event-Driven Architecture

The project demonstrates an event-driven architecture using Kafka.

Instead of directly calling another service for every operation, important business events are published.

For example:

Payment Service
      |
      | PaymentCompletedEvent
      v
Kafka
      |
      v
Notification Service

This allows the Notification Service to process payment notifications independently.

37. Advantages of the Architecture

The architecture provides:

Independent microservices
Service discovery
Separate databases
Synchronous communication using OpenFeign
Asynchronous communication using Kafka
Event-driven processing
Independent service deployment
Better separation of responsibilities
Centralized notification processing
Independent billing and payment management
38. GitHub

Repository:

https://github.com/swamy-sidda/dev.kafka-hospital-management-system
39. Author

Siddarapu Kumarswamy

GitHub:

https://github.com/swamy-sidda
40. Project Summary

This project demonstrates a real-world hospital management system using Spring Boot microservices.

The system separates hospital functionality into independent services and uses Eureka for service discovery, OpenFeign for synchronous communication, and Apache Kafka for asynchronous event communication.

The main business flow covers:

Patient
   ↓
Doctor
   ↓
Appointment
   ↓
Billing
   ↓
Medicine
   ↓
Payment
   ↓
Notification

The project also demonstrates separate databases, REST APIs, validation, exception handling, Kafka producers and consumers, payment processing, medicine stock management, and event-driven notifications.