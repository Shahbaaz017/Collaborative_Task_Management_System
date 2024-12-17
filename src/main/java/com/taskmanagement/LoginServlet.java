package com.taskmanagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "taskdb";
        String dbUser = "root";
        String dbPassword = ""; // Replace with your MySQL password
        Connection conn = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL and ensure database and table exist
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // Create the database if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);

            // Connect to the specific database
            conn = DriverManager.getConnection(dbURL + dbName, dbUser, dbPassword);

            // Create the users table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL" +
                    ")";
            stmt.executeUpdate(createTableSQL);

            // Check user credentials
            String selectSQL = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(selectSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Successful login
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("collaborations.jsp");
            } else {
                // Failed login
                request.setAttribute("errorMessage", "Invalid username or password!");
                request.getRequestDispatcher("login.html").forward(request, response);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("JDBC Driver not found!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
