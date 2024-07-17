package com.yourpackage.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import com.yourpackage.dao.CustomerDAO;

@WebServlet("/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // HttpSession session = request.getSession();
        //String customer_id = (String) session.getAttribute("customer_id");
        String customer_id = request.getParameter("customer_id");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        CustomerDAO customerDAO = new CustomerDAO();

        try {
            boolean isPasswordChanged = customerDAO.changePassword(customer_id, oldPassword, newPassword);

            if (isPasswordChanged) {
                response.sendRedirect("customer_dashboard.jsp?message=Password successfully changed.");
            } else {
                response.sendRedirect("customer_dashboard.jsp?error=Password change failed. Please check your old password and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("customer_dashboard.jsp?error=Unexpected error occurred. Please try again.");
        }
    }
}
