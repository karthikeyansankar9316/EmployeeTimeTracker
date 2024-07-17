package com.yourpackage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.model.Transaction;
import com.yourpackage.util.DBConnection;

@WebServlet("/FetchTransactionsServlet")
public class FetchTransactionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String password = request.getParameter("password"); // Assuming password is passed securely

        // Validate customer credentials here (implement your own validation logic)
        boolean isAuthenticated = validateCustomer(customerId, password);

        if (!isAuthenticated) {
            // Handle unauthorized access (redirect, error message, etc.)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        ArrayList<Transaction> transactions = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM transaction WHERE customer_id = ? ORDER BY transaction_date DESC LIMIT 10";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(customerId));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toString());
                transaction.setTransactionType(rs.getString("transaction_type"));
                transaction.setAmount(rs.getDouble("amount"));
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Build JSON response manually
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (Transaction transaction : transactions) {
            jsonBuilder.append("{");
            jsonBuilder.append("\"transactionDate\":\"").append(transaction.getTransactionDate()).append("\",");
            jsonBuilder.append("\"transactionType\":\"").append(transaction.getTransactionType()).append("\",");
            jsonBuilder.append("\"amount\":").append(transaction.getAmount());
            jsonBuilder.append("},");
        }
        if (transactions.size() > 0) {
            jsonBuilder.deleteCharAt(jsonBuilder.length() - 1); // Remove trailing comma
        }
        jsonBuilder.append("]");

        // Set response content type
        response.setContentType("application/json");
        // Get PrintWriter
        PrintWriter out = response.getWriter();
        // Write JSON string to response
        out.print(jsonBuilder.toString());
        // Flush the output
        out.flush();
    }
    

    // Example method to validate customer credentials
    private boolean validateCustomer(String customerId, String password) {
        // Implement your own logic to validate customer credentials (query database, etc.)
        // For illustration, assuming a simple check based on hardcoded values
        return customerId.equals(customerId) && password.equals(password);
    }
}
