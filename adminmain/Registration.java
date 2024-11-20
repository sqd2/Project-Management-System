package adminmain;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import entities.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Registration {

    // Generate the next available user ID
    public String generateNextId(String role) {
        String prefix = role.substring(0, 1).toUpperCase(); // Get the first letter
        int nextNumber = 1;

        try (Scanner scanner = new Scanner(new File("src/userdata/" + role.toLowerCase() + "s.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                String existingId = fields[0];
                if (existingId.startsWith(prefix)) {
                    int existingNumber = Integer.parseInt(existingId.substring(1)); // Get the number part
                    nextNumber = Math.max(nextNumber, existingNumber + 1); //  track the highest number
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        
        return String.format("%s%04d", prefix, nextNumber); // Format with leading zeros
    }

    
    public void registerStudent(Student student, String filePath) {
        String newStudentId = generateNextId("Student");
        student.setID(newStudentId);

        try (FileWriter writer = new FileWriter("src/userdata/students.txt", true)) {
            writer.write(studentData(student));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    
    public void registerLecturer(Lecturer lecturer, String filePath) {
        String newLecturerId = generateNextId("Lecturer");
        lecturer.setID(newLecturerId);
        try (FileWriter writer = new FileWriter("src/userdata/lecturers.txt", true)) {
            writer.write(lecturerData(lecturer));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void registerAdmin(Admin admin, String filePath) {
        String newAdminId = generateNextId("Admin");
        admin.setID(newAdminId);
        try (FileWriter writer = new FileWriter("src/userdata/admins.txt", true)) {
            writer.write(adminData(admin));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    //method to format the data 
    private String studentData(Student student) {
        return student.getID() + "," + student.getName() + ","  
               + student.getPassword() + "," + student.getEmail() + "," 
               + student.getProgram() + "," + student.getIntake() + "\n";
    }
    private String lecturerData(Lecturer lecturer) {
        return lecturer.getID() + "," + lecturer.getName() + ","  
               + lecturer.getPassword() + "," + lecturer.getEmail() + "," 
               + lecturer.getDepartment() + "," + lecturer.getLecturerRole() + "\n";
    }
    private String adminData(Admin admin) {
        return admin.getID() + "," + admin.getName() + ","  
               + admin.getPassword() + "," + admin.getEmail() + "\n";
    }
}