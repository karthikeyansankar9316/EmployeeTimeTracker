<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Home</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f7f7f7;
	margin: 0;
	color: #333;
}

.container {
	background: #fff;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 500px;
	margin: 60px auto;
	display: flex;
	flex-direction: column;
	align-items: center;
}

h1 {
	margin-bottom: 20px;
	color: #ffa07a;
	font-weight: bold;
	font-size: 28px;
}

.welcome-message {
	font-size: 20px;
	margin-bottom: 20px;
	color: #333;
}

.link-button {
	display: block;
	padding: 10px 6px;
	margin: 10px 0;
	text-decoration: none;
	color: #fff;
	background-color: #ff9900;
	border-radius: 4px;
	font-size: 16px;
	transition: background-color 0.3s, color 0.3s;
	cursor: pointer;
	width: 100%;
	text-align: center;
}

.link-button:hover {
	background-color: grey;
	color: white;
}

.footer {
	margin-top: 20px;
	font-size: 14px;
	color: #666;
	text-align: center;
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			<span class="welcome-message">Welcome,</span>
			<%= session.getAttribute("username") %>!
		</h1>
		<a href="addTask.jsp" class="link-button">Add Task</a> 
		<a href="manageTasks.jsp" class="link-button">Manage Tasks</a> 
		<a href="viewCharts.jsp" class="link-button">View Charts</a> 
		<a href="logout" class="link-button">Logout</a>
		<p class="footer">Employee's Dashboard		</p>
	</div>
</body>
</html>