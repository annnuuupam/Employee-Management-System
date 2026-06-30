package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.util.Constants;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class DashboardFrame extends JFrame {

    private JPanel contentPane;
    private EmployeeDAO employeeDAO;

    // Stat Labels
    private JLabel lblEmpCount;
    private JLabel lblDeptCount;
    private JLabel lblSalaryValue;
    private JTable recentTable;
    private DefaultTableModel tableModel;

    // Header Time Labels
    private JLabel lblDateTime;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(() -> {
            try {
                DashboardFrame frame = new DashboardFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DashboardFrame() {
        employeeDAO = new EmployeeDAO();

        setTitle("EMS - Admin Dashboard");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Constants.COLOR_BACKGROUND);
        setContentPane(contentPane);

        // ================= HEADER ======================
        JPanel header = new JPanel();
        header.setLayout(null);
        header.setBounds(0, 0, 1200, 75);
        header.setBackground(Constants.COLOR_PRIMARY);
        contentPane.add(header);

        JLabel lblTitle = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(20, 20, 400, 35);
        header.add(lblTitle);

        JLabel lblAdmin = new JLabel("Welcome, Admin");
        lblAdmin.setForeground(Color.WHITE);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAdmin.setBounds(500, 25, 150, 25);
        header.add(lblAdmin);

        lblDateTime = new JLabel();
        lblDateTime.setForeground(Color.WHITE);
        lblDateTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDateTime.setBounds(680, 25, 330, 25);
        header.add(lblDateTime);

        JButton btnHeaderLogout = new JButton("Logout");
        UIUtil.styleButton(btnHeaderLogout, Constants.COLOR_BUTTON_DANGER, Color.WHITE);
        btnHeaderLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnHeaderLogout.setBounds(1080, 20, 90, 35);
        header.add(btnHeaderLogout);

        // Start Clock Timer
        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
            lblDateTime.setText("Date & Time: " + sdf.format(new Date()));
        });
        timer.start();

        // ================= LEFT MENU / NAVIGATION ====================
        JPanel menu = new JPanel();
        menu.setLayout(null);
        menu.setBounds(0, 75, 250, 615);
        menu.setBackground(Constants.COLOR_SECONDARY);
        contentPane.add(menu);

        JLabel lblMenu = new JLabel("NAVIGATION");
        lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
        lblMenu.setForeground(Constants.COLOR_TEXT_LIGHT);
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMenu.setBounds(20, 20, 210, 25);
        menu.add(lblMenu);

        JButton btnNavDashboard = new JButton("Dashboard");
        UIUtil.styleButton(btnNavDashboard, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavDashboard.setBounds(25, 70, 200, 45);
        menu.add(btnNavDashboard);

        JButton btnNavAdd = new JButton("Add Employee");
        UIUtil.styleButton(btnNavAdd, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavAdd.setBounds(25, 130, 200, 45);
        menu.add(btnNavAdd);

        JButton btnNavView = new JButton("View Employees");
        UIUtil.styleButton(btnNavView, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavView.setBounds(25, 190, 200, 45);
        menu.add(btnNavView);

        JButton btnNavUpdate = new JButton("Update Employee");
        UIUtil.styleButton(btnNavUpdate, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavUpdate.setBounds(25, 250, 200, 45);
        menu.add(btnNavUpdate);

        JButton btnNavDelete = new JButton("Delete Employee");
        UIUtil.styleButton(btnNavDelete, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavDelete.setBounds(25, 310, 200, 45);
        menu.add(btnNavDelete);

        JButton btnNavSearch = new JButton("Search Employee");
        UIUtil.styleButton(btnNavSearch, Constants.COLOR_PRIMARY, Color.WHITE);
        btnNavSearch.setBounds(25, 370, 200, 45);
        menu.add(btnNavSearch);

        JButton btnNavLogout = new JButton("Logout");
        UIUtil.styleButton(btnNavLogout, Constants.COLOR_BUTTON_DANGER, Color.WHITE);
        btnNavLogout.setBounds(25, 520, 200, 45);
        menu.add(btnNavLogout);

        // ================= MAIN CONTENT AREA ====================
        
        // --- STATISTIC CARDS ---
        JPanel card1 = new JPanel();
        card1.setLayout(null);
        card1.setBackground(Constants.COLOR_CARD);
        card1.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        card1.setBounds(300, 110, 260, 120);
        contentPane.add(card1);

        JLabel lblEmp = new JLabel("Total Employees");
        lblEmp.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmp.setFont(Constants.FONT_LABEL);
        lblEmp.setForeground(Constants.COLOR_TEXT_MUTED);
        lblEmp.setBounds(20, 20, 220, 25);
        card1.add(lblEmp);

        lblEmpCount = new JLabel("0");
        lblEmpCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmpCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblEmpCount.setForeground(Constants.COLOR_PRIMARY);
        lblEmpCount.setBounds(20, 50, 220, 45);
        card1.add(lblEmpCount);

        JPanel card2 = new JPanel();
        card2.setLayout(null);
        card2.setBackground(Constants.COLOR_CARD);
        card2.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        card2.setBounds(590, 110, 260, 120);
        contentPane.add(card2);

        JLabel lblDept = new JLabel("Departments");
        lblDept.setHorizontalAlignment(SwingConstants.CENTER);
        lblDept.setFont(Constants.FONT_LABEL);
        lblDept.setForeground(Constants.COLOR_TEXT_MUTED);
        lblDept.setBounds(20, 20, 220, 25);
        card2.add(lblDept);

        lblDeptCount = new JLabel("0");
        lblDeptCount.setHorizontalAlignment(SwingConstants.CENTER);
        lblDeptCount.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblDeptCount.setForeground(Constants.COLOR_BUTTON_SUCCESS);
        lblDeptCount.setBounds(20, 50, 220, 45);
        card2.add(lblDeptCount);

        JPanel card3 = new JPanel();
        card3.setLayout(null);
        card3.setBackground(Constants.COLOR_CARD);
        card3.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        card3.setBounds(880, 110, 280, 120);
        contentPane.add(card3);

        JLabel lblSalary = new JLabel("Average Salary");
        lblSalary.setHorizontalAlignment(SwingConstants.CENTER);
        lblSalary.setFont(Constants.FONT_LABEL);
        lblSalary.setForeground(Constants.COLOR_TEXT_MUTED);
        lblSalary.setBounds(20, 20, 240, 25);
        card3.add(lblSalary);

        lblSalaryValue = new JLabel("₹0.0");
        lblSalaryValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblSalaryValue.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblSalaryValue.setForeground(Constants.COLOR_BUTTON_DANGER);
        lblSalaryValue.setBounds(20, 50, 240, 45);
        card3.add(lblSalaryValue);

        // --- QUICK ACTIONS ---
        JLabel lblQuick = new JLabel("Quick Actions");
        lblQuick.setFont(Constants.FONT_SUBTITLE);
        lblQuick.setForeground(Constants.COLOR_PRIMARY);
        lblQuick.setBounds(300, 260, 250, 30);
        contentPane.add(lblQuick);

        JButton btnQuickAdd = new JButton("Add Employee");
        UIUtil.styleButton(btnQuickAdd, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnQuickAdd.setBounds(300, 300, 195, 45);
        contentPane.add(btnQuickAdd);

        JButton btnQuickView = new JButton("View Employees");
        UIUtil.styleButton(btnQuickView, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnQuickView.setBounds(520, 300, 195, 45);
        contentPane.add(btnQuickView);

        JButton btnQuickUpdate = new JButton("Update Employee");
        UIUtil.styleButton(btnQuickUpdate, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnQuickUpdate.setBounds(740, 300, 195, 45);
        contentPane.add(btnQuickUpdate);

        JButton btnQuickDelete = new JButton("Delete Employee");
        UIUtil.styleButton(btnQuickDelete, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnQuickDelete.setBounds(960, 300, 200, 45);
        contentPane.add(btnQuickDelete);

        // --- RECENT EMPLOYEES PANEL ---
        JPanel recentPanel = new JPanel();
        recentPanel.setLayout(null);
        recentPanel.setBackground(Constants.COLOR_CARD);
        recentPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        recentPanel.setBounds(300, 370, 860, 280);
        contentPane.add(recentPanel);

        JLabel lblRecent = new JLabel("Recent Hires");
        lblRecent.setFont(Constants.FONT_SUBTITLE);
        lblRecent.setForeground(Constants.COLOR_PRIMARY);
        lblRecent.setBounds(20, 15, 250, 25);
        recentPanel.add(lblRecent);

        // Setup dynamic table model
        String[] columns = {"Employee ID", "Name", "Department", "Designation", "Salary"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only
            }
        };
        recentTable = new JTable(tableModel);
        UIUtil.styleTable(recentTable);

        JScrollPane scrollPane = new JScrollPane(recentTable);
        scrollPane.setBounds(20, 50, 820, 210);
        recentPanel.add(scrollPane);

        // Load data initially
        refreshData();

        // ================= NAVIGATION ACTIONS ====================
        
        btnNavDashboard.addActionListener(e -> refreshData());

        btnNavAdd.addActionListener(e -> {
            AddEmployeeFrame frame = new AddEmployeeFrame(this);
            frame.setVisible(true);
        });

        btnNavView.addActionListener(e -> {
            ViewEmployeeFrame frame = new ViewEmployeeFrame(this);
            frame.setVisible(true);
        });

        btnNavUpdate.addActionListener(e -> {
            UpdateEmployeeFrame frame = new UpdateEmployeeFrame(this, null);
            frame.setVisible(true);
        });

        btnNavDelete.addActionListener(e -> {
            DeleteEmployeeFrame frame = new DeleteEmployeeFrame(this);
            frame.setVisible(true);
        });

        btnNavSearch.addActionListener(e -> {
            SearchEmployeeFrame frame = new SearchEmployeeFrame(this);
            frame.setVisible(true);
        });

        // ================= QUICK BUTTON ACTIONS ====================
        btnQuickAdd.addActionListener(e -> btnNavAdd.doClick());
        btnQuickView.addActionListener(e -> btnNavView.doClick());
        btnQuickUpdate.addActionListener(e -> btnNavUpdate.doClick());
        btnQuickDelete.addActionListener(e -> btnNavDelete.doClick());

        // ================= LOGOUT ACTION ====================
        btnNavLogout.addActionListener(e -> triggerLogout());
        btnHeaderLogout.addActionListener(e -> triggerLogout());
    }

    /**
     * Refreshes the metrics cards and the recent employees list from database.
     */
    public void refreshData() {
        try {
            // 1. Refresh statistic cards
            int total = employeeDAO.getTotalEmployeeCount();
            int depts = employeeDAO.getDepartmentCount();
            double avgSal = employeeDAO.getAverageSalary();

            lblEmpCount.setText(String.valueOf(total));
            lblDeptCount.setText(String.format("%02d", depts));

            DecimalFormat df = new DecimalFormat("##,##,##0.00");
            lblSalaryValue.setText("₹" + df.format(avgSal));

            // 2. Refresh JTable rows
            tableModel.setRowCount(0);
            List<Employee> recent = employeeDAO.getRecentEmployees(5);
            for (Employee e : recent) {
                tableModel.addRow(new Object[]{
                        e.getId(),
                        e.getName(),
                        e.getDepartment(),
                        e.getDesignation(),
                        String.format("%.2f", e.getSalary())
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void triggerLogout() {
        boolean confirm = DialogUtil.showConfirm(
                this,
                "Confirm Logout",
                "Are you sure you want to logout of the system?"
        );
        if (confirm) {
            dispose();
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        }
    }
}