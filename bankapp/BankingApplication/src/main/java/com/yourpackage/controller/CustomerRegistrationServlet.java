package com.yourpackage.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.CustomerDAO;
import com.yourpackage.model.Customer;

@WebServlet("/CustomerRegistrationServlet")
public class CustomerRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract parameters from request
        String customer_id = request.getParameter("customer_id");
        String full_name = request.getParameter("full_name"); // Make sure this matches the JSP form
        String address = request.getParameter("address");
        String mobile_no = request.getParameter("mobile_no");
        String email_id = request.getParameter("email_id");
        String account_type = request.getParameter("account_type");
        double initial_balance = Double.parseDouble(request.getParameter("initial_balance"));
        String date_of_birth = request.getParameter("date_of_birth");
        String id_proof = request.getParameter("id_proof");

        // Generate unique account number and temporary password
        String account_no = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String temporary_password = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

        // Create Customer object
        Customer customer = new Customer(customer_id, full_name, address, mobile_no, email_id, account_type, BigDecimal.valueOf(initial_balance), date_of_birth, id_proof, account_no, temporary_password, temporary_password);

        // Create CustomerDAO instance
        CustomerDAO customerDAO = new CustomerDAO();

        try {
            // Register customer in database
            boolean isRegistered = customerDAO.registerCustomer(customer);

            if (isRegistered) {
                // If registration is successful, redirect to success page with account info
                response.sendRedirect("customer_registration_success.jsp?accountNo=" + account_no + "&tempPassword=" + temporary_password);
            } else {
                // If registration fails, redirect back to registration page with error message
                response.sendRedirect("customer_registration.jsp?error=Registration failed. Please try again.");
            }
        } catch (Exception e) {
            // Handle any exceptions (e.g., database errors)
            e.printStackTrace(); // For debugging purposes; handle exception appropriately in production
            response.sendRedirect("customer_registration.jsp?error=Unexpected error occurred. Please try again.");
        }
    }
}
