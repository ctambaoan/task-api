# Task API

A RESTful API for managing tasks built with Spring Boot and Java 21. This application provides endpoints to create, retrieve, update, and delete tasks with status tracking.

> **Learning Exercise**: This project is a learning exercise focused on **Test-Driven Development (TDD)** principles. It demonstrates how to build features by writing tests first, then implementing the code to satisfy those tests.

## Features

- ✅ Create new tasks with name and description
- ✅ Retrieve tasks by ID or list all tasks
- ✅ Filter tasks by status (TODO, DONE)
- ✅ Mark tasks as complete
- ✅ Update task descriptions
- ✅ Delete tasks
- ✅ Input validation
- ✅ Exception handling
- ✅ H2 in-memory database for development

## Technology Stack

- **Framework**: Spring Boot 3.5.x
- **Language**: Java 21
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Data Access**: Spring Data JDBC
- **Validation**: Jakarta Bean Validation
- **Additional**: Lombok for boilerplate reduction

## Project Structure

```
src/
├── main/
│   ├── java/ctambaoan/taskapi/
│   │   ├── TaskApiApplication.java          # Spring Boot application entry point
│   │   ├── controller/
│   │   │   └── TaskController.java          # REST API endpoints
│   │   ├── domain/
│   │   │   ├── Task.java                    # Task entity with business logic
│   │   │   └── TaskStatus.java              # Task status enum (TODO, DONE)
│   │   ├── dto/
│   │   │   ├── CreateTaskRequest.java       # Request DTO for creating tasks
│   │   │   ├── TaskResponse.java            # Response DTO for tasks
│   │   │   └── UpdateDescriptionRequest.java # Request DTO for updating descriptions
│   │   ├── service/
│   │   │   ├── TaskService.java             # Service interface
│   │   │   └── TaskServiceImpl.java          # Service implementation
│   │   ├── repository/
│   │   │   └── TaskRepository.java          # Data access interface
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java  # Global exception handling
│   │       └── TaskNotFoundException.java    # Custom exception
│   └── resources/
│       ├── application.properties           # Application configuration
│       └── schema.sql                       # Database schema
└── test/
    └── java/ctambaoan/taskapi/
        ├── controller/
        │   └── TaskControllerTest.java
        ├── domain/
        │   └── TaskTest.java
        ├── repository/
        │   └── TaskRepositoryTest.java
        └── service/
            └── TaskServiceTest.java
```

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd task-api
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

### 4. Access H2 Console (Optional)

The H2 in-memory database console is available at:
```
http://localhost:8080/h2-console
```

Use the connection URL: `jdbc:h2:mem:testdb`

## API Endpoints

### Create a Task
**POST** `/api/tasks`

Request body:
```json
{
  "name": "Buy groceries",
  "description": "Milk, eggs, bread"
}
```

Response (201 Created):
```json
{
  "id": 1,
  "name": "Buy groceries",
  "description": "Milk, eggs, bread",
  "status": "TODO",
  "created": "2025-12-13T10:30:00"
}
```

### Get a Task by ID
**GET** `/api/tasks/{id}`

Response (200 OK):
```json
{
  "id": 1,
  "name": "Buy groceries",
  "description": "Milk, eggs, bread",
  "status": "TODO",
  "created": "2025-12-13T10:30:00"
}
```

### List All Tasks
**GET** `/api/tasks`

Response (200 OK):
```json
[
  {
    "id": 1,
    "name": "Buy groceries",
    "description": "Milk, eggs, bread",
    "status": "TODO",
    "created": "2025-12-13T10:30:00"
  }
]
```

### Filter Tasks by Status
**GET** `/api/tasks?status=TODO`

Query Parameters:
- `status` (optional): `TODO` or `DONE`

Response (200 OK):
```json
[
  {
    "id": 1,
    "name": "Buy groceries",
    "description": "Milk, eggs, bread",
    "status": "TODO",
    "created": "2025-12-13T10:30:00"
  }
]
```

### Update Task Description
**PUT** `/api/tasks/{id}`

Request body:
```json
{
  "description": "Updated description"
}
```

Response (204 No Content)

### Mark Task as Complete
**PUT** `/api/tasks/{id}/complete`

Response (204 No Content)

### Delete a Task
**DELETE** `/api/tasks/{id}`

Response (204 No Content)

## Running Tests

Run all tests:
```bash
mvn test
```

Run specific test class:
```bash
mvn test -Dtest=TaskControllerTest
```

## Validation Rules

- **Task Name**: 
  - Required (cannot be blank)
  - Maximum 50 characters
  
- **Description**: 
  - Cannot be blank when updating

## Exception Handling

The API includes global exception handling that provides meaningful error responses:

- `TaskNotFoundException` (404): When a requested task is not found
- `IllegalArgumentException` (400): When validation fails
- Other exceptions are handled with appropriate HTTP status codes

## Configuration

Application configuration can be modified in `src/main/resources/application.properties`:

```properties
spring.application.name=task-api
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb
```

## Development

### Building without running tests
```bash
mvn clean install -DskipTests
```

### Viewing generated classes
Compiled classes are in the `target/classes` directory.
