package com.ims.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ims.dao.StudentDAO;
import com.ims.model.Student;

@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String regNo = request.getParameter("regNo");
        String name = request.getParameter("name");
        String deptStr = request.getParameter("dept"); // Assuming generic mapping logic here
        int semester = Integer.parseInt(request.getParameter("semester"));

        // Simple logic to map Dept Name to ID (In real app, fetch from DB)
        int deptId = "CS".equals(deptStr) ? 1 : 2;

        Student newStudent = new Student(regNo, name, deptId, semester);
        boolean success = studentDAO.addStudent(newStudent);

        if (success) {
            response.getWriter().write("success");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("failure");
        }
    }
}
