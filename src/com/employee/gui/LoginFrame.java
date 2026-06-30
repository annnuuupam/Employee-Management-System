package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.util.Constants;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class LoginFrame extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private EmployeeDAO employeeDAO;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginFrame() {
        employeeDAO = new EmployeeDAO();

        setTitle("EMS - System Login");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBackground(Constants.COLOR_BACKGROUND);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ================= HEADER ====================
        JPanel header = new JPanel();
        header.setBackground(Constants.COLOR_PRIMARY);
        header.setBounds(0, 0, 750, 80);
        header.setLayout(null);
        contentPane.add(header);

        JLabel title = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBounds(0, 20, 750, 40);
        header.add(title);

        // ================= LOGIN PANEL (CARD) ====================
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Constants.COLOR_CARD);
        loginPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        loginPanel.setLayout(null);
        loginPanel.setBounds(140, 110, 470, 290);
        contentPane.add(loginPanel);

        JLabel loginTitle = new JLabel("Login to Admin Dashboard");
        loginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginTitle.setForeground(Constants.COLOR_PRIMARY);
        loginTitle.setBounds(50, 20, 370, 30);
        loginPanel.add(loginTitle);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(Constants.FONT_LABEL);
        lblUser.setForeground(Constants.COLOR_TEXT_DARK);
        lblUser.setBounds(50, 70, 100, 30);
        loginPanel.add(lblUser);

        txtUsername = new JTextField();
        UIUtil.styleTextField(txtUsername);
        txtUsername.setBounds(150, 70, 270, 35);
        loginPanel.add(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(Constants.FONT_LABEL);
        lblPass.setForeground(Constants.COLOR_TEXT_DARK);
        lblPass.setBounds(50, 130, 100, 30);
        loginPanel.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setFont(Constants.FONT_FIELD);
        txtPassword.setBorder(txtUsername.getBorder());
        txtPassword.setBounds(150, 130, 270, 35);
        // Apply focus border matching text field
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constants.COLOR_PRIMARY, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)
                ));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constants.COLOR_BORDER, 1),
                    BorderFactory.createEmptyBorder(5, 7, 5, 7)
                ));
            }
        });
        loginPanel.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        UIUtil.styleButton(btnLogin, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnLogin.setBounds(50, 200, 110, 40);
        loginPanel.add(btnLogin);

        JButton btnClear = new JButton("Clear");
        UIUtil.styleButton(btnClear, Constants.COLOR_BUTTON_SECONDARY, Color.WHITE);
        btnClear.setBounds(180, 200, 110, 40);
        loginPanel.add(btnClear);

        JButton btnExit = new JButton("Exit");
        UIUtil.styleButton(btnExit, Constants.COLOR_BUTTON_DANGER, Color.WHITE);
        btnExit.setBounds(310, 200, 110, 40);
        loginPanel.add(btnExit);

        JLabel lblFooter = new JLabel("© 2026 Employee Management System. All Rights Reserved.");
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setForeground(Constants.COLOR_TEXT_MUTED);
        lblFooter.setFont(Constants.FONT_STATUS);
        lblFooter.setBounds(0, 425, 750, 20);
        contentPane.add(lblFooter);

        // ================= BUTTON EVENTS ====================
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
        });

        btnExit.addActionListener(e -> System.exit(0));

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());

            if (username.trim().isEmpty() || password.isEmpty()) {
                DialogUtil.showError(this, "Please enter both Username and Password.");
                return;
            }

            // Authenticate against database
            boolean success = employeeDAO.authenticate(username, password);

            if (success) {
                DialogUtil.showSuccess(this, "Login Successful! Welcome Admin.");
                // Open Dashboard
                DashboardFrame dashboard = new DashboardFrame();
                dashboard.setVisible(true);
                dispose(); // Close login frame
            } else {
                DialogUtil.showError(this, "Invalid Username or Password.");
            }
        });
    }
}