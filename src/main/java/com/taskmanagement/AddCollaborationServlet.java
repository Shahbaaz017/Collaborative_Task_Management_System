package com.taskmanagement;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class AddCollaborationServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String username = (String) request.getSession().getAttribute("username");
        String projectName = request.getParameter("project_name");
        String peopleInvolved = request.getParameter("people_involved");
        String meetingLink = request.getParameter("meeting_link");
        String meetingDate = request.getParameter("meeting_date");
        String meetingTime = request.getParameter("meeting_time");
        String priority = request.getParameter("priority");
        String headOfCollaboration = request.getParameter("head_of_collaboration");

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taskdb", "root", "password");
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO collaborations(user, project_name, people_involved, meeting_link, meeting_date, meeting_time, priority, head_of_collaboration) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, projectName);
            ps.setString(3, peopleInvolved);
            ps.setString(4, meetingLink);
            ps.setDate(5, Date.valueOf(meetingDate));
            ps.setTime(6, Time.valueOf(meetingTime));
            ps.setString(7, priority);
            ps.setString(8, headOfCollaboration);
            ps.executeUpdate();
            response.sendRedirect("collaborations.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
