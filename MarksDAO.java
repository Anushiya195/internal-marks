package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarksDAO {

    public boolean addOrUpdateMarks(int studentId, int subId, double iat1, double iat2, double iat3, double attendance,
            double assignment) {
        String query = "INSERT INTO marks (student_id, sub_id, iat1, iat2, iat3, attendance, assignment, internal_total, grade) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE iat1=?, iat2=?, iat3=?, attendance=?, assignment=?, internal_total=?, grade=?";

        // Total Calculation Logic:
        // Average of 3 IATs (300/10 = 30 max) + Attend(10) + Assign(10) = Total 50
        double iatAvg = (iat1 + iat2 + iat3) / 10.0;
        double internalTotal = iatAvg + attendance + assignment;
        String grade = calculateGrade(internalTotal);

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set params for INSERT
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subId);
            pstmt.setDouble(3, iat1);
            pstmt.setDouble(4, iat2);
            pstmt.setDouble(5, iat3);
            pstmt.setDouble(6, attendance);
            pstmt.setDouble(7, assignment);
            pstmt.setDouble(8, internalTotal);
            pstmt.setString(9, grade);

            // Set params for UPDATE
            pstmt.setDouble(10, iat1);
            pstmt.setDouble(11, iat2);
            pstmt.setDouble(12, iat3);
            pstmt.setDouble(13, attendance);
            pstmt.setDouble(14, assignment);
            pstmt.setDouble(15, internalTotal);
            pstmt.setString(16, grade);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateIATMark(int studentId, int subId, String iatType, double score) {
        // iatType should be 'iat1', 'iat2', or 'iat3'
        String query = "INSERT INTO marks (student_id, sub_id, " + iatType + ") " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " + iatType + " = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, subId);
            pstmt.setDouble(3, score);
            pstmt.setDouble(4, score);

            pstmt.executeUpdate();

            // Recalculate total and grade after update
            return recalculateInternalTotal(studentId, subId);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean recalculateInternalTotal(int studentId, int subId) {
        String selectQuery = "SELECT iat1, iat2, iat3, attendance, assignment FROM marks WHERE student_id = ? AND sub_id = ?";
        String updateQuery = "UPDATE marks SET internal_total = ?, grade = ? WHERE student_id = ? AND sub_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement selectPstmt = conn.prepareStatement(selectQuery)) {

            selectPstmt.setInt(1, studentId);
            selectPstmt.setInt(2, subId);

            try (var rs = selectPstmt.executeQuery()) {
                if (rs.next()) {
                    double iat1 = rs.getDouble("iat1");
                    double iat2 = rs.getDouble("iat2");
                    double iat3 = rs.getDouble("iat3");
                    double attendance = rs.getDouble("attendance");
                    double assignment = rs.getDouble("assignment");

                    double iatAvg = (iat1 + iat2 + iat3) / 10.0;
                    double internalTotal = iatAvg + attendance + assignment;
                    String grade = calculateGrade(internalTotal);

                    try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                        updatePstmt.setDouble(1, internalTotal);
                        updatePstmt.setString(2, grade);
                        updatePstmt.setInt(3, studentId);
                        updatePstmt.setInt(4, subId);
                        updatePstmt.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String calculateGrade(double total) {
        if (total >= 40)
            return "O";
        else if (total >= 30)
            return "A";
        else if (total >= 20)
            return "B";
        else if (total >= 15)
            return "C";
        else
            return "F";
    }
}
