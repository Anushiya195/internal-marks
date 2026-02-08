package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.ims.model.User; // Assuming User model

public class FacultyDAO {

    public boolean addFaculty(String name, String userId, String dept) {
        // Need to add to Users table first, then Faculty table
        String userQuery = "INSERT INTO users (username, password, role) VALUES (?, ?, 'FACULTY')";
        String facultyQuery = "INSERT INTO faculty (user_id, full_name, dept_id) VALUES (?, ?, (SELECT dept_id FROM departments WHERE dept_name = ?))";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Transaction

            // 1. Add User
            PreparedStatement pstmt1 = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, userId); // Using ID as username for simplicity
            pstmt1.setString(2, "password123"); // Default password
            pstmt1.executeUpdate();

            var rs = pstmt1.getGeneratedKeys();
            int newUserId = 0;
            if (rs.next())
                newUserId = rs.getInt(1);

            // 2. Add Faculty Profile
            PreparedStatement pstmt2 = conn.prepareStatement(facultyQuery);
            pstmt2.setInt(1, newUserId);
            pstmt2.setString(2, name);
            pstmt2.setString(3, dept);
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
