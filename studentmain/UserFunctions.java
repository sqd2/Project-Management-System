package studentmain;
//TP070810
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions {
    protected static final String REPORTS_FILE = "src/studentmain/Reports.txt";
    protected static final String PRESENTATION_REQUESTS_FILE = "src/studentmain/presentation_requests.txt";
    protected static final String RESULTS_FILE = "src/studentmain/results.txt";
    protected static final String STUDENTS_FILE = "src/userdata/students.txt";

    public List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return lines;
    }

    public void writeFile(String fileName, List<String> lines) {
        try (FileWriter fw = new FileWriter(fileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (String line : lines) {
                out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
