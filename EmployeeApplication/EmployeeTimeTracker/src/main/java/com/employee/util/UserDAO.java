package com.employee.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.employee.model.User;

public class UserDAO {
	private Connection connection;

	public UserDAO(Connection connection) {
		this.connection = connection;
	}

	public String addUser(User user) throws SQLException {
		String employeeId = generateEmployeeId();
		String sql = "INSERT INTO users (name, role, username, password, employee_id) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getRole());
			stmt.setString(3, user.getUsername());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, employeeId);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("Creating user failed, no rows affected.");
			}

			return employeeId;
		}
	}

	private String generateEmployeeId() {
		// Generate a unique employee ID, e.g., "EMP" followed by a random 7-digit
		// number
		return "EMP" + (int) (Math.random() * 1_000_0000);
	}

	public User validateUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setEmpId(rs.getString("employee_id"));
					user.setName(rs.getString("name"));
					user.setRole(rs.getString("role"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			}
		}
		return null;
	}

	public boolean updateUser(User user) throws SQLException {
		String sql = "UPDATE users SET name = ?, role = ?, username = ?, password = ? WHERE employee_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getRole());
			stmt.setString(3, user.getUsername());
			stmt.setString(4, user.getPassword());
			stmt.setString(5, user.getEmpId());
			stmt.executeUpdate();
		}
		return true;
	}

	public User getUserByEmpId(String emp_id) throws SQLException {
		String sql = "SELECT * FROM users WHERE employee_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, emp_id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setEmpId(rs.getString("employee_id"));
					user.setName(rs.getString("name"));
					user.setRole(rs.getString("role"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			}
		}
		return null;
	}

	public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setEmpId(rs.getString("employee_id"));
					user.setName(rs.getString("name"));
					user.setRole(rs.getString("role"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			}
		}
		return null;
	}

	public List<User> getAllUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users where role= ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, "Employee");
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();
					user.setEmpId(rs.getString("employee_id"));
					user.setName(rs.getString("name"));
					user.setRole(rs.getString("role"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					users.add(user);
				}
			}
		}
		return users;
	}

	public boolean deleteUser(String empID) throws SQLException {
		String sql = "DELETE FROM users WHERE employee_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, empID);
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public User getUserById(String empID) throws SQLException {
		String sql = "SELECT * FROM users WHERE employee_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, empID);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					User user = new User();
					user.setEmpId(resultSet.getString("employee_id"));
					user.setName(resultSet.getString("name"));
					user.setRole(resultSet.getString("role"));
					user.setUsername(resultSet.getString("username"));
					user.setPassword(resultSet.getString("password"));
					return user;
				} else {
					return null;
				}
			}
		}
	}

	// Method to fetch all employees
	public List<User> getAllEmployees() throws SQLException {
		List<User> employees = new ArrayList<>();
		String sql = "SELECT * FROM users WHERE role = 'Employee'";
		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				User user = new User();
				user.setEmpId(rs.getString("employee_id"));
				user.setName(rs.getString("name"));
				user.setRole(rs.getString("role"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				employees.add(user);
			}
		}
		return employees;
	}
}
