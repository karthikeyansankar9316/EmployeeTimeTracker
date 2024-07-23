package com.employee.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/employee_task_tracking";
	private static final String USER = "root";
	public static final String PASSWORD = "1234";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public static void close(Connection conn, PreparedStatement... statements) {
		try {
			if (statements != null) {
				for (PreparedStatement stmt : statements) {
					if (stmt != null) {
						stmt.close();
					}
				}
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
