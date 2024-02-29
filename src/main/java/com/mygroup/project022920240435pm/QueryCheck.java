package com.mygroup.project022920240435pm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryCheck extends HttpServlet {

    // JDBC Driver Name & Database URL
    static final String JDBC_DRIVER = "org.postgresql.Driver";  
    static final String JDBC_DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    // JDBC Database Credentials
    static final String JDBC_USER = "postgres";
    static final String JDBC_PASS = "admin";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection conn = null;
        Statement stmt = null;
        try (PrintWriter out = response.getWriter()) {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            conn = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
            stmt = conn.createStatement();
            
            // SQL statement to create a new table
            String sqlCreate = "CREATE TABLE IF NOT EXISTS clients " +
                               "(username VARCHAR(255), " +
                               " email VARCHAR(255), " + 
                               " password VARCHAR(255), " + 
                               " name VARCHAR(255), " + 
                               " lastname VARCHAR(255), " + 
                               " age INTEGER, " + 
                               " PRIMARY KEY ( username ))";
            
            // Execute SQL statement to create table
            stmt.executeUpdate(sqlCreate);
            
            // SQL statement to insert dummy data
            String sqlInsert = "INSERT INTO clients (username, email, password, name, lastname, age) VALUES " +
                               "('jdoe', 'jdoe@example.com', 'pass123', 'John', 'Doe', 30), " +
                               "('asmith', 'asmith@example.com', 'pass456', 'Alice', 'Smith', 25), " +
                               "('bwhite', 'bwhite@example.com', 'pass789', 'Bob', 'White', 40)";
            
            // Execute SQL statement to insert data
            stmt.executeUpdate(sqlInsert);
            
            out.println("<html><body>");
            out.println("Table 'clients' created and dummy data inserted successfully.<br>");
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while closing resources: " + se.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "QueryCheck servlet creates a 'clients' table and inserts dummy data.";
    }
}
