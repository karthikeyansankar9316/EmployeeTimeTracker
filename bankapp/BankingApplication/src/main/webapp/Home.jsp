<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 20px;
        }
        h1 {
            margin-bottom: 20px;
        }
        nav {
            margin-top: 20px;
        }
        input[type="submit"] {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <h1>Welcome To KSA Bank</h1>
    <nav>
        <form action="admin_login.jsp">
            <center><input type="submit" value="Admin Login"></center>
        </form>
        <br><br>
        <form action="customer_login.jsp">
            <center><input type="submit" value="Customer Login"></center>
        </form>
    </nav>
</body>
</html>
