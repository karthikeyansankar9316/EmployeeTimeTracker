package com.yourpackage.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yourpackage.model.Customer;
import com.yourpackage.util.DBConnection;

public class CustomerDAO {

    // Method to register a new customer
	public boolean registerCustomer(Customer customer) {
	    boolean status = false;
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(
	                 "INSERT INTO Customer (customer_id, full_name, address, mobile_no, email_id, account_type, initial_balance, date_of_birth, id_proof, account_no, password, temporary_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
	        ps.setString(1, customer.getCustomerId());
	        ps.setString(2, customer.getFullName());
	        ps.setString(3, customer.getAddress());
	        ps.setString(4, customer.getMobileNo());
	        ps.setString(5, customer.getEmailId());
	        ps.setString(6, customer.getAccountType());
	        ps.setBigDecimal(7, customer.getInitialBalance());
	        ps.setString(8, customer.getDateOfBirth());
	        ps.setString(9, customer.getIdProof());
	        ps.setString(10, customer.getAccountNo());
	        ps.setString(11, customer.getPassword());
	        ps.setString(12, customer.getTemporaryPassword());

	        int rows = ps.executeUpdate();
	        status = rows > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return status;
	}


    // Method to update customer details (except password)
    public boolean updateCustomerDetails(Customer customer) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE Customer SET full_name=?, address=?, mobile_no=?, email_id=?, account_type=?, initial_balance=?, date_of_birth=?, id_proof=? WHERE customer_id=?")) {
            ps.setString(1, customer.getFullName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getMobileNo());
            ps.setString(4, customer.getEmailId());
            ps.setString(5, customer.getAccountType());
            ps.setBigDecimal	(6, customer.getInitialBalance());
            ps.setString(7, customer.getDateOfBirth());
            ps.setString(8, customer.getIdProof());
            ps.setString(9, customer.getCustomerId());

            int rows = ps.executeUpdate();
            status = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Method to update customer password
    public boolean updatePassword(String customerId, String currentPassword, String newPassword) {
        boolean status = false;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE Customer SET password=? WHERE customer_id=? AND password=?")) {
            ps.setString(1, newPassword);
            ps.setString(2, customerId);
            ps.setString(3, currentPassword);

            int rows = ps.executeUpdate();
            status = rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // Method to validate customer credentials
    public boolean validateCustomer(String customerId, String password) {
        boolean isValid = false;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT customer_id FROM Customer WHERE customer_id=? AND password=?")) {
            ps.setString(1, customerId);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            isValid = rs.next(); // true if customer exists with the given credentials
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    // Method to retrieve customer by customer_id
    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM Customer WHERE customer_id=?")) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                        rs.getString("customer_id"),
                        rs.getString("full_name"),
                        rs.getString("address"),
                        rs.getString("mobile_no"),
                        rs.getString("email_id"),
                        rs.getString("account_type"),
                        rs.getBigDecimal("initial_balance"),
                        rs.getString("date_of_birth"),
                        rs.getString("id_proof"),
                        rs.getString("account_no"),
                        rs.getString("password"),
                        rs.getString("temporary_password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
        
    public boolean depositAmount(String customerId, double amount) {
        boolean status = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Update customer balance
            String updateBalanceQuery = "UPDATE customer SET initial_balance = initial_balance + ? WHERE customer_id = ?";
            ps = con.prepareStatement(updateBalanceQuery);
            ps.setDouble(1, amount);
            ps.setString(2, customerId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Insert transaction record
                String insertTransactionQuery = "INSERT INTO transaction (customer_id, transaction_type, amount) VALUES (?, 'Deposit', ?)";
                ps = con.prepareStatement(insertTransactionQuery);
                ps.setString(1, customerId);
                ps.setDouble(2, amount);
                ps.executeUpdate();

                con.commit(); // Commit transaction
                status = true;
            } else {
                con.rollback(); // Rollback transaction if update fails
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback transaction on exception
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBConnection.closeResources(con, ps, null);
        }
        return status;
        }

 // Method to withdraw amount from customer's balance
    public boolean withdrawAmount(String customerId, BigDecimal amount) {
        boolean status = false;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Check if customer exists and has sufficient balance
            Customer customer = getCustomerById(customerId);
            if (customer == null) {
                return false; // Customer not found
            }

            BigDecimal currentBalance = customer.getInitialBalance();
            if (currentBalance == null || currentBalance.compareTo(amount) < 0) {
                return false; // Insufficient balance or null balance
            }

            // Update customer balance
            String updateBalanceQuery = "UPDATE customer SET initial_balance = initial_balance - ? WHERE customer_id = ?";
            ps = con.prepareStatement(updateBalanceQuery);
            ps.setBigDecimal(1, amount);
            ps.setString(2, customerId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Insert transaction record
                String insertTransactionQuery = "INSERT INTO transaction (customer_id, transaction_type, amount) VALUES (?, 'Withdrawal', ?)";
                ps = con.prepareStatement(insertTransactionQuery);
                ps.setString(1, customerId);
                ps.setBigDecimal(2, amount);
                ps.executeUpdate();

                con.commit(); // Commit transaction
                status = true;
            } else {
                con.rollback(); // Rollback transaction if update fails
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback transaction on exception
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBConnection.closeResources(con, ps, null);
        }
        return status;
    }
    public boolean closeAccount(String customerId) {
        boolean status = false;
        Connection con = null;
        PreparedStatement deleteTransactionsPs = null;
        PreparedStatement deleteCustomerPs = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Delete related transactions
            String deleteTransactionsQuery = "DELETE FROM transaction WHERE customer_id = ?";
            deleteTransactionsPs = con.prepareStatement(deleteTransactionsQuery);
            deleteTransactionsPs.setString(1, customerId);
            deleteTransactionsPs.executeUpdate();

            // Delete customer account
            String deleteCustomerQuery = "DELETE FROM customer WHERE customer_id = ?";
            deleteCustomerPs = con.prepareStatement(deleteCustomerQuery);
            deleteCustomerPs.setString(1, customerId);
            int rows = deleteCustomerPs.executeUpdate();

            status = rows > 0;
            con.commit(); // Commit transaction
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) {
                    con.rollback(); // Rollback transaction on exception
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            DBConnection.closeResources(con, deleteCustomerPs, null);
            DBConnection.closeResources(null, deleteTransactionsPs, null);
        }
        return status;
    }
    public boolean changePassword(String customer_id, String oldPassword, String newPassword) throws SQLException {
    	System.out.print(customer_id);
        Connection connection = DBConnection.getConnection();
        String checkPasswordQuery = "SELECT password FROM customer WHERE customer_id = ?";
        String updatePasswordQuery = "UPDATE customer SET password = ? WHERE customer_id = ?";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkPasswordQuery);
             PreparedStatement updateStmt = connection.prepareStatement(updatePasswordQuery)) {

            checkStmt.setString(1, customer_id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
            	System.out.println("okay");
                String currentPassword = rs.getString("password");

                if (currentPassword.equals(oldPassword)) {
                
                    updateStmt.setString(1, newPassword);
                    updateStmt.setString(2, customer_id);
                    int rowsUpdated = updateStmt.executeUpdate();

                    return rowsUpdated > 0;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } finally {
            connection.close();
        }
    }
    




    }

