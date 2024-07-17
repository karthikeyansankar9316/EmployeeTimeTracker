package com.yourpackage.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.AdminDAO;
import com.yourpackage.model.Admin;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin admin = new Admin(username, password);
        AdminDAO adminDAO = new AdminDAO();

        try {
            boolean isValidAdmin = adminDAO.validateAdmin(admin);

            if (isValidAdmin) {
                // Store admin username in session
                request.getSession().setAttribute("adminUsername", username);
                response.sendRedirect(request.getContextPath() + "/admin_dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin_login.jsp?error=Invalid credentials");
            }
        } catch (Exception e) {
            // Handle database errors or other exceptions
            e.printStackTrace(); // For debugging purposes; handle exception appropriately in production
            response.sendRedirect(request.getContextPath() + "/admin_login.jsp?error=Unexpected error occurred");
        }
    }
}
