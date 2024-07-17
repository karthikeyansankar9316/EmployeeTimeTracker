package com.yourpackage.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.CustomerDAO;

@WebServlet("/CustomPassword")
public class CustomPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("account_no");
        String temporaryPassword = request.getParameter("temporary_password");
        String newPassword = request.getParameter("new_password");

        CustomerDAO customerDAO = new CustomerDAO();
        boolean passwordUpdated = customerDAO.updatePassword(accountNo, temporaryPassword, newPassword);

        if (passwordUpdated) {
            response.sendRedirect("customer_login.jsp?message=Password updated successfully. Login with your new password.");
        } else {
            response.sendRedirect("customer_password_setup.jsp?error=Failed to update password. Please try again.");
        }
    }
}
