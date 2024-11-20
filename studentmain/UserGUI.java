package studentmain;
//TP070810
import javax.swing.*;

public abstract class UserGUI {
    protected UserFunctions userFunctions;

    public UserGUI(UserFunctions userFunctions) {
        this.userFunctions = userFunctions;
    }

    protected void showError(JFrame parentFrame, String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    protected void showSuccess(JFrame parentFrame, String message) {
        JOptionPane.showMessageDialog(parentFrame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public abstract void submitReportDetails(JFrame parentFrame);
    public abstract void editReportDetails(JFrame parentFrame);
    public abstract void deleteReportDetails(JFrame parentFrame);
    public abstract void requestPresentationDate(JFrame parentFrame);
    public abstract void checkSubmissionStatus(JFrame parentFrame);
}
