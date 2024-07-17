<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Close Account</title>
    <!-- Add any necessary CSS or JavaScript includes here -->
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 20px;
        }
        h2 {
            margin-bottom: 20px;
        }
        p {
            margin-bottom: 20px;
        }
        form {
            max-width: 300px;
            margin: 0 auto;
            border: 1px solid #ccc;
            padding: 20px;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        label {
            display: block;
            margin-bottom: 10px;
            text-align: left;
        }
        input[type="text"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            background-color: #dc3545;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover {
            background-color: #c82333;
        }
        a {
            display: block;
            margin-top: 20px;
            text-decoration: none;
            color: #007bff;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h2>Close Account</h2>
    
    <p>Are you sure you want to close your account?</p>
    <form action="CloseAccountServlet" method="post">
        <label for="customerId">Customer ID:</label>
        <input type="text" id="customerId" name="customerId" required><br><br>
        <input type="hidden" name="confirm" value="true">
        <input type="submit" value="Confirm Close Account">
    </form>
    
    <br>
    <a href="customer_dashboard.jsp">Back to Dashboard</a>
</body>
</html>
