package com.yourpackage.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yourpackage.model.Transaction;
import com.yourpackage.util.DBConnection;

public class TransactionDAO {

    // Method to retrieve transactions by customer ID
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE customer_id = ? ORDER BY transaction_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                String transactionType = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                String transactionDate = rs.getString("transaction_date");

                Transaction transaction = new Transaction(transactionId, customerId, transactionType, amount, transactionDate);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // You can add more methods as needed, such as inserting new transactions, updating, etc.
}
