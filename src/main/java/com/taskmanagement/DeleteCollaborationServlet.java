package com.taskmanagement;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DeleteCollaborationServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        int collabId = Integer.parseInt(request.getParameter("collab_id"));

        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/taskdb", "root", "password");
            PreparedStatement ps = con.prepareStatement("DELETE FROM collaborations WHERE id = ?");
            ps.setInt(1, collabId);
            ps.executeUpdate();
            response.sendRedirect("collaborations.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
