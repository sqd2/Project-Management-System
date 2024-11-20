package gui;

//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import adminmain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ManageUsersGUI extends JPanel {

    private JComboBox<String> roleComboBox;
    private JTextField idTextField;
    private JTable userInfoTable;
    private JScrollPane scrollPane;
    private JComboBox<String> attributeComboBox;
    private JTextField newValueTextField;
    private JButton searchButton, updateButton, deleteButton;
    private JLabel roleLabel, idLabel, attributeLabel, newValueLabel, titleLabel, statusLabel;
    private ManageUsers userManager;

    public ManageUsersGUI() {
        setLayout(new GridBagLayout());
        setBackground(new Color(43, 43, 43));

        roleComboBox = new JComboBox<>(new String[]{"Admin", "Lecturer", "Student"});
        idTextField = new JTextField(20);
        idTextField.setBackground(new Color(60, 63, 65));
        idTextField.setForeground(Color.WHITE);
        idTextField.setCaretColor(Color.WHITE);

        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(59, 89, 182));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        userInfoTable = new JTable();
        userInfoTable.setBackground(new Color(60, 63, 65));
        userInfoTable.setForeground(Color.WHITE);
        userInfoTable.setGridColor(Color.GRAY);

        scrollPane = new JScrollPane(userInfoTable);
        scrollPane.setPreferredSize(new Dimension(600, 150));

        attributeComboBox = new JComboBox<>();
        attributeComboBox.setBackground(new Color(60, 63, 65));
        attributeComboBox.setForeground(Color.WHITE);

        newValueTextField = new JTextField(20);
        newValueTextField.setBackground(new Color(60, 63, 65));
        newValueTextField.setForeground(Color.WHITE);
        newValueTextField.setCaretColor(Color.WHITE);

        updateButton = new JButton("Update");
        updateButton.setBackground(new Color(59, 89, 182));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(59, 89, 182));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 12));

        roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.WHITE);

        idLabel = new JLabel("User ID:");
        idLabel.setForeground(Color.WHITE);

        attributeLabel = new JLabel("Attribute:");
        attributeLabel.setForeground(Color.WHITE);

        newValueLabel = new JLabel("New Value:");
        newValueLabel.setForeground(Color.WHITE);

        titleLabel = new JLabel("Manage Users");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.RED);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(roleLabel, gbc);
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(idLabel, gbc);
        gbc.gridx = 1;
        add(idTextField, gbc);
        gbc.gridx = 2;
        add(searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(attributeLabel, gbc);
        gbc.gridx = 1;
        add(attributeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(newValueLabel, gbc);
        gbc.gridx = 1;
        add(newValueTextField, gbc);
        gbc.gridx = 2;
        add(updateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        add(statusLabel, gbc);

        gbc.gridy = 7;
        add(deleteButton, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                userManager = new ManageUsers(role);
                String id = idTextField.getText();
                try {
                    String userInfo = userManager.findUserById(id);
                    if (userInfo != null) {
                        populateUserInfoTable(userInfo, role);
                        populateAttributeComboBox(role);
                    } else {
                        clearUserInfoTable();
                        statusLabel.setText("User not found.");
                        attributeComboBox.removeAllItems(); // Clear attributes
                    }
                } catch (IOException ex) {
                    clearUserInfoTable();
                    statusLabel.setText("Error reading file.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = idTextField.getText();
                    String attribute = (String) attributeComboBox.getSelectedItem();
                    String newValue = newValueTextField.getText();
                    
                    // Input Validation
                    if (attribute.equals("Name")) {
                        String role = (String) roleComboBox.getSelectedItem();
                        if (!Validator.validateUnique(newValue, "src/userdata/" + role.toLowerCase() + "s.txt")) {
                            JOptionPane.showMessageDialog(ManageUsersGUI.this, "Name is already taken!");
                            return; 
                        }
                    } else if (attribute.equals("Password")) {
                        if (!Validator.validatePassword(newValue)) {
                            JOptionPane.showMessageDialog(ManageUsersGUI.this, 
                                "Password must be at least 8 characters long and contain:\n" +
                                "- At least one uppercase letter\n" +
                                "- At least one lowercase letter\n" +
                                "- At least one digit\n" +
                                "- At least one special character (@#$%^&+=!)");
                            return;
                        }
                    } else if (attribute.equals("Email")) {
                        if (!Validator.validateEmail(newValue)) {
                            JOptionPane.showMessageDialog(ManageUsersGUI.this, "Invalid email format!");
                            return;
                        }
                    }

                    int attributeIndex = attributeComboBox.getSelectedIndex() + 1; // ID is at index 0
                    if (userManager.updateUserAttribute(id, attributeIndex, newValue)) {
                        populateUserInfoTable(userManager.findUserById(id), (String) roleComboBox.getSelectedItem()); // Show updated info
                    } else {
                        statusLabel.setText("Failed to update.");
                    }
                } catch (IOException ex) {
                    statusLabel.setText("Error updating file.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                userManager = new ManageUsers(role);
                String id = idTextField.getText();

                try {
                    if (userManager.deleteUserById(id)) {
                        clearUserInfoTable();
                        statusLabel.setText("User deleted successfully.");
                        attributeComboBox.removeAllItems(); // Clear attributes
                        newValueTextField.setText(""); // Clear new value field
                    } else {
                        statusLabel.setText("User not found.");
                    }
                } catch (IOException ex) {
                    statusLabel.setText("Error deleting user: " + ex.getMessage());
                }
            }
        });
    }

    private void populateAttributeComboBox(String role) {
        attributeComboBox.removeAllItems();
        if (role.equals("Admin")) {
            attributeComboBox.addItem("Name");
            attributeComboBox.addItem("Password");
            attributeComboBox.addItem("Email");
        } else if (role.equals("Lecturer")) {
            attributeComboBox.addItem("Name");
            attributeComboBox.addItem("Password");
            attributeComboBox.addItem("Email");
            attributeComboBox.addItem("Department");
            attributeComboBox.addItem("Lecturer Role");
        } else if (role.equals("Student")) {
            attributeComboBox.addItem("Name");
            attributeComboBox.addItem("Password");
            attributeComboBox.addItem("Email");
            attributeComboBox.addItem("Program");
            attributeComboBox.addItem("Intake");
        }
    }

    private void populateUserInfoTable(String userInfo, String role) {
        String[] columns = getColumnNames(role);
        String[][] data = {userInfo.split(",")};
        userInfoTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
    }

    private void clearUserInfoTable() {
        userInfoTable.setModel(new javax.swing.table.DefaultTableModel());
    }

    private String[] getColumnNames(String role) {
        if (role.equals("Admin")) {
            return new String[]{"ID", "Name", "Password", "Email"};
        } else if (role.equals("Lecturer")) {
            return new String[]{"ID", "Name", "Password", "Email", "Department", "Lecturer Role"};
        } else if (role.equals("Student")) {
            return new String[]{"ID", "Name", "Password", "Email", "Program", "Intake"};
        }
        return new String[]{};
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(new ManageUsersGUI());
        frame.pack();
        frame.setVisible(true);
    }
}