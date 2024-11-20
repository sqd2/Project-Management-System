package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import entities.Lecturer;
import entities.Student;
import entities.Admin;
import entities.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTable extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public UserTable(String[] columnNames, List<? extends User> userData) { 
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        populateTable(userData); // Populate initially
    }

    public void populateTable(List<? extends User> userData) {
        tableModel.setRowCount(0); // Clear existing data

        for (User user : userData) {
            if (user instanceof Lecturer) {
                Lecturer lecturer = (Lecturer) user;
                tableModel.addRow(new Object[]{lecturer.getID(), lecturer.getName(), lecturer.getDepartment(), lecturer.getLecturerRole()});
            } else if (user instanceof Student) {
                Student student = (Student) user;
                tableModel.addRow(new Object[]{student.getID(), student.getName(), student.getProgram(), student.getIntake()});
            } else if (user instanceof Admin) {
                Admin admin = (Admin) user;
                tableModel.addRow(new Object[]{admin.getID(), admin.getName(), admin.getEmail()});
            }
        }
    }

    // Filtering Method
      public void filter(String searchText, String roleFilter, int filterColumnIndex) {
        RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchText);

        if (filterColumnIndex != -1 && !roleFilter.equals("All")) {
            rf = RowFilter.andFilter(Arrays.asList(rf, new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    String value = entry.getStringValue(filterColumnIndex); 
                    return value.equalsIgnoreCase(roleFilter);
                }
            }));
        }
        sorter.setRowFilter(rf);
    }

    // Sorting Method
    public void sortByColumn(int columnIndex, SortOrder order) {
        sorter.toggleSortOrder(columnIndex); // Toggles between ascending and descending
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(columnIndex, order));
        sorter.setSortKeys(sortKeys);
    }

    public JTable getTable() {
        return table;
    }
}
