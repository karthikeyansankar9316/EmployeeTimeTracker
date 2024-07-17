<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registration Successful</title>
</head>
<body>
    <h2>Customer Registration Successful</h2>
    <p>Account No: <%= request.getAttribute("account_no") %></p>
    <p>Temporary Password: <%= request.getAttribute("password") %></p>
    <a href="admin_dashboard.jsp">Back to Dashboard</a>
</body>
</html>
