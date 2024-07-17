package com.yourpackage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourpackage.dao.TransactionDAO;
import com.yourpackage.model.Transaction;

@WebServlet("/DownloadTransactions")
public class DownloadTransactionsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");
        int customerId = Integer.parseInt(customerIdStr); // Convert String to int

        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getTransactionsByCustomerId(customerId);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=transaction_statement.csv");

        try (PrintWriter writer = response.getWriter()) {
            // Write CSV header
            writer.println("Transaction ID, Transaction Type, Amount, Date");

            // Write CSV rows
            for (Transaction transaction : transactions) {
                writer.println(
                    transaction.getTransactionId() + "," +
                    transaction.getTransactionType() + "," +
                    transaction.getAmount() + "," +
                    transaction.getTransactionDate()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
