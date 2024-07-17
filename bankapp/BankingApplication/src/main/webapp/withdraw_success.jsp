<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Withdrawal Success</title>
    <!-- CSS -->
</head>
<body>
    <h2>Withdrawal Successful</h2>
    <p>Your withdrawal transaction has been successfully processed.</p>
    <!-- Display transaction details -->
    <p>Amount Withdrawn: Rs.<%= request.getParameter("amount") %></p>
    <p>Transaction Date: <%= new java.util.Date() %></p>
    
    
    <p><a href="customer_dashboard.jsp">Back to Dashboard</a></p>
</body>
</html>
