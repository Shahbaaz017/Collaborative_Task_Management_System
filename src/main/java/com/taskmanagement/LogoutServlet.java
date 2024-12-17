package com.taskmanagement;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        // Invalidate the current session
        HttpSession session = request.getSession(false); // Get the current session if it exists
        if (session != null) {
            session.invalidate(); // End the session
        }
        // Redirect to login page
        response.sendRedirect("login.html");
    }
}
