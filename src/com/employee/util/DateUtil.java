package com.employee.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * Formats java.sql.Date or java.util.Date into a String (yyyy-MM-dd).
     */
    public static String formatDate(java.util.Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Parses a String (yyyy-MM-dd) into java.sql.Date.
     */
    public static java.sql.Date parseDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        java.util.Date parsed = sdf.parse(dateStr.trim());
        return new java.sql.Date(parsed.getTime());
    }

    /**
     * Validates if a date string matches the yyyy-MM-dd format and represents a valid date.
     */
    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr.trim());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
