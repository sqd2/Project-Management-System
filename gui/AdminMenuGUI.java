package gui;
//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import javax.swing.*;

import adminmain.Login;

import java.awt.*;

public class AdminMenuGUI extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel; 
    private Login loginManager; 
    private AdminHomeGUI adminHomeGUI; 
    
    public AdminMenuGUI(Login loginManager) {
        super("Admin Menu");
        this.loginManager = loginManager;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(Color.DARK_GRAY);
        
        cardPanel.add(new RegistrationGUI(), "Registration");
        cardPanel.add(new ManageUsersGUI(), "Manage Users"); 
        cardPanel.add(new ManageLecturersGUI(), "Manage Lecturers"); 
        cardPanel.add(new ViewUsersGUI(), "View Users");
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.DARK_GRAY);
        createButton("Home", "Admin Home");
        createButton("Registration", "Registration");
        createButton("Manage Users", "Manage Users");
        createButton("Manage Lecturers", "Manage Lecturers");
        createButton("View Users", "View Users");
        
        // Add panels to frame
        add(buttonPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        showAdminHome();
    }

    // Helper method to create buttons
    private void createButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(45, 45, 45));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        buttonPanel.add(button); 
    }

    private void showAdminHome() {
        adminHomeGUI = new AdminHomeGUI(loginManager.getCurrentAdminId(), loginManager.getCurrentAdminName());
        cardPanel.add(adminHomeGUI, "Admin Home");
        cardLayout.show(cardPanel, "Admin Home"); // Show by default after creation
    }
}
