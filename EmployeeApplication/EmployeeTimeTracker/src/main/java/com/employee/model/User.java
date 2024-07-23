package com.employee.model;

public class User {
	private String emp_id;
	private String name;
	private String role;
	private String username;
	private String password;

	// Getters and setters for all fields

	public String getEmpId() {
		return emp_id;
	}

	public void setEmpId(String id) {
		this.emp_id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
