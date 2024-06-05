# Amazon-like Webshop Application

This project is an Amazon-like webshop application developed using Java with Spring Boot, Thymeleaf for templating, some JavaScript for client-side dynamics, and PostgreSQL as the database backend.

## Prerequisites

Before you begin, ensure you have the following installed:
- Java JDK 11 or later
- Maven (for dependency management and running the application)
- PostgreSQL
- Git (for cloning the repository)
- npm (to install packages needed)
- node (to run javaScript)

## Setup and Installation

Follow these steps to get your application up and running:

### 1. Clone the Repository

Clone the repository from Discord. You will find the link to the Git repository in the designated Discord channel. Use the following command to clone it:

```bash
git clone <repository-url>
```

### Set Up PostgreSQL
Install PostgreSQL if it's not already installed. After installation, you need to create a database for the application:
  1. Open your terminal or PostgreSQL command line tool.
  2. Log in to PostgreSQL
  ```bash
  psql -U postgres
  ```
  3. Create a new database
  ```bash
CREATE DATABASE webshop;
  ```
  4. Exit PostgreSQL command line
  ```bash
  \q
  ```
Adjust the application.properties file in your Java project with your PostgreSQL configurations(if needed) for Example

  ```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/webshop
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

  ```
