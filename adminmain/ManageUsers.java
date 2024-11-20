package adminmain;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import java.io.*;
import java.util.*;

public class ManageUsers {

    private String filePath; 

    
    public ManageUsers(String role) {
        this.filePath = "src/userdata/" + role.toLowerCase() + "s.txt"; 
    }

    
    public String findUserById(String id) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals(id)) {
                reader.close();
                return line;
            }
        }
        reader.close();
        return null; 
    }
   
    public boolean updateUserAttribute(String id, int attributeIndex, String newValue) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean updated = false;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals(id)) {
                fields[attributeIndex] = newValue;
                line = String.join(",", fields); 
                updated = true;
            }
            lines.add(line);
        }
        reader.close();
        
        // Update file with modified data
        if (updated) {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            for (String l : lines) {
                writer.println(l);
            }
            writer.close();
        }
        return updated;
    }
    public boolean deleteUserById(String id) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        boolean deleted = false;
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals(id)) {
                deleted = true; // Mark for deletion
            } else {
                lines.add(line);
            }
        }
        reader.close();

        // Rewrite the file without the deleted user
        if (deleted) {
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            for (String l : lines) {
                writer.println(l);
            }
            writer.close();
        }

        return deleted;
    }
    
}

