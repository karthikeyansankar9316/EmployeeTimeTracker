<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.employee.model.Task"%>
<%@ page import="com.google.gson.Gson"%>
<html>
<head>
<title>View Chart</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 20px;
}

h2 {
	text-align: center;
	color: #333;
}

form {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	background-color: #fff;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

label {
	display: block;
	margin-bottom: 8px;
	font-weight: bold;
}

select, input[type="text"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #ddd;
	border-radius: 4px;
}

input[type="submit"] {
	width: 100%;
	padding: 15px;
	border: none;
	border-radius: 4px;
	background-color: #4CAF50;
	color: #fff;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.3s;
}

input[type="submit"]:hover {
	background-color: #45a049;
}

.chart-container {
	position: relative;
	height: 400px;
	width: 100%;
	margin-bottom: 20px;
}

canvas {
	display: block;
	width: 100%;
	height: 100%;
}

p {
	text-align: center;
	color: #f00;
}

.back-link {
	display: block;
	margin-top: 20px;
	text-align: center;
	font-size: 16px;
	color: #007BFF;
	text-decoration: none;
}

.back-link:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<h2>View Chart</h2>
	<form action="ViewTasksServlet" method="post">
		<label for="searchType">Search By:</label> <select name="searchType"
			id="searchType">
			<option value="project">Project</option>
			<option value="employee">Employee</option>
		</select> <label for="searchValue">Enter Value:</label> <input type="text"
			name="searchValue" id="searchValue" placeholder="Enter value"
			required> <label for="timeFrame">Time Frame:</label> <select
			name="timeFrame" id="timeFrame">
			<option value="daily">Daily</option>
			<option value="weekly">Weekly</option>
			<option value="monthly">Monthly</option>
		</select> <input type="submit" value="Search">
	</form>

	<h3>Task Charts</h3>
<div class="chart-container">
	<canvas id="taskPieChart"></canvas>
</div>
<div class="chart-container">
	<canvas id="taskBarChart"></canvas>
</div>

<%
    String chartDataJson = (String) request.getAttribute("chartData");
    if (chartDataJson != null && !chartDataJson.isEmpty()) {
%>
<script>
    var chartData = <%= chartDataJson %>;

    // Pie chart
    var pieCtx = document.getElementById('taskPieChart').getContext('2d');
    var taskPieChart = new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: chartData.labels,
            datasets: [{
                label: 'Task Distribution',
                data: chartData.data,
                backgroundColor: chartData.backgroundColors
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: 'Task Distribution Pie Chart'
                }
            }
        }
    });

    // Bar chart
    var barCtx = document.getElementById('taskBarChart').getContext('2d');
    var taskBarChart = new Chart(barCtx, {
        type: 'bar',
        data: {
            labels: chartData.labels,
            datasets: [{
                label: 'Task Distribution',
                data: chartData.data,
                backgroundColor: chartData.backgroundColors,
                borderColor: chartData.backgroundColors.map(color => color.replace('0.8', '1')),
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                title: {
                    display: true,
                    text: 'Task Distribution Bar Chart'
                }
            },
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
<%
    } else {
        out.println("<p>No tasks found for the selected criteria.</p>");
    }
%>
<a href="adminHome.jsp" class="back-link">Back to Admin Home</a>
</body>
</html>
