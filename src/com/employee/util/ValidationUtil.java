package com.employee.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    // Regular expression patterns
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^\\d{10}$"); // Matches exactly 10 digits
        
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[a-zA-Z\\s]+$"); // Matches only letters and spaces

    /**
     * Validates if the email matches standard format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates if the phone number is exactly 10 digits.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validates if the salary is a valid positive numeric value.
     */
    public static boolean isValidSalary(String salary) {
        if (salary == null || salary.trim().isEmpty()) {
            return false;
        }
        try {
            double sal = Double.parseDouble(salary.trim());
            return sal > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if the name contains only alphabetic characters and spaces.
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Checks if a general string field is empty.
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
