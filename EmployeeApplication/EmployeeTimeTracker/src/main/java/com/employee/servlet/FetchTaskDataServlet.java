package com.employee.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.employee.model.Task;
import com.employee.model.User;
import com.employee.util.DatabaseConnection;
import com.employee.util.TaskDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/FetchTaskDataServlet")
public class FetchTaskDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> responseData = new HashMap<>();
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		String empId = currentUser.getEmpId();

		try (Connection connection = DatabaseConnection.getConnection()) {
			TaskDAO taskDAO = new TaskDAO(connection);

			// Fetch daily, weekly, and monthly tasks data
			LocalDate today = LocalDate.now();
			LocalDate startDate = today.with(ChronoField.DAY_OF_WEEK, 1); // Start of the current week (Monday)
			LocalDate endDate = startDate.plusDays(6); // End of the current week (Sunday)

			// Fetch tasks for different time frames
			List<Task> dailyTasks = taskDAO.getTasksByDateRangeAndEmployee(today, today, empId); // Daily tasks
			List<Task> weeklyTasks = taskDAO.getTasksByDateRangeAndEmployee(startDate, endDate, empId); // Weekly tasks
			List<Task> monthlyTasks = taskDAO.getTasksByMonthAndEmployee(today.getMonthValue(), today.getYear(), empId); // Monthly
																															// tasks

			// Prepare data for charts
			responseData.put("daily", prepareChartData(dailyTasks));
			responseData.put("weekly", prepareWeeklyChartData(weeklyTasks, startDate)); // Enhanced weekly data
			responseData.put("monthly", prepareChartData(monthlyTasks, today)); // Pass 'today' for monthly tasks

			// Convert responseData to JSON and write to response
			Gson gson = new Gson();
			String jsonData = gson.toJson(responseData);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(jsonData);
			out.flush();

		} catch (SQLException e) {
			e.printStackTrace(); // Handle properly in production code
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private Map<String, Object> prepareChartData(List<Task> tasks) {
		Map<String, Object> chartData = new HashMap<>();
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();

		for (Task task : tasks) {
			labels.add(task.getDescription()); // Example: Use task details for labels
			data.add(task.getHoursSpent()); // Example: Use hours spent on task for data
		}

		chartData.put("labels", labels);
		chartData.put("data", data);

		return chartData;
	}

	private Map<String, Object> prepareWeeklyChartData(List<Task> tasks, LocalDate startDate) {
		Map<String, Object> chartData = new HashMap<>();
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		List<String> backgroundColors = new ArrayList<>();

		// Initialize labels and data for each day of the week
		for (int i = 0; i < 7; i++) {
			LocalDate date = startDate.plusDays(i);
			labels.add("Day " + (i + 1)); // Day 1, Day 2, ..., Day 7
			data.add(0); // Initialize data with 0 hours
			backgroundColors.add("rgba(255, 206, 86, 0.8)");
			date.isAfter(date);// Default yellow for future days
		}

		// Update data and colors for days with tasks
		for (Task task : tasks) {
			int dayIndex = task.getDate().getDayOfWeek().getValue() - 1; // Get the day index (0-6)
			data.set(dayIndex, task.getHoursSpent());
			backgroundColors.set(dayIndex, "rgba(75, 192, 192, 0.8)"); // Green for days with tasks
		}

		chartData.put("labels", labels);
		chartData.put("data", data);
		chartData.put("backgroundColors", backgroundColors);

		return chartData;
	}

	private Map<String, Object> prepareChartData(List<Task> tasks, LocalDate today) {
		Map<String, Object> chartData = new HashMap<>();
		List<String> labels = new ArrayList<>();
		List<Integer> data = new ArrayList<>();
		List<String> backgroundColors = new ArrayList<>();

		int daysInMonth = today.lengthOfMonth();

		// Initialize data and labels for each day of the month
		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate date = LocalDate.of(today.getYear(), today.getMonth(), day);
			labels.add(date.toString());

			// Default colors for past days with no tasks (red), future days (yellow), and
			// days with tasks (green)
			if (date.isAfter(today)) {
				backgroundColors.add("rgba(255, 206, 86, 0.8)"); // Yellow for future dates
				data.add(0);
			} else {
				backgroundColors.add("rgba(255, 99, 132, 0.8)"); // Red for past dates with no tasks
				data.add(0);
			}
		}

		// Update the colors and data for days with tasks
		for (Task task : tasks) {
			int day = task.getDate().getDayOfMonth() - 1;
			backgroundColors.set(day, "rgba(75, 192, 192, 0.8)"); // Green for days with tasks
			data.set(day, task.getHoursSpent());
		}

		chartData.put("labels", labels);
		chartData.put("data", data);
		chartData.put("backgroundColors", backgroundColors);

		return chartData;
	}
}
