package com.yourpackage.model;

import java.math.BigDecimal;

public class Customer {
	private String customer_id;
    private String full_name;
    private String address;
    private String mobile_no;
    private String email_id;
    private String account_type;
    private BigDecimal initial_balance;
    private String date_of_birth;
    private String id_proof;
    private String accountNo;
    private String password;
    private String temporary_password;
    
  

    public Customer(String customer_id, String full_name, String address, String mobile_no, String email_id, String account_type,BigDecimal initial_balance2, String date_of_birth, String id_proof, String accountNo, String password, String temporary_password) {
        this.customer_id = customer_id;
        this.full_name = full_name;
        this.address = address;
        this.mobile_no = mobile_no;
        this.email_id = email_id;
        this.account_type = account_type;
        this.initial_balance = initial_balance2;
        this.date_of_birth = date_of_birth;
        this.id_proof = id_proof;
        this.accountNo = accountNo;
        this.password = password;
        this.temporary_password=temporary_password;
    }
    public Customer(String customer_id2, String full_name2, String address2, String mobile_no2, String email_id2,
			String account_type2, double initial_balance2, String date_of_birth2, String id_proof2, String account_no,
			String temporary_password2, String temporary_password3) {
		// TODO Auto-generated constructor stub
	}
	public String getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobile_no;
    }

    public void setMobileNo(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getEmailId() {
        return email_id;
    }

    public void setEmailId(String email_id) {
        this.email_id = email_id;
    }

    public String getAccountType() {
        return account_type;
    }

    public void setAccountType(String account_type) {
        this.account_type = account_type;
    }

    public BigDecimal getInitialBalance() {
        return initial_balance;
    }

    public void setInitialBalance(BigDecimal initial_balance) {
        this.initial_balance = initial_balance;
    }

    public String getDateOfBirth() {
        return date_of_birth;
    }

    public void setDateOfBirth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getIdProof() {
        return id_proof;
    }

    public void setIdProof(String id_proof) {
        this.id_proof = id_proof;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getTemporaryPassword() {
        return temporary_password;
    }

    public void setTemporaryPassword(String temporary_password) {
        this.temporary_password = temporary_password;
    }
}
