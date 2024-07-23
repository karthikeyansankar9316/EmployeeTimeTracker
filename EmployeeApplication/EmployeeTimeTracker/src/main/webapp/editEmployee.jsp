<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.employee.model.User"%>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Employee</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8f9fa;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background: #ffffff;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 100%;
	max-width: 500px;
}

h1 {
	color: #ffa07a; /* orange color */
	margin-bottom: 20px;
	text-align: center;
	font-size: 24px;
	font-weight: bold;
}

label {
	display: block;
	margin: 10px 0 5px;
	font-weight: bold;
	color: #333;
}

input[type="text"], input[type="password"] {
	background-color: #f7f7f7;
	color: black;
	border: 1px solid #ccc;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s;
	padding: 10px;
	border-radius: 4px;
	width: 100%;
	box-sizing: border-box;
}

input[type="submit"] {
	background-color: #ffa07a; /* orange color */
	color: #fff;
	padding: 10px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
	transition: background-color 0.3s;
}

input[type="submit"]:hover {
	background-color: #ff9900; /* darker orange color */
}

.back-to-dashboard {
	display: inline-block;
	margin-top: 20px;
	color: #ffa07a;
	text-decoration: none;
	font-size: 16px;
}

.back-to-dashboard:hover {
	text-decoration: underline;
}

select {
	padding: 10px;
	margin: 10px 0;
	border-radius: 5px;
	border: 1px solid #ccc;
	width: 100%;
	box-sizing: border-box;
}

.error {
	color: #ff0000; /* red color */
	font-size: 14px;
	margin-top: 5px;
}

.success {
	color: #00ff00; /* green color */
	font-size: 14px;
	margin-top: 5px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Edit Employee Details</h1>
		<% User user = (User) request.getAttribute("user"); %>
		<form action="ManageEmployeeServlet" method="post">
			<input type="hidden" name="action" value="update"> 
			<label for="id">Employee ID:</label> 
			<input type="text" id="id" name="id" value="<%=user.getEmpId()%>" readonly>
			<label for="name">Name:</label>
			<input type="text" id="name" name="name" value="<%=user.getName()%>">
			<% if (request.getAttribute("nameError") != null) { %>
				<p class="error"><%= request.getAttribute("nameError") %></p>
			<% } %>

			<label for="role" style="margin-right: 10px;">Role:</label>
			<select id="role" name="role" required>
			    <option value="Employee" <%= "Employee".equals(user.getRole()) ? "selected" : "" %>>Employee</option>
			    <option value="Admin" <%= "Admin".equals(user.getRole()) ? "selected" : "" %>>Admin</option>
			</select>
			<% if (request.getAttribute("roleError") != null) { %>
				<p class="error"><%= request.getAttribute("roleError") %></p>
			<% } %>

			<label for="username">Username:</label>
			<input type="text" id="username" name="username" value="<%=user.getUsername()%>">
			<% if (request.getAttribute("usernameError") != null) { %>
				<p class="error"><%= request.getAttribute("usernameError") %></p>
			<% } %>

			<label for="password">Password:</label>
			<input type="password" id="password" name="password" value="<%=user.getPassword()%>">
			<% if (request.getAttribute("passwordError") != null) { %>
				<p class="error"><%= request.getAttribute("passwordError") %></p>
			<% } %><br>

			<input type="submit" value="Save">
			<% if (request.getAttribute("successMessage") != null) { %>
				<p class="success"><%= request.getAttribute("successMessage") %></p>
			<% } %>
		</form>
		<div class="center">
			<a href="manageEmployees.jsp" class="back-to-dashboard">Back to Manage Employees</a>
		</div>
	</div>
</body>
</html>