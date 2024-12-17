<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%
    // Check if user is logged in
    HttpSession sessions = request.getSession(false);
    if (sessions == null || sessions.getAttribute("username") == null) {
        response.sendRedirect("login.html");
        return;
    }

    String username = (String) session.getAttribute("username");

    // Database connection
    String dbURL = "jdbc:mysql://localhost:3306/taskdb";
    String dbUser = "root";
    String dbPassword = "your_password";
    Connection conn = null;
    List<Map<String, String>> collaborations = new ArrayList<>();

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

        // Fetch collaborations for the logged-in user
        String sql = "SELECT * FROM collaborations WHERE head = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            row.put("id", rs.getString("id"));
            row.put("projectName", rs.getString("project_name"));
            row.put("peopleInvolved", rs.getString("people_involved"));
            row.put("meetingLink", rs.getString("meeting_link"));
            row.put("date", rs.getString("date"));
            row.put("time", rs.getString("time"));
            row.put("priority", rs.getString("priority"));
            row.put("head", rs.getString("head"));
            collaborations.add(row);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Collaborations</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <header>
        <h1>Welcome, <%= username %>!</h1>
        <form action="LogoutServlet" method="post" style="display: inline;">
            <button type="submit" class="logout-button">Logout</button>
        </form>
    </header>

    <h2>Your Collaborations</h2>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>Project Name</th>
                <th>People Involved</th>
                <th>Meeting Link</th>
                <th>Date</th>
                <th>Time</th>
                <th>Priority</th>
                <th>Head</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% if (collaborations.isEmpty()) { %>
                <tr>
                    <td colspan="8" style="text-align: center;">No collaborations found</td>
                </tr>
            <% } else { 
                for (Map<String, String> collaboration : collaborations) { %>
                <tr>
                    <td><%= collaboration.get("projectName") %></td>
                    <td><%= collaboration.get("peopleInvolved") %></td>
                    <td><a href="<%= collaboration.get("meetingLink") %>" target="_blank">Link</a></td>
                    <td><%= collaboration.get("date") %></td>
                    <td><%= collaboration.get("time") %></td>
                    <td><%= collaboration.get("priority") %></td>
                    <td><%= collaboration.get("head") %></td>
                    <td>
                        <form action="DeleteCollaborationServlet" method="post" style="display: inline;">
                            <input type="hidden" name="id" value="<%= collaboration.get("id") %>">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            <% } } %>
        </tbody>
    </table>

    <h3>Add New Collaboration</h3>
    <form action="AddCollaborationServlet" method="post">
        <label for="projectName">Project Name:</label>
        <input type="text" id="projectName" name="projectName" required>

        <label for="peopleInvolved">People Involved:</label>
        <input type="text" id="peopleInvolved" name="peopleInvolved" required>

        <label for="meetingLink">Meeting Link:</label>
        <input type="url" id="meetingLink" name="meetingLink" required>

        <label for="date">Date:</label>
        <input type="date" id="date" name="date" required>

        <label for="time">Time:</label>
        <input type="time" id="time" name="time" required>

        <label for="priority">Priority:</label>
        <select id="priority" name="priority" required>
            <option value="High">High</option>
            <option value="Medium">Medium</option>
            <option value="Low">Low</option>
        </select>

        <input type="hidden" name="head" value="<%= username %>">

        <button type="submit">Add Collaboration</button>
    </form>
</body>
</html>
