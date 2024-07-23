package com.employee.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

@WebServlet("/ManageTaskServlet")
public class ManageTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String taskId = request.getParameter("id");

		try (Connection connection = DatabaseConnection.getConnection()) {
			TaskDAO taskDAO = new TaskDAO(connection);
			HttpSession session = request.getSession();
			String empId = ((User) session.getAttribute("user")).getEmpId();

			if ("edit".equals(action)) {
				response.sendRedirect("editTask.jsp?id=" + taskId);
			} else if ("delete".equals(action)) {
				taskDAO.deleteTask(taskId);
				response.sendRedirect("manageTasks.jsp");
			} else if ("update".equals(action)) {
				String date = request.getParameter("date");
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				String category = request.getParameter("category");
				String description = request.getParameter("description");

				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

				LocalDate localDate = LocalDate.parse(date, dateFormatter);
				LocalTime localStartTime = LocalTime.parse(startTime, timeFormatter);
				LocalTime localEndTime = LocalTime.parse(endTime, timeFormatter);

				// Validate task duration
				long newTaskDuration = Duration.between(localStartTime, localEndTime).toHours();
				if (newTaskDuration > 8) {
					request.setAttribute("error", "Task duration cannot exceed 8 hours.");
					request.getRequestDispatcher("editTask.jsp?id=" + taskId).forward(request, response);
					return;
				}

				// Check for duplicate task
				if (taskDAO.isTaskDuplicate(empId, localDate, localStartTime, localEndTime, taskId)) {
					request.setAttribute("error", "Duplicate task entry detected.");
					request.getRequestDispatcher("editTask.jsp?id=" + taskId).forward(request, response);
					return;
				}

				// Check for overlapping tasks and total duration for the day
				List<Task> tasksForTheDay = taskDAO.getTasksForDate(empId, localDate);
				long totalDurationForTheDay = newTaskDuration; // Start with the duration of the new task
				for (Task task : tasksForTheDay) {
					// Ignore the current task being updated
					if (task.getId() != null && task.getId().equals(taskId)) {
						continue;
					}
					LocalTime existingStartTime = task.getStartTime();
					LocalTime existingEndTime = task.getEndTime();
					if (localStartTime.isBefore(existingEndTime) && localEndTime.isAfter(existingStartTime)) {
						request.setAttribute("error", "Task times overlap with an existing task.");
						request.getRequestDispatcher("editTask.jsp?id=" + taskId).forward(request, response);
						return;
					}
					long taskDuration = Duration.between(existingStartTime, existingEndTime).toHours();
					totalDurationForTheDay += taskDuration;
				}

				if (totalDurationForTheDay > 8) {
					request.setAttribute("error", "Total task duration for the day cannot exceed 8 hours.");
					request.getRequestDispatcher("editTask.jsp?id=" + taskId).forward(request, response);
					return;
				}

				// Update task
				Task task = new Task();
				task.setId(taskId);
				task.setDate(localDate);
				task.setStartTime(localStartTime);
				task.setEndTime(localEndTime);
				task.setCategory(category);
				task.setDescription(description);

				taskDAO.updateTask(task);

				response.sendRedirect("manageTasks.jsp");
			}
		} catch (SQLException e) {
			throw new ServletException("Unable to manage task", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
