package com.yourpackage.controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yourpackage.dao.CustomerDAO;


@WebServlet("/WithdrawServlet")
public class WithdrawServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String amountStr = request.getParameter("amount");

        // Log received parameters for debugging
        System.out.println("Received customerId: " + customerId);
        System.out.println("Received amount: " + amountStr);
        HttpSession session = request.getSession();
        session.setAttribute("amount", amountStr);
        

        // Validate parameters
        if (customerId == null || customerId.isEmpty() || amountStr == null || amountStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid amount format");
            return;
        }

        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.withdrawAmount(customerId, amount);

        if (success) {
        
        	response.sendRedirect("withdraw_success.jsp?amount=" + amountStr);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Withdrawal failed");
        }
    }
}
