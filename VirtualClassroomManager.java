import java.util.ArrayList;
import java.util.List;

// Observer Pattern
interface Observer {
    void update(String message);
}

class Student implements Observer {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}

interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

class AssignmentManager implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String assignment;

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update("New assignment posted: " + assignment);
        }
    }

    public void postAssignment(String assignment) {
        this.assignment = assignment;
        notifyObservers();
    }
}

// Strategy Pattern
interface GradingStrategy {
    char grade(int score);
}

class PercentageGradingStrategy implements GradingStrategy {
    @Override
    public char grade(int score) {
        if (score >= 90) return 'A';
        if (score >= 80) return 'B';
        if (score >= 70) return 'C';
        if (score >= 60) return 'D';
        return 'F';
    }
}

class PassFailGradingStrategy implements GradingStrategy {
    @Override
    public char grade(int score) {
        return score >= 60 ? 'P' : 'F';
    }
}

class Grader {
    private GradingStrategy strategy;

    public Grader(GradingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(GradingStrategy strategy) {
        this.strategy = strategy;
    }

    public char grade(int score) {
        return strategy.grade(score);
    }
}

// Singleton Pattern
class ClassroomManager {
    private static ClassroomManager instance;

    private ClassroomManager() {}

    public static ClassroomManager getInstance() {
        if (instance == null) {
            instance = new ClassroomManager();
        }
        return instance;
    }

    public void manage() {
        System.out.println("Managing classroom...");
    }
}

// Factory Pattern
abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public abstract void role();
}

class Teacher extends User {
    public Teacher(String name) {
        super(name);
    }

    @Override
    public void role() {
        System.out.println(name + " is a teacher.");
    }
}

class StudentFactory {
    public static User createUser(String type, String name) {
        if (type.equalsIgnoreCase("teacher")) {
            return new Teacher(name);
        } else {
            return new Student(name);
        }
    }
}

// Adapter Pattern
interface NewNotificationSystem {
    void sendNotification(String message);
}

class LegacyNotificationSystem {
    public void notifyUser(String message) {
        System.out.println("Legacy notification: " + message);
    }
}

class NotificationAdapter implements NewNotificationSystem {
    private LegacyNotificationSystem legacyNotificationSystem;

    public NotificationAdapter(LegacyNotificationSystem legacyNotificationSystem) {
        this.legacyNotificationSystem = legacyNotificationSystem;
    }

    @Override
    public void sendNotification(String message) {
        legacyNotificationSystem.notifyUser(message);
    }
}

// Decorator Pattern
class BasicStudent {
    public void study() {
        System.out.println("Studying...");
    }
}

class AdvancedStudent extends BasicStudent {
    private BasicStudent student;

    public AdvancedStudent(BasicStudent student) {
        this.student = student;
    }

    @Override
    public void study() {
        student.study();
        attendExtraClasses();
    }

    private void attendExtraClasses() {
        System.out.println("Attending extra classes...");
    }
}

// Main class
public class VirtualClassroomManager {
    public static void main(String[] args) {
        // Observer Pattern demo
        AssignmentManager assignmentManager = new AssignmentManager();
        Student student1 = new Student("Alice");
        Student student2 = new Student("Bob");
        assignmentManager.addObserver(student1);
        assignmentManager.addObserver(student2);
        assignmentManager.postAssignment("Math Homework");

        // Strategy Pattern demo
        Grader grader = new Grader(new PercentageGradingStrategy());
        System.out.println("Grade: " + grader.grade(85));
        grader.setStrategy(new PassFailGradingStrategy());
        System.out.println("Grade: " + grader.grade(85));

        // Singleton Pattern demo
        ClassroomManager classroomManager = ClassroomManager.getInstance();
        classroomManager.manage();

        // Factory Pattern demo
        User teacher = StudentFactory.createUser("teacher", "Mr. Smith");
        User student = StudentFactory.createUser("student", "Charlie");
        teacher.role();
        student.role();

        // Adapter Pattern demo
        LegacyNotificationSystem legacySystem = new LegacyNotificationSystem();
        NewNotificationSystem adapter = new NotificationAdapter(legacySystem);
        adapter.sendNotification("New assignment available");

        // Decorator Pattern demo
        BasicStudent basicStudent = new BasicStudent();
        AdvancedStudent advancedStudent = new AdvancedStudent(basicStudent);
        advancedStudent.study();
    }
}
