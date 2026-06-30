package com.employee.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    // Load db.properties configuration
    static {
        Properties props = new Properties();
        boolean loaded = false;
        
        // Try to load from root directory first
        try (InputStream is = new FileInputStream("db.properties")) {
            props.load(is);
            url = props.getProperty("db.url");
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");
            loaded = true;
        } catch (Exception e) {
            // Ignore and try classpath
        }

        if (!loaded) {
            // Fallback: load as a resource from the classpath
            try (InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (is != null) {
                    props.load(is);
                    url = props.getProperty("db.url");
                    username = props.getProperty("db.username");
                    password = props.getProperty("db.password");
                    driver = props.getProperty("db.driver");
                    loaded = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (loaded && driver != null) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found: " + e.getMessage());
            }
        } else {
            System.err.println("Could not load db.properties settings!");
        }
    }

    private DBConnection() {
        // Private constructor for Singleton
    }

    /**
     * Retrieves the singleton connection instance.
     * Recreates the connection if it was closed or is null.
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            if (url == null) {
                // Last-resort fallback to standard local DB settings to prevent crash
                url = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true";
                username = "root";
                password = "";
            }
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }
}
