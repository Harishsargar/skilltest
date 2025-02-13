# Spring Boot project

## Objective

The purpose of this task is to evaluate your Spring Boot skills, particularly in building RESTful APIs, implementing role-based access control, and integrating token-based authentication. Additionally, this test will assess your ability to handle real-world scenarios like entity relationships, error handling, and database interactions.

## Task Details

You are required to build a User Management System using Spring Boot. Follow the instructions below to complete the task.

### 1. Set Up a Spring Boot Application

- Create a new Spring Boot project with the following dependencies:
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - JWT (JSON Web Token)
  - MySQl Database

### 2. Implement User and Role Management

#### 1. Create the Following Entities:

- **User**:
  - Fields: `id`, `name`, `email`, `password`, `roles` (list of roles).
  
- **Role**:
  - Fields: `id`, `name` (e.g., Admin, Manager, User).

#### 2. Requirements:

- A user can have multiple roles.
- Passwords should be securely hashed (e.g., using BCrypt).

### 3. Create APIs with Role-Based Access Control

#### 1. Admin APIs:
- Create, Read, Update, and Delete users.
- Assign roles to users.

#### 2. Manager APIs:
- View all users and their assigned tasks.
- Assign tasks to users.

#### 3. User APIs:
- View their own profile and assigned tasks only.

### 4. Secure APIs with JWT Authentication

- Implement JWT-based authentication for all APIs.
- Ensure the following role-based access control:
  - Admin APIs should only be accessible to users with the Admin role.
  - Manager APIs should only be accessible to users with the Manager role.
  - User APIs should be accessible to all authenticated users.

### 5. Implement Database Integration

- Use a relational database to persist data.
- Pre-populate the database with sample data for testing:
  - Example Roles: Admin, Manager, User.
  - Example Users: A few users assigned to different roles.

### 6. Error Handling and Validation

- Implement error handling for scenarios like:
  - Duplicate users.
  - Invalid or expired JWT tokens.
  - Unauthorized access to restricted APIs.
  
- Validate input data, such as:
  - Email format.
  - Password strength.
