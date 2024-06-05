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

### 2. Set Up PostgreSQL
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
Ensure to replace yourpassword with your actual PostgreSQL password

### 3. Start the Application

### 4. Run the Category Setup Script
To populate the database with necessary categories, run the cath.sql script provided in the repository. Execute the script using:
```bash
psql -U postgres -d webshop -a -f path/to/cath.sql
```
Replace path/to/cath.sql with the actual path to the cath.sql script.

### 5. Register a New Company
After the application starts, open a web browser and navigate to register page

###6. Add Products
You can add products to your webshop in two ways:

Upload a CSV File: Navigate to the product upload section. Use the provided CSV file or a similar one formatted according to the guidelines provided in the application documentation.

Manual Entry: Go to add products manually through the form provided.

## Support
If you encounter any issues or require assistance, please contact me via Teams or  email at damjan.mlinaric@iu-study.org 



This README is designed to be comprehensive and should guide users through setting up your project smoothly. Adjust paths and URLs as per your actual project setup and repository details.
