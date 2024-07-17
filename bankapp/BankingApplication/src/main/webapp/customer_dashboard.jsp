<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 20px;
            background-color: #f2f2f2;
        }
        h2 {
            margin-bottom: 20px;
        }
        h3 {
            margin-top: 30px;
            margin-bottom: 10px;
        }
        p {
            margin-bottom: 10px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        ul li {
            margin-bottom: 10px;
        }
        a {
            text-decoration: none;
            color: #007bff;
            transition: color 0.3s ease;
        }
        a:hover {
            color: #0056b3;
        }
        /* Styles for password dialog box */
        #passwordDialog {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 300px;
            max-width: 90%;
            border: 1px solid #ccc;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0px 0px 10px rgba(0,0,0,0.5);
            z-index: 1000; /* Ensure dialog is on top */
        }
        #passwordDialog .close {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
            font-size: 20px;
            color: #aaa;
        }
        #passwordDialog h3 {
            margin-top: 0;
            margin-bottom: 10px;
        }
        #passwordDialog label {
            display: block;
            margin-bottom: 5px;
            text-align: left;
        }
        #passwordDialog input[type="number"],
        #passwordDialog input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        #passwordDialog button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            background-color: #007bff;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        #passwordDialog button:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function showPasswordDialog() {
            document.getElementById('passwordDialog').style.display = 'block';
        }

        function closePasswordDialog() {
            document.getElementById('passwordDialog').style.display = 'none';
        }

        function submitPasswordChange() {
            var oldPassword = document.getElementById('oldPassword').value;
            var newPassword = document.getElementById('newPassword').value;

            if (oldPassword && newPassword) {
                document.getElementById('passwordForm').submit();
            } else {
                alert('Please fill in both fields.');
            }
        }
    </script>
</head>
<body>
    <h2>Welcome, <%= session.getAttribute("FullName") %></h2>
    
    <h3>Account Information</h3>
    <p>Account No: <%= session.getAttribute("customerAccountNo") %></p>
    <p>Balance: <%= session.getAttribute("customerBalance") %></p>
    
    <h3>Actions</h3>
    <ul>
        <li><a href="view_transactions.jsp">View Transactions</a></li>
        <li><a href="deposit.jsp">Deposit Funds</a></li>
        <li><a href="withdraw.jsp">Withdraw Funds</a></li>
        <li><a href="close_account.jsp">Close Account</a></li>
        <li><a href="#" onclick="showPasswordDialog()">Create New Password</a></li>
        <li><a href="Home.jsp">Home</a></li>
    </ul>

    <!-- Password Dialog Box -->
    <div id="passwordDialog">
        <span class="close" onclick="closePasswordDialog()">x</span>
        <h3>Create New Password</h3>
        <form id="passwordForm" action="ChangePasswordServlet" method="post">
            <p>
                <label for="customer_id">Customer ID:</label>
                <input type="number" id="customer_id" name="customer_id" required>
            </p>
            <p>
                <label for="oldPassword">Old Password:</label>
                <input type="password" id="oldPassword" name="oldPassword" required>
            </p>
            <p>
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required>
            </p>
            <p>
                <button type="button" onclick="submitPasswordChange()">Submit</button>
            </p>
        </form>
    </div>
</body>
</html>
