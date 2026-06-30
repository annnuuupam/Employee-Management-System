package com.employee.gui;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import com.employee.util.Constants;
import com.employee.util.DialogUtil;
import com.employee.util.UIUtil;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class SearchEmployeeFrame extends JFrame {

    private DashboardFrame parentDashboard;
    private EmployeeDAO employeeDAO;

    private JComboBox<String> comboFilter;
    private JTextField txtQuery;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;

    public SearchEmployeeFrame(DashboardFrame parentDashboard) {
        this.parentDashboard = parentDashboard;
        this.employeeDAO = new EmployeeDAO();

        setTitle("EMS - Search Employee");
        setSize(900, 560);
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
        header.setBounds(0, 0, 900, 70);
        header.setLayout(null);
        contentPane.add(header);

        JLabel title = new JLabel("SEARCH EMPLOYEE");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(30, 20, 300, 30);
        header.add(title);

        // ================= FILTER BAR CARD ====================
        JPanel filterCard = new JPanel();
        filterCard.setBackground(Constants.COLOR_CARD);
        filterCard.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1));
        filterCard.setLayout(null);
        filterCard.setBounds(30, 85, 840, 70);
        contentPane.add(filterCard);

        JLabel lblSearchBy = new JLabel("Search By:");
        lblSearchBy.setFont(Constants.FONT_LABEL);
        lblSearchBy.setBounds(20, 22, 80, 25);
        filterCard.add(lblSearchBy);

        String[] filters = {"Employee ID", "Employee Name", "Phone", "Department"};
        comboFilter = new JComboBox<>(filters);
        comboFilter.setFont(Constants.FONT_FIELD);
        comboFilter.setBounds(110, 20, 150, 30);
        filterCard.add(comboFilter);

        JLabel lblValue = new JLabel("Search Value:");
        lblValue.setFont(Constants.FONT_LABEL);
        lblValue.setBounds(280, 22, 100, 25);
        filterCard.add(lblValue);

        txtQuery = new JTextField();
        UIUtil.styleTextField(txtQuery);
        txtQuery.setBounds(390, 20, 180, 30);
        filterCard.add(txtQuery);

        JButton btnSearch = new JButton("Search");
        UIUtil.styleButton(btnSearch, Constants.COLOR_PRIMARY, Color.WHITE);
        btnSearch.setBounds(590, 17, 100, 35);
        filterCard.add(btnSearch);

        JButton btnReset = new JButton("Reset");
        UIUtil.styleButton(btnReset, Constants.COLOR_BUTTON_SECONDARY, Color.WHITE);
        btnReset.setBounds(705, 17, 100, 35);
        filterCard.add(btnReset);

        // ================= RESULTS JTABLE PANEL ====================
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

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 175, 840, 280);
        contentPane.add(scrollPane);

        // Back Button at the bottom
        JButton btnBack = new JButton("Back to Dashboard");
        UIUtil.styleButton(btnBack, Constants.COLOR_PRIMARY, Color.WHITE);
        btnBack.setBounds(350, 470, 200, 40);
        contentPane.add(btnBack);

        // ================= EVENTS ====================
        btnBack.addActionListener(e -> dispose());
        btnReset.addActionListener(e -> {
            txtQuery.setText("");
            tableModel.setRowCount(0);
        });

        btnSearch.addActionListener(e -> performSearch());
        txtQuery.addActionListener(e -> performSearch()); // Enter key runs search too

        // Double click to open Update Screen
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
                        dispose(); // Close search frame
                    }
                }
            }
        });
    }

    private void performSearch() {
        String filterField = comboFilter.getSelectedItem().toString();
        String queryText = txtQuery.getText().trim();

        if (queryText.isEmpty()) {
            DialogUtil.showError(this, "Please enter a value to search.");
            return;
        }

        try {
            tableModel.setRowCount(0);
            List<Employee> results = employeeDAO.searchEmployees(filterField, queryText);
            
            if (results.isEmpty()) {
                DialogUtil.showInfo(this, "No matching records found.");
                return;
            }

            for (Employee e : results) {
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
            DialogUtil.showError(this, "Error performing search query.");
        }
    }
}
