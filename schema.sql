-- Database Name: internal_marks_db

CREATE DATABASE IF NOT EXISTS internal_marks_db;
USE internal_marks_db;

-- 1. Departments Table
CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL UNIQUE
);

-- 2. Users Table (Stores Login Credentials & Roles)
-- Roles: 'ADMIN', 'FACULTY', 'STUDENT'
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL, -- In a real app, use hashing!
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'FACULTY', 'STUDENT'))
);

-- 3. Subjects Table
CREATE TABLE subjects (
    sub_id INT PRIMARY KEY AUTO_INCREMENT,
    sub_name VARCHAR(100) NOT NULL,
    sub_code VARCHAR(20) NOT NULL UNIQUE,
    dept_id INT,
    semester INT NOT NULL,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE CASCADE
);

-- 4. Faculty Table (Profile linked to User and Department)
CREATE TABLE faculty (
    faculty_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    dept_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- 5. Students Table (Profile linked to User and Department)
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    register_number VARCHAR(20) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    dept_id INT,
    semester INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (dept_id) REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- 6. Marks Table
CREATE TABLE marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    sub_id INT,
    cia_score DECIMAL(5,2) DEFAULT 0.0, -- Max 20 or 25 usually
    assign_score DECIMAL(5,2) DEFAULT 0.0,
    lab_score DECIMAL(5,2) DEFAULT 0.0,
    total_score DECIMAL(5,2) GENERATED ALWAYS AS (cia_score + assign_score + lab_score) VIRTUAL,
    grade VARCHAR(5), -- Calculated via Logic in App or Trigger, but simple storage here
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (sub_id) REFERENCES subjects(sub_id) ON DELETE CASCADE,
    UNIQUE(student_id, sub_id) -- One mark entry per student per subject
);

-- Default Data Injection --

-- Admin
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');

-- Sample Departments
INSERT INTO departments (dept_name) VALUES ('Computer Science'), ('Information Technology');

-- Sample Subjects
INSERT INTO subjects (sub_name, sub_code, dept_id, semester) VALUES 
('Java Programming', 'CS301', 1, 3),
('Database Systems', 'CS302', 1, 3),
('Web Technologies', 'IT301', 2, 3);

-- Sample Faculty
INSERT INTO users (username, password, role) VALUES ('faculty1', 'fac123', 'FACULTY');
INSERT INTO faculty (user_id, full_name, dept_id) VALUES (LAST_INSERT_ID(), 'Dr. John Smith', 1);

-- Sample Student
INSERT INTO users (username, password, role) VALUES ('student1', 'stu123', 'STUDENT');
INSERT INTO students (user_id, register_number, full_name, dept_id, semester) VALUES (LAST_INSERT_ID(), 'REG001', 'Alice Johnson', 1, 3);
