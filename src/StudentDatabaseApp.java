/**
 * Main Application class for Student Database Management System
 * This class provides the user interface and coordinates operations
 * between the Student, DatabaseManager, and InputValidator classes
 */
public class StudentDatabaseApp {
    private static DatabaseManager dbManager;

    /**
     * Main method - entry point of the application
     */
    public static void main(String[] args) {
        // Initialize database manager
        dbManager = new DatabaseManager();

        // Check if database connection is successful
        if (!dbManager.isConnectionActive()) {
            System.out.println("Failed to establish database connection. Exiting application.");
            return;
        }

        // Welcome message
        displayWelcomeMessage();

        // Main application loop
        runApplication();

        // Cleanup and exit
        cleanup();
    }

    /**
     * Displays welcome message and initial information
     */
    private static void displayWelcomeMessage() {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║           STUDENT DATABASE MANAGEMENT SYSTEM          ║");
        System.out.println("║                    Version 1.0                        ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();

        // Display current database statistics
        int studentCount = dbManager.getStudentCount();
        System.out.printf("Database Status: Connected | Total Students: %d%n", studentCount);
        System.out.println();
    }

    /**
     * Main application loop
     */
    private static void runApplication() {
        while (true) {
            try {
                displayMainMenu();
                int choice = InputValidator.getMenuChoice("Enter your choice: ", 1, 7);

                System.out.println(); // Add spacing

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewAllStudents();
                        break;
                    case 3:
                        searchStudentById();
                        break;
                    case 4:
                        searchStudentByName();
                        break;
                    case 5:
                        updateStudent();
                        break;
                    case 6:
                        deleteStudent();
                        break;
                    case 7:
                        exitApplication();
                        return;
                }

                // Pause before showing menu again
                InputValidator.pause("\\nPress Enter to continue...");

            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    /**
     * Displays the main menu
     */
    private static void displayMainMenu() {
        System.out.println("┌─────────────────── MAIN MENU ───────────────────┐");
        System.out.println("│  1. Add New Student                             │");
        System.out.println("│  2. View All Students                           │");
        System.out.println("│  3. Search Student by ID                        │");
        System.out.println("│  4. Search Students by Name                     │");
        System.out.println("│  5. Update Student Information                  │");
        System.out.println("│  6. Delete Student                              │");
        System.out.println("│  7. Exit Application                            │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    /**
     * Handles adding a new student
     */
    private static void addStudent() {
        System.out.println("━━━━━━━━━━━━━━━ ADD NEW STUDENT ━━━━━━━━━━━━━━━");

        try {
            // Get student information with validation
            String name = InputValidator.getValidName("Enter student name: ");
            String email = InputValidator.getValidEmail("Enter student email: ");
            int age = InputValidator.getValidAge("Enter student age: ");
            String course = InputValidator.getValidCourse("Enter student course: ");

            // Create student object
            Student student = new Student(name, email, age, course);

            // Add to database
            if (dbManager.addStudent(student)) {
                System.out.println("✓ Student added successfully!");
                System.out.println("Student Details: " + student);
            } else {
                System.out.println("✗ Failed to add student. Please check if email already exists.");
            }

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    /**
     * Displays all students
     */
    private static void viewAllStudents() {
        System.out.println("━━━━━━━━━━━━━━━ VIEW ALL STUDENTS ━━━━━━━━━━━━━━━");

        try {
            dbManager.viewAllStudents();

            // Display summary
            int totalStudents = dbManager.getStudentCount();
            System.out.printf("\\nTotal Students in Database: %d%n", totalStudents);

        } catch (Exception e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    /**
     * Searches for a student by ID
     */
    private static void searchStudentById() {
        System.out.println("━━━━━━━━━━━━━━━ SEARCH STUDENT BY ID ━━━━━━━━━━━━━━━");

        try {
            int id = InputValidator.getIntInput("Enter student ID: ");

            Student student = dbManager.findStudentById(id);
            if (student != null) {
                System.out.println("\\n✓ Student found:");
                System.out.println("┌─────────────────────────────────────────────┐");
                System.out.printf("│ %-43s │%n", student);
                System.out.println("└─────────────────────────────────────────────┘");
            } else {
                System.out.println("✗ Student not found with ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }

    /**
     * Searches for students by name pattern
     */
    private static void searchStudentByName() {
        System.out.println("━━━━━━━━━━━━━━━ SEARCH STUDENTS BY NAME ━━━━━━━━━━━━━━━");

        try {
            String namePattern = InputValidator.getStringInput("Enter name (or partial name) to search: ");

            var students = dbManager.searchStudentsByName(namePattern);

            if (students.isEmpty()) {
                System.out.println("✗ No students found with name containing: " + namePattern);
            } else {
                System.out.printf("\\n✓ Found %d student(s) with name containing '%s':%n",
                        students.size(), namePattern);
                System.out.println("┌─────────────────────────────────────────────┐");
                for (Student student : students) {
                    System.out.printf("│ %-43s │%n", student);
                }
                System.out.println("└─────────────────────────────────────────────┘");
            }

        } catch (Exception e) {
            System.out.println("Error searching students: " + e.getMessage());
        }
    }

    /**
     * Handles updating student information
     */
    private static void updateStudent() {
        System.out.println("━━━━━━━━━━━━━━━ UPDATE STUDENT ━━━━━━━━━━━━━━━");

        try {
            int id = InputValidator.getIntInput("Enter student ID to update: ");

            Student existingStudent = dbManager.findStudentById(id);
            if (existingStudent == null) {
                System.out.println("✗ Student not found with ID: " + id);
                return;
            }

            System.out.println("\nCurrent student details:");
            System.out.println("┌─────────────────────────────────────────────┐");
            System.out.printf("│ %-43s │%n", existingStudent);
            System.out.println("└─────────────────────────────────────────────┘");

            System.out.println("\nEnter new details (press Enter to keep current value):");

            // Get updated information with current values as defaults
            String name = InputValidator.getStringInputWithDefault(
                    "Name [" + existingStudent.getName() + "]: ",
                    existingStudent.getName()
            );

            String email = InputValidator.getValidEmailWithDefault(
                    "Email [" + existingStudent.getEmail() + "]: ",
                    existingStudent.getEmail()
            );

            int age = InputValidator.getValidAgeWithDefault(
                    "Age [" + existingStudent.getAge() + "]: ",
                    existingStudent.getAge()
            );

            String course = InputValidator.getValidCourseWithDefault(
                    "Course [" + existingStudent.getCourse() + "]: ",
                    existingStudent.getCourse()
            );

            // Create updated student object
            Student updatedStudent = new Student(name, email, age, course);

            // Confirm update
            boolean confirm = InputValidator.getConfirmation("Do you want to update this student? (yes/no): ");

            if (confirm) {
                if (dbManager.updateStudent(id, updatedStudent)) {
                    System.out.println("✓ Student updated successfully!");

                    // Show updated details
                    Student refreshedStudent = dbManager.findStudentById(id);
                    System.out.println("Updated Details: " + refreshedStudent);
                } else {
                    System.out.println("✗ Failed to update student.");
                }
            } else {
                System.out.println("Update cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    /**
     * Handles deleting a student
     */
    private static void deleteStudent() {
        System.out.println("━━━━━━━━━━━━━━━ DELETE STUDENT ━━━━━━━━━━━━━━━");

        try {
            int id = InputValidator.getIntInput("Enter student ID to delete: ");

            Student student = dbManager.findStudentById(id);
            if (student == null) {
                System.out.println("✗ Student not found with ID: " + id);
                return;
            }

            System.out.println("\nStudent to be deleted:");
            System.out.println("┌─────────────────────────────────────────────┐");
            System.out.printf("│ %-43s │%n", student);
            System.out.println("└─────────────────────────────────────────────┘");

            System.out.println("\n⚠️  WARNING: This action cannot be undone!");
            boolean confirm = InputValidator.getConfirmation("Are you sure you want to delete this student? (yes/no): ");

            if (confirm) {
                if (dbManager.deleteStudent(id)) {
                    System.out.println("✓ Student deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete student.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    /**
     * Handles application exit
     */
    private static void exitApplication() {
        System.out.println("━━━━━━━━━━━━━━━ EXIT APPLICATION ━━━━━━━━━━━━━━━");

        boolean confirm = InputValidator.getConfirmation("Are you sure you want to exit? (yes/no): ");

        if (!confirm) {
            System.out.println("Exit cancelled. Returning to main menu...");
            return;
        }

        System.out.println("\nThank you for using Student Database Management System!");
        System.out.println("Goodbye! 👋");
    }

    /**
     * Cleanup resources before application exit
     */
    private static void cleanup() {
        try {
            // Close database connection
            if (dbManager != null) {
                dbManager.closeConnection();
            }

            // Close scanner
            InputValidator.closeScanner();

        } catch (Exception e) {
            System.out.println("Error during cleanup: " + e.getMessage());
        }
    }
}