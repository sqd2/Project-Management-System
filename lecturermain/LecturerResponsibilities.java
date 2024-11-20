package lecturermain;
//TP072764
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LecturerResponsibilities extends JFrame {

    private List<String[]> studentData;
    private List<String[]> presentationRequests;
    private List<String[]> secondMarkerData;

    public LecturerResponsibilities() {
        super("Lecturer Responsibilities");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Load data from files
        studentData = loadDataFromFile("src/lecturermain/studentactivity.txt");
        presentationRequests = loadDataFromFile("src/studentmain/presentation_requests.txt");
        secondMarkerData = loadDataFromFile("src/lecturermain/second_marker.txt");

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons
        JButton viewAssignedSuperviseesButton = new JButton("View Assigned Supervisees");
        JButton viewPresentationRequestsButton = new JButton("View Presentation Requests");
        JButton viewSecondMarkerAcceptanceButton = new JButton("View Second Marker Acceptance / Available Slot");
        JButton confirmPresentationDateButton = new JButton("Confirm Date of Presentation & Slot");
        JButton evaluateReportButton = new JButton("Evaluate Report with Feedback");
        JButton viewSuperviseesByStatusButton = new JButton("View Supervisee List (Pending / Scheduled)");
        JButton lecturerDashboardButton = new JButton("Dashboard with Details of Assigned Supervisees");

        // Add buttons to the panel
        buttonPanel.add(viewAssignedSuperviseesButton);
        buttonPanel.add(viewPresentationRequestsButton);
        buttonPanel.add(viewSecondMarkerAcceptanceButton);
        buttonPanel.add(confirmPresentationDateButton);
        buttonPanel.add(evaluateReportButton);
        buttonPanel.add(viewSuperviseesByStatusButton);
        buttonPanel.add(lecturerDashboardButton);

        // Add button panel to the frame
        this.add(buttonPanel, BorderLayout.CENTER);

        // Set a nice font for all buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setFont(buttonFont);
                component.setBackground(new Color(59, 89, 182));
                component.setForeground(Color.WHITE);
                ((JButton) component).setFocusPainted(false);
            }
        }

        // Add button listeners
        viewAssignedSuperviseesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAssignedSupervisees();
            }
        });

        viewPresentationRequestsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewPresentationRequests();
            }
        });

        viewSecondMarkerAcceptanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSecondMarkerAcceptance();
            }
        });

        confirmPresentationDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmPresentationDate();
            }
        });

        evaluateReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                evaluateReport();
            }
        });

        viewSuperviseesByStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewSuperviseesByStatus();
            }
        });

        lecturerDashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lecturerDashboard();
            }
        });

        // Set the frame to be visible
        this.setVisible(true);
    }

    private List<String[]> loadDataFromFile(String filename) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void viewAssignedSupervisees() {
        String[] columnNames = {"ID", "Name", "Assessment Type", "Status"};
        Object[][] data = studentData.toArray(new Object[0][]);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Assigned Supervisees");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void viewPresentationRequests() {
        String[] columnNames = {"ID", "Student Name", "Requested Date"};
        Object[][] data = presentationRequests.toArray(new Object[0][]);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Presentation Requests");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void viewSecondMarkerAcceptance() {
        String[] columnNames = {"Lecturer", "Status"};
        Object[][] data = secondMarkerData.toArray(new Object[0][]);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Second Marker Acceptance / Available Slot");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void confirmPresentationDate() {
        JFrame frame = new JFrame("Confirm Presentation Date & Slot");
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField();
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText();
                String date = dateField.getText();
                // Simulate confirmation logic
                JOptionPane.showMessageDialog(frame, "Confirmed presentation date for Student ID: " + studentId + " on " + date);
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(new JLabel()); // empty cell
        panel.add(confirmButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void evaluateReport() {
        JFrame frame = new JFrame("Evaluate Report");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel feedbackLabel = new JLabel("Feedback:");
        JTextArea feedbackArea = new JTextArea();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentId = idField.getText();
                String feedback = feedbackArea.getText();
                // Simulate feedback submission logic
                JOptionPane.showMessageDialog(frame, "Submitted feedback for Student ID: " + studentId + "\nFeedback: " + feedback);
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(feedbackLabel);
        panel.add(new JScrollPane(feedbackArea));
        panel.add(new JLabel()); // empty cell
        panel.add(submitButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void viewSuperviseesByStatus() {
        JFrame frame = new JFrame("Supervisee List (Pending / Scheduled)");
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        String[] statuses = {"Pending", "Scheduled"};
        JComboBox<String> statusComboBox = new JComboBox<>(statuses);
        JTextArea resultArea = new JTextArea();

        statusComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedStatus = (String) statusComboBox.getSelectedItem();
                StringBuilder result = new StringBuilder();
                for (String[] student : studentData) {
                    if (student[3].equalsIgnoreCase(selectedStatus)) {
                        result.append("ID: ").append(student[0]).append(", Name: ").append(student[1])
                                .append(", Assessment Type: ").append(student[2]).append("\n");
                    }
                }
                resultArea.setText(result.toString());
            }
        });

        panel.add(statusComboBox, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void lecturerDashboard() {
        JFrame frame = new JFrame("Lecturer Dashboard");
        frame.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel assignedLabel = new JLabel("Total Assigned Supervisees:");
        JLabel assignedCount = new JLabel(String.valueOf(studentData.size()));
        JLabel pendingLabel = new JLabel("Pending Supervisees:");
        long pendingCount = studentData.stream().filter(s -> s[3].equalsIgnoreCase("Pending")).count();
        JLabel pendingCountLabel = new JLabel(String.valueOf(pendingCount));
        JLabel scheduledLabel = new JLabel("Scheduled Supervisees:");
        long scheduledCount = studentData.stream().filter(s -> s[3].equalsIgnoreCase("Scheduled")).count();
        JLabel scheduledCountLabel = new JLabel(String.valueOf(scheduledCount));

        panel.add(assignedLabel);
        panel.add(assignedCount);
        panel.add(pendingLabel);
        panel.add(pendingCountLabel);
        panel.add(scheduledLabel);
        panel.add(scheduledCountLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LecturerResponsibilities();
            }
        });
    }
}
