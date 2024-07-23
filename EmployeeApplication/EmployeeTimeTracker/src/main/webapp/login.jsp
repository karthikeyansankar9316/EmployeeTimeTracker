<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f7f7f7;
    display: flex;
    flex-direction: column; /* add this line */
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
}

.container {
	background: #fff;
	padding: 30px;
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	width: 350px;
	text-align: center;
}

h1 {
    margin-bottom: 20px;
    color: #ff9900;
    font-weight: bold;
    font-size: 36px;
    text-align: center;
    width: 100%;
}

h2 {
	margin-bottom: 20px;
	color: #333;
	font-weight: bold;
	font-size: 24px;
}

input[type="text"], input[type="password"] {
	width: 100%;
	padding: 12px;
	margin: 10px 0;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-sizing: border-box;
	font-size: 16px;
}

input[type="submit"] {
	background-color: #ff9900;
	color: #fff;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
	font-size: 16px;
}

input[type="submit"]:hover {
	background-color: #ff8f00;
}

p {
	color: #ff9900;
	font-weight: bold;
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
	<h1>Employee Time Tracker</h1><br><br>
	<div class="container">
		<h2>Login</h2>
		<form action="LoginServlet" method="post">
			<input type="text" name="username" placeholder="Username" required><br>
			<input type="password" name="password" placeholder="Password"
				required><br> <input type="submit" value="Login">
		</form>
		<% if (request.getAttribute("error")!= null) { %>
		<p><%= request.getAttribute("error") %></p>
		<% } %>
		<p class="footer">Employee Management System</p>
	</div>
</body>
</html>