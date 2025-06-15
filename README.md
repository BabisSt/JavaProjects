# Book REST API

> **Note:** This repository contains multiple Java projects.  
> The **Book REST API** project is located in the `endpoint` folder.  
> Each project has its own folder and README file.

This is a Java Spring Boot REST API project for managing books with full CRUD operations (Create, Read, Update, Delete).

## Highlights

- Uses an in-memory H2 database for easy setup and testing.
- Leverages Spring Data JPA’s `JpaRepository` for streamlined data access and repository management.
- Simple and clean REST endpoints to manage book resources.
- **Spring Security integration** with HTTP Basic Authentication and role-based access control.
- Method-level security using `@PreAuthorize` annotations.
- **DTOs (Data Transfer Objects)** used to decouple internal Book entities from API responses.

## Features

- Create new books _(admin only)_
- Retrieve all books or individual books by ID _(any authenticated user)_
- Update existing books _(admin only)_
- Delete books by ID _(admin only)_

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- H2 Database (in-memory)
- Maven

## Security Configuration

The app uses **Spring Security** with the following setup:

- **Authentication**: In-memory users
  - `user` / `password` → Role: `USER`
  - `admin` / `adminpass` → Role: `ADMIN`
- **Authorization**:
  - All `/books/**` endpoints require authentication.
  - Only users with `ADMIN` role can perform POST, PUT, or DELETE operations.
  - `@PreAuthorize` is enabled for method-level access control.
- **HTTP Basic Authentication**: Used for simplicity.
- **CSRF Protection**: Disabled for testing purposes (should be enabled in production).

## DTO Layer

The project introduces **DTOs (Data Transfer Objects)** to:

- Prevent direct exposure of internal JPA entity structures.
- Provide clean and consistent response models for the API.
- Enable better separation of concerns between persistence and API layers.

The `BookDTO` class includes only essential fields such as ISDN, title, and author to simplify data returned to the client and avoid exposing sensitive or irrelevant fields.
