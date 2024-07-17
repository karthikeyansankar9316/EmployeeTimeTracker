package com.yourpackage.model;

public class Transaction {
    private int transactionId;
    private int customerId;
    private String transactionType;
    private double amount;
    private String transactionDate;

    // Default constructor
    public Transaction() {}

    // Parameterized constructor
    public Transaction(int transactionId, int customerId, String transactionType, double amount, String transactionDate) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    // Getters and setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
