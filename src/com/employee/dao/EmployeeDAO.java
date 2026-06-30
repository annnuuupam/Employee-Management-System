package com.employee.dao;

import com.employee.db.DBConnection;
import com.employee.model.Employee;
import com.employee.util.DateUtil;

import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

public class EmployeeDAO {

    // Dynamic mock database variables
    private static boolean isMockMode = false;
    private static boolean initComplete = false;
    private static final List<Employee> mockEmployees = new ArrayList<>();

    public EmployeeDAO() {
        initializeDatabaseState();
    }

    /**
     * Initializes the DB state. Sets up tables/records if MySQL is available,
     * or falls back to an in-memory Mock List with 100 entries.
     */
    private synchronized void initializeDatabaseState() {
        if (initComplete) {
            return;
        }

        try {
            // 1. Attempt connection
            Connection conn = DBConnection.getConnection();
            
            // 2. Setup tables if they do not exist
            createTablesIfNotExist(conn);
            
            // 3. Pre-populate tables if empty (data on the fly!)
            int count = getTotalEmployeeCountFromDB();
            if (count == 0) {
                System.out.println("MySQL database is empty. Populating sample data...");
                populateSampleDataToDB(conn);
            }
            
            isMockMode = false;
            System.out.println("Application connected successfully to MySQL Server.");
        } catch (Exception e) {
            // MySQL offline -> Fallback to Mock Mode
            isMockMode = true;
            System.out.println("MySQL connection failed. Falling back to In-Memory Preview Mode.");
            if (mockEmployees.isEmpty()) {
                populateSampleDataToMemory();
            }
            
            // Notify user about fallback
            java.awt.EventQueue.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                    null,
                    "MySQL Server is offline. Starting application in standalone In-Memory Demo Mode.\n" +
                    "100 test records have been loaded. Note: Changes will not be saved on exit.",
                    "Standalone Mode - MySQL Offline",
                    JOptionPane.WARNING_MESSAGE
                );
            });
        }
        initComplete = true;
    }

    /**
     * Creates SQL tables dynamically.
     */
    private void createTablesIfNotExist(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Create user login table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  username VARCHAR(50) PRIMARY KEY, " +
                "  password VARCHAR(255) NOT NULL" +
                ")"
            );
            
            // Insert admin credentials
            stmt.executeUpdate(
                "INSERT IGNORE INTO users (username, password) VALUES ('admin', 'admin')"
            );

            // Create employee table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS employees (" +
                "  id VARCHAR(50) PRIMARY KEY, " +
                "  name VARCHAR(100) NOT NULL, " +
                "  department VARCHAR(50) NOT NULL, " +
                "  designation VARCHAR(150) NOT NULL, " +
                "  salary DOUBLE NOT NULL, " +
                "  phone VARCHAR(15) NOT NULL, " +
                "  email VARCHAR(100) NOT NULL, " +
                "  gender VARCHAR(15) NOT NULL, " +
                "  address TEXT NOT NULL, " +
                "  date_of_joining DATE NOT NULL" +
                ")"
            );
        }
    }

    private int getTotalEmployeeCountFromDB() {
        String sql = "SELECT COUNT(*) FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            // Table might not exist yet, return 0
        }
        return 0;
    }

    /**
     * Populates database tables with 100 sample employees.
     */
    private void populateSampleDataToDB(Connection conn) {
        String sql = "INSERT INTO employees (id, name, department, designation, salary, phone, email, gender, address, date_of_joining) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        List<Employee> list = generateSampleList();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Employee emp : list) {
                ps.setString(1, emp.getId());
                ps.setString(2, emp.getName());
                ps.setString(3, emp.getDepartment());
                ps.setString(4, emp.getDesignation());
                ps.setDouble(5, emp.getSalary());
                ps.setString(6, emp.getPhone());
                ps.setString(7, emp.getEmail());
                ps.setString(8, emp.getGender());
                ps.setString(9, emp.getAddress());
                ps.setDate(10, emp.getDateOfJoining());
                ps.addBatch();
            }
            ps.executeBatch();
            System.out.println("Successfully populated MySQL database with 100 records.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates in-memory static list with 100 sample employees.
     */
    private void populateSampleDataToMemory() {
        mockEmployees.clear();
        mockEmployees.addAll(generateSampleList());
        System.out.println("Successfully populated in-memory mock database with 100 records.");
    }

    /**
     * Helper to generate 100 realistic dummy records.
     */
    private List<Employee> generateSampleList() {
        List<Employee> list = new ArrayList<>();
        Random rand = new Random();
        
        String[] firstNames = {
            "Amit", "Rahul", "Priya", "Sunita", "Deepak", "Rohan", "Anjali", "Sanjay", "Neha", "Vikram",
            "Kiran", "Arjun", "Sneha", "Rajesh", "Pooja", "Manish", "Divya", "Suresh", "Ritu", "Alok",
            "Karan", "Nisha", "Gaurav", "Swati", "Abhishek", "Shweta", "Vijay", "Preeti", "Sunil", "Aarati"
        };
        String[] lastNames = {
            "Sharma", "Gupta", "Kumar", "Singh", "Verma", "Joshi", "Mehta", "Patel", "Reddy", "Nair",
            "Rao", "Chawla", "Bose", "Das", "Sen", "Mishra", "Pandey", "Yadav", "Choudhury", "Saxena",
            "Trivedi", "Dubey", "Shukla", "Deshmukh", "Kulkarni", "Jha", "Pillai", "Menon", "Jain", "Bhat"
        };
        String[] depts = {"IT", "HR", "Finance", "Sales", "Marketing", "Operations", "R&D", "Support"};
        String[] designations = {
            "Intern", "Software Engineer Intern", "Software Engineer", "Senior Developer",
            "Tech Lead", "QA Engineer", "DevOps Engineer", "UI/UX Designer",
            "Product Manager", "Project Manager", "Director of Engineering", "Vice President (VP)",
            "HR Intern", "HR Specialist", "HR Manager", "Financial Intern",
            "Financial Analyst", "Accountant", "Sales Representative", "Sales Executive",
            "Sales Manager", "Marketing Intern", "Marketing Executive", "Operations Associate",
            "Operations Lead", "Customer Support Executive", "Tech Support Engineer"
        };
        String[] genders = {"Male", "Female", "Other"};
        String[] cities = {"Mumbai", "Delhi", "Bengaluru", "Pune", "Hyderabad", "Chennai", "Kolkata", "Noida", "Gurugram", "Ahmedabad"};
        String[] areas = {"Kora Mangala", "Whitefield", "Andheri West", "Sector 62", "Gachibowli", "Hinjewadi", "Salt Lake", "T Nagar"};

        for (int i = 1; i <= 100; i++) {
            String id = "EMP" + (1000 + i);
            String fName = firstNames[rand.nextInt(firstNames.length)];
            String lName = lastNames[rand.nextInt(lastNames.length)];
            String name = fName + " " + lName;
            String dept = depts[rand.nextInt(depts.length)];
            String designation = designations[rand.nextInt(designations.length)];
            
            double salary = 30000 + rand.nextInt(120000);
            if (designation.contains("Senior") || designation.contains("Manager") || designation.contains("Lead") || designation.contains("Director") || designation.contains("VP")) {
                salary += 50000;
            } else if (designation.contains("Intern")) {
                salary = 15000 + rand.nextInt(15000); // Intern salary
            }

            String phone = (7 + rand.nextInt(3)) + String.format("%09d", rand.nextInt(1000000000));
            if (phone.length() > 10) {
                phone = phone.substring(0, 10);
            }
            String email = fName.toLowerCase() + "." + lName.toLowerCase() + "@company.com";
            
            String gender = genders[rand.nextInt(genders.length)];
            if (fName.endsWith("a") || fName.endsWith("i") || fName.equals("Preeti") || fName.equals("Shweta") || fName.equals("Nisha")) {
                gender = "Female";
            }
            
            String address = rand.nextInt(500) + ", " + areas[rand.nextInt(areas.length)] + ", " + cities[rand.nextInt(cities.length)];
            
            int year = 2020 + rand.nextInt(7);
            int month = 1 + rand.nextInt(12);
            int day = 1 + rand.nextInt(28);
            String dateStr = String.format("%04d-%02d-%02d", year, month, day);
            Date doj;
            try {
                doj = DateUtil.parseDate(dateStr);
            } catch (Exception e) {
                doj = new Date(System.currentTimeMillis());
            }

            list.add(new Employee(id, name, dept, designation, salary, phone, email, gender, address, doj));
        }
        return list;
    }

    /**
     * Authenticates administrator.
     */
    public boolean authenticate(String username, String password) {
        if (isMockMode) {
            return username.equals("admin") && password.equals("admin");
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds an employee record.
     */
    public boolean addEmployee(Employee emp) {
        if (isMockMode) {
            // Check for duplicate ID
            for (Employee e : mockEmployees) {
                if (e.getId().equalsIgnoreCase(emp.getId())) {
                    return false;
                }
            }
            mockEmployees.add(emp);
            return true;
        }

        String sql = "INSERT INTO employees (id, name, department, designation, salary, phone, email, gender, address, date_of_joining) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getId());
            ps.setString(2, emp.getName());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDouble(5, emp.getSalary());
            ps.setString(6, emp.getPhone());
            ps.setString(7, emp.getEmail());
            ps.setString(8, emp.getGender());
            ps.setString(9, emp.getAddress());
            ps.setDate(10, emp.getDateOfJoining());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates an employee record.
     */
    public boolean updateEmployee(Employee emp) {
        if (isMockMode) {
            for (int i = 0; i < mockEmployees.size(); i++) {
                if (mockEmployees.get(i).getId().equalsIgnoreCase(emp.getId())) {
                    mockEmployees.set(i, emp);
                    return true;
                }
            }
            return false;
        }

        String sql = "UPDATE employees SET name = ?, department = ?, designation = ?, salary = ?, phone = ?, email = ?, gender = ?, address = ?, date_of_joining = ? " +
                     "WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDepartment());
            ps.setString(3, emp.getDesignation());
            ps.setDouble(4, emp.getSalary());
            ps.setString(5, emp.getPhone());
            ps.setString(6, emp.getEmail());
            ps.setString(7, emp.getGender());
            ps.setString(8, emp.getAddress());
            ps.setDate(9, emp.getDateOfJoining());
            ps.setString(10, emp.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes an employee by ID.
     */
    public boolean deleteEmployee(String id) {
        if (isMockMode) {
            return mockEmployees.removeIf(e -> e.getId().equalsIgnoreCase(id));
        }

        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gets an employee by ID.
     */
    public Employee getEmployee(String id) {
        if (isMockMode) {
            for (Employee e : mockEmployees) {
                if (e.getId().equalsIgnoreCase(id)) {
                    return e;
                }
            }
            return null;
        }

        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractEmployee(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all employees.
     */
    public List<Employee> getAllEmployees() {
        if (isMockMode) {
            List<Employee> list = new ArrayList<>(mockEmployees);
            list.sort((e1, e2) -> e1.getId().compareToIgnoreCase(e2.getId()));
            return list;
        }

        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractEmployee(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Searches employees by fields.
     */
    public List<Employee> searchEmployees(String fieldName, String value) {
        if (isMockMode) {
            List<Employee> list = new ArrayList<>();
            String valLower = value.trim().toLowerCase();
            for (Employee e : mockEmployees) {
                boolean match = false;
                switch (fieldName.trim().toLowerCase()) {
                    case "employee id":
                    case "id":
                        match = e.getId().toLowerCase().contains(valLower);
                        break;
                    case "employee name":
                    case "name":
                        match = e.getName().toLowerCase().contains(valLower);
                        break;
                    case "phone":
                        match = e.getPhone().contains(valLower);
                        break;
                    case "department":
                        match = e.getDepartment().toLowerCase().contains(valLower);
                        break;
                }
                if (match) {
                    list.add(e);
                }
            }
            list.sort((e1, e2) -> e1.getId().compareToIgnoreCase(e2.getId()));
            return list;
        }

        List<Employee> list = new ArrayList<>();
        String dbField = "id";
        if (fieldName != null) {
            switch (fieldName.trim().toLowerCase()) {
                case "employee id":
                case "id":
                    dbField = "id";
                    break;
                case "employee name":
                case "name":
                    dbField = "name";
                    break;
                case "phone":
                    dbField = "phone";
                    break;
                case "department":
                    dbField = "department";
                    break;
            }
        }
        
        String sql = "SELECT * FROM employees WHERE " + dbField + " LIKE ? ORDER BY id ASC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + value + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractEmployee(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Gets total employee count.
     */
    public int getTotalEmployeeCount() {
        if (isMockMode) {
            return mockEmployees.size();
        }
        return getTotalEmployeeCountFromDB();
    }

    /**
     * Gets unique department counts.
     */
    public int getDepartmentCount() {
        if (isMockMode) {
            Set<String> depts = new HashSet<>();
            for (Employee e : mockEmployees) {
                depts.add(e.getDepartment());
            }
            return depts.size();
        }

        String sql = "SELECT COUNT(DISTINCT department) FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets average employee salary.
     */
    public double getAverageSalary() {
        if (isMockMode) {
            if (mockEmployees.isEmpty()) return 0.0;
            double sum = 0;
            for (Employee e : mockEmployees) {
                sum += e.getSalary();
            }
            return sum / mockEmployees.size();
        }

        String sql = "SELECT AVG(salary) FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Gets recent employees.
     */
    public List<Employee> getRecentEmployees(int limit) {
        if (isMockMode) {
            List<Employee> list = new ArrayList<>(mockEmployees);
            list.sort((e1, e2) -> {
                int cmp = e2.getDateOfJoining().compareTo(e1.getDateOfJoining());
                if (cmp == 0) {
                    return e2.getId().compareToIgnoreCase(e1.getId());
                }
                return cmp;
            });
            if (list.size() > limit) {
                return list.subList(0, limit);
            }
            return list;
        }

        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY date_of_joining DESC, id DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractEmployee(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Helper extracts Employee details from ResultSet
    private Employee extractEmployee(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getString("designation"),
            rs.getDouble("salary"),
            rs.getString("phone"),
            rs.getString("email"),
            rs.getString("gender"),
            rs.getString("address"),
            rs.getDate("date_of_joining")
        );
    }
}
