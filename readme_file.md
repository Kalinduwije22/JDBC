# Student Database Management System
**Name - W.K.K.G Wijenayake**/n
**Index Number - s16756**

A console based Java application using JDBC for managing student information in a MySQL database.

## Project Structure

```
StudentDatabaseApp/
├── Student.java              # Student entity class
├── DatabaseManager.java      # Database operations handler
├── InputValidator.java       # Input validation utility
├── DatabaseConfig.java       # Configuration constants
├── StudentDatabaseApp.java   # Main application class
└── README.md                 # Project documentation
```

## Features

- ✅ **Add Student**: Add new student records with validation
- ✅ **View All Students**: Display all students in a formatted table
- ✅ **Search by ID**: Find a specific student using their ID
- ✅ **Search by Name**: Find students by name (partial matching)
- ✅ **Update Student**: Modify existing student information
- ✅ **Delete Student**: Remove student records with confirmation
- ✅ **Input Validation**: Comprehensive validation for all inputs
- ✅ **Error Handling**: Robust error handling and user feedback

## Prerequisites

### Software Requirements
1. **Java Development Kit (JDK) 11 or higher**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **MySQL Server 8.0 or higher**
   - Download from [MySQL Official Site](https://dev.mysql.com/downloads/mysql/)

3. **MySQL JDBC Driver (Connector/J)**
   - Download from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## Setup Instructions

### 1. Database Setup

1. **Install and start MySQL Server**

2. **Create the database:**
   ```sql
   CREATE DATABASE student_db;
   USE student_db;
   ```

3. **Create a MySQL user (optional but recommended):**
   ```sql
   CREATE USER 'student_admin'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON student_db.* TO 'student_admin'@'localhost';
   FLUSH PRIVILEGES;
   ```

### 2. Project Setup

1. **Download and place the JDBC driver:**
   - Download `mysql-connector-java-x.x.xx.jar`
   - Place it in your project directory or add to classpath

2. **Configure database connection:**
   - Open `DatabaseConfig.java`
   - Update the following constants:
   ```java
   public static final String USERNAME = "your_username";
   public static final String PASSWORD = "your_password";
   ```

3. **Compile the Java files:**
   ```bash
   # Method 1: Compile all files at once
   javac -cp ".:mysql-connector-java-8.0.33.jar" *.java
   
   # Method 2: Compile individually
   javac -cp ".:mysql-connector-java-8.0.33.jar" DatabaseConfig.java
   javac -cp ".:mysql-connector-java-8.0.33.jar" Student.java
   javac -cp ".:mysql-connector-java-8.0.33.jar" InputValidator.java
   javac -cp ".:mysql-connector-java-8.0.33.jar" DatabaseManager.java
   javac -cp ".:mysql-connector-java-8.0.33.jar" StudentDatabaseApp.java
   ```

4. **Run the application:**
   ```bash
   java -cp ".:mysql-connector-java-8.0.33.jar" StudentDatabaseApp
   ```

### 3. Alternative Setup (Windows)

For Windows users, use semicolon (;) instead of colon (:) in classpath:

```cmd
# Compile
javac -cp ".;mysql-connector-java-8.0.33.jar" *.java

# Run
java -cp ".;mysql-connector-java-8.0.33.jar" StudentDatabaseApp
```

## Usage Guide

### Main Menu Options

1. **Add New Student**
   - Enter student name (2-50 characters, letters only)
   - Enter valid email address
   - Enter age (16-100 years)
   - Enter course name (2-50 characters)

2. **View All Students**
   - Displays all students in a formatted table
   - Shows total count of students

3. **Search Student by ID**
   - Enter student ID number
   - Displays student details if found

4. **Search Students by Name**
   - Enter full or partial name
   - Shows all matching students

5. **Update Student Information**
   - Enter student ID to update
   - Modify any field (press Enter to keep current value)
   - Confirm changes before saving

6. **Delete Student**
   - Enter student ID to delete
   - Confirm deletion (irreversible action)

7. **Exit Application**
   - Safely closes database connection
   - Confirms before exiting

### Input Validation

The application includes comprehensive input validation:

- **Names**: Letters, spaces, hyphens, and apostrophes only (2-50 chars)
- **Emails**: Valid email format (user@domain.com)
- **Ages**: Numeric values between 16-100
- **Courses**: Any text between 2-50 characters
- **Menu choices**: Only valid menu numbers accepted

## Database Schema

```sql
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT NOT NULL CHECK (age >= 16 AND age <= 100),
    course VARCHAR(100) NOT NULL
);
```

## Error Handling

The application handles various error scenarios:

- Database connection failures
- Duplicate email addresses
- Invalid input formats
- Network connectivity issues
- SQL constraint violations

## Troubleshooting

### Common Issues

1. **"ClassNotFoundException: com.mysql.cj.jdbc.Driver"**
   - Ensure MySQL JDBC driver is in classpath
   - Verify the JAR file name and path

2. **"Access denied for user"**
   - Check username and password in `DatabaseConfig.java`
   - Ensure MySQL user has proper privileges

3. **"Unknown database 'student_db'"**
   - Create the database using: `CREATE DATABASE student_db;`

4. **"Connection refused"**
   - Ensure MySQL server is running
   - Check host and port configuration

5. **"Table doesn't exist"**
   - The application auto-creates tables on first run
   - Check database permissions

