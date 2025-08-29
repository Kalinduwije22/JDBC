/**
 * Student class to represent student data
 * This class encapsulates all student information and provides
 * getter and setter methods for data access
 */
public class Student {
    private int id;
    private String name;
    private String email;
    private int age;
    private String course;

    // Default constructor
    public Student() {}

    // Parameterized constructor (without id - for new students)
    public Student(String name, String email, int age, String course) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Full parameterized constructor (with id - for existing students)
    public Student(int id, String name, String email, int age, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.course = course;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    // toString method for displaying student information
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s, Age: %d, Course: %s",
                id, name, email, age, course);
    }

    // equals method for comparing students
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Student student = (Student) obj;
        return id == student.id;
    }

    // hashCode method
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
