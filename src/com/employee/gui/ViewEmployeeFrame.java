package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.util.Constants;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;

import java.awt.Color;
import java.awt.Font;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ViewEmployeeFrame extends JFrame {

    private DashboardFrame parentDashboard;
    private EmployeeDAO employeeDAO;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField txtSearch;

    public ViewEmployeeFrame(DashboardFrame parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.employeeDAO = new EmployeeDAO();

        setTitle("EMS - View Employees");
        setSize(1000, 600);
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
        header.setBounds(0, 0, 1000, 70);
        header.setLayout(null);
        contentPane.add(header);

        JLabel title = new JLabel("EMPLOYEE DIRECTORY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(30, 20, 300, 30);
        header.add(title);

        // ================= TOOLBAR PANEL ====================
        JPanel toolbar = new JPanel();
        toolbar.setBackground(Constants.COLOR_CARD);
        toolbar.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        toolbar.setLayout(null);
        toolbar.setBounds(30, 85, 940, 60);
        contentPane.add(toolbar);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(Constants.FONT_LABEL);
        lblSearch.setBounds(20, 18, 60, 25);
        toolbar.add(lblSearch);

        txtSearch = new JTextField();
        UIUtil.styleTextField(txtSearch);
        txtSearch.setBounds(80, 15, 200, 30);
        toolbar.add(txtSearch);

        // Search trigger
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String text = txtSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JButton btnRefresh = new JButton("Refresh");
        UIUtil.styleButton(btnRefresh, Constants.COLOR_PRIMARY, Color.WHITE);
        btnRefresh.setBounds(460, 12, 100, 35);
        toolbar.add(btnRefresh);

        JButton btnPrint = new JButton("Print Table");
        UIUtil.styleButton(btnPrint, Constants.COLOR_PRIMARY, Color.WHITE);
        btnPrint.setBounds(570, 12, 110, 35);
        toolbar.add(btnPrint);

        JButton btnExport = new JButton("Export CSV");
        UIUtil.styleButton(btnExport, Constants.COLOR_BUTTON_SUCCESS, Color.WHITE);
        btnExport.setBounds(690, 12, 110, 35);
        toolbar.add(btnExport);

        JButton btnBack = new JButton("Back");
        UIUtil.styleButton(btnBack, Constants.COLOR_BUTTON_PRIMARY, Color.WHITE);
        btnBack.setBounds(810, 12, 110, 35);
        toolbar.add(btnBack);

        // ================= TABLE PANEL ====================
        String[] columns = {
            "ID", "Name", "Department", "Designation", "Salary", "Phone", "Email", "Gender", "Date of Join"
        };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        UIUtil.styleTable(table);

        // Enable sorting
        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 160, 940, 370);
        contentPane.add(scrollPane);

        // Load initially
        loadEmployeeData();

        // ================= EVENTS ====================
        btnBack.addActionListener(e -> dispose());
        btnRefresh.addActionListener(e -> loadEmployeeData());
        btnPrint.addActionListener(e -> printTable());
        btnExport.addActionListener(e -> exportCSV());

        // Double-click to update
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int modelRow = table.convertRowIndexToModel(selectedRow);
                        String empId = (String) tableModel.getValueAt(modelRow, 0);

                        UpdateEmployeeFrame updateFrame = new UpdateEmployeeFrame(parentDashboard, empId);
                        updateFrame.setVisible(true);
                        dispose(); // Close View Window
                    }
                }
            }
        });
    }

    private void loadEmployeeData() {
        try {
            tableModel.setRowCount(0);
            List<Employee> list = employeeDAO.getAllEmployees();
            for (Employee e : list) {
                tableModel.addRow(new Object[]{
                    e.getId(),
                    e.getName(),
                    e.getDepartment(),
                    e.getDesignation(),
                    String.format("%.2f", e.getSalary()),
                    e.getPhone(),
                    e.getEmail(),
                    e.getGender(),
                    e.getDateOfJoining().toString()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            DialogUtil.showError(this, "Failed to load database records.");
        }
    }

    private void printTable() {
        try {
            boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, 
                new java.text.MessageFormat("Employee Directory"), 
                new java.text.MessageFormat("Page {0}"));
            if (complete) {
                DialogUtil.showSuccess(this, "Printing completed successfully.");
            }
        } catch (Exception ex) {
            DialogUtil.showError(this, "Printing failed: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Directory to CSV File");
        int selection = fileChooser.showSaveDialog(this);
        
        if (selection == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            try (FileWriter fw = new FileWriter(filePath)) {
                // Header
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    fw.write(tableModel.getColumnName(i) + (i == tableModel.getColumnCount() - 1 ? "" : ","));
                }
                fw.write("\n");

                // Rows
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        String cellVal = tableModel.getValueAt(i, j).toString().replace(",", ";"); // Escape commas
                        fw.write(cellVal + (j == tableModel.getColumnCount() - 1 ? "" : ","));
                    }
                    fw.write("\n");
                }
                DialogUtil.showSuccess(this, "CSV file exported successfully to " + filePath);
            } catch (IOException ex) {
                DialogUtil.showError(this, "Failed to export data: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
