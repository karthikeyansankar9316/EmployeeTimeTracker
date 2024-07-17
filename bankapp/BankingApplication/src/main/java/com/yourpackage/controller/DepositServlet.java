package com.yourpackage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.CustomerDAO;

@WebServlet("/DepositServlet")
public class DepositServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public DepositServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        double depositAmount = Double.parseDouble(request.getParameter("depositAmount"));

        CustomerDAO customerDAO = new CustomerDAO();
        boolean success = customerDAO.depositAmount(customerId, depositAmount);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (success) {
            out.println("<html><body>");
            out.println("<h2>Deposit Successful</h2>");
            out.println("<p>Amount Deposited: " + depositAmount + "</p>");
            out.println("<a href='customer_dashboard.jsp'>Go to Dashboard</a>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h2>Deposit Failed</h2>");
            out.println("<p>There was an error processing your deposit. Please try again.</p>");
            out.println("<a href='customer_dashboard.jsp'>Go to Dashboard</a>");
            out.println("</body></html>");
        }
    }
}
