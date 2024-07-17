<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            padding: 20px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            max-width: 500px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        input[type=text], input[type=email], input[type=number], input[type=date], select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type=submit] {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type=submit]:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <h2>Customer Registration</h2>
    <form action="CustomerRegistrationServlet" method="post">

        <label for="full_name">Full Name:</label>
        <input type="text" id="full_name" name="full_name" required>

        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>

        <label for="mobile_no">Mobile No:</label>
        <input type="text" id="mobile_no" name="mobile_no" required>

        <label for="email_id">Email ID:</label>
        <input type="email" id="email_id" name="email_id" required>

        <label for="account_type">Account Type:</label>
        <select id="account_type" name="account_type" required>
            <option value="Saving">Saving Account</option>
            <option value="Current">Current Account</option>
        </select>

        <label for="initial_balance">Initial Balance:</label>
        <input type="number" id="initial_balance" name="initial_balance" min="1000" required>

        <label for="date_of_birth">Date of Birth:</label>
        <input type="date" id="date_of_birth" name="date_of_birth" required>

        <label for="id_proof">ID Proof:</label>
        <input type="text" id="id_proof" name="id_proof" required>

        <input type="submit" value="Register">
        
        <% String error = request.getParameter("error");
           if (error != null) { %>
        <p class="error-message"><%= error %></p>
        <% } %>
    </form>
</body>
</html>
