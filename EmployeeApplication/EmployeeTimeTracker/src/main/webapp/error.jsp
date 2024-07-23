<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Error</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f8d7da;
	color: #721c24;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.container {
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 80%;
	max-width: 600px;
	text-align: center;
}

h2 {
	color: #721c24;
	margin-bottom: 20px;
}

p {
	font-size: 16px;
	margin-bottom: 20px;
}

a {
	display: inline-block;
	padding: 10px 20px;
	font-size: 16px;
	color: #fff;
	background-color: #721c24;
	text-decoration: none;
	border-radius: 4px;
	transition: background-color 0.3s;
}

a:hover {
	background-color: #5c1a1a;
}
</style>
</head>
<body>
	<div class="container">
		<h2>Error</h2>
		<p><%= request.getAttribute("errorMessage") %></p>
		<a href="viewTasks.jsp">Go Back</a>
	</div>
</body>
</html>
