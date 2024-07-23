<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Add Task</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .container {
        background: #ffffff;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        width: 100%;
        max-width: 500px;
    }
    h1 {
        color: #ff6f00;
        margin-bottom: 20px;
        text-align: center;
        font-size: 24px;
    }
    label {
        display: block;
        margin: 10px 0 5px;
        font-weight: bold;
        color: #333;
    }
    input[type="date"], input[type="time"], input[type="text"], input[type="submit"] {
        width: calc(100% - 20px);
        padding: 10px;
        margin-bottom: 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
        box-sizing: border-box;
    }
    input[type="submit"] {
        background-color: #ff6f00;
        color: #fff;
        border: none;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s;
    }
    input[type="submit"]:hover {
        background-color: #e65100;
    }
    .error {
        color: #e53935;
        font-size: 14px;
        margin-top: 10px;
        text-align: center;
    }
    .back-link {
        display: block;
        margin-top: 20px;
        text-align: center;
        color: #ff6f00;
        text-decoration: none;
        font-size: 16px;
    }
    .back-link:hover {
        text-decoration: underline;
    }
</style>
<script>
    function validateTimes() {
        var startTime = document.querySelector('input[name="startTime"]').value;
        var endTime = document.querySelector('input[name="endTime"]').value;

        if (startTime && endTime) {
            var start = new Date("1970-01-01T" + startTime + ":00Z");
            var end = new Date("1970-01-01T" + endTime + ":00Z");

            if (end <= start) {
                alert("End Time must be later than Start Time.");
                return false;
            }
        }
        return true;
    }
</script>
</head>
<body>
    <div class="container">
        <h1>Add Task</h1>
        <form action="AddTaskServlet" method="post" onsubmit="return validateTimes()">
            <label for="taskDate">Date:</label>
            <input type="date" id="taskDate" name="taskDate" required>

            <label for="startTime">Start Time:</label>
            <input type="time" id="startTime" name="startTime" required>

            <label for="endTime">End Time:</label>
            <input type="time" id="endTime" name="endTime" required>

            <label for="category">Category:</label>
            <input type="text" id="category" name="category" required>

            <label for="description">Description:</label>
            <input type="text" id="description" name="description" required>

            <label for="projectname">Project Name:</label>
            <input type="text" id="projectname" name="projectname" required>

            <input type="submit" value="Add ">
        </form>
        <%
        if (request.getAttribute("error") != null) {
        %>
        <p class="error"><%=request.getAttribute("error")%></p>
        <%
        }
        %>
        <a href="employeeHome.jsp" class="back-link">Home</a>
    </div>
</body>
</html>
