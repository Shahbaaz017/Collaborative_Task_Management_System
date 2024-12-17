<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="jakarta.servlet.http.*" %>
<%
    HttpSession sessions = request.getSession(false);
    String username = (String) sessions.getAttribute("username");

    if (username == null) {
        response.sendRedirect("login.html");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Welcome, <%= username %>!</h2>
        <p>Here are your tasks:</p>
        <!-- Future enhancement: Display tasks dynamically from the database -->
        <ul>
            <li>Task 1</li>
            <li>Task 2</li>
            <li>Task 3</li>
        </ul>
        <a href="logout.jsp" class="button">Logout</a>
    </div>
</body>
</html>
