# Todo App Backend

This is the backend service for the Todo App, built using **Spring Boot**. It provides authentication, user management, and task management APIs.

## 🛠️ Technologies Used
- **Spring Boot** (REST API)
- **Spring Security** (JWT Authentication)
- **JPA** (Database Handling)
- **MySQL** (Database)
- **Maven** (Build & Dependency Management)

## 🚀 Features
- **User Authentication** (JWT-based)
- **User Registration & Login**
- **Task Management** (Create, Update, Delete, Fetch)
- **Jwt Token Refresh Mechanism**

## 📌 API Endpoints

### 🔹 **Authentication Routes**
| Method | Endpoint           | Description |
|--------|--------------------|-------------|
| POST   | `/auth/register`   | Register a new user |
| POST   | `/auth/login`      | Login user and get access token |
| POST   | `/auth/refresh-token` | Refresh the access token |

### 🔹 **Task Routes**
| Method | Endpoint           | Description |
|--------|--------------------|-------------|
| POST   | `/tasks`           | Create a new task |
| GET    | `/tasks`           | Fetch all tasks (Optional: filter by status) |
| PUT    | `/tasks/{id}`      | Update a task by ID |
| DELETE | `/tasks/{id}`      | Delete a task by ID |

## 🏗️ Installation & Setup
create a new project in spring initilizer and add dependencies mentioned in pom.xml file

