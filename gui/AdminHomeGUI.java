
package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomeGUI extends JPanel {
    private JLabel welcomeLabel;
    private JButton logoutButton;

    public AdminHomeGUI(String adminId, String adminName) {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        
        // Welcome message
        welcomeLabel = new JLabel("<html>Welcome, Admin " + adminName + " (ID: " + adminId + ")</html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Customize font
        welcomeLabel.setForeground(Color.WHITE);
        add(welcomeLabel, BorderLayout.CENTER);

        // Logout button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(45, 45, 45));
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.getWindowAncestor(AdminHomeGUI.this).dispose(); // Close AdminMenuGUI
                new LoginGUI().setVisible(true); // Open LoginGUI
            }
        });
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

