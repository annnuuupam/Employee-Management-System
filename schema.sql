-- Database initialization script for EMS

CREATE DATABASE IF NOT EXISTS employee_db;
USE employee_db;

-- Table for User Accounts
CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- Default admin user insertion
INSERT INTO users (username, password) 
VALUES ('admin', 'admin') 
ON DUPLICATE KEY UPDATE password='admin';

-- Table for Employees
CREATE TABLE IF NOT EXISTS employees (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department VARCHAR(50) NOT NULL,
    designation VARCHAR(50) NOT NULL,
    salary DOUBLE NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    gender VARCHAR(15) NOT NULL,
    address TEXT NOT NULL,
    date_of_joining DATE NOT NULL
);
