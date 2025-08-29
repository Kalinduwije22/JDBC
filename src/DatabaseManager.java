import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseManager class handles all database operations
 * This class manages the connection to MySQL database and provides
 * CRUD operations for Student entities
 */
public class DatabaseManager {
    // Database connection parameters
    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root"; // Change this to your MySQL password

    private Connection connection;

    /**
     * Constructor - establishes database connection and creates table
     */
    public DatabaseManager() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            System.out.println("Database connected successfully!");

            // Create table if it doesn't exist
            createTableIfNotExists();

        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Creates the students table if it doesn't exist
     */
    private void createTableIfNotExists() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS students (
                id INT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                age INT NOT NULL CHECK (age > 0 AND age < 150),
                course VARCHAR(100) NOT NULL
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Students table is ready!");
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * Adds a new student to the database
     * @param student The student object to add
     * @return true if student was added successfully, false otherwise
     */
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, age, course) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error code
                System.out.println("Error: Email already exists in database!");
            } else {
                System.out.println("Error adding student: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Retrieves all students from the database
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving students: " + e.getMessage());
        }

        return students;
    }

    /**
     * Displays all students in a formatted way
     */
    public void viewAllStudents() {
        List<Student> students = getAllStudents();

        System.out.println("\n==================== STUDENT LIST ====================");
        if (students.isEmpty()) {
            System.out.println("No students found in database.");
            return;
        }

        System.out.printf("%-5s %-20s %-30s %-5s %-20s%n", "ID", "NAME", "EMAIL", "AGE", "COURSE");
        System.out.println("-----------------------------------------------------------------------");

        for (Student student : students) {
            System.out.printf("%-5d %-20s %-30s %-5d %-20s%n",
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getAge(),
                    student.getCourse());
        }
        System.out.println("=======================================================");
    }

    /**
     * Finds a student by their ID
     * @param id The student ID to search for
     * @return Student object if found, null otherwise
     */
    public Student findStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("course")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error finding student: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates an existing student's information
     * @param id The ID of the student to update
     * @param student The updated student information
     * @return true if update was successful, false otherwise
     */
    public boolean updateStudent(int id, Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, age = ?, course = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getEmail());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getCourse());
            pstmt.setInt(5, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Duplicate entry error code
                System.out.println("Error: Email already exists in database!");
            } else {
                System.out.println("Error updating student: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Deletes a student from the database
     * @param id The ID of the student to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches students by name (partial match)
     * @param namePattern The name pattern to search for
     * @return List of students matching the pattern
     */
    public List<Student> searchStudentsByName(String namePattern) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ? ORDER BY name";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + namePattern + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Student student = new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getInt("age"),
                            rs.getString("course")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching students: " + e.getMessage());
        }

        return students;
    }

    /**
     * Gets the total count of students in database
     * @return Number of students
     */
    public int getStudentCount() {
        String sql = "SELECT COUNT(*) FROM students";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting student count: " + e.getMessage());
        }

        return 0;
    }

    /**
     * Checks if database connection is active
     * @return true if connection is active, false otherwise
     */
    public boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}