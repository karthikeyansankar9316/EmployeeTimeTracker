<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Charts</title>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
    .chart-container {
        position: relative;
        height: 400px;
        width: 100%;
        margin-bottom: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        padding: 20px;
    }
    canvas {
        display: block;
        width: 100%;
        height: 100%;
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
    <h2>Task Charts</h2>
    <div class="chart-container">
        <canvas id="dailyChart"></canvas>
    </div>
    <div class="chart-container">
        <canvas id="weeklyChart"></canvas>
    </div>
    <div class="chart-container">
        <canvas id="monthlyChart"></canvas>
    </div>
    <script>
        function fetchDataAndUpdateCharts() {
            $.ajax({
                url: 'FetchTaskDataServlet',
                type: 'GET',
                dataType: 'json',
                success: function(responseData) {
                    console.log('Data fetched:', responseData);
                    if (responseData.daily && responseData.daily.data.length > 0) {
                        updateDailyChart(responseData.daily);
                    } else {
                        console.error('Daily data is empty or invalid.');
                    }
                    if (responseData.weekly && responseData.weekly.data.length > 0) {
                        updateWeeklyChart(responseData.weekly);
                    } else {
                        console.error('Weekly data is empty or invalid.');
                    }
                    if (responseData.monthly && responseData.monthly.data.length > 0) {
                        updateMonthlyChart(responseData.monthly);
                    } else {
                        console.error('Monthly data is empty or invalid.');
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data: ' + error);
                }
            });
        }

        function updateDailyChart(data) {
            var dailyCtx = document.getElementById('dailyChart').getContext('2d');
            new Chart(dailyCtx, {
                type: 'pie',
                data: {
                    labels: data.labels,
                    datasets: [{
                        label: 'Daily Tasks',
                        data: data.data,
                        backgroundColor: ['rgba(255, 99, 132, 0.8)', 'rgba(54, 162, 235, 0.8)', 'rgba(255, 206, 86, 0.8)', 'rgba(75, 192, 192, 0.8)', 'rgba(153, 102, 255, 0.8)']
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Daily Tasks'
                        }
                    }
                }
            });
        }

        function updateWeeklyChart(data) {
            var weeklyCtx = document.getElementById('weeklyChart').getContext('2d');
            new Chart(weeklyCtx, {
                type: 'bar',
                data: {
                    labels: data.labels,
                    datasets: [{
                        label: 'Weekly Tasks',
                        data: data.data,
                        backgroundColor: data.backgroundColors
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Current Week Tasks'
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }

        function updateMonthlyChart(data) {
            var monthlyCtx = document.getElementById('monthlyChart').getContext('2d');
            new Chart(monthlyCtx, {
                type: 'bar',
                data: {
                    labels: data.labels,
                    datasets: [{
                        label: 'Monthly Tasks',
                        data: data.data,
                        backgroundColor: data.backgroundColors
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        title: {
                            display: true,
                            text: 'Monthly Tasks'
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        }

        $(document).ready(function() {
            fetchDataAndUpdateCharts();
        });
    </script>
   
    <a href="employeeHome.jsp" class="back-link">Back to Employee Home</a>
</body>
</html>
