# Leads Import Service

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Configuration](#configuration)
    - [Running the Application](#running-the-application)
- [Usage](#usage)

## Introduction

Welcome to the Leads Import Service! This Spring Boot application is designed to import and manage leads data into a
MySQL database. The application provides an easy-to-use interface for importing and handling leads data.

## Prerequisites

Before you get started with the Leads Import Service, ensure you have the following prerequisites installed on your
system:

- **Java 17**: The application is built using Java, and Java 17 is required to run it.
- **Spring Boot**: The Leads Import Service is a Spring Boot application.
- **MySQL**: You need a MySQL database to store the imported leads data. Make sure you have MySQL installed and have the
  database connection details ready.

## Getting Started

Follow these steps to get the Leads Import Service up and running:

### Clone the Repository

```bash
git clone https://github.com/md-shadhin-mia/import-leads-csv
cd import-leads-csv
```

### Configuration

Before running the application, you need to configure the database connection.
Certainly, to configure database properties using environment variables, you can set the database configurations as
environment variables in different operating systems. Below are examples of how to do this for Windows, Linux, and
macOS:

### Windows:

1. **Using Command Prompt:**

   Open Command Prompt and set the environment variables for your database configurations.

   ```cmd
   set DATABASE_URL=jdbc:mysql://localhost:3306/your_database_name
   set DATABASE_USERNAME=your_database_username
   set DATABASE_PASSWORD=your_database_password
   ```

### Linux and macOS:

**Using Bash:**

Open your terminal and set the environment variables for your database configurations.

   ```bash
   export DATABASE_URL=jdbc:mysql://localhost:3306/your_database_name
   export DATABASE_USERNAME=your_database_username
   export DATABASE_PASSWORD=your_database_password
   ```

To make these changes permanent, you can add these export commands to your shell profile file (
e.g., `~/.bashrc`, `~/.bash_profile`, `~/.zshrc`, etc.).

### Application Configuration:

Open the `application.yml` file in the `src/main/resources` directory, and update the following properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
```

You can also configure other properties such as server port, logging, and more in this file.

### Running the Application

You can run the Leads Import Service using your favorite IDE or by using Maven command line tools.

#### Using Maven

If you prefer using Maven, navigate to the project root directory and run the following command:

```bash
mvn spring-boot:run
```

The application will start, and you should see logs indicating that the service is up and running.

## Usage

To use the Leads Import Service, you can make HTTP requests to the exposed endpoints. The API documentation, if
available, can be found at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
after the application is running. You can use tools like Swagger to explore and test the API endpoints.

First you have to upload file and wait until file save to database

**Example**

 ```bash
 curl --location 'http://localhost:8080/import-csv' \
--header 'Content-Type: multipart/form-data' \
--header 'Accept: */*' \
--form 'file=@"~/moch_data_1000.csv"
 ```

It should return file saving Status like this

```json
{
  "id": 1,
  "filename": "D:\\import-leads\\bf1264a3-43dc-474d-829c-47b1ed0b771e-moch_data_1000.csv",
  "duration": null,
  "status": "PROCESSING",
  "createdAt": "2023-10-25T17:52:05.712+00:00",
  "updatedAt": "2023-10-25T17:52:05.712+00:00"
}
```

this example show status `PROCESSING` . You can check that update status using `id`.

**example**

```bash
curl --location 'http://localhost:8080/import-csv/1' \
--header 'Accept: */*'
```

It should give update status with duration to save data to database:

```json
{
  "id": 1,
  "filename": "D:\\import-leads\\bf1264a3-43dc-474d-829c-47b1ed0b771e-moch_data_1000.csv",
  "duration": "1026 ms",
  "status": "SUCCESS",
  "createdAt": "2023-10-25T17:30:37.000+00:00",
  "updatedAt": "2023-10-25T17:30:38.000+00:00"
}
```

If status is `FAILURE` you may give wrong file or format of csv file. csv format should like this:

| id | first_name | last_name | email              | phone            | gender | ip_address     |
|----|------------|-----------|--------------------|------------------|--------|----------------|
| 1  | Farah,     | Thomas    | fgomez0@rediff.com | +86 601 627 2905 | Female | 70.102.65.201  |
| 8  | Ginam      | Flington  | gbustard7@i2i.jp   | +55 733 100 6405 | Male   | 40.195.117.180 |