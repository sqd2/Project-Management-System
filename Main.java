//SALEM AHMED ABDULLAH BA SUHAI - TP073526

import javax.swing.SwingUtilities;
import gui.LoginGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setLocationRelativeTo(null));
    }
}
