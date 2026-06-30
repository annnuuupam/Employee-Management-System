package com.employee.util;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;
import java.sql.Date;
import java.util.Random;

public class PopulateData {
    public static void main(String[] args) {
        System.out.println("Populating database with 100 sample employees...");
        EmployeeDAO dao = new EmployeeDAO();
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

        int successCount = 0;

        for (int i = 1; i <= 100; i++) {
            String id = "EMP" + (1000 + i);
            String fName = firstNames[rand.nextInt(firstNames.length)];
            String lName = lastNames[rand.nextInt(lastNames.length)];
            String name = fName + " " + lName;
            
            String dept = depts[rand.nextInt(depts.length)];
            String designation = designations[rand.nextInt(designations.length)];
            
            // Generate realistic salary based on designation
            double salary = 35000 + rand.nextInt(120000);
            if (designation.contains("Senior") || designation.contains("Manager") || designation.contains("Lead")) {
                salary += 40000;
            }
            
            // Phone number
            String phone = (7 + rand.nextInt(3)) + String.format("%09d", rand.nextInt(1000000000));
            if (phone.length() > 10) {
                phone = phone.substring(0, 10);
            }

            // Email
            String email = fName.toLowerCase() + "." + lName.toLowerCase() + "@company.com";
            
            String gender = genders[rand.nextInt(genders.length)];
            if (fName.endsWith("a") || fName.endsWith("i") || fName.equals("Preeti") || fName.equals("Shweta") || fName.equals("Nisha")) {
                gender = "Female";
            }
            
            String address = rand.nextInt(500) + ", " + areas[rand.nextInt(areas.length)] + ", " + cities[rand.nextInt(cities.length)];
            
            // Random Date of joining (between 2020 and 2026)
            int year = 2020 + rand.nextInt(7);
            int month = 1 + rand.nextInt(12);
            int day = 1 + rand.nextInt(28);
            String dateStr = String.format("%04d-%02d-%02d", year, month, day);
            Date dateOfJoining = null;
            try {
                dateOfJoining = DateUtil.parseDate(dateStr);
            } catch (Exception e) {
                dateOfJoining = new Date(System.currentTimeMillis());
            }

            Employee emp = new Employee(id, name, dept, designation, salary, phone, email, gender, address, dateOfJoining);
            
            // Check if employee ID already exists before inserting
            if (dao.getEmployee(id) == null) {
                if (dao.addEmployee(emp)) {
                    successCount++;
                }
            }
        }

        System.out.println("Finished! Successfully populated " + successCount + " sample records.");
    }
}
