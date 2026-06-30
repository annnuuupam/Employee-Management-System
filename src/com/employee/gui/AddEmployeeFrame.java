package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.util.Constants;
import com.employee.util.DateUtil;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;
import com.employee.util.ValidationUtil;

import java.awt.Color;
import java.awt.Font;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddEmployeeFrame extends JFrame {

    private DashboardFrame parentDashboard;
    private EmployeeDAO employeeDAO;

    // Fields
    private JTextField txtId;
    private JTextField txtName;
    private JComboBox<String> comboDept;
    private JComboBox<String> comboDesignation;
    private JTextField txtSalary;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JComboBox<String> comboGender;
    private JTextArea txtAddress;
    private JTextField txtDoj;

    public AddEmployeeFrame(DashboardFrame parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.employeeDAO = new EmployeeDAO();

        setTitle("EMS - Add Employee");
        setSize(800, 600);
        setLocationRelativeTo(parentDashboard);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(Constants.COLOR_BACKGROUND);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // ================= HEADER ====================
        JPanel header = new JPanel();
        header.setBackground(Constants.COLOR_PRIMARY);
        header.setBounds(0, 0, 800, 70);
        header.setLayout(null);
        contentPane.add(header);

        JLabel title = new JLabel("ADD NEW EMPLOYEE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(30, 20, 400, 30);
        header.add(title);

        // ================= FORM CARD ====================
        JPanel formCard = new JPanel();
        formCard.setBackground(Constants.COLOR_CARD);
        formCard.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        formCard.setLayout(null);
        formCard.setBounds(40, 90, 720, 390);
        contentPane.add(formCard);

        // Left Column Labels and Inputs
        JLabel lblId = new JLabel("Employee ID *");
        lblId.setFont(Constants.FONT_LABEL);
        lblId.setBounds(40, 30, 120, 25);
        formCard.add(lblId);

        txtId = new JTextField();
        UIUtil.styleTextField(txtId);
        txtId.setBounds(170, 30, 170, 30);
        formCard.add(txtId);

        JLabel lblName = new JLabel("Full Name *");
        lblName.setFont(Constants.FONT_LABEL);
        lblName.setBounds(40, 80, 120, 25);
        formCard.add(lblName);

        txtName = new JTextField();
        UIUtil.styleTextField(txtName);
        txtName.setBounds(170, 80, 170, 30);
        formCard.add(txtName);

        JLabel lblDept = new JLabel("Department *");
        lblDept.setFont(Constants.FONT_LABEL);
        lblDept.setBounds(40, 130, 120, 25);
        formCard.add(lblDept);

        String[] depts = {"IT", "HR", "Finance", "Sales", "Marketing", "Operations", "R&D", "Support"};
        comboDept = new JComboBox<>(depts);
        comboDept.setFont(Constants.FONT_FIELD);
        comboDept.setBounds(170, 130, 170, 30);
        formCard.add(comboDept);

        JLabel lblDesignation = new JLabel("Designation *");
        lblDesignation.setFont(Constants.FONT_LABEL);
        lblDesignation.setBounds(40, 180, 120, 25);
        formCard.add(lblDesignation);

        String[] designations = {
            "Intern", "Software Engineer Intern", "Software Engineer", "Senior Developer",
            "Tech Lead", "QA Engineer", "DevOps Engineer", "UI/UX Designer",
            "Product Manager", "Project Manager", "Director of Engineering", "Vice President (VP)",
            "HR Intern", "HR Specialist", "HR Manager", "Financial Intern",
            "Financial Analyst", "Accountant", "Sales Representative", "Sales Executive",
            "Sales Manager", "Marketing Intern", "Marketing Executive", "Operations Associate",
            "Operations Lead", "Customer Support Executive", "Tech Support Engineer"
        };
        comboDesignation = new JComboBox<>(designations);
        comboDesignation.setFont(Constants.FONT_FIELD);
        comboDesignation.setBounds(170, 180, 170, 30);
        formCard.add(comboDesignation);

        JLabel lblSalary = new JLabel("Salary (₹) *");
        lblSalary.setFont(Constants.FONT_LABEL);
        lblSalary.setBounds(40, 230, 120, 25);
        formCard.add(lblSalary);

        txtSalary = new JTextField();
        UIUtil.styleTextField(txtSalary);
        txtSalary.setBounds(170, 230, 170, 30);
        formCard.add(txtSalary);

        // Right Column Labels and Inputs
        JLabel lblPhone = new JLabel("Phone Number *");
        lblPhone.setFont(Constants.FONT_LABEL);
        lblPhone.setBounds(380, 30, 130, 25);
        formCard.add(lblPhone);

        txtPhone = new JTextField();
        UIUtil.styleTextField(txtPhone);
        txtPhone.setBounds(520, 30, 160, 30);
        formCard.add(txtPhone);

        JLabel lblEmail = new JLabel("Email Address *");
        lblEmail.setFont(Constants.FONT_LABEL);
        lblEmail.setBounds(380, 80, 130, 25);
        formCard.add(lblEmail);

        txtEmail = new JTextField();
        UIUtil.styleTextField(txtEmail);
        txtEmail.setBounds(520, 80, 160, 30);
        formCard.add(txtEmail);

        JLabel lblGender = new JLabel("Gender *");
        lblGender.setFont(Constants.FONT_LABEL);
        lblGender.setBounds(380, 130, 130, 25);
        formCard.add(lblGender);

        String[] genders = {"Male", "Female", "Other"};
        comboGender = new JComboBox<>(genders);
        comboGender.setFont(Constants.FONT_FIELD);
        comboGender.setBounds(520, 130, 160, 30);
        formCard.add(comboGender);

        JLabel lblDoj = new JLabel("Date of Join *");
        lblDoj.setFont(Constants.FONT_LABEL);
        lblDoj.setBounds(380, 180, 130, 25);
        formCard.add(lblDoj);

        txtDoj = new JTextField();
        UIUtil.styleTextField(txtDoj);
        txtDoj.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        txtDoj.setBounds(520, 180, 160, 30);
        formCard.add(txtDoj);

        JLabel lblAddress = new JLabel("Address *");
        lblAddress.setFont(Constants.FONT_LABEL);
        lblAddress.setBounds(380, 230, 130, 25);
        formCard.add(lblAddress);

        txtAddress = new JTextArea();
        txtAddress.setFont(Constants.FONT_FIELD);
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        txtAddress.setBorder(BorderFactory.createLineBorder(Constants.COLOR_BORDER, 1));
        JScrollPane addressScroll = new JScrollPane(txtAddress);
        addressScroll.setBounds(520, 230, 160, 110);
        formCard.add(addressScroll);

        // ================= BUTTONS ====================
        JButton btnSave = new JButton("Save");
        UIUtil.styleButton(btnSave, Constants.COLOR_BUTTON_SUCCESS, Color.WHITE);
        btnSave.setBounds(200, 500, 110, 40);
        contentPane.add(btnSave);

        JButton btnReset = new JButton("Reset");
        UIUtil.styleButton(btnReset, Constants.COLOR_BUTTON_SECONDARY, Color.WHITE);
        btnReset.setBounds(345, 500, 110, 40);
        contentPane.add(btnReset);

        JButton btnBack = new JButton("Back");
        UIUtil.styleButton(btnBack, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnBack.setBounds(490, 500, 110, 40);
        contentPane.add(btnBack);

        // ================= EVENTS ====================
        btnBack.addActionListener(e -> dispose());

        btnReset.addActionListener(e -> resetFields());

        btnSave.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String dept = comboDept.getSelectedItem().toString();
            String designation = comboDesignation.getSelectedItem().toString();
            String salaryStr = txtSalary.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String gender = comboGender.getSelectedItem().toString();
            String dojStr = txtDoj.getText().trim();
            String address = txtAddress.getText().trim();

            // 1. Validation
            if (ValidationUtil.isEmpty(id) || ValidationUtil.isEmpty(name) || ValidationUtil.isEmpty(designation)
                || ValidationUtil.isEmpty(salaryStr) || ValidationUtil.isEmpty(phone) || ValidationUtil.isEmpty(email)
                || ValidationUtil.isEmpty(dojStr) || ValidationUtil.isEmpty(address)) {
                DialogUtil.showError(this, "All fields are required.");
                return;
            }

            if (!ValidationUtil.isValidName(name)) {
                DialogUtil.showError(this, "Invalid name format (Letters and spaces only).");
                return;
            }

            if (!ValidationUtil.isValidSalary(salaryStr)) {
                DialogUtil.showError(this, "Salary must be a positive numeric value.");
                return;
            }

            if (!ValidationUtil.isValidPhone(phone)) {
                DialogUtil.showError(this, "Phone number must be exactly 10 digits.");
                return;
            }

            if (!ValidationUtil.isValidEmail(email)) {
                DialogUtil.showError(this, "Invalid email address format.");
                return;
            }

            if (!DateUtil.isValidDate(dojStr)) {
                DialogUtil.showError(this, "Invalid Date format. Use yyyy-MM-dd.");
                return;
            }

            // Check if employee ID already exists
            if (employeeDAO.getEmployee(id) != null) {
                DialogUtil.showError(this, "Employee ID already exists. Please choose a unique ID.");
                return;
            }

            try {
                double salary = Double.parseDouble(salaryStr);
                Date dateOfJoining = DateUtil.parseDate(dojStr);

                // Create Employee object
                Employee newEmp = new Employee(id, name, dept, designation, salary, phone, email, gender, address, dateOfJoining);

                // Save to MySQL
                boolean isSaved = employeeDAO.addEmployee(newEmp);

                if (isSaved) {
                    DialogUtil.showSuccess(this, "Employee added successfully!");
                    // Refresh stats and table on dashboard
                    if (parentDashboard != null) {
                        parentDashboard.refreshData();
                    }
                    dispose();
                } else {
                    DialogUtil.showError(this, "Could not insert record into Database.");
                }
            } catch (Exception ex) {
                DialogUtil.showError(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void resetFields() {
        txtId.setText("");
        txtName.setText("");
        comboDept.setSelectedIndex(0);
        comboDesignation.setSelectedIndex(0);
        txtSalary.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        comboGender.setSelectedIndex(0);
        txtAddress.setText("");
        txtDoj.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
    }
}
