package studentmain;
//TP070810
import javax.swing.*;
import java.awt.*;

public class StudentMenu {
    private StudentFunctions studentFunctions;
    private StudentGUI studentGUI;

    public StudentMenu(String studentId, String studentName) {
        this.studentFunctions = new StudentFunctions(studentId, studentName);
        this.studentGUI = new StudentGUI(studentFunctions);
    }

    public void displayMenu() {
        JFrame frame = new JFrame("Student Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(0, 1));

        JButton submitReportButton = new JButton("Submit Report Details");
        submitReportButton.addActionListener(e -> studentGUI.submitReportDetails(frame));

        JButton editReportButton = new JButton("Edit Report Details");
        editReportButton.addActionListener(e -> studentGUI.editReportDetails(frame));

        JButton deleteReportButton = new JButton("Delete Report Details");
        deleteReportButton.addActionListener(e -> studentGUI.deleteReportDetails(frame));

        JButton requestPresentationDateButton = new JButton("Request Presentation Date");
        requestPresentationDateButton.addActionListener(e -> studentGUI.requestPresentationDate(frame));

        JButton checkSubmissionStatusButton = new JButton("Check Submission Status");
        checkSubmissionStatusButton.addActionListener(e -> studentGUI.checkSubmissionStatus(frame));

        frame.add(submitReportButton);
        frame.add(editReportButton);
        frame.add(deleteReportButton);
        frame.add(requestPresentationDateButton);
        frame.add(checkSubmissionStatusButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Example usage:
        // Replace with actual student ID and name from your application
        String studentId = "S123456";
        String studentName = "John Doe";
        StudentMenu studentMenu = new StudentMenu(studentId, studentName);
        studentMenu.displayMenu();
    }
}
