package com.yourpackage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.CustomerDAO;

@WebServlet("/CloseAccount")
public class CloseAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");

        // Log received parameters for debugging
        System.out.println("Received customerId: " + customerId);

        // Validate parameters
        if (customerId == null || customerId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.closeAccount(customerId);

        if (success) {
            response.sendRedirect("close_account_success.jsp");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account closure failed");
        }
    }
}
