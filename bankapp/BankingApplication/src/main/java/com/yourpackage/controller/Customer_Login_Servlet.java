package com.yourpackage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yourpackage.dao.CustomerDAO;
import com.yourpackage.model.Customer;

@WebServlet("/Customer_Login_Servlet")
public class Customer_Login_Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public void init() {
        customerDAO = new CustomerDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String password = request.getParameter("password");

        try {
            boolean isValidCustomer = customerDAO.validateCustomer(customerId, password);

            if (isValidCustomer) {
                Customer customer = customerDAO.getCustomerById(customerId);
                

                if (customer != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("customerId", customer.getCustomerId());
                    session.setAttribute("FullName", customer.getFullName());
                    session.setAttribute("customerAccountNo", customer.getAccountNo());
                    session.setAttribute("customerBalance", customer.getInitialBalance());
                    session.setAttribute("password", customer.getPassword());
                 // In Customer_Login_Servlet
                    System.out.println("Logged in customer ID: " + customer.getCustomerId());


                    response.sendRedirect(request.getContextPath() + "/customer_dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/customer_login.jsp?error=Customer details not found");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/customer_login.jsp?error=Invalid username or password");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/customer_login.jsp?error=Unexpected error occurred. Please try again.");
        }
    }
}
