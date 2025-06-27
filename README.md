# Java Projects Collection 📦

> **Note:** This repository contains multiple Java and Spring Boot projects.  
> The **Book REST API** project is located in the `endpoint` folder.  
> Each project has its own directory and focuses on different backend concepts.

---

## 📁 Projects Overview

### 📘 `endpoint` – Book REST API
A fully-featured RESTful API built with Spring Boot for managing books.

**Highlights:**
- Java 21, Spring Boot, Spring Security, H2 DB
- Full CRUD for books
- **Role-based access control** using HTTP Basic Auth
- **Method-level security** with `@PreAuthorize`
- Clean **DTO layer** to expose only necessary data

🧪 Example Users:
- `admin` / `adminpass` → role: `ADMIN`
- `user` / `password` → role: `USER`

🔒 Access Control:
- `GET` → accessible to all authenticated users
- `POST`, `PUT`, `DELETE` → restricted to admins

---

### ✅ `taskManager`
A simple desktop application built with JavaFX that allows users to manage tasks by category, priority, and due date. Tasks are saved persistently in a JSON file.

## Features

- Add new tasks with:
  - Name
  - Category (Work, Shopping, Personal, Fitness)
  - Priority (High, Medium, Low)
  - Due date (with date picker)
- Task categories are visually grouped.
- JSON-based task storage.
- Predefined tasks are generated on the first launch.
- Simple and clean UI with JavaFX.



## Project Structure

- `Main.java`: The main entry point for the JavaFX application.
- `Task.java`: Data class for individual tasks (not shown here).
- `Category.java`: Enum for task categories (not shown here).
- `Priority.java`: Enum for task priorities (not shown here).
- `tasks.json`: File where tasks are saved persistently.

---

### 📝 `toDo-Lists`
A simple command-line To-Do List application written in Java. Users can add tasks, mark them as done, delete them, and view all tasks with their completion status.

## 📦 Features

- Add new tasks  
- Mark tasks as completed  
- Delete tasks  
- Display task list with status 

---

## 🧰 Common Tech Stack

- Java 17–21
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Security
- H2 / MySQL
- Maven
- JavaFX
- Jackson (for JSON serialization/deserialization)


changes