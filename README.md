# Umuganda Management System Backend

**Academic Year**: 2026-2027 (1)  
**Course Code & Name**: INSY 8322 WEB TECHNOLOGY AND INTERNET  
**Institution**: Adventist University of Central Africa (AUCA)  
**Lecturer**: Patrick DUSHIMIMANA  
**Student Name**: MUGENI Cynthia  
**Student ID**: 26600  
**Project**: Umuganda Management System  
**Repository**: `umuganda-management-system-backend`

---

## 1. Project Overview

The **Umuganda Management System** is a Spring Boot RESTful API developed to plan, coordinate, and report community work activities (Umuganda) in Rwanda. 

The domain model contains 6 core entities:
- **Village**: The local village where community work takes place (identified uniquely by `villageCode`).
- **Cell**: The administrative unit with numbered cells where coordinators are assigned.
- **Coordinator**: Community coordinator heading a cell, skilled in various activity types.
- **ActivityType**: Classification of community work (e.g., Road Cleaning, Tree Planting).
- **Activity**: Scheduled community work event with status lifecycle (`PLANNED` -> `ONGOING` -> `ACCOMPLISHED` or `POSTPONED`).
- **ActivityReport**: Detailed report (attendance, work done, remarks) filed after an activity is marked `ACCOMPLISHED`.

---

## 2. Prerequisites & Tech Stack

- **Java**: 21
- **Spring Boot**: 4.1.1 / Spring Data JPA / Spring Web
- **Database**: PostgreSQL
- **Build Tool**: Maven wrapper (`./mvnw` or `mvnw.cmd`)

---

## 3. Database Setup

1. Open your PostgreSQL console (`psql` or pgAdmin) and create the database:
   ```sql
   CREATE DATABASE umuganda_management;
   ```
2. Verify or update connection credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/umuganda_management
   spring.datasource.username=postgres
   spring.datasource.password=YOUR_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
   *(Or create a `.env` file with `DB_PASSWORD=YOUR_PASSWORD` in the root folder).*

---

## 4. Steps to Run the API

1. Clone the repository:
   ```bash
   git clone https://github.com/cynthia26600/umuganda-management-system-backend.git
   cd umuganda-management-system-backend
   ```
2. Build the project:
   ```bash
   ./mvnw clean compile
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The server runs by default on `http://localhost:8080`.

---

## 5. Endpoints Reference & Specification

All endpoints strictly follow the examination specification and return the exact HTTP status codes and response messages:

### Part A: CRUD
- `POST /api/{path}/save` (201 Created)
- `GET /api/{path}/all` (200 OK)
- `GET /api/{path}/{id}` (200 OK / 404 Not Found)
- `PUT /api/{path}/update/{id}` (200 OK / 404 Not Found)
- `DELETE /api/{path}/delete/{id}` (200 OK / 404 Not Found)
  *(where `{path}` is `activity-types`, `cells`, `coordinators`, `villages`, or `activities`)*

### Part B: Derived Queries
- **B1**: `GET /api/villages/by-sector-name?sectorName=remera` - Derived query (IgnoreCase, OrderBy)
- **B2**: `GET /api/activities/by-status?status=PLANNED` - Derived query (enum parameter, OrderBy)
- **B3**: `GET /api/activities/between?start=2026-10-01&end=2026-10-31` - Derived query (Between)

### Part C: JPQL with Joins
- **C1**: `GET /api/coordinators/by-activity-type?name=tree` - JPQL (`JOIN` on collection, `LOWER`)
- **C2**: `GET /api/cells/free` - JPQL (`NOT EXISTS` subquery)
- **C3**: `GET /api/activity-types/unused` - JPQL (`IS EMPTY`)
- **C4**: `GET /api/villages/of-coordinator/{coordinatorId}` - JPQL (`DISTINCT`, path navigation)

### Part D: Business Rules
- **D1**: `POST /api/activities/save` - Safe saving (blocks past dates 400, prevents date clash 409, sets PLANNED 201)
- **D2**: `PATCH /api/activities/{id}/status?status=ONGOING` - Strict status transition rules (400 if invalid transition, 404 if missing, 200 on success)
- **D3**: `PUT /api/coordinators/{coordinatorId}/activity-types/{activityTypeId}` - Add activity type to coordinator (404 / 409 / 200)
- **D4**: `PATCH /api/coordinators/{coordinatorId}/cell?cellNumber=4` - Move coordinator to cell (404 / 409 / 200)

### Part E: ActivityReport Entity
- **E1**: `POST /api/activity-reports/save?activityId=...` - Write report for ACCOMPLISHED activity (201 / 400 / 404)
- **E2**: `GET /api/activity-reports/village/{villageCode}` - Report history by village code (JPQL path navigation)
- **E3**: `DELETE /api/activity-reports/delete/{id}` - Delete report (200 / 404) + Protect activity deletion if reports exist (409)

### Part F: Reports and Bulk Updates
- **F1**: `GET /api/activities/stats/by-status` - Group by count per status as `List<Object[]>`
- **F2**: `PATCH /api/activities/cancel-day?coordinatorId=...&date=2026-10-20` - Bulk cancel day with `@Modifying` JPQL

### Bonus Requirements (+10)
- **+5 Dashboard**: `GET /api/dashboard/summary` - LinkedHashMap summary metrics
- **+5 Pageable**: `GET /api/activities/page?page=0&size=5&sort=activityDate,desc` - Paginated response

---

## 6. Postman Collection

The exported Postman collection is located at the root of the project:  
`umuganda-management-system-midterm.postman_collection.json`  
It contains requests named by requirement IDs (A2, B1, C4, D2, ...) ready for the live demonstration.
