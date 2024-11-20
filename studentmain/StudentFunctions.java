package studentmain;
//TP070810
import java.io.*;
import java.util.List;

public class StudentFunctions extends UserFunctions {
    private String studentId;
    private String studentName;

    public StudentFunctions(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public void submitReport(String date, String type, String link, String lecturerId) throws IOException {
        String reportId = "R" + String.format("%04d", getNextIndex(REPORTS_FILE));
        try (FileWriter fw = new FileWriter(REPORTS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(reportId + "," + studentId + "," + lecturerId + "," + date + "," + type + "," + link);
        }
    }

    public List<String> readReports() {
        return readFile(REPORTS_FILE);
    }

    public void updateReport(String oldReport, String newReport) {
        List<String> reports = readFile(REPORTS_FILE);
        reports.set(reports.indexOf(oldReport), newReport);
        writeFile(REPORTS_FILE, reports);
    }

    public void deleteReport(String report) {
        List<String> reports = readFile(REPORTS_FILE);
        reports.remove(report);
        writeFile(REPORTS_FILE, reports);
    }

    public void requestPresentation(String date) throws IOException {
        try (FileWriter fw = new FileWriter(PRESENTATION_REQUESTS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(studentId + "," + studentName + "," + date);
        }
    }

    public List<String> checkResults() {
        return readFile(RESULTS_FILE);
    }

    private int getNextIndex(String fileName) {
        List<String> lines = readFile(fileName);
        return lines.size() + 1;
    }

    public String getStudentId() {
        return studentId;
    }
}
