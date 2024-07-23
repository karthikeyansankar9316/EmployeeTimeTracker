package com.employee.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.employee.model.User;
import com.employee.util.DatabaseConnection;
import com.employee.util.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageEmployeeServlet")
public class ManageEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String empID = request.getParameter("id");

		try {
			switch (action) {
			case "delete":
				handleDeleteEmployee(request, response, empID);
				break;
			case "edit":
				handleEditEmployee(request, response, empID);
				break;
			case "update":
				handleUpdateEmployee(request, response);
				break;
			default:
				response.sendRedirect("adminHome.jsp");
				break;
			}
		} catch (SQLException e) {
			throw new ServletException("Database error while managing employees", e);
		}
	}

	private void handleDeleteEmployee(HttpServletRequest request, HttpServletResponse response, String empID)
			throws SQLException, IOException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			UserDAO userDAO = new UserDAO(connection);
			boolean deleted = userDAO.deleteUser(empID);
			if (deleted) {
				response.getWriter().write("Employee deleted successfully.");
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Failed to delete the employee.");
			}
		}
	}

	private void handleEditEmployee(HttpServletRequest request, HttpServletResponse response, String empID)
			throws SQLException, IOException, ServletException {
		try (Connection connection = DatabaseConnection.getConnection()) {
			UserDAO userDAO = new UserDAO(connection);
			User user = userDAO.getUserById(empID);
			if (user != null) {
				request.setAttribute("user", user);
				request.getRequestDispatcher("/editEmployee.jsp").forward(request, response);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().write("Employee not found.");
			}
		}
	}

	private void handleUpdateEmployee(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String empID = request.getParameter("id");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = new User();
		user.setEmpId(empID);
		user.setName(name);
		user.setRole(role);
		user.setUsername(username);
		user.setPassword(password);

		try (Connection connection = DatabaseConnection.getConnection()) {
			UserDAO userDAO = new UserDAO(connection);
			boolean updated = userDAO.updateUser(user);
			if (updated) {
				response.sendRedirect("adminHome.jsp");
			} else {
				response.setContentType("text/html");
				response.getWriter().write("<script type='text/javascript'>");
				response.getWriter().write("alert('Failed to update the employee.');");
				response.getWriter().write("window.location.href = 'editEmployee.jsp?id=" + empID + "';");
				response.getWriter().write("</script>");
			}
		}
	}
}
