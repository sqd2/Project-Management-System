package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526
import entities.*;
import adminmain.Registration;
import adminmain.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class RegistrationGUI extends JPanel {
    private JComboBox<String> roleSelection;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JButton registerButton;
    private JButton uploadFileButton;
    private JFileChooser fileChooser;
    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JTextField emailField = new JTextField(25);
    private JComboBox<Program> studentProgramComboBox;
    private JComboBox<Intake> studentIntakeComboBox;
    private JComboBox<Department> lecturerDepartmentComboBox;
    private JComboBox<String> lecturerRoleComboBox = new JComboBox<>(new String[]{"LECTURER", "PROJECT_MANAGER"});
    private JComboBox<String> registrationTypeComboBox;
    private JLabel statusLabel;

    public RegistrationGUI() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.DARK_GRAY);

        roleSelection = new JComboBox<>(new String[]{"Student", "Lecturer", "Admin"});
        roleSelection.setBackground(new Color(45, 45, 45));
        roleSelection.setForeground(Color.WHITE);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(roleSelection, BorderLayout.NORTH);
        mainPanel.setBackground(Color.DARK_GRAY);

        inputPanel = new JPanel();
        inputPanel.setBackground(Color.DARK_GRAY);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        registerButton = new JButton("Register");
        uploadFileButton = new JButton("Upload File");
        registerButton.setBackground(new Color(45, 45, 45));
        registerButton.setForeground(Color.WHITE);
        uploadFileButton.setBackground(new Color(45, 45, 45));
        uploadFileButton.setForeground(Color.WHITE);

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.WHITE);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.add(registerButton, BorderLayout.WEST);
        bottomPanel.add(uploadFileButton, BorderLayout.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);
        setVisible(true);

        roleSelection.addActionListener(new RoleSelectionListener());
        registerButton.addActionListener(new RegisterButtonListener());
        uploadFileButton.addActionListener(new UploadFileButtonListener());
    }

    private void addCommonInputFields() {
        idField.setBackground(new Color(45, 45, 45));
        idField.setForeground(Color.WHITE);
        inputPanel.add(createLabel("ID:"));
        inputPanel.add(idField);
        idField.setEditable(false);

        nameField.setBackground(new Color(45, 45, 45));
        nameField.setForeground(Color.WHITE);
        inputPanel.add(createLabel("Name:"));
        inputPanel.add(nameField);

        passwordField.setBackground(new Color(45, 45, 45));
        passwordField.setForeground(Color.WHITE);
        inputPanel.add(createLabel("Password:"));
        inputPanel.add(passwordField);

        emailField.setBackground(new Color(45, 45, 45));
        emailField.setForeground(Color.WHITE);
        inputPanel.add(createLabel("Email:"));
        inputPanel.add(emailField);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(45, 45, 45));
        return label;
    }

    private class RoleSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputPanel.removeAll();
            inputPanel.setLayout(new GridLayout(0, 2, 5, 5));

            String selectedRole = (String) roleSelection.getSelectedItem();

            if ("Student".equals(selectedRole) || "Lecturer".equals(selectedRole)) {
                registrationTypeComboBox = new JComboBox<>(new String[]{"Individual Registration", "Group Registration"});
                registrationTypeComboBox.setBackground(new Color(45, 45, 45));
                registrationTypeComboBox.setForeground(Color.WHITE);
                inputPanel.add(createLabel("Registration Type:"));
                inputPanel.add(registrationTypeComboBox);
                registrationTypeComboBox.addActionListener(new RegistrationTypeListener());
            } else {
                addCommonInputFields();
            }

            inputPanel.revalidate();
            inputPanel.repaint();
        }
    }

    private class RegistrationTypeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputPanel.removeAll();
            inputPanel.setLayout(new GridLayout(0, 2, 5, 5));

            String registrationType = (String) registrationTypeComboBox.getSelectedItem();
            String selectedRole = (String) roleSelection.getSelectedItem();

            if ("Individual Registration".equals(registrationType)) {
                addCommonInputFields();

                if ("Student".equals(selectedRole)) {
                    addStudentInputFields();
                } else if ("Lecturer".equals(selectedRole)) {
                    addLecturerInputFields();
                }
            } else {
                addGroupInputFields(selectedRole);
            }

            inputPanel.revalidate();
            inputPanel.repaint();
        }

        private void addStudentInputFields() {
            studentProgramComboBox = new JComboBox<>(Program.values());
            studentProgramComboBox.setBackground(new Color(45, 45, 45));
            studentProgramComboBox.setForeground(Color.WHITE);
            studentIntakeComboBox = new JComboBox<>(Intake.values());
            studentIntakeComboBox.setBackground(new Color(45, 45, 45));
            studentIntakeComboBox.setForeground(Color.WHITE);

            inputPanel.add(createLabel("Program:"));
            inputPanel.add(studentProgramComboBox);
            inputPanel.add(createLabel("Intake:"));
            inputPanel.add(studentIntakeComboBox);
        }

        private void addLecturerInputFields() {
            lecturerDepartmentComboBox = new JComboBox<>(Department.values());
            lecturerDepartmentComboBox.setBackground(new Color(45, 45, 45));
            lecturerDepartmentComboBox.setForeground(Color.WHITE);
            lecturerRoleComboBox.setBackground(new Color(45, 45, 45));
            lecturerRoleComboBox.setForeground(Color.WHITE);
            inputPanel.add(createLabel("Department:"));
            inputPanel.add(lecturerDepartmentComboBox);
            inputPanel.add(createLabel("Role (PM or not):"));
            inputPanel.add(lecturerRoleComboBox);
        }

        private void addGroupInputFields(String role) {
            if (role.equals("Student")) {
                studentIntakeComboBox = new JComboBox<>(Intake.values());
                studentIntakeComboBox.setBackground(new Color(45, 45, 45));
                studentIntakeComboBox.setForeground(Color.WHITE);
                inputPanel.add(createLabel("Intake:"));
                inputPanel.add(studentIntakeComboBox);
            } else if (role.equals("Lecturer")) {
                lecturerDepartmentComboBox = new JComboBox<>(Department.values());
                lecturerDepartmentComboBox.setBackground(new Color(45, 45, 45));
                lecturerDepartmentComboBox.setForeground(Color.WHITE);
                inputPanel.add(createLabel("Department:"));
                inputPanel.add(lecturerDepartmentComboBox);
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String role = (String) roleSelection.getSelectedItem();
            String registrationType = registrationTypeComboBox != null ? (String) registrationTypeComboBox.getSelectedItem() : "Individual Registration";

            if ("Individual Registration".equals(registrationType)) {
                Registration registration = new Registration();

                // Get the generated ID
                String generatedId = registration.generateNextId(role);

                // Get common input values
                String name = nameField.getText();
                String password = new String(passwordField.getPassword()); // Get password as string
                String email = emailField.getText();

                if (!Validator.validateUnique(name, "src/userdata/" + role.toLowerCase() + "s.txt")) {
                    JOptionPane.showMessageDialog(RegistrationGUI.this, "Name is already taken!");
                    return;
                }

                if (!Validator.validatePassword(password)) {
                    JOptionPane.showMessageDialog(RegistrationGUI.this,
                            "Password must be at least 8 characters long and contain:\n" +
                                    "- At least one uppercase letter\n" +
                                    "- At least one lowercase letter\n" +
                                    "- At least one digit\n" +
                                    "- At least one special character (@#$%^&+=!)");
                    return;
                }

                if (!Validator.validateEmail(email)) {
                    JOptionPane.showMessageDialog(RegistrationGUI.this, "Invalid email format!");
                    return;
                }

                if (role.equals("Student")) {
                    Program program = (Program) studentProgramComboBox.getSelectedItem();
                    Intake intake = (Intake) studentIntakeComboBox.getSelectedItem();

                    Student student = new Student(generatedId, name, password, email, program, intake);
                    registration.registerStudent(student, "src/userdata/students.txt");
                } else if (role.equals("Lecturer")) {
                    Department department = (Department) lecturerDepartmentComboBox.getSelectedItem();
                    LecturerRole lecturerRole = LecturerRole.valueOf((String) lecturerRoleComboBox.getSelectedItem());

                    Lecturer lecturer = new Lecturer(generatedId, name, password, email, department, lecturerRole);
                    registration.registerLecturer(lecturer, "src/userdata/lecturers.txt");
                } else if (role.equals("Admin")) {
                    Admin admin = new Admin(generatedId, name, password, email);
                    registration.registerAdmin(admin, "src/userdata/admins.txt");
                }

                // Reset everything after each registration
                idField.setText(generatedId);
                idField.setEditable(false);
                nameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                if (lecturerRoleComboBox != null) {
                    lecturerRoleComboBox.setSelectedIndex(0);
                }

                JOptionPane.showMessageDialog(RegistrationGUI.this,
                        "User registered successfully with ID: " + generatedId, "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegistrationGUI.this,
                        "Please use the 'Upload File' button for group registration.", "Group Registration",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class UploadFileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String departmentOrIntake = getDepartmentOrIntake();
                if (departmentOrIntake == null || departmentOrIntake.isEmpty()) {
                    statusLabel.setText("Please specify the department or intake.");
                    return;
                }

                try {
                    List<String> lines = Files.readAllLines(selectedFile.toPath());
                    String role = (String) roleSelection.getSelectedItem();
                    String fileName = role.equals("Student") ? "src/userdata/students.txt" : "src/userdata/lecturers.txt";
                    Registration registration = new Registration();
                    int idCounter = Integer.parseInt(registration.generateNextId(role).substring(1));

                    try (FileWriter writer = new FileWriter(fileName, true)) {
                        for (String line : lines) {
                            String[] parts = line.split(",");
                            String name = parts[0];
                            String password = parts[1];
                            String email = parts[2];
                            String programOrRole = parts[3];
                            String id = role.charAt(0) + String.format("%04d", idCounter++);

                            if (role.equals("Student")) {
                                writer.write(id + "," + name + "," + password + "," + email + "," + programOrRole + "," + departmentOrIntake + "\n");
                            } else {
                                writer.write(id + "," + name + "," + password + "," + email + "," + departmentOrIntake + "," + programOrRole + "\n");
                            }
                        }
                        statusLabel.setText("Bulk registration successful.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Error writing to file.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Error reading the file.");
                }
            }
        }
    }

    private String getDepartmentOrIntake() {
        String selectedRole = (String) roleSelection.getSelectedItem();
        if (selectedRole.equals("Student")) {
            return studentIntakeComboBox != null ? studentIntakeComboBox.getSelectedItem().toString() : "";
        } else if (selectedRole.equals("Lecturer")) {
            return lecturerDepartmentComboBox != null ? lecturerDepartmentComboBox.getSelectedItem().toString() : "";
        }
        return "";
    }
}