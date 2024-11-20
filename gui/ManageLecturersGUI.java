package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import adminmain.ManageLecturers;
import entities.LecturerRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ManageLecturersGUI extends JPanel {
    private UserTable lecturerTable;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JButton updateButton;
    private ManageLecturers manager = new ManageLecturers();
    public JPanel panel;

    public ManageLecturersGUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(43, 43, 43));

        // Top Panel for Search and Filter
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(43, 43, 43));
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchField = new JTextField(20);
        searchField.setBackground(new Color(60, 63, 65));
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        filterComboBox = new JComboBox<>(new String[]{"All", "Lecturer", "Project Manager"});
        filterComboBox.setBackground(new Color(60, 63, 65));
        filterComboBox.setForeground(Color.WHITE);
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(filterComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Lecturer Table Setup
        String[] columnNames = {"ID", "Name", "Department", "Role"};
        lecturerTable = new UserTable(columnNames, manager.loadLecturers());

        // Apply dark theme to table
        lecturerTable.getTable().setBackground(new Color(60, 63, 65));
        lecturerTable.getTable().setForeground(Color.WHITE);
        lecturerTable.getTable().setSelectionBackground(new Color(75, 110, 175));
        lecturerTable.getTable().setSelectionForeground(Color.WHITE);
        lecturerTable.getTable().setGridColor(new Color(43, 43, 43));

        JScrollPane scrollPane = new JScrollPane(lecturerTable.getTable());
        scrollPane.getViewport().setBackground(new Color(43, 43, 43));
        add(scrollPane, BorderLayout.CENTER);

        // Table Row Sorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) lecturerTable.getTable().getModel());
        lecturerTable.getTable().setRowSorter(sorter);

        // Update Button
        updateButton = new JButton("Update Role");
        updateButton.setBackground(new Color(60, 63, 65));
        updateButton.setForeground(Color.WHITE);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(43, 43, 43));
        bottomPanel.add(updateButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Search Field Listener
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

        // Filter ComboBox Listener
        filterComboBox.addActionListener(e -> filter());

        // Update Button Listener
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = lecturerTable.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    String selectedId = lecturerTable.getTable().getValueAt(selectedRow, 0).toString();
                    LecturerRole currentRole = LecturerRole.valueOf(lecturerTable.getTable().getValueAt(selectedRow, 3).toString());
                    LecturerRole newRole = (currentRole == LecturerRole.LECTURER) ? LecturerRole.PROJECT_MANAGER : LecturerRole.LECTURER;
                    manager.updateLecturerRole(selectedId, newRole);

                    // Update table display
                    lecturerTable.getTable().setValueAt(newRole, selectedRow, 3);
                } else {
                    JOptionPane.showMessageDialog(ManageLecturersGUI.this, "Please select a lecturer.");
                }
            }
        });

        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void filter() {
        RowFilter<DefaultTableModel, Integer> rf = RowFilter.regexFilter("(?i)" + searchField.getText(), 1, 2);  // Case-insensitive search

        String filterRole = (String) filterComboBox.getSelectedItem();
        if (!filterRole.equals("All")) {
            LecturerRole role = LecturerRole.valueOf(filterRole.toUpperCase().replace(" ", "_"));
            rf = RowFilter.andFilter(Arrays.asList(rf, new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    LecturerRole lecturerRole = (LecturerRole) entry.getValue(3);
                    return lecturerRole == role;
                }
            }));
        }

        ((TableRowSorter<DefaultTableModel>) lecturerTable.getTable().getRowSorter()).setRowFilter(rf);
    }


    
}