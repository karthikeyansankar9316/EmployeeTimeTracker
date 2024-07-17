<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Transactions</title>
    <!-- Link to your custom CSS file or use Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Custom CSS styles */
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        h2 {
            margin-bottom: 20px;
        }
        #transactionTable {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        #transactionTable th, #transactionTable td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }
        #transactionTable th {
            background-color: #f2f2f2;
        }
        #transactionTable tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        #transactionTable tbody tr:hover {
            background-color: #e9ecef;
        }
        .btn-link {
            color: #007bff;
            text-decoration: none;
        }
        .btn-link:hover {
            text-decoration: underline;
        }
    </style>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            var customerId = '<%= session.getAttribute("customerId") %>';
            var password = '<%= session.getAttribute("password") %>';

            function fetchTransactions(customerId, password) {
                $.ajax({
                    url: "FetchTransactionsServlet",
                    type: "GET",
                    data: {
                        customerId: customerId,
                        password: password
                    },
                    dataType: "json",
                    beforeSend: function() {
                        // Show loading spinner or text
                        $("#transactionTable tbody").html("<tr><td colspan='3'>Loading transactions...</td></tr>");
                    },
                    success: function(transactions) {
                        displayTransactions(transactions);
                    },
                    error: function(xhr, status, error) {
                        console.error("Failed to fetch transactions:", error);
                        $("#transactionTable tbody").html("<tr><td colspan='3'>Failed to fetch transactions. Please try again later.</td></tr>");
                    }
                });
            }

            function displayTransactions(transactions) {
                var tableBody = $("#transactionTable tbody");
                tableBody.empty();

                $.each(transactions, function(index, transaction) {
                    var row = $("<tr>");
                    row.append("<td>" + transaction.transactionDate + "</td>");
                    row.append("<td>" + transaction.transactionType + "</td>");
                    row.append("<td>" + transaction.amount + "</td>");
                    tableBody.append(row);
                });
            }

            fetchTransactions(customerId, password);
        });
    </script>
</head>
<body>
    <div class="container">
        <h2>View Transactions</h2>
        
        <table class="table table-bordered" id="transactionTable">
            <thead class="thead-light">
                <tr>
                    <th>Date</th>
                    <th>Transaction Type</th>
                    <th>Amount</th>
                </tr>
            </thead>
            <tbody>
                <!-- Transactions will be dynamically added here -->
            </tbody>
        </table>
        
        <div>
            <a class="btn btn-link" href="customer_dashboard.jsp">Back to Dashboard</a>
            <a class="btn btn-link ml-3" href="DownloadTransactionsServlet?customerId=<%= session.getAttribute("customerId") %>&password=<%= session.getAttribute("password") %>">Download PDF of Transactions</a>
        </div>
    </div>
</body>
</html>
