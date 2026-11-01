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

---
