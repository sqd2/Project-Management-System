package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import entities.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ViewUsersGUI extends JPanel {
    private JComboBox<String> roleComboBox;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private UserTable userTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public ViewUsersGUI() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(43, 43, 43));

        /*Role Selection, Search, Filter*/
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(43, 43, 43));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"Student", "Lecturer", "Admin"});
        roleComboBox.setBackground(new Color(60, 63, 65));
        roleComboBox.setForeground(Color.WHITE);
        gbc.gridx = 1;
        topPanel.add(roleComboBox, gbc);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        gbc.gridx = 2;
        topPanel.add(searchLabel, gbc);

        searchField = new JTextField(15);
        searchField.setBackground(new Color(60, 63, 65));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        gbc.gridx = 3;
        topPanel.add(searchField, gbc);

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setForeground(Color.WHITE);
        gbc.gridx = 4;
        topPanel.add(filterLabel, gbc);

        filterComboBox = new JComboBox<>();
        filterComboBox.setBackground(new Color(60, 63, 65));
        filterComboBox.setForeground(Color.WHITE);
        gbc.gridx = 5;
        topPanel.add(filterComboBox, gbc);

        add(topPanel, BorderLayout.NORTH);

        // User Table
        String[] initialColumnNames = {"ID", "Name", "Email", "Program", "Intake"};
        userTable = new UserTable(initialColumnNames, List.of());
        tableModel = (DefaultTableModel) userTable.getTable().getModel();
        sorter = new TableRowSorter<>(tableModel);
        userTable.getTable().setRowSorter(sorter);

        // Apply dark theme to table
        userTable.getTable().setBackground(new Color(60, 63, 65));
        userTable.getTable().setForeground(Color.WHITE);
        userTable.getTable().setSelectionBackground(new Color(75, 110, 175));
        userTable.getTable().setSelectionForeground(Color.WHITE);
        userTable.getTable().setGridColor(new Color(43, 43, 43));

        // filling it with student data first
        createUserTable("Student");
        updateFilterComboBox("Student");

        JScrollPane scrollPane = new JScrollPane(userTable.getTable());
        scrollPane.getViewport().setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        roleComboBox.addActionListener(this::populateUserTableAndFilter);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });

        filterComboBox.addActionListener(e -> filter());

        setVisible(true);
    }

    private void populateUserTableAndFilter(ActionEvent e) {
        String role = (String) roleComboBox.getSelectedItem();

        // Update the table
        tableModel.setRowCount(0); // Clear existing data
        sorter = new TableRowSorter<>(tableModel);
        userTable.getTable().setRowSorter(sorter);
        // display users based on role
        loadUsersFromFile(role.toLowerCase() + "s.txt");

        // Update column names based on role
        String[] columnNames = {"ID", "Name", "Email"}; // Default for Admin
        if (role.equals("Student")) {
            columnNames = new String[]{"ID", "Name", "Email", "Program", "Intake"};
        } else if (role.equals("Lecturer")) {
            columnNames = new String[]{"ID", "Name", "Email", "Department", "Role"};
        }
        tableModel.setColumnIdentifiers(columnNames);

        updateFilterComboBox(role);
        filterComboBox.setSelectedIndex(0); // Reset filter to "All"
        tableModel.fireTableDataChanged();
        userTable.getTable().revalidate();
        userTable.getTable().repaint();
    }

    // Method to load users from a specified file
    private void loadUsersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/userdata/" + filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (filename.equals("students.txt")) {
                    tableModel.addRow(new Object[]{data[0], data[1], data[3], data[4], data[5]}); // Adjusted column order
                } else if (filename.equals("lecturers.txt")) {
                    tableModel.addRow(new Object[]{data[0], data[1], data[3], data[4], data[5]});
                } else if (filename.equals("admins.txt")) {
                    tableModel.addRow(new Object[]{data[0], data[1], data[3]});
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());

        }
    }

    // Updates filter options based on the selected role
    private void updateFilterComboBox(String role) {
        filterComboBox.removeAllItems();
        filterComboBox.addItem("All");
        if (role.equals("Student")) {
            for (Program program : Program.values()) {
                filterComboBox.addItem(program.toString());
            }
        } else if (role.equals("Lecturer")) {
            for (Department dept : Department.values()) {
                filterComboBox.addItem(dept.toString());
            }
        }
    }

    // Filter the user table based on search and filter criteria
    private void filter() {
        String searchText = searchField.getText();
        String filterValue = (String) filterComboBox.getSelectedItem();
        if (filterValue == null) {
            filterValue = "All";
        }

        sorter.setRowFilter(null);

        if (!searchText.trim().isEmpty() || !filterValue.equals("All")) {
            List<RowFilter<DefaultTableModel, Integer>> filters = new ArrayList<>();
            RowFilter<DefaultTableModel, Integer> searchFilter = RowFilter.regexFilter("(?i)" + searchText, 0, 1); // Search in ID and name
            filters.add(searchFilter);

            if (!filterValue.equals("All")) {
                int filterColumnIndex =
                        (roleComboBox.getSelectedItem().equals("Student")) ? 3 : // Program column for Student
                                (roleComboBox.getSelectedItem().equals("Lecturer")) ? 3 : -1; // dept column for Lecturer, -1 for Admin

                if (filterColumnIndex != -1) {
                    RowFilter<DefaultTableModel, Integer> roleFilter;
                    if (roleComboBox.getSelectedItem().equals("Lecturer")) {
                        Department departmentFilter = Department.fromString(filterValue);
                        String enumFilterValue = (departmentFilter != null) ? departmentFilter.name() : "";
                        roleFilter = RowFilter.regexFilter(enumFilterValue, filterColumnIndex);
                    } else { // Student
                        roleFilter = RowFilter.regexFilter(filterValue, filterColumnIndex);
                    }
                    filters.add(roleFilter);
                }
            }

            RowFilter<DefaultTableModel, Integer> combinedFilter = RowFilter.andFilter(filters);
            sorter.setRowFilter(combinedFilter);
        }
    }

    private UserTable createUserTable(String role) {
        String[] columnNames = {"ID", "Name", "Email"};
        if (role.equals("Student")) {
            columnNames = new String[]{"ID", "Name", "Email", "Program", "Intake"};
        } else if (role.equals("Lecturer")) {
            columnNames = new String[]{"ID", "Name", "Email", "Department", "Role"};
        }
        return new UserTable(columnNames, List.of());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("View Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.add(new ViewUsersGUI());
        frame.setVisible(true);
    }
}