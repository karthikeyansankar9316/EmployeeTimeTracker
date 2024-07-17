<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dash board</title>
</head>

<body>
    <h2>Welcome, <%= session.getAttribute("adminUsername") %></h2>
    <nav>
        <ul>
            <a href="customer_registration.jsp">Register Customer</a><br><br>
            <a href="Home.jsp">Logout</a>
        </ul>
    </nav>
</body>


</html>
