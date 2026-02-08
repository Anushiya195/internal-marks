package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.ims.model.User; // Assuming a User model class exists

public class UserDAO {

    public String authenticateUser(String username, String password) {
        String role = null;
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return role; // Returns 'ADMIN', 'FACULTY', 'STUDENT', or null
    }
}
