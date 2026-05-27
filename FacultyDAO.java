package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacultyDAO {

    public boolean addFaculty(String name, String userId, String dept, String year, int semester, String subjectName,
            String subjectCode) {
        // 1. Get User ID if exists, or create new
        String checkUserQuery = "SELECT user_id FROM users WHERE username = ?";
        String userQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, 'FACULTY')";
        // 2. Check for duplicate assignment
        String checkAssignQuery = "SELECT COUNT(*) FROM faculty f JOIN users u ON f.user_id = u.user_id WHERE u.username = ? AND f.subject_code = ?";
        // 3. Insert assignment
        String facultyQuery = "INSERT INTO faculty (user_id, full_name, dept_id, handling_year, semester, subject_name, subject_code) VALUES (?, ?, "
                + "(SELECT dept_id FROM departments WHERE dept_name = ?), ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // Check existing user
            PreparedStatement pCheckUser = conn.prepareStatement(checkUserQuery);
            pCheckUser.setString(1, userId);
            ResultSet rsUser = pCheckUser.executeQuery();

            int existingUserId = 0;
            if (rsUser.next()) {
                existingUserId = rsUser.getInt(1);
            }

            // Check Duplicate Assignment
            PreparedStatement pCheckAssign = conn.prepareStatement(checkAssignQuery);
            pCheckAssign.setString(1, userId);
            pCheckAssign.setString(2, subjectCode);
            ResultSet rsAssign = pCheckAssign.executeQuery();
            if (rsAssign.next() && rsAssign.getInt(1) > 0) {
                System.out.println("Assignment already exists for " + userId + " - " + subjectCode);
                return false;
            }

            int finalUserId = existingUserId;
            if (finalUserId == 0) {
                // Add New User
                PreparedStatement pstmt1 = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt1.setString(1, userId);
                pstmt1.setString(2, "password123");
                pstmt1.executeUpdate();
                var rsKeys = pstmt1.getGeneratedKeys();
                if (rsKeys.next())
                    finalUserId = rsKeys.getInt(1);
            }

            // Add Faculty Assignment
            PreparedStatement pstmt2 = conn.prepareStatement(facultyQuery);
            pstmt2.setInt(1, finalUserId);
            pstmt2.setString(2, name);
            pstmt2.setString(3, dept);
            pstmt2.setString(4, year);
            pstmt2.setInt(5, semester);
            pstmt2.setString(6, subjectName);
            pstmt2.setString(7, subjectCode);
            pstmt2.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null)
                    conn.rollback();
            } catch (Exception ex) {
            }
            return false;
        } finally {
            try {
                if (conn != null)
                    conn.setAutoCommit(true);
                conn.close();
            } catch (Exception ex) {
            }
        }
    }
}
