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
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try (Connection connection = DatabaseConnection.getConnection()) {
			UserDAO userDAO = new UserDAO(connection);
			User user = userDAO.validateUser(username, password);

			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
                session.setAttribute("username", username);
				if ("Admin".equalsIgnoreCase(user.getRole())) {
					response.sendRedirect("adminHome.jsp");
				} else {
					response.sendRedirect("employeeHome.jsp");
				}
			} else {
				request.setAttribute("error", "Invalid username or password.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException("Login failed", e);
		}
	}
}
