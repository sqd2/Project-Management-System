package adminmain;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import java.io.*;
import java.util.Scanner;

public class Validator {

    // Check if a name is unique within a data file
    public static boolean validateUnique(String name, String filePath) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");
                if (fields[1].equalsIgnoreCase(name)) { 
                    return false; 
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return true; // No duplicate found
    }

    // Check password strength
    public static boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(pattern);
    }
 
    // Check email format
    public static boolean validateEmail(String email) {
        String pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(pattern);
    }
}
