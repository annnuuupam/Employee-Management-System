package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.util.Constants;
import com.employee.util.DateUtil;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeleteEmployeeFrame extends JFrame {

    private DashboardFrame parentDashboard;
    private EmployeeDAO employeeDAO;

    // Search bar UI
    private javax.swing.JTextField txtSearchId;
    private JButton btnSearch;

    // Detail Panel Labels (read-only labels)
    private JLabel valName;
    private JLabel valDept;
    private JLabel valDesignation;
    private JLabel valSalary;
    private JLabel valPhone;
    private JLabel valEmail;
    private JLabel valGender;
    private JLabel valDoj;
    private JLabel valAddress;

    private JButton btnDelete;

    public DeleteEmployeeFrame(DashboardFrame parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.employeeDAO = new EmployeeDAO();

        setTitle("EMS - Delete Employee");
        setSize(800, 560);
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

        JLabel title = new JLabel("DELETE EMPLOYEE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(30, 20, 400, 30);
        header.add(title);

        // ================= SEARCH PANEL ====================
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(Constants.COLOR_CARD);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        searchPanel.setLayout(null);
        searchPanel.setBounds(40, 85, 720, 60);
        contentPane.add(searchPanel);

        JLabel lblSearch = new JLabel("Enter Employee ID:");
        lblSearch.setFont(Constants.FONT_LABEL);
        lblSearch.setBounds(20, 18, 140, 25);
        searchPanel.add(lblSearch);

        txtSearchId = new javax.swing.JTextField();
        UIUtil.styleTextField(txtSearchId);
        txtSearchId.setBounds(160, 15, 200, 30);
        searchPanel.add(txtSearchId);

        btnSearch = new JButton("Search");
        UIUtil.styleButton(btnSearch, Constants.COLOR_PRIMARY, Color.WHITE);
        btnSearch.setBounds(380, 12, 110, 35);
        searchPanel.add(btnSearch);

        // ================= DETAILS DISPLAY CARD ====================
        JPanel detailCard = new JPanel();
        detailCard.setBackground(Constants.COLOR_CARD);
        detailCard.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        detailCard.setLayout(null);
        detailCard.setBounds(40, 160, 720, 330);
        contentPane.add(detailCard);

        int lblX = 40, valX = 180, width = 130, height = 25;
        int lblX2 = 380, valX2 = 520;

        // Row 1
        JLabel lblName = new JLabel("Full Name:");
        lblName.setFont(Constants.FONT_LABEL);
        lblName.setBounds(lblX, 30, width, height);
        detailCard.add(lblName);

        valName = new JLabel("-");
        valName.setFont(Constants.FONT_FIELD);
        valName.setBounds(valX, 30, 180, height);
        detailCard.add(valName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setFont(Constants.FONT_LABEL);
        lblPhone.setBounds(lblX2, 30, width, height);
        detailCard.add(lblPhone);

        valPhone = new JLabel("-");
        valPhone.setFont(Constants.FONT_FIELD);
        valPhone.setBounds(valX2, 30, 180, height);
        detailCard.add(valPhone);

        // Row 2
        JLabel lblDept = new JLabel("Department:");
        lblDept.setFont(Constants.FONT_LABEL);
        lblDept.setBounds(lblX, 70, width, height);
        detailCard.add(lblDept);

        valDept = new JLabel("-");
        valDept.setFont(Constants.FONT_FIELD);
        valDept.setBounds(valX, 70, 180, height);
        detailCard.add(valDept);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(Constants.FONT_LABEL);
        lblEmail.setBounds(lblX2, 70, width, height);
        detailCard.add(lblEmail);

        valEmail = new JLabel("-");
        valEmail.setFont(Constants.FONT_FIELD);
        valEmail.setBounds(valX2, 70, 180, height);
        detailCard.add(valEmail);

        // Row 3
        JLabel lblDesig = new JLabel("Designation:");
        lblDesig.setFont(Constants.FONT_LABEL);
        lblDesig.setBounds(lblX, 110, width, height);
        detailCard.add(lblDesig);

        valDesignation = new JLabel("-");
        valDesignation.setFont(Constants.FONT_FIELD);
        valDesignation.setBounds(valX, 110, 180, height);
        detailCard.add(valDesignation);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(Constants.FONT_LABEL);
        lblGender.setBounds(lblX2, 110, width, height);
        detailCard.add(lblGender);

        valGender = new JLabel("-");
        valGender.setFont(Constants.FONT_FIELD);
        valGender.setBounds(valX2, 110, 180, height);
        detailCard.add(valGender);

        // Row 4
        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setFont(Constants.FONT_LABEL);
        lblSalary.setBounds(lblX, 150, width, height);
        detailCard.add(lblSalary);

        valSalary = new JLabel("-");
        valSalary.setFont(Constants.FONT_FIELD);
        valSalary.setBounds(valX, 150, 180, height);
        detailCard.add(valSalary);

        JLabel lblDoj = new JLabel("Date of Join:");
        lblDoj.setFont(Constants.FONT_LABEL);
        lblDoj.setBounds(lblX2, 150, width, height);
        detailCard.add(lblDoj);

        valDoj = new JLabel("-");
        valDoj.setFont(Constants.FONT_FIELD);
        valDoj.setBounds(valX2, 150, 180, height);
        detailCard.add(valDoj);

        // Row 5
        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(Constants.FONT_LABEL);
        lblAddress.setBounds(lblX, 190, width, height);
        detailCard.add(lblAddress);

        valAddress = new JLabel("-");
        valAddress.setFont(Constants.FONT_FIELD);
        valAddress.setBounds(valX, 190, 500, height);
        detailCard.add(valAddress);

        // Bottom Action buttons inside card
        btnDelete = new JButton("Delete Employee");
        UIUtil.styleButton(btnDelete, Constants.COLOR_BUTTON_DANGER, Color.WHITE);
        btnDelete.setBounds(230, 260, 150, 40);
        btnDelete.setEnabled(false);
        detailCard.add(btnDelete);

        JButton btnBack = new JButton("Back");
        UIUtil.styleButton(btnBack, Constants.COLOR_PRIMARY, Color.WHITE);
        btnBack.setBounds(400, 260, 110, 40);
        detailCard.add(btnBack);

        // ================= EVENTS ====================
        btnBack.addActionListener(e -> dispose());

        btnSearch.addActionListener(e -> {
            String searchId = txtSearchId.getText().trim();
            if (searchId.isEmpty()) {
                DialogUtil.showError(this, "Please enter an Employee ID to search.");
                return;
            }
            fetchEmployeeDetails(searchId);
        });

        btnDelete.addActionListener(e -> {
            String id = txtSearchId.getText().trim();
            
            boolean confirm = DialogUtil.showConfirm(
                this,
                "Confirm Deletion",
                "Are you sure you want to permanently delete Employee with ID: " + id + "?"
            );

            if (confirm) {
                boolean success = employeeDAO.deleteEmployee(id);
                if (success) {
                    DialogUtil.showSuccess(this, "Employee record deleted successfully.");
                    if (parentDashboard != null) {
                        parentDashboard.refreshData();
                    }
                    dispose();
                } else {
                    DialogUtil.showError(this, "Failed to delete database record.");
                }
            }
        });
    }

    private void fetchEmployeeDetails(String empId) {
        Employee emp = employeeDAO.getEmployee(empId);
        if (emp != null) {
            valName.setText(emp.getName());
            valDept.setText(emp.getDepartment());
            valDesignation.setText(emp.getDesignation());
            valSalary.setText("₹" + String.format("%.2f", emp.getSalary()));
            valPhone.setText(emp.getPhone());
            valEmail.setText(emp.getEmail());
            valGender.setText(emp.getGender());
            valDoj.setText(DateUtil.formatDate(emp.getDateOfJoining()));
            valAddress.setText(emp.getAddress());

            btnDelete.setEnabled(true);
        } else {
            DialogUtil.showError(this, "Employee with ID '" + empId + "' not found.");
            clearDetails();
            btnDelete.setEnabled(false);
        }
    }

    private void clearDetails() {
        valName.setText("-");
        valDept.setText("-");
        valDesignation.setText("-");
        valSalary.setText("-");
        valPhone.setText("-");
        valEmail.setText("-");
        valGender.setText("-");
        valDoj.setText("-");
        valAddress.setText("-");
    }
}
