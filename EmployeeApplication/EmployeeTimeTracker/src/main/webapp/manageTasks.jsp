<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.employee.model.Task"%>
<%@ page import="com.employee.util.DatabaseConnection"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.employee.util.TaskDAO"%>
<%@ page import="com.employee.model.User"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

List<Task> tasks = null;
try (Connection connection = DatabaseConnection.getConnection()) {
    TaskDAO taskDAO = new TaskDAO(connection);
    tasks = taskDAO.getTasksByUserId(((User) session.getAttribute("user")).getEmpId());
} catch (Exception e) {
    e.printStackTrace();
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Manage Tasks</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f7f7f7;
	margin: 0;
	padding: 0;
}

h1 {
	text-align: center;
	color: #ff9900;
	margin-top: 20px;
	font-weight: bold;
	font-size: 36px;
}

table {
	width: 90%;
	margin: 20px auto;
	border-collapse: collapse;
	background-color: #fff;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}
}

th, td {
	padding: 10px;
	text-align: left;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #ff9900;
	color: #fff;
}

tr:nth-child(even) {
	background-color: #f9f9f9;
}

tr:hover {
	background-color: #f1f1f1;
}

form {
	display: inline;
}

.center {
	text-align: center;
	margin: 20px;
}

.back-to-dashboard {
	display: inline-block;
	padding: 10px 20px;
	font-size: 16px;
	color: #fff;
	background-color: #ff9900;
	text-decoration: none;
	border-radius: 4px;
	transition: background-color 0.3s;
}

.back-to-dashboard:hover {
	background-color: #ff8f00;
}

/* Add styles for the popup box */
.popup-box {
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	background-color: #fff;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	display: none;
}

.popup-box.show {
	display: block;
}

.popup-box.close {
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
}

.popup-box.close:hover {
	color: #4CAF50;
}
</style>
</head>
<body>
	<h1>Manage Tasks</h1>
	<table>
		<thead>
			<tr>
				<th>Date</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Category</th>
				<th>Description</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<%
            if (tasks!= null &&!tasks.isEmpty()) {
            %>
			<%
            for (Task task : tasks) {
            %>
			<tr>
				<td><%=task.getDate()%></td>
				<td><%=task.getStartTime()%></td>
				<td><%=task.getEndTime()%></td>
				<td><%=task.getCategory()%></td>
				<td><%=task.getDescription()%></td>
				<td>
					<a href="#" onclick="showPopup(<%=task.getId()%>)">Edit/Delete</a>
				</td>
			</tr>
			<%
            }
            %>
			<%
            } else {
            %>
			<tr>
				<td colspan="6">No tasks found.</td>
			</tr>
			<%
            }
            %>
		</tbody>
	</table>
	
	<!-- Add the popup box -->
	<div class="popup-box">
		<div class="close" onclick="hidePopup()">×</div>
		
		<form action="ManageTaskServlet" method="post">
			<input type="hidden" name="action" value="edit"> 
			<input type="hidden" name="id" id="taskId"> 
			<input type="submit" value="Edit">
		</form>
		<form action="ManageTaskServlet" method="post">
			<input type="hidden" name="action" value="delete"> 
		<input type="hidden" name="id" id="taskId"> 
			<input type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this task?');">
		</form>
	</div>
	
	<div class="center">
		<a href="employeeHome.jsp" class="back-to-dashboard">Back to
			Dashboard</a>
	</div>

	<script>
	// Add JavaScript code to show and hide the popup box
	function showPopup(taskId) {
		document.getElementById("taskId").value = taskId;
		document.querySelector(".popup-box").classList.add("show");
	}

	function hidePopup() {
		document.querySelector(".popup-box").classList.remove("show");
	}
	</script>
</body>
</html> 