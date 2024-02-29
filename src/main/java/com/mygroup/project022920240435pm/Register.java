package com.mygroup.project022920240435pm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

    // JDBC Driver Name & Database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";  
    static final String JDBC_DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // JDBC Database Credentials
    static final String JDBC_USER = "postgres";
    static final String JDBC_PASS = "admin";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the register.jsp page
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Read user data from request
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password"); // In real application, password should be hashed
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        String ageParam = request.getParameter("age");
        int age = 0; // Default value if age parameter is missing or null
        
        if (ageParam != null && !ageParam.isEmpty()) {
            age = Integer.parseInt(ageParam);
        }

        response.setContentType("text/html;charset=UTF-8");
        
        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Establish connection
            try (Connection conn = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS)) {
                // SQL Insert statement
                String sql = "INSERT INTO clients (username, email, password, name, lastname, age) VALUES (?, ?, ?, ?, ?, ?)";

                // Use PreparedStatement to avoid SQL Injection
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, password);
                    pstmt.setString(4, name);
                    pstmt.setString(5, lastname);
                    pstmt.setInt(6, age);

                    // Execute update
                    int rowsAffected = pstmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        // Redirect to success page
                        response.sendRedirect("success.jsp");
                    } else {
                        response.sendRedirect("register.jsp?error=1");
                    }
                }
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            // Handle SQL and Class.forName errors
            throw new ServletException("Registration failed", e);
        } catch (NumberFormatException e) {
            // Handle invalid age parameter
            response.sendRedirect("register.jsp?error=2");
        }
    }

    @Override
    public String getServletInfo() {
        return "Register servlet handles user registration.";
    }
}
