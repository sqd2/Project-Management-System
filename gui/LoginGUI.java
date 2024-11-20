package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526
import adminmain.Login;
import studentmain.StudentMenu;
import entities.Lecturer;
import entities.LecturerRole;
import lecturermain.LecturerResponsibilities;
import projectmanagermain.ProjectManager;
import projectmanagermain.ProjectManagerGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    private JButton loginButton;
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JLabel idLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    private JLabel statusLabel;
    private Login loginManager; // Persistent instance of Login

    public LoginGUI() {
        super("Login");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        

        // Initialize loginManager
        loginManager = new Login();

        // Set background color
        getContentPane().setBackground(new Color(43, 43, 43));

        loginButton = new JButton("Login");
        userIdField = new JTextField(20);
        passwordField = new JPasswordField(20);
        idLabel = new JLabel("Enter your ID:");
        passwordLabel = new JLabel("Enter your password:");
        titleLabel = new JLabel("Login Menu");
        subtitleLabel = new JLabel("Academic Guidance Hub");
        statusLabel = new JLabel();

        // Style components
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Serif", Font.BOLD, 30));
        subtitleLabel.setForeground(new Color(135, 206, 235));
        idLabel.setForeground(Color.WHITE);
        passwordLabel.setForeground(Color.WHITE);
        statusLabel.setForeground(Color.RED);

        loginButton.setBackground(new Color(59, 89, 182));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(subtitleLabel, gbc);

        gbc.gridy = 1;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(loginButton, gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(statusLabel, gbc);

        getContentPane().add(mainPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String id = userIdField.getText();
                char[] passwordChar = passwordField.getPassword();
                String password = new String(passwordChar);

                if (loginManager.hasExceededMaxAttempts()) {
                    statusLabel.setText("Maximum login attempts exceeded. Please try again later.");
                    return;
                }

                boolean isValid = loginManager.authenticate(id, password);
                String role = loginManager.getIDRole(id);

                if (isValid) {
                    passwordField.setText("");
                    dispose();

                    if (role.equalsIgnoreCase("admin")) {
                        new AdminMenuGUI(loginManager).setVisible(true);
                    } else if (role.equalsIgnoreCase("lecturer")) {
                        Lecturer lecturer = loginManager.getCurrentLecturer();
                        LecturerRole lecturerRole = lecturer.getLecturerRole();

                        if (lecturerRole == LecturerRole.LECTURER) {
                            new LecturerResponsibilities();
                        } else if (lecturerRole == LecturerRole.PROJECT_MANAGER) {
                            ProjectManager projectManager = new ProjectManager(lecturer.getID(), lecturer.getName(), lecturer.getPassword(), lecturer.getEmail(), lecturer.getDepartment(), lecturerRole);
                            new ProjectManagerGUI(projectManager).setVisible(true);
                        }
                    } else if (role.equalsIgnoreCase("student")) {
                        StudentMenu studentMenu = new StudentMenu(loginManager.getCurrentStudentId(), loginManager.getCurrentStudentName());
                        studentMenu.displayMenu();
                    }
                } else {
                    statusLabel.setText("Invalid ID or Password entered. Check Again. (" 
                                        + loginManager.getFailedAttempts() + "/" 
                                        + loginManager.getMaxAttempts() + ")");
                }
            }
        });

        pack();
        setLocationRelativeTo(null);  // Center the window after packing
        setVisible(true);  // Make the frame visible after setting location
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}
