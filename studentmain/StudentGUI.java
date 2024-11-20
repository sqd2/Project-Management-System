package studentmain;
//TP070810
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentGUI extends UserGUI {

    public StudentGUI(StudentFunctions studentFunctions) {
        super(studentFunctions);
    }

    @Override
    public void submitReportDetails(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField dateField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField linkField = new JTextField();
        JTextField lecturerIdField = new JTextField();

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);
        panel.add(new JLabel("Assessment Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Moodle Link:"));
        panel.add(linkField);
        panel.add(new JLabel("Lecturer ID (L001):"));
        panel.add(lecturerIdField);

        int option = JOptionPane.showConfirmDialog(parentFrame, panel, "Submit Report Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();
            String type = typeField.getText();
            String link = linkField.getText();
            String lecturerId = lecturerIdField.getText();

            if (!userFunctions.isValidDate(date)) {
                showError(parentFrame, "Invalid date format. Please enter date as YYYY-MM-DD.");
                return;
            }

            try {
                ((StudentFunctions) userFunctions).submitReport(date, type, link, lecturerId);
                showSuccess(parentFrame, "Report details submitted successfully.");
            } catch (IOException e) {
                showError(parentFrame, "An error occurred while submitting report details: " + e.getMessage());
            }
        }
    }

    @Override
    public void editReportDetails(JFrame parentFrame) {
        List<String> reports = ((StudentFunctions) userFunctions).readReports();
        List<String> studentReports = new ArrayList<>();

        for (String report : reports) {
            if (report.contains("," + ((StudentFunctions) userFunctions).getStudentId() + ",")) {
                studentReports.add(report);
            }
        }

        if (studentReports.isEmpty()) {
            showError(parentFrame, "No reports found for editing.");
            return;
        }

        String[] reportArray = studentReports.toArray(new String[0]);
        JComboBox<String> reportList = new JComboBox<>(reportArray);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select report to edit:"));
        panel.add(reportList);

        int option = JOptionPane.showConfirmDialog(parentFrame, panel, "Edit Report Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int index = reportList.getSelectedIndex();
            if (index >= 0 && index < studentReports.size()) {
                String selectedReport = studentReports.get(index);
                String[] parts = selectedReport.split(",");
                String reportId = parts[0];
                String date = parts[3];
                String type = parts[4];
                String link = parts[5];
                String lecturerId = parts[2];

                JPanel editPanel = new JPanel(new GridLayout(0, 1));
                JTextField dateField = new JTextField(date);
                JTextField typeField = new JTextField(type);
                JTextField linkField = new JTextField(link);
                JTextField lecturerIdField = new JTextField(lecturerId);

                editPanel.add(new JLabel("Date (YYYY-MM-DD):"));
                editPanel.add(dateField);
                editPanel.add(new JLabel("Assessment Type:"));
                editPanel.add(typeField);
                editPanel.add(new JLabel("Moodle Link:"));
                editPanel.add(linkField);
                editPanel.add(new JLabel("Lecturer ID (L001):"));
                editPanel.add(lecturerIdField);

                int editOption = JOptionPane.showConfirmDialog(parentFrame, editPanel, "Edit Report Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (editOption == JOptionPane.OK_OPTION) {
                    String newDate = dateField.getText();
                    String newType = typeField.getText();
                    String newLink = linkField.getText();
                    String newLecturerId = lecturerIdField.getText();

                    if (!userFunctions.isValidDate(newDate)) {
                        showError(parentFrame, "Invalid date format. Please enter date as YYYY-MM-DD.");
                        return;
                    }

                    String newReport = reportId + "," + ((StudentFunctions) userFunctions).getStudentId() + "," + newLecturerId + "," + newDate + "," + newType + "," + newLink;
                    ((StudentFunctions) userFunctions).updateReport(selectedReport, newReport);
                    showSuccess(parentFrame, "Report details updated successfully.");
                }
            } else {
                showError(parentFrame, "Invalid index.");
            }
        }
    }

    @Override
    public void deleteReportDetails(JFrame parentFrame) {
        List<String> reports = ((StudentFunctions) userFunctions).readReports();
        List<String> studentReports = new ArrayList<>();

        for (String report : reports) {
            if (report.contains("," + ((StudentFunctions) userFunctions).getStudentId() + ",")) {
                studentReports.add(report);
            }
        }

        if (studentReports.isEmpty()) {
            showError(parentFrame, "No reports found for deletion.");
            return;
        }

        String[] reportArray = studentReports.toArray(new String[0]);
        JComboBox<String> reportList = new JComboBox<>(reportArray);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select report to delete:"));
        panel.add(reportList);

        int option = JOptionPane.showConfirmDialog(parentFrame, panel, "Delete Report Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            int index = reportList.getSelectedIndex();
            if (index >= 0 && index < studentReports.size()) {
                String selectedReport = studentReports.get(index);
                ((StudentFunctions) userFunctions).deleteReport(selectedReport);
                showSuccess(parentFrame, "Report details deleted successfully.");
            } else {
                showError(parentFrame, "Invalid index.");
            }
        }
    }

    @Override
    public void requestPresentationDate(JFrame parentFrame) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JTextField dateField = new JTextField();

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);

        int option = JOptionPane.showConfirmDialog(parentFrame, panel, "Request Presentation Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String date = dateField.getText();

            if (!userFunctions.isValidDate(date)) {
                showError(parentFrame, "Invalid date format. Please enter date as YYYY-MM-DD.");
                return;
            }

            try {
                ((StudentFunctions) userFunctions).requestPresentation(date);
                showSuccess(parentFrame, "Presentation date requested successfully.");
            } catch (IOException e) {
                showError(parentFrame, "An error occurred while requesting presentation date: " + e.getMessage());
            }
        }
    }

    @Override
    public void checkSubmissionStatus(JFrame parentFrame) {
        List<String> results = ((StudentFunctions) userFunctions).checkResults();
        List<String> studentResults = new ArrayList<>();

        for (String result : results) {
            if (result.contains("," + ((StudentFunctions) userFunctions).getStudentId() + ",")) {
                studentResults.add(result);
            }
        }

        if (studentResults.isEmpty()) {
            showError(parentFrame, "No submission status found for student ID: " + ((StudentFunctions) userFunctions).getStudentId());
            return;
        }

        StringBuilder message = new StringBuilder("Submission Status:\n\n");

        for (String result : studentResults) {
            message.append(result).append("\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Submission Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
