package com.employee.util;

import java.awt.Component;
import javax.swing.JOptionPane;

public class DialogUtil {

    /**
     * Show success message dialog.
     */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Show error message dialog.
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Show general info message dialog.
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Information",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Show confirmation dialog (Yes/No). Returns true if yes, false otherwise.
     */
    public static boolean showConfirm(Component parent, String title, String message) {
        int choice = JOptionPane.showConfirmDialog(
            parent,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return choice == JOptionPane.YES_OPTION;
    }
}
