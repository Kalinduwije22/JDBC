import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * InputValidator class handles all user input validation and processing
 * This utility class provides methods for safe input handling with validation
 */
public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    // Name validation pattern (letters, spaces, and common punctuation)
    private static final Pattern NAME_PATTERN =
            Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");

    /**
     * Gets a string input from user with validation
     * @param prompt The prompt message to display
     * @return Valid string input
     */
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    /**
     * Gets a string input with default value option
     * @param prompt The prompt message to display
     * @param defaultValue The default value if user presses Enter
     * @return String input or default value
     */
    public static String getStringInputWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }

    /**
     * Gets a valid integer input from user
     * @param prompt The prompt message to display
     * @return Valid integer input
     */
    public static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /**
     * Gets an integer input with default value option
     * @param prompt The prompt message to display
     * @param defaultValue The default value if user presses Enter
     * @return Integer input or default value
     */
    public static int getIntInputWithDefault(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, keeping current value: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Gets a valid name input from user
     * @param prompt The prompt message to display
     * @return Valid name input
     */
    public static String getValidName(String prompt) {
        while (true) {
            String name = getStringInput(prompt);

            if (NAME_PATTERN.matcher(name).matches()) {
                return name;
            }

            System.out.println("Invalid name format. Name should contain only letters, spaces, hyphens, and apostrophes (2-50 characters).");
        }
    }

    /**
     * Gets a valid email input from user
     * @param prompt The prompt message to display
     * @return Valid email input
     */
    public static String getValidEmail(String prompt) {
        while (true) {
            String email = getStringInput(prompt);

            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email.toLowerCase(); // Store emails in lowercase
            }

            System.out.println("Invalid email format. Please enter a valid email address (e.g., user@example.com).");
        }
    }

    /**
     * Gets a valid email input with default value option
     * @param prompt The prompt message to display
     * @param defaultValue The default email value
     * @return Valid email input or default value
     */
    public static String getValidEmailWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        if (EMAIL_PATTERN.matcher(input).matches()) {
            return input.toLowerCase();
        }

        System.out.println("Invalid email format, keeping current value: " + defaultValue);
        return defaultValue;
    }

    /**
     * Gets a valid age input from user (between 16 and 100)
     * @param prompt The prompt message to display
     * @return Valid age input
     */
    public static int getValidAge(String prompt) {
        while (true) {
            int age = getIntInput(prompt);

            if (age >= 16 && age <= 100) {
                return age;
            }

            System.out.println("Age must be between 16 and 100 years.");
        }
    }

    /**
     * Gets a valid age input with default value option
     * @param prompt The prompt message to display
     * @param defaultValue The default age value
     * @return Valid age input or default value
     */
    public static int getValidAgeWithDefault(String prompt, int defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        try {
            int age = Integer.parseInt(input);
            if (age >= 16 && age <= 100) {
                return age;
            }
            System.out.println("Invalid age (must be 16-100), keeping current value: " + defaultValue);
            return defaultValue;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, keeping current value: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Gets a valid course name input from user
     * @param prompt The prompt message to display
     * @return Valid course name
     */
    public static String getValidCourse(String prompt) {
        while (true) {
            String course = getStringInput(prompt);

            if (course.length() >= 2 && course.length() <= 50) {
                return course;
            }

            System.out.println("Course name must be between 2 and 50 characters.");
        }
    }

    /**
     * Gets a valid course name with default value option
     * @param prompt The prompt message to display
     * @param defaultValue The default course value
     * @return Valid course name or default value
     */
    public static String getValidCourseWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            return defaultValue;
        }

        if (input.length() >= 2 && input.length() <= 50) {
            return input;
        }

        System.out.println("Invalid course name (must be 2-50 characters), keeping current value: " + defaultValue);
        return defaultValue;
    }

    /**
     * Gets a confirmation input (yes/no) from user
     * @param prompt The prompt message to display
     * @return true if user confirms (yes/y), false otherwise
     */
    public static boolean getConfirmation(String prompt) {
        while (true) {
            String input = getStringInput(prompt).toLowerCase();

            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }

            System.out.println("Please enter 'yes' or 'no' (or 'y'/'n').");
        }
    }

    /**
     * Gets a menu choice input within specified range
     * @param prompt The prompt message to display
     * @param minChoice Minimum valid choice
     * @param maxChoice Maximum valid choice
     * @return Valid menu choice
     */
    public static int getMenuChoice(String prompt, int minChoice, int maxChoice) {
        while (true) {
            int choice = getIntInput(prompt);

            if (choice >= minChoice && choice <= maxChoice) {
                return choice;
            }

            System.out.printf("Please enter a number between %d and %d.%n", minChoice, maxChoice);
        }
    }

    /**
     * Pauses execution until user presses Enter
     * @param message The message to display
     */
    public static void pause(String message) {
        System.out.print(message);
        scanner.nextLine();
    }

    /**
     * Closes the scanner (call this when application is ending)
     */
    public static void closeScanner() {
        scanner.close();
    }
}