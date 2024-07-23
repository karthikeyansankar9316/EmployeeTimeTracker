package com.employee.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.employee.model.Task;
import com.employee.model.User;
import com.employee.util.DatabaseConnection;
import com.employee.util.TaskDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AddTaskServlet")
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		LocalDate taskDate = LocalDate.parse(request.getParameter("taskDate"));
		LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
		LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
		String category = request.getParameter("category");
		String description = request.getParameter("description");
		String projectName = request.getParameter("projectname");

		long newTaskDuration = Duration.between(startTime, endTime).toMinutes();
		if (newTaskDuration > 480) { // 8 hours = 480 minutes
			request.setAttribute("error", "Task duration cannot exceed 8 hours.");
			request.getRequestDispatcher("addTask.jsp").forward(request, response);
			return;
		}

		try (Connection connection = DatabaseConnection.getConnection()) {
			TaskDAO taskDAO = new TaskDAO(connection);

			// Check for duplicate or overlapping task
			List<Task> tasksForTheDay = taskDAO.getTasksForDate(user.getEmpId(), taskDate);
			long totalDurationForTheDay = 0;
			for (Task task : tasksForTheDay) {
				LocalTime existingStartTime = task.getStartTime();
				LocalTime existingEndTime = task.getEndTime();
				if (startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime)) {
					request.setAttribute("error", "Task times overlap with an existing task.");
					request.getRequestDispatcher("addTask.jsp").forward(request, response);
					return;
				}
				totalDurationForTheDay += Duration.between(existingStartTime, existingEndTime).toMinutes();
			}

			if (totalDurationForTheDay + newTaskDuration > 480) { // 8 hours = 480 minutes
				request.setAttribute("error", "Total task duration for the day cannot exceed 8 hours.");
				request.getRequestDispatcher("addTask.jsp").forward(request, response);
				return;
			}

			// Create new task and add it to the database
			Task task = new Task();
			task.setEmpId(user.getEmpId());
			task.setProjectName(projectName);
			task.setDate(taskDate);
			task.setStartTime(startTime);
			task.setEndTime(endTime);
			task.setCategory(category);
			task.setDescription(description);

			taskDAO.addTask(task);
		} catch (SQLException e) {
			throw new ServletException("Unable to add task", e);
		}

		response.sendRedirect("employeeHome.jsp");
	}
}
