package com.ims.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ims.dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        // Authenticate
        String role = userDAO.authenticateUser(username, pass);

        if (role != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            session.setAttribute("role", role);

            // Redirect based on role
            if ("ADMIN".equalsIgnoreCase(role)) {
                response.sendRedirect("admin_dashboard.html"); // In real app: admin_dashboard.jsp
            } else if ("FACULTY".equalsIgnoreCase(role)) {
                response.sendRedirect("faculty_dashboard.html");
            } else if ("STUDENT".equalsIgnoreCase(role)) {
                response.sendRedirect("student_dashboard.html");
            }
        } else {
            response.sendRedirect("index.html?error=InvalidCredentials");
        }
    }
}
