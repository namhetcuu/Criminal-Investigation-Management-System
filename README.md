# 🕵️ Criminal Investigation Management System 

## 📌 Project Objective
To develop a comprehensive system for managing criminal case investigations, covering all stages from crime reporting to sentencing. The system supports citizen reporting, police investigations, prosecution processes, and correctional management, with role-based access control for various user types (citizens, investigators, prosecutors, etc.).

---

## 🗂️ Database Design Overview

The database is structured by **phases** that reflect the real-world process of criminal investigation and prosecution. Additionally, it includes cross-cutting tables for access control and user management.

---

## ✅ Phase 1: Detection and Reporting of Crime

| Table     | Description |
|-----------|-------------|
| `users`   | Manages user accounts (citizens, victims, witnesses, police officers). |
| `reports` | Stores initial reports filed by citizens or police, including description, crime type, and location. |
| `cases`   | Creates official case records based on reports; includes classification and severity. |
| `victims` | Records victim information if any are reported. |
| `witnesses` | Stores witness information and initial testimonies. |
| `suspects` | Stores suspected individuals identified during initial assessment. |

---

## ✅ Phase 2: Preliminary Investigation

| Table                 | Description |
|-----------------------|-------------|
| `evidence`            | Records evidence collected at the crime scene (description, location, collected by, etc.). |
| `investigation_plans` | Stores preliminary investigation plans and assigned officers. |
| `interviews`          | Records initial interviews with victims, witnesses, and suspects. |
| `statements`          | Stores testimonies and assesses their credibility. |


---

## ✅ Cross-cutting Tables: Access Control

| Table            | Description |
|------------------|-------------|
| `roles`          | Defines user roles (admin, investigator, prosecutor, citizen, etc.). |
| `permissions`    | Defines system-level permissions. |
| `user_roles`     | Assigns roles to users. |
| `role_permissions` | Assigns permissions to roles. |

---

## 🔗 Key Table Relationships (Examples)

- `reports.user_id` → `users.id`
- `cases.report_id` → `reports.id`
- `victims.case_id` → `cases.id`
- `evidence.case_id` → `cases.id`
- `arrests.suspect_id` → `suspects.id`
- `prosecutions.case_id` → `cases.id`
- `sentences.case_id` → `cases.id`
- `inmates.sentence_id` → `sentences.id`

---

## 🛠️ Technologies Used

- **Backend:** Java Spring Boot  
- **Frontend:** React.js / Next.js  
- **Database:** MySQL  
- **ORM:** Hibernate / JPA  
- **Authentication & Authorization:** JWT + Role-based Access Control (RBAC)

---

## 📁 Folder Structure (High-level)

