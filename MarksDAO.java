package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarksDAO {

    public boolean addOrUpdateMarks(int studentId, int subId, double cia, double assign, double lab) {
        String query = "INSERT INTO marks (student_id, sub_id, cia_score, assign_score, lab_score, grade) " +
                "VALUES (?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE cia_score=?, assign_score=?, lab_score=?, grade=?";

        // Calculate Total and Grade
        double total = cia + assign + lab;
        String grade = calculateGrade(total);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set params for INSERT
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subId);
            pstmt.setDouble(3, cia);
            pstmt.setDouble(4, assign);
            pstmt.setDouble(5, lab);
            pstmt.setString(6, grade);

            // Set params for UPDATE (if duplicated)
            pstmt.setDouble(7, cia);
            pstmt.setDouble(8, assign);
            pstmt.setDouble(9, lab);
            pstmt.setString(10, grade);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String calculateGrade(double total) {
        if (total >= 90)
            return "O";
        else if (total >= 80)
            return "A+";
        else if (total >= 70)
            return "A";
        else if (total >= 60)
            return "B+";
        else if (total >= 50)
            return "B";
        else
            return "F";
    }
}
