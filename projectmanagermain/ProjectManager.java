package projectmanagermain;
//TP073878
import entities.Department;
import entities.LecturerRole;
import entities.Lecturer;
import entities.Program;
import entities.Intake;
import entities.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectManager extends Lecturer {

    private static final String ASSESSMENTS_FILE = "src/projectmanagermain/assessments.txt";
    private static final String REPORTS_FILE = "src/studentmain/Reports.txt";
    private static final String LECTURER_FILE = "src/userdata/lecturers.txt";
    private static final String STUDENT_FILE = "src/userdata/students.txt";

    public ProjectManager(String lecturerID, String name, String password, String email, Department department, LecturerRole lecturerRole) {
        super(lecturerID, name, password, email, department, lecturerRole);
    }

    public List<Lecturer> filterLecturersByRole(LecturerRole role) throws IOException {
        List<Lecturer> lecturers = new ArrayList<>();
        List<String> lines = FileHandling.readLinesFromFile(LECTURER_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 6 && LecturerRole.fromString(parts[5]).equals(role)) {
                lecturers.add(new Lecturer(parts[0], parts[1], parts[2], parts[3], Department.fromString(parts[4]), LecturerRole.fromString(parts[5])));
            }
        }
        return lecturers;
    }

    public List<Student> filterStudentsByIntake(String intakeNumber) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = FileHandling.readLinesFromFile(STUDENT_FILE);
        Intake intake = Intake.fromString(intakeNumber);
        if (intake == null) {
            // Handle the case where the intake is not found
            System.err.println("Unknown intake: " + intakeNumber);
            return students; // Return an empty list or handle as needed
        }
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Intake.fromString(parts[5]).equals(intake)) {
                students.add(new Student(parts[0], parts[1], parts[2], parts[3], Program.fromString(parts[4]), Intake.fromString(parts[5])));
            }
        }
        return students;
    }

    public Student findStudentById(String studentId) throws IOException {
        List<String> lines = FileHandling.readLinesFromFile(STUDENT_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && parts[0].equals(studentId)) {
                return new Student(parts[0], parts[1], parts[2], parts[3], Program.fromString(parts[4]), Intake.fromString(parts[5]));
            }
        }
        return null;
    }

    public List<String[]> loadAssessmentsByStudentId(String studentId) throws IOException {
        List<String[]> assessments = new ArrayList<>();
        List<String> lines = FileHandling.readLinesFromFile(ASSESSMENTS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 7 && parts[2].equals(studentId)) {
                assessments.add(parts);
            }
        }
        return assessments;
    }

    public List<String[]> loadAssessments() throws IOException {
        List<String[]> assessments = new ArrayList<>();
        List<String> lines = FileHandling.readLinesFromFile(ASSESSMENTS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 7) {
                assessments.add(parts);
            }
        }
        return assessments;
    }

    public List<String[]> loadReports() throws IOException {
        List<String[]> reports = new ArrayList<>();
        List<String> lines = FileHandling.readLinesFromFile(REPORTS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                reports.add(parts);
            }
        }
        return reports;
    }

    public void saveAssessmentByIntake(List<Student> students, String assessmentType, String assessmentId, String supervisorId, String secondMarkerId) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student student : students) {
            lines.add(String.join(",", assessmentId, assessmentType, student.getStudentID(), student.getName(), student.getIntake().toString(), supervisorId, secondMarkerId));
        }
        FileHandling.writeFile(ASSESSMENTS_FILE, lines, true);
    }

    public void saveAssessmentByIndividual(Student student, String assessmentType, String assessmentId, String supervisorId, String secondMarkerId) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(String.join(",", assessmentId, assessmentType, student.getStudentID(), student.getName(), student.getIntake().toString(), supervisorId, secondMarkerId));
        FileHandling.writeFile(ASSESSMENTS_FILE, lines, true);
    }

    public void editAssessment(String assessmentId, String assessmentType, String supervisorId, String secondMarkerId) throws IOException {
        List<String> lines = FileHandling.readLinesFromFile(ASSESSMENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            if (parts.length == 7 && parts[0].equals(assessmentId)) {
                lines.set(i, String.join(",", assessmentId, assessmentType, parts[2], parts[3], parts[4], supervisorId, secondMarkerId));
            }
        }
        FileHandling.writeFile(ASSESSMENTS_FILE, lines);
    }

    public void deleteAssessment(String assessmentId) throws IOException {
        List<String> lines = FileHandling.readLinesFromFile(ASSESSMENTS_FILE);
        lines.removeIf(line -> line.split(",")[0].equals(assessmentId));
        FileHandling.writeFile(ASSESSMENTS_FILE, lines);
    }

    public String generateAssessmentId() throws IOException {
        List<String> lines = FileHandling.readLinesFromFile(ASSESSMENTS_FILE);
        int maxId = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 1 && parts[0].startsWith("AS")) {
                int id = Integer.parseInt(parts[0].substring(2));
                if (id > maxId) {
                    maxId = id;
                }
            }
        }
        return String.format("AS%03d", maxId + 1);
    }

    public Set<String> getIntakeNumbers() throws IOException {
        Set<String> intakeNumbers = new HashSet<>();
        List<String> lines = FileHandling.readLinesFromFile(STUDENT_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                intakeNumbers.add(parts[5]);
            }
        }
        return intakeNumbers;
    }
}
