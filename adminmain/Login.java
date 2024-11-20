package adminmain;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import java.io.*;
import java.util.Scanner;
import entities.*;

public class Login {
    private Admin currentAdmin; // logged in instance of admin
    private Student currentStudent;
    private Lecturer currentLecturer;
    private int failedAttempts; // counter for failed login attempts
    private static final int MAX_ATTEMPTS = 5; // maximum allowed failed attempts

    public Login() {
        this.failedAttempts = 0;
    }

    public boolean authenticate(String id, String password) {
        if (failedAttempts >= MAX_ATTEMPTS) {
            return false;
        }

        String role = getIDRole(id);
        String filePath = role.toLowerCase() + "s.txt";

        try (Scanner scanner = new Scanner(new File("src/userdata/" + filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");

                if (tokens[0].equals(id) && tokens[2].equals(password)) {
                    if (role.equalsIgnoreCase("admin")) {
                        currentAdmin = new Admin(tokens[0], tokens[1], tokens[2], tokens[3]);
                    } else if (role.equalsIgnoreCase("student")) {
                        currentStudent = new Student(tokens[0], tokens[1], tokens[2], tokens[3], null, null);
                    } else if (role.equalsIgnoreCase("lecturer")) {
                        Department department = Department.valueOf(tokens[4]);
                        LecturerRole lecturerRole = LecturerRole.valueOf(tokens[5]);
                        currentLecturer = new Lecturer(tokens[0], tokens[1], tokens[2], tokens[3], department, lecturerRole);
                    }
                    resetFailedAttempts();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        failedAttempts++;
        return false;
    }

    public void resetFailedAttempts() {
        this.failedAttempts = 0;
    }

    public String getIDRole(String id) {
        if (id.startsWith("A")) return "admin";
        if (id.startsWith("L")) return "lecturer";
        if (id.startsWith("S")) return "student";
        return "Unavailable";
    }

    public String getCurrentAdminId() {
        return (currentAdmin != null) ? currentAdmin.getID() : null;
    }

    public String getCurrentAdminName() {
        return (currentAdmin != null) ? currentAdmin.getName() : null;
    }

    public String getCurrentStudentId() {
        return (currentStudent != null) ? currentStudent.getID() : null;
    }

    public String getCurrentStudentName() {
        return (currentStudent != null) ? currentStudent.getName() : null;
    }

    public Lecturer getCurrentLecturer() {
        return currentLecturer;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    public boolean hasExceededMaxAttempts() {
        return failedAttempts >= MAX_ATTEMPTS;
    }
}
