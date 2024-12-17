package com.taskmanagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String dbURL = "jdbc:mysql://localhost:3306/";
        String dbName = "taskdb";
        String dbUser = "root";
        String dbPassword = "";
        Connection conn = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL without a database
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

            // Insert the new user into the database
            String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            // Redirect to the login page after successful registration
            response.sendRedirect("login.html");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().println("Error: JDBC Driver not found!");
        } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate username
            request.setAttribute("errorMessage", "Username already exists. Please choose a different one.");
            request.getRequestDispatcher("register.html").forward(request, response);
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
