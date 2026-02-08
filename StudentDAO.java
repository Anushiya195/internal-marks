package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ims.model.Student; // Assuming Student model

public class StudentDAO {

    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (register_number, full_name, dept_id, semester) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getRegisterNumber());
            pstmt.setString(2, student.getFullName());
            pstmt.setInt(3, student.getDeptId());
            pstmt.setInt(4, student.getSemester());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Additional Method: Get All Students for Faculty View
    // ...
}
