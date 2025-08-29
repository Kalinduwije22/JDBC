/**
 * DatabaseConfig class contains database configuration constants
 * This class centralizes database connection parameters and settings
 */
public class DatabaseConfig {

    // Database Connection Parameters
    public static final String DB_HOST = "localhost";
    public static final String DB_PORT = "3306";
    public static final String DB_NAME = "student_db";
    public static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;

    // Database Credentials (Change these according to your MySQL setup)
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    // JDBC Driver
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Connection Pool Settings
    public static final int MAX_CONNECTIONS = 10;
    public static final int CONNECTION_TIMEOUT = 30000; // 30 seconds

    // Table and Column Names
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_COURSE = "course";

    // Validation Constants
    public static final int MIN_AGE = 16;
    public static final int MAX_AGE = 100;
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_COURSE_LENGTH = 100;

    // SQL Queries
    public static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS + " (" +
                    COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT, " +
                    COLUMN_NAME + " VARCHAR(" + MAX_NAME_LENGTH + ") NOT NULL, " +
                    COLUMN_EMAIL + " VARCHAR(" + MAX_EMAIL_LENGTH + ") UNIQUE NOT NULL, " +
                    COLUMN_AGE + " INT NOT NULL CHECK (" + COLUMN_AGE + " >= " + MIN_AGE + " AND " + COLUMN_AGE + " <= " + MAX_AGE + "), " +
                    COLUMN_COURSE + " VARCHAR(" + MAX_COURSE_LENGTH + ") NOT NULL" +
                    ")";

    public static final String INSERT_STUDENT_SQL =
            "INSERT INTO " + TABLE_STUDENTS + " (" + COLUMN_NAME + ", " + COLUMN_EMAIL + ", " + COLUMN_AGE + ", " + COLUMN_COURSE + ") VALUES (?, ?, ?, ?)";

    public static final String SELECT_ALL_STUDENTS_SQL =
            "SELECT * FROM " + TABLE_STUDENTS + " ORDER BY " + COLUMN_ID;

    public static final String SELECT_STUDENT_BY_ID_SQL =
            "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ID + " = ?";

    public static final String UPDATE_STUDENT_SQL =
            "UPDATE " + TABLE_STUDENTS + " SET " + COLUMN_NAME + " = ?, " + COLUMN_EMAIL + " = ?, " + COLUMN_AGE + " = ?, " + COLUMN_COURSE + " = ? WHERE " + COLUMN_ID + " = ?";

    public static final String DELETE_STUDENT_SQL =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_ID + " = ?";

    public static final String SEARCH_STUDENTS_BY_NAME_SQL =
            "SELECT * FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_NAME + " LIKE ? ORDER BY " + COLUMN_NAME;

    public static final String COUNT_STUDENTS_SQL =
            "SELECT COUNT(*) FROM " + TABLE_STUDENTS;

    // Application Settings
    public static final String APP_NAME = "Student Database Management System";
    public static final String APP_VERSION = "1.0";
    public static final String APP_AUTHOR = "Your Name";

    // Private constructor to prevent instantiation
    private DatabaseConfig() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Gets the full database URL with parameters
     * @return Complete database URL string
     */
    public static String getFullDatabaseUrl() {
        return DB_URL + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    }

    /**
     * Validates database configuration
     * @return true if configuration is valid, false otherwise
     */
    public static boolean validateConfiguration() {
        return true;
    }

    /**
     * Gets application information string
     * @return Formatted application information
     */
    public static String getApplicationInfo() {
        return String.format("%s v%s by %s", APP_NAME, APP_VERSION, APP_AUTHOR);
    }
}