package com.employee.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.employee.model.Task;
import com.employee.util.DatabaseConnection;
import com.employee.util.TaskDAO;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewTasksServlet")
public class ViewTasksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchType = request.getParameter("searchType");
		String searchValue = request.getParameter("searchValue");
		String timeFrame = request.getParameter("timeFrame");

		Connection connection = null;
		List<Task> tasks = null;
		try {
			connection = DatabaseConnection.getConnection();
			TaskDAO taskDAO = new TaskDAO(connection);

			// Validate searchValue based on searchType
			boolean isValid = false;
			if ("project".equals(searchType)) {
				isValid = taskDAO.isValidProject(searchValue);
			} else if ("employee".equals(searchType)) {
				isValid = taskDAO.isValidEmployee(searchValue);
			}

			if (!isValid) {
				request.setAttribute("errorMessage", "Invalid " + searchType + " specified.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}

			// Fetch tasks based on search criteria
			if ("project".equals(searchType)) {
				tasks = taskDAO.getTasksByProjectAndTimeFrame(searchValue, timeFrame);
			} else if ("employee".equals(searchType)) {
				tasks = taskDAO.getTasksByEmployeeAndTimeFrame(searchValue, timeFrame);
			}

			if (tasks == null || tasks.isEmpty()) {
				request.setAttribute("errorMessage", "No tasks found for the specified criteria.");
				request.getRequestDispatcher("error.jsp").forward(request, response);
				return;
			}

			// Prepare data for charts
			Map<String, Object> chartData = prepareChartData(tasks, searchType, searchValue, timeFrame);

			// Convert chart data to JSON and set as request attribute
			Gson gson = new Gson();
			String jsonData = gson.toJson(chartData);
			request.setAttribute("chartData", jsonData);

			request.getRequestDispatcher("viewTasks.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		} finally {
			DatabaseConnection.close(connection);
		}
	}

	private Map<String, Object> prepareChartData(List<Task> tasks, String searchType, String searchValue,
			String timeFrame) {
		// Map for storing the chart data
		Map<String, Object> chartData = new HashMap<>();

		// Prepare data for pie chart and bar chart
		List<String> labels;
		List<Integer> data;
		List<String> backgroundColors = new ArrayList<>();

		if ("project".equals(searchType)) {
			// Group tasks by employee and sum their hours
			Map<String, Integer> employeeHours = tasks.stream()
					.collect(Collectors.groupingBy(Task::getEmpId, Collectors.summingInt(Task::getHoursSpent)));

			labels = new ArrayList<>(employeeHours.keySet());
			data = new ArrayList<>(employeeHours.values());
			for (int i = 0; i < labels.size(); i++) {
				backgroundColors.add("rgba(" + (Math.random() * 255) + "," + (Math.random() * 255) + ","
						+ (Math.random() * 255) + ", 0.8)");
			}
		} else {
			labels = tasks.stream().map(Task::getDescription).collect(Collectors.toList());
			data = tasks.stream().map(Task::getHoursSpent).collect(Collectors.toList());
			for (int i = 0; i < labels.size(); i++) {
				backgroundColors.add("rgba(" + (Math.random() * 255) + "," + (Math.random() * 255) + ","
						+ (Math.random() * 255) + ", 0.8)");
			}
		}

		chartData.put("labels", labels);
		chartData.put("data", data);
		chartData.put("backgroundColors", backgroundColors);

		return chartData;
	}
}
