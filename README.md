# Umuganda Management System Backend

**Institution:** Adventist University of Central Africa (AUCA)  
**Faculty:** Faculty of Information Technology  
**Academic Year:** 2026–2027 (1)  
**Course:** INSY 8322 – Web Technology and Internet  
**Lecturer:** Patrick DUSHIMIMANA  
**Student:** MUGENI Cynthia  
**Student ID:** 26600  
**Project:** Umuganda Management System  
**Repository:** [umuganda-management-system-backend](https://github.com/M26600/umuganda-management-system-backend)

---

## 1. Project overview

The Umuganda Management System is a Spring Boot REST API for planning, coordinating and reporting community work activities in Rwanda. It manages villages, cells, coordinators, activity types, activities and activity reports.

An activity belongs to a village and is assigned to a coordinator. Activities follow the status flow `PLANNED` → `ONGOING` → `ACCOMPLISHED` or `POSTPONED`. Activity reports can be submitted only after an activity has reached `ACCOMPLISHED`.

## 2. Technology stack

- Java 21
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven Wrapper
- Postman

## 3. Main domain entities

| Entity | Purpose |
|---|---|
| `Village` | Village where community work takes place; `villageCode` is unique. |
| `Cell` | Cell identified by `cellNumber` and `name`; it does not contain a coordinator relationship. |
| `Coordinator` | Person responsible for coordinating activities, assigned to a cell and associated with activity types. |
| `ActivityType` | Classification of community work, such as Road Cleaning or Tree Planting. |
| `Activity` | Dated community work activity linked to a village and coordinator, with an `ActivityStatus`. |
| `ActivityReport` | Records work done, remarks, attendance and submission date for an accomplished activity. |

## 4. Prerequisites

Install or have access to:

1. Java Development Kit (JDK) 21.
2. PostgreSQL.
3. Git.
4. Postman (for testing the API).

The project includes Maven Wrapper scripts, so a separate Maven installation is not normally required.

## 5. Database configuration

1. Start PostgreSQL.
2. Create the database:

   ```sql
   CREATE DATABASE umuganda_management;
   ```

3. In the project root, create a file named `.env` containing your local PostgreSQL password:

   ```properties
   DB_PASSWORD=your_local_postgres_password
   ```

   Do not commit `.env` or share a real database password. The database username and database name are configured in `src/main/resources/application.properties`.

The application is configured to use:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/umuganda_management
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Change the username or connection URL if your local PostgreSQL setup is different.

## 6. Clone and run the application

Clone the repository:

```bash
git clone https://github.com/M26600/umuganda-management-system-backend.git
cd umuganda-management-system-backend
```

**Windows PowerShell:**

```powershell
.\mvnw.cmd clean compile
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

The API runs at:

```text
http://localhost:8080
```

If the Maven Wrapper cannot download Maven, check your internet connection and confirm that `.mvn/wrapper/maven-wrapper.properties` exists in the repository.

## 7. Seed data

Create the required examination seed data through the API's own save endpoints. The seed dataset includes:

- Four activity types: Road Cleaning, Tree Planting, Terrace Building and School Construction. No coordinator holds School Construction.
- Four cells numbered 1–4. Cell 4 is initially unoccupied.
- Three coordinators, each assigned to a cell, with the required activity-type assignments.
- Five villages, including two in the Remera sector.
- Activities dated in October and November 2026, using all four activity statuses and satisfying the examination's date and coordinator conditions.

The API uses `spring.jpa.hibernate.ddl-auto=update`; this creates or updates the schema but does not automatically insert the examination seed records. Use the seed records and save endpoints established for the project.

## 8. API requirements and endpoints

### A2 – CRUD

The five CRUD resource paths are `activity-types`, `cells`, `coordinators`, `villages` and `activities`.

| Operation | Endpoint pattern |
|---|---|
| Save | `POST /api/{path}/save` |
| Get all | `GET /api/{path}/all` |
| Get by ID | `GET /api/{path}/{id}` |
| Update | `PUT /api/{path}/update/{id}` |
| Delete | `DELETE /api/{path}/delete/{id}` |

### B – Derived queries

- **B1:** `GET /api/villages/by-sector-name?sectorName=remera`
- **B2:** `GET /api/activities/by-status?status=PLANNED`
- **B3:** `GET /api/activities/between?start=2026-10-01&end=2026-10-31`

### C – JPQL queries

- **C1:** `GET /api/coordinators/by-activity-type?name=tree`
- **C2:** `GET /api/cells/free`
- **C3:** `GET /api/activity-types/unused`
- **C4:** `GET /api/villages/of-coordinator/{coordinatorId}`

### D – Business rules

- **D1:** `POST /api/activities/save` – rejects past dates and coordinator/date conflicts; new activities start as `PLANNED`.
- **D2:** `PATCH /api/activities/{id}/status?status=ONGOING` – enforces the allowed activity status transitions.
- **D3:** `PUT /api/coordinators/{coordinatorId}/activity-types/{activityTypeId}` – adds an activity type without removing existing assignments.
- **D4:** `PATCH /api/coordinators/{coordinatorId}/cell?cellNumber=4` – changes a coordinator's cell only when the target cell is free.

### E – Activity reports

- **E1:** `POST /api/activity-reports/save?activityId={activityId}`
- **E2:** `GET /api/activity-reports/village/{villageCode}`
- **E3:** `DELETE /api/activity-reports/delete/{id}`; activity deletion is protected when reports exist.

### F – Reports and bulk updates

- **F1:** `GET /api/activities/stats/by-status`
- **F2:** `PATCH /api/activities/cancel-day?coordinatorId={coordinatorId}&date=2026-10-20`

### Bonus

- **Dashboard:** `GET /api/dashboard/summary`
- **Pagination:** `GET /api/activities/page?page=0&size=5&sort=activityDate,desc`

## 9. Postman collection

The exported collection is included in the repository root:

```text
umuganda-management-system-midterm.postman_collection.json
```

To use it:

1. Start the Spring Boot application.
2. Open Postman and import the JSON collection.
3. Confirm the collection variable `baseUrl` is `http://localhost:8080`.
4. Ensure the examination seed data is present in PostgreSQL.
5. Run the requests in collection order. Run the B and C query requests before the D and E requests that modify data.
6. For the A2 CRUD folders, run Save, Get All, Get by ID, Update and successful Delete in order. The Get All requests capture the IDs of the temporary test records automatically.
7. For the E1–E3 report demonstration, run E1 first, then E2, then the E3 activity-delete conflict request, and finally the E3 report-delete success request.

The A2 requests use temporary records and delete them after demonstrating CRUD. They are designed not to occupy Cell 4 or alter the original seed records.

## 10. Notes

- Keep database credentials in your local `.env` file; do not commit secrets.
- The seed data must be entered through the API save endpoints as required by the examination.
- Run and verify each requirement in Postman, including its required error case, before the live demonstration.
- Commit the work incrementally so the Git history shows progress through the examination requirements.
