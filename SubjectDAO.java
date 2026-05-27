package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubjectDAO {

    public boolean addSubject(String name, String code, String deptName) {
        String query = "INSERT INTO subjects (sub_name, sub_code, dept_id, semester) VALUES (?, ?, (SELECT dept_id FROM departments WHERE dept_name = ?), 3)";
        // Hardcoding semester to 3 for simplicity in this demo DAO, ideally passed as
        // param

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, code);
            pstmt.setString(3, deptName);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
