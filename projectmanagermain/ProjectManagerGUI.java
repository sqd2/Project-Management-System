package projectmanagermain;
//TP073878
import entities.Department;
import entities.LecturerRole;
import entities.Lecturer;
import entities.Student;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ProjectManagerGUI extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JComboBox<String> intakeComboBox;
    private JTextField studentIdField;
    private JComboBox<String> assessmentTypeComboBox;
    private JLabel intakeLabel;
    private JLabel studentIdLabel;
    private JButton submitButton;
    private JButton cancelButton;
    private JRadioButton intakeRadioButton;
    private JRadioButton individualRadioButton;
    private JTable assessmentTable;
    private DefaultTableModel tableModel;
    private JTable studentAssessmentTable;
    private DefaultTableModel studentTableModel;
    private JTable reportTable;
    private DefaultTableModel reportTableModel;
    private JComboBox<String> assessmentTypeField;
    private JButton updateButton;
    private JButton deleteButton;
    private JComboBox<String> supervisorComboBox;
    private JComboBox<String> secondMarkerComboBox;
    private JComboBox<String> editSupervisorComboBox;
    private JComboBox<String> editSecondMarkerComboBox;

    private ProjectManager projectManager;

    public ProjectManagerGUI(ProjectManager projectManager) {
        this.projectManager = projectManager;
        initializeUI();
        try {
            loadTableData(); // Load the table data on startup
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setTitle("Project Manager System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Main menu panel
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "Menu");

        // New assessment panel with tabs
        JPanel newAssessmentPanel = createNewAssessmentPanelWithTabs();
        mainPanel.add(newAssessmentPanel, "NewAssessment");

        // View Report Panel
        JPanel viewReportPanel = createViewReportPanel();
        mainPanel.add(viewReportPanel, "ViewReport");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "Menu");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel menu = new JPanel(new GridLayout(4, 1));
        menu.setBackground(new Color(0, 0, 0));

        JButton btnAssignAssessment = createMenuButton("Assign Assessment");
        btnAssignAssessment.addActionListener(e -> {
            try {
                loadIntakeNumbers();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            cardLayout.show(mainPanel, "NewAssessment");
        });
        menu.add(btnAssignAssessment);

        JButton btnViewReport = createMenuButton("View Report");
        btnViewReport.addActionListener(e -> {
            try {
                loadReportTableData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            cardLayout.show(mainPanel, "ViewReport");
        });
        menu.add(btnViewReport);

        panel.add(menu, BorderLayout.WEST);

        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel welcomeLabel = new JLabel("Welcome to the Project Manager System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, gbc);

        gbc.gridy++;
        JLabel nameLabel = new JLabel("Aiman Hamad");
        nameLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        welcomePanel.add(nameLabel, gbc);

        panel.add(welcomePanel, BorderLayout.CENTER);
        return panel;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private JPanel createViewReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        reportTableModel = new DefaultTableModel(new Object[]{"Report ID", "Student ID", "Assessment Type", "Supervisor", "Second Marker", "Status"}, 0);
        reportTable = new JTable(reportTableModel);
        reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(reportTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createNewAssessmentPanelWithTabs() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("New Assessment", createNewAssessmentPanel());
        tabbedPane.addTab("Upload Assessment", createTablePanel());
        tabbedPane.addTab("Student Assessment", createStudentAssessmentPanel());

        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createNewAssessmentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridx = 0;
        gbc.gridy = 0;

        intakeRadioButton = new JRadioButton("Intake");
        individualRadioButton = new JRadioButton("Individual");
        ButtonGroup group = new ButtonGroup();
        group.add(intakeRadioButton);
        group.add(individualRadioButton);

        intakeLabel = new JLabel("Which intake you want");
        intakeComboBox = new JComboBox<>();

        try {
            loadIntakeNumbers();
        } catch (IOException e) {
            e.printStackTrace();
        }

        studentIdLabel = new JLabel("Student ID");
        studentIdField = new JTextField("S0", 10);
        assessmentTypeComboBox = new JComboBox<>(new String[]{"FYP", "Internship", "Investigation Reports", "CP1", "CP2", "RMCP"});

        supervisorComboBox = new JComboBox<>();
        secondMarkerComboBox = new JComboBox<>();
        try {
            loadLecturerComboBoxData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        submitButton = new JButton("Submit");
        cancelButton = new JButton("Back");

        intakeRadioButton.addActionListener(e -> switchToIntakeMode());
        individualRadioButton.addActionListener(e -> switchToIndividualMode());

        submitButton.addActionListener(e -> {
            try {
                handleSubmit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(intakeRadioButton, gbc);
        gbc.gridx = 1;
        panel.add(individualRadioButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(intakeLabel, gbc);
        gbc.gridx = 1;
        panel.add(intakeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(studentIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Assessment Type"), gbc);
        gbc.gridx = 1;
        panel.add(assessmentTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Supervisor"), gbc);
        gbc.gridx = 1;
        panel.add(supervisorComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Second Marker"), gbc);
        gbc.gridx = 1;
        panel.add(secondMarkerComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(submitButton, gbc);
        gbc.gridx = 1;
        panel.add(cancelButton, gbc);

        switchToIntakeMode();
        return panel;
    }

    private void switchToIntakeMode() {
        intakeLabel.setVisible(true);
        intakeComboBox.setVisible(true);
        studentIdLabel.setVisible(false);
        studentIdField.setVisible(false);
    }

    private void switchToIndividualMode() {
        intakeLabel.setVisible(false);
        intakeComboBox.setVisible(false);
        studentIdLabel.setVisible(true);
        studentIdField.setVisible(true);
    }

    // Inside ProjectManagerGUI class
    private void handleSubmit() throws IOException {
        String assessmentType = (String) assessmentTypeComboBox.getSelectedItem();

        String supervisorId = (String) supervisorComboBox.getSelectedItem();
        String secondMarkerId = (String) secondMarkerComboBox.getSelectedItem();

        if (supervisorId.equals(secondMarkerId)) {
            JOptionPane.showMessageDialog(this, "Supervisor and second marker cannot be the same.");
            return;
        }

        if (assessmentType.equals("Internship")) {
            secondMarkerId = "NA";
        }

        if (intakeRadioButton.isSelected()) {
            String intakeNumber = (String) Objects.requireNonNull(intakeComboBox.getSelectedItem());
            List<Student> intakeStudents = projectManager.filterStudentsByIntake(intakeNumber);
            if (intakeStudents.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No students found for intake: " + intakeNumber);
                return;
            }
            for (Student student : intakeStudents) {
                if (!isValidAssignment(student.getStudentID(), assessmentType)) {
                    return;
                }
            }
            String assessmentId = projectManager.generateAssessmentId();
            projectManager.saveAssessmentByIntake(intakeStudents, assessmentType, assessmentId, supervisorId, secondMarkerId);
            JOptionPane.showMessageDialog(this, "Assessment uploaded successfully with ID: " + assessmentId + " for intake number: " + intakeNumber);

            for (Student student : intakeStudents) {
                tableModel.addRow(new Object[]{assessmentId, assessmentType, student.getStudentID(), student.getName(), intakeNumber, supervisorId, secondMarkerId});
            }
        } else if (individualRadioButton.isSelected()) {
            String studentId = studentIdField.getText();
            Student student = projectManager.findStudentById(studentId);
            if (student != null) {
                if (!isValidAssignment(student.getStudentID(), assessmentType)) {
                    return;
                }
                String assessmentId = projectManager.generateAssessmentId();
                projectManager.saveAssessmentByIndividual(student, assessmentType, assessmentId, supervisorId, secondMarkerId);
                JOptionPane.showMessageDialog(this, "Assessment uploaded successfully with ID: " + assessmentId + " for student ID: " + studentId);

                tableModel.addRow(new Object[]{assessmentId, assessmentType, student.getStudentID(), student.getName(), student.getIntake().toString(), supervisorId, secondMarkerId});
            } else {
                JOptionPane.showMessageDialog(this, "Student ID not found.");
            }
        }

        // Refresh the table data after submission
        loadTableData();
        cardLayout.show(mainPanel, "UploadAssessment");
    }


    private boolean isValidAssignment(String studentId, String assessmentType) throws IOException {
        List<String[]> existingAssessments = projectManager.loadAssessmentsByStudentId(studentId);

        for (String[] assessment : existingAssessments) {
            if (assessment[1].equals(assessmentType)) {
                JOptionPane.showMessageDialog(this, "The same assessment cannot be assigned twice to the same student.");
                return false;
            }
            if ((assessment[1].equals("FYP") && assessmentType.equals("Internship")) || (assessment[1].equals("Internship") && assessmentType.equals("FYP"))) {
                JOptionPane.showMessageDialog(this, "A student cannot have both FYP and Internship assessments.");
                return false;
            }
        }
        return true;
    }

    private void loadLecturerComboBoxData() throws IOException {
        List<Lecturer> normalLecturers = projectManager.filterLecturersByRole(LecturerRole.LECTURER);

        for (Lecturer lecturer : normalLecturers) {
            supervisorComboBox.addItem(lecturer.getName());
            secondMarkerComboBox.addItem(lecturer.getName());
        }
    }

    private void loadIntakeNumbers() throws IOException {
        Set<String> intakeNumbers = projectManager.getIntakeNumbers();
        intakeComboBox.removeAllItems();
        for (String intakeNumber : intakeNumbers) {
            intakeComboBox.addItem(intakeNumber);
        }
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel assessmentTypeLabel = new JLabel("Assessment Type:");
        assessmentTypeField = new JComboBox<>(new String[]{"FYP", "Internship", "Investigation Reports", "CP1", "CP2", "RMCP"});
        editSupervisorComboBox = new JComboBox<>();
        editSecondMarkerComboBox = new JComboBox<>();
        try {
            loadEditLecturerComboBoxData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        topPanel.add(assessmentTypeLabel);
        topPanel.add(assessmentTypeField);
        topPanel.add(new JLabel("Supervisor ID:"));
        topPanel.add(editSupervisorComboBox);
        topPanel.add(new JLabel("Second Marker ID:"));
        topPanel.add(editSecondMarkerComboBox);
        topPanel.add(updateButton);
        topPanel.add(deleteButton);

        updateButton.addActionListener(e -> {
            try {
                handleUpdate();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        deleteButton.addActionListener(e -> {
            try {
                handleDelete();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Assessment ID", "Assessment Type", "Student ID", "Student Name", "Intake Number", "Supervisor ID", "Second Marker ID"}, 0);
        assessmentTable = new JTable(tableModel);
        assessmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(assessmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Add a mouse listener to handle row selection
        assessmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = assessmentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String assessmentType = tableModel.getValueAt(selectedRow, 1).toString();
                    assessmentTypeField.setSelectedItem(assessmentType);
                    String supervisorId = tableModel.getValueAt(selectedRow, 5).toString();
                    editSupervisorComboBox.setSelectedItem(supervisorId);
                    String secondMarkerId = tableModel.getValueAt(selectedRow, 6).toString();
                    editSecondMarkerComboBox.setSelectedItem(secondMarkerId);
                }
            }
        });

        return panel;
    }

    private JPanel createStudentAssessmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search by Student ID:");
        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        searchButton.addActionListener(e -> {
            try {
                searchByStudentID(searchField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        clearButton.addActionListener(e -> {
            searchField.setText("");
            try {
                loadStudentTableData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);

        studentTableModel = new DefaultTableModel(new Object[]{"Assessment ID", "Assessment Type", "Student ID", "Student Name", "Intake Number", "Supervisor ID", "Second Marker ID"}, 0);
        studentAssessmentTable = new JTable(studentTableModel);
        studentAssessmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(studentAssessmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void searchByStudentID(String studentID) throws IOException {
        List<String[]> lines = projectManager.loadAssessments();

        studentTableModel.setRowCount(0); // Clear existing data

        for (String[] parts : lines) {
            if (parts.length == 7 && parts[2].equals(studentID)) {
                studentTableModel.addRow(parts);
            }
        }
    }

    private void handleUpdate() throws IOException {
        int selectedRow = assessmentTable.getSelectedRow();
        if (selectedRow != -1) {
            String assessmentId = tableModel.getValueAt(selectedRow, 0).toString();
            String newAssessmentType = (String) assessmentTypeField.getSelectedItem();
            String newSupervisorId = (String) editSupervisorComboBox.getSelectedItem();
            String newSecondMarkerId = (String) editSecondMarkerComboBox.getSelectedItem();

            if (newSupervisorId.equals(newSecondMarkerId)) {
                JOptionPane.showMessageDialog(this, "Supervisor and second marker cannot be the same.");
                return;
            }

            if (newAssessmentType.equals("Internship")) {
                newSecondMarkerId = "NA";
            }

            if (!isValidAssignmentForUpdate(assessmentId, newAssessmentType, newSupervisorId, newSecondMarkerId)) {
                return;
            }
            projectManager.editAssessment(assessmentId, newAssessmentType, newSupervisorId, newSecondMarkerId);

            loadTableData();
            JOptionPane.showMessageDialog(this, "Assessment updated successfully.");
        }
    }

    private boolean isValidAssignmentForUpdate(String assessmentId, String assessmentType, String supervisorId, String secondMarkerId) throws IOException {
        List<String[]> existingAssessments = projectManager.loadAssessments();

        for (String[] assessment : existingAssessments) {
            if (!assessment[0].equals(assessmentId) && assessment[2].equals(assessment[2])) {
                if (assessment[1].equals(assessmentType)) {
                    JOptionPane.showMessageDialog(this, "The same assessment cannot be assigned twice to the same student.");
                    return false;
                }
                if ((assessment[1].equals("FYP") && assessmentType.equals("Internship")) || (assessment[1].equals("Internship") && assessmentType.equals("FYP"))) {
                    JOptionPane.showMessageDialog(this, "A student cannot have both FYP and Internship assessments.");
                    return false;
                }
            }
        }
        return true;
    }

    private void handleDelete() throws IOException {
        int selectedRow = assessmentTable.getSelectedRow();
        if (selectedRow != -1) {
            String assessmentId = tableModel.getValueAt(selectedRow, 0).toString();

            projectManager.deleteAssessment(assessmentId);

            loadTableData();
            JOptionPane.showMessageDialog(this, "Assessment deleted successfully.");
        }
    }

    private void loadTableData() throws IOException {
        List<String[]> assessments = projectManager.loadAssessments();

        tableModel.setRowCount(0); // Clear existing data

        for (String[] assessment : assessments) {
            tableModel.addRow(assessment);
        }

        studentTableModel.setRowCount(0); // Clear existing data

        for (String[] assessment : assessments) {
            studentTableModel.addRow(assessment);
        }
    }

    private void loadStudentTableData() throws IOException {
        List<String[]> assessments = projectManager.loadAssessments();

        studentTableModel.setRowCount(0); // Clear existing data

        for (String[] assessment : assessments) {
            studentTableModel.addRow(assessment);
        }
    }

    private void loadEditLecturerComboBoxData() throws IOException {
        List<Lecturer> normalLecturers = projectManager.filterLecturersByRole(LecturerRole.LECTURER);

        for (Lecturer lecturer : normalLecturers) {
            editSupervisorComboBox.addItem(lecturer.getName());
            editSecondMarkerComboBox.addItem(lecturer.getName());
        }
    }

    private void loadReportTableData() throws IOException {
        List<String[]> reports = projectManager.loadReports();

        reportTableModel.setRowCount(0); // Clear existing data

        for (String[] report : reports) {
            reportTableModel.addRow(report);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectManagerGUI frame = new ProjectManagerGUI(new ProjectManager("P001", "Project Manager", "password", "pm@domain.com", Department.IT, LecturerRole.PROJECT_MANAGER));
            frame.setVisible(true);
        });
    }
}
