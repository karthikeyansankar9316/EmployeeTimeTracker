<%@ page import="com.employee.model.Task"%>
<%@ page import="com.employee.util.TaskDAO"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="com.employee.util.DatabaseConnection"%>
<%@ page import="java.sql.SQLException"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Task</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f9;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	color: #333;
}

.container {
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 400px;
	max-width: 100%;
}

h1 {
	margin-bottom: 20px;
	color: #333;
	font-weight: bold;
}

label {
	display: block;
	margin: 10px 0 5px;
	font-weight: bold;
	color: #333;
}

input[type="date"], input[type="time"], input[type="text"], input[type="password"]
	{
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #ffa07a; /* orange color */
	color: #fff;
	padding: 10px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s;
}

input[type="submit"]:hover {
	background-color: #ff9900; /* darker orange color */
}

.error-message {
	color: #ff0000; /* red color */
	font-size: 14px;
	margin-top: 10px;
}

.back-link {
	display: block;
	margin-top: 20px;
	text-align: center;
	color: #ff9900;
	text-decoration: none;
}

.back-link:hover {
	text-decoration: underline;
}
</style>
<script>
    function validateTime() {
        var startTime = document.getElementsByName('startTime')[0].value;
        var endTime = document.getElementsByName('endTime')[0].value;
        
        if (startTime && endTime) {
            var start = new Date("1970-01-01T" + startTime + "Z");
            var end = new Date("1970-01-01T" + endTime + "Z");
            
            if (end <= start) {
                alert("End time must be after the start time.");
                return false;
            }
        }
        return true;
    }
</script>
</head>
<body>
	<div class="container">
		<h1>Edit Task</h1>
		<%
            String taskId = request.getParameter("id");
            Task task = null;
            try (Connection connection = DatabaseConnection.getConnection()) {
                TaskDAO taskDAO = new TaskDAO(connection);
                task = taskDAO.getTaskById(taskId);
            } catch (SQLException e) {
                throw new ServletException("Unable to retrieve task details", e);
            }

            if (task == null) {
                out.println("<p class='error-message'>Task not found.</p>");
            } else {
        %>
		<form action="ManageTaskServlet" method="post"
			onsubmit="return validateTime()">
			<input type="hidden" name="id" value="<%=task.getId()%>"> 
			<label for="date">Date:</label> 
			<input type="date" id="date" name="date" value="<%=task.getDate()%>" required>
			<% if (request.getAttribute("dateError") != null) { %>
				<p class="error-message"><%= request.getAttribute("dateError") %></p>
			<% } %>

			<label for="startTime">Start Time:</label> 
			<input type="time" id="startTime" name="startTime" value="<%=task.getStartTime()%>" required>
			<% if (request.getAttribute("startTimeError") != null) { %>
				<p class="error-message"><%= request.getAttribute("startTimeError") %></p>
			<% } %>

			<label for="endTime">End Time:</label> 
			<input type="time" id="endTime" name="endTime" value="<%=task.getEndTime()%>" required>
			<% if (request.getAttribute("endTimeError") != null) { %>
				<p class="error-message"><%= request.getAttribute("endTimeError") %></p>
			<% } %>

			<label for="category">Category:</label> 
			<input type="text" id="category" name="category" value="<%=task.getCategory()%>" required>
			<% if (request.getAttribute("categoryError") != null) { %>
				<p class="error-message"><%= request.getAttribute("categoryError") %></p>
			<% } %>

			<label for="description">Description:</label> 
			<input type="text" id="description" name="description" value="<%=task.getDescription()%>" required>
			<% if (request.getAttribute("descriptionError") != null) { %>
				<p class="error-message"><%= request.getAttribute("descriptionError") %></p>
			<% } %>

			<input type="hidden" name="action" value="save">
			<input type="submit" value="Update">
		</form>
		<a href="employeeHome.jsp" class="back-link">Back to Employee Home</a>
		<% } %>
	</div>
</body>
</html>