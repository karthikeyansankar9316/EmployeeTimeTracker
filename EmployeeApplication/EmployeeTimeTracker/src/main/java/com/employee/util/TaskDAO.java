package com.employee.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.employee.model.User;
import com.employee.model.Task;

public class TaskDAO {
	private Connection connection;

	public TaskDAO(Connection connection) {
		this.connection = connection;
	}

	// Existing methods...

	public boolean isTaskDuplicate(String empId, LocalDate taskDate, LocalTime startTime, LocalTime endTime)
			throws SQLException {
		String query = "SELECT COUNT(*) FROM tasks WHERE employee_id = ? AND task_date = ? AND start_time = ? AND end_time = ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, empId);
			stmt.setDate(2, java.sql.Date.valueOf(taskDate));
			stmt.setTime(3, java.sql.Time.valueOf(startTime));
			stmt.setTime(4, java.sql.Time.valueOf(endTime));
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	public boolean isTaskDuplicate(LocalDate date, LocalTime startTime, LocalTime endTime, String taskId)
			throws SQLException {
		String query = "SELECT COUNT(*) FROM tasks WHERE task_date = ? AND (start_time < ? AND end_time > ?) AND id != ?";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDate(1, java.sql.Date.valueOf(date));
			stmt.setTime(2, java.sql.Time.valueOf(startTime));
			stmt.setTime(3, java.sql.Time.valueOf(endTime));
			stmt.setString(4, taskId);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	public boolean isTaskDuplicate(String empId, LocalDate date, LocalTime startTime, LocalTime endTime, String taskId)
			throws SQLException {
		// Check for overlapping tasks
		String countQuery = "SELECT COUNT(*) FROM tasks WHERE employee_id = ? AND task_date = ? AND "
				+ "((start_time <= ? AND end_time >= ?) OR (start_time < ? AND end_time > ?))";

		try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
			countStatement.setString(1, empId);
			countStatement.setDate(2, java.sql.Date.valueOf(date));
			countStatement.setTime(3, java.sql.Time.valueOf(startTime));
			countStatement.setTime(4, java.sql.Time.valueOf(startTime));
			countStatement.setTime(5, java.sql.Time.valueOf(endTime));
			countStatement.setTime(6, java.sql.Time.valueOf(endTime));

			try (ResultSet countResultSet = countStatement.executeQuery()) {
				countResultSet.next();
				int count = countResultSet.getInt(1);

				// If count is 0, there are no overlapping tasks
				if (count == 0) {
					return false;
				}

				// If taskId is null, any overlapping task is a duplicate
				if (taskId == null) {
					return true;
				}

				// Check if the current taskId is the same as any existing overlapping task
				String idQuery = "SELECT id FROM tasks WHERE employee_id = ? AND task_date = ? AND "
						+ "((start_time <= ? AND end_time >= ?) OR (start_time < ? AND end_time > ?))";

				try (PreparedStatement idStatement = connection.prepareStatement(idQuery)) {
					idStatement.setString(1, empId);
					idStatement.setDate(2, java.sql.Date.valueOf(date));
					idStatement.setTime(3, java.sql.Time.valueOf(startTime));
					idStatement.setTime(4, java.sql.Time.valueOf(startTime));
					idStatement.setTime(5, java.sql.Time.valueOf(endTime));
					idStatement.setTime(6, java.sql.Time.valueOf(endTime));

					try (ResultSet idResultSet = idStatement.executeQuery()) {
						while (idResultSet.next()) {
							String existingTaskId = idResultSet.getString("id");
							if (taskId.equals(existingTaskId)) {
								return false; // Current task is not a duplicate
							}
						}
					}
				}
				return true; // If there's an overlap but not the same task
			}
		}
	}

	public boolean isTaskOverlap(LocalDate date, LocalTime startTime, LocalTime endTime, String taskId)
			throws SQLException {
		String query = "SELECT COUNT(*) FROM tasks WHERE task_date = ? AND id != ? AND (start_time < ? AND end_time > ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setDate(1, java.sql.Date.valueOf(date));
			stmt.setString(2, taskId);
			stmt.setTime(3, java.sql.Time.valueOf(startTime));
			stmt.setTime(4, java.sql.Time.valueOf(endTime));

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	public List<Task> getTasksForDate(String empId, LocalDate date) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks WHERE employee_id = ? AND task_date = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, empId);
			statement.setDate(2, java.sql.Date.valueOf(date));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getString("id"));
				task.setDate(resultSet.getDate("task_date").toLocalDate());
				task.setStartTime(resultSet.getTime("start_time").toLocalTime());
				task.setEndTime(resultSet.getTime("end_time").toLocalTime());
				task.setCategory(resultSet.getString("category"));
				task.setDescription(resultSet.getString("description"));
				tasks.add(task);
			}
		}
		return tasks;
	}

	public void addTask(Task task) throws SQLException {
		String query = "INSERT INTO tasks (employee_id, project_name, task_date, start_time, end_time, category, description) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, task.getEmpId());
			stmt.setString(2, task.getProjectName());
			stmt.setDate(3, java.sql.Date.valueOf(task.getDate()));
			stmt.setTime(4, java.sql.Time.valueOf(task.getStartTime()));
			stmt.setTime(5, java.sql.Time.valueOf(task.getEndTime()));
			stmt.setString(6, task.getCategory());
			stmt.setString(7, task.getDescription());
			stmt.executeUpdate();
		}
	}

	public void updateTask(Task task) throws SQLException {
		String query = "UPDATE tasks SET task_date = ?, start_time = ?, end_time = ?, category = ?, description = ? WHERE id = ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDate(1, java.sql.Date.valueOf(task.getDate()));
			statement.setTime(2, java.sql.Time.valueOf(task.getStartTime()));
			statement.setTime(3, java.sql.Time.valueOf(task.getEndTime()));
			statement.setString(4, task.getCategory());
			statement.setString(5, task.getDescription());
			statement.setString(6, task.getId());

			statement.executeUpdate();
		}
	}

	public void deleteTask(String taskId) throws SQLException {
		String sql = "DELETE FROM tasks WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, taskId);
			statement.executeUpdate();
		}
	}

	public Task getTaskById(String taskId) throws SQLException {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, taskId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					Task task = new Task();
					task.setId(resultSet.getString("id"));
					task.setDate(LocalDate.parse(resultSet.getString("task_date")));
					task.setStartTime(LocalTime.parse(resultSet.getString("start_time")));
					task.setEndTime(LocalTime.parse(resultSet.getString("end_time")));
					task.setCategory(resultSet.getString("category"));
					task.setDescription(resultSet.getString("description"));
					return task;
				} else {
					return null; // Task not found
				}
			}
		}
	}

	public List<Task> getTasksByDateRangeAndEmployee(LocalDate startDate, LocalDate endDate, String empId)
			throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks WHERE employee_id = ? AND task_date BETWEEN ? AND ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, empId);
			statement.setDate(2, java.sql.Date.valueOf(startDate));
			statement.setDate(3, java.sql.Date.valueOf(endDate));
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getString("id"));
				task.setDate(resultSet.getDate("task_date").toLocalDate());
				task.setStartTime(resultSet.getTime("start_time").toLocalTime());
				task.setEndTime(resultSet.getTime("end_time").toLocalTime());
				task.setCategory(resultSet.getString("category"));
				task.setDescription(resultSet.getString("description"));
				tasks.add(task);
			}
		}
		return tasks;
	}

	public List<Task> getTasksByMonthAndEmployee(int month, int year, String empId) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String query = "SELECT * FROM tasks WHERE employee_id = ? AND MONTH(task_date) = ? AND YEAR(task_date) = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, empId);
			statement.setInt(2, month);
			statement.setInt(3, year);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getString("id"));
				task.setDate(resultSet.getDate("task_date").toLocalDate());
				task.setStartTime(resultSet.getTime("start_time").toLocalTime());
				task.setEndTime(resultSet.getTime("end_time").toLocalTime());
				task.setCategory(resultSet.getString("category"));
				task.setDescription(resultSet.getString("description"));
				tasks.add(task);
			}
		}
		return tasks;
	}

	public List<Task> getTasksByWeek(LocalDate startDate, LocalDate endDate) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE task_date BETWEEN ? AND ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDate(1, java.sql.Date.valueOf(startDate));
			stmt.setDate(2, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Task task = new Task();
					task.setId(rs.getString("id"));
					task.setEmpId(rs.getString("employee_id"));
					task.setDate(rs.getDate("task_date").toLocalDate());
					task.setStartTime(rs.getTime("start_time").toLocalTime());
					task.setEndTime(rs.getTime("end_time").toLocalTime());
					task.setCategory(rs.getString("category"));
					task.setDescription(rs.getString("description"));
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public List<Task> getTasksByUserId(String userId) throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE employee_id = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Task task = new Task();
					task.setId(rs.getString("id"));
					task.setEmpId(rs.getString("employee_id"));
					task.setProjectName(rs.getString("project_name"));
					task.setDate(rs.getDate("task_date").toLocalDate());
					task.setStartTime(rs.getTime("start_time").toLocalTime());
					task.setEndTime(rs.getTime("end_time").toLocalTime());
					task.setCategory(rs.getString("category"));
					task.setDescription(rs.getString("description"));
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public List<Task> getTasksByProjectIdAndDate(String projectName, LocalDate startDate, LocalDate endDate)
			throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE project_name = ? AND task_date BETWEEN ? AND ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, projectName);
			stmt.setDate(2, java.sql.Date.valueOf(startDate));
			stmt.setDate(3, java.sql.Date.valueOf(endDate));
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Task task = new Task();
					task.setId(rs.getString("id"));
					task.setEmpId(rs.getString("employee_id"));
					task.setProjectName(rs.getString("project_name"));
					task.setDate(rs.getDate("task_date").toLocalDate());
					task.setStartTime(rs.getTime("start_time").toLocalTime());
					task.setEndTime(rs.getTime("end_time").toLocalTime());
					task.setCategory(rs.getString("category"));
					task.setDescription(rs.getString("description"));
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public List<Task> getTasksByProjectAndTimeFrame(String projectName, String timeFrame) throws SQLException {
		return getTasksByCriteria("project_name", projectName, timeFrame);
	}

	public List<Task> getTasksByEmployeeAndTimeFrame(String empId, String timeFrame) throws SQLException {
		return getTasksByCriteria("employee_id", empId, timeFrame);
	}

	private List<Task> getTasksByCriteria(String criteriaColumn, String criteriaValue, String timeFrame)
			throws SQLException {
		List<Task> tasks = new ArrayList<>();
		String sql = "SELECT * FROM tasks WHERE " + criteriaColumn + " = ? AND " + getTimeFrameCondition(timeFrame);
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, criteriaValue);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Task task = new Task();
					task.setId(rs.getString("id"));
					task.setEmpId(rs.getString("employee_id"));
					task.setProjectName(rs.getString("project_name"));
					task.setDate(rs.getDate("task_date").toLocalDate());
					task.setStartTime(rs.getTime("start_time").toLocalTime());
					task.setEndTime(rs.getTime("end_time").toLocalTime());
					task.setCategory(rs.getString("category"));
					task.setDescription(rs.getString("description"));
					tasks.add(task);
				}
			}
		}
		return tasks;
	}

	public boolean isValidProject(String projectName) throws SQLException {
		String query = "SELECT COUNT(*) FROM tasks WHERE project_name = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, projectName);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	public boolean isValidEmployee(String employeeId) throws SQLException {
		String query = "SELECT COUNT(*) FROM users WHERE employee_id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, employeeId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	private String getTimeFrameCondition(String timeFrame) {
		switch (timeFrame) {
		case "daily":
			return "task_date = CURDATE()";
		case "weekly":
			return "YEARWEEK(task_date, 1) = YEARWEEK(CURDATE(), 1)";
		case "monthly":
			return "MONTH(task_date) = MONTH(CURDATE()) AND YEAR(task_date) = YEAR(CURDATE())";
		default:
			throw new IllegalArgumentException("Invalid time frame: " + timeFrame);
		}
	}
}
