<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Home</title>
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
        width: 60%;
	text-align: center;
}

h1 {
	color: #ff6f00;
        margin-bottom: 10px;
        text-align: center;
        font-size: 24px;
}

a {
	display: block;
	padding: 10px;
	margin: 10px 0;
	text-decoration: none;
	color: black;
	background-color: #e9ecef;
	border-radius: 4px;
	font-size: 16px;
	transition: background-color 0.3s, color 0.3s;
}

a:hover {
	background-color: #ff6f00;
	color: white;
}

.footer {
	margin-top: 10px;
	font-size: 14px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			Welcome,
			<%= session.getAttribute("username") %>!
		</h1>
		<a href="registerEmployee.jsp">Register Employee</a> <a
			href="manageEmployees.jsp">Edit Employee Details</a> <a
			href="viewTasks.jsp">View Employee Tasks</a> <a href="logout.jsp">Logout</a>
	</div>
</body>
</html>
