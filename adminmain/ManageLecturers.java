package adminmain; 
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import entities.Department;
import entities.Lecturer;
import entities.LecturerRole;

import java.io.*;
import java.util.*;

public class ManageLecturers {
    private final String filePath = "src/userdata/lecturers.txt";

    public List<Lecturer> loadLecturers() {
        List<Lecturer> lecturers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                lecturers.add(new Lecturer(data[0], data[1], data[2], data[3], Department.valueOf(data[4]), LecturerRole.valueOf(data[5])));
            }
        } catch (IOException e) {
            System.err.println("Error loading lecturers: " + e.getMessage());
        }
        return lecturers;
    }

    public void updateLecturerRole(String lecturerId, LecturerRole newRole) {
        List<Lecturer> lecturers = loadLecturers();
        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i).getID().equals(lecturerId)) {
                lecturers.get(i).setRole(newRole);
                break;
            }
        }

        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (Lecturer lecturer : lecturers) {
                writer.println(lecturer.getID() + "," + lecturer.getName() + "," +
                        lecturer.getPassword() + "," + lecturer.getEmail() + "," +
                        lecturer.getDepartment() + "," + lecturer.getLecturerRole());
            }
        } catch (IOException e) {
            System.err.println("Error updating lecturer role: " + e.getMessage());
        }
    }
}
