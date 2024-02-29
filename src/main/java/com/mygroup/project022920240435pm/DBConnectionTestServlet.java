package com.mygroup.project022920240435pm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DBConnectionTestServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            // Open a connection
            out.println("<html><body>");
            out.println("Connecting to database...<br>");
            conn = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
            out.println("Connected successfully.<br>");
            out.println("</body></html>");
            
        } catch (ClassNotFoundException | SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        } finally {
            // Close the connection if it was opened
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException se) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while closing the connection: " + se.getMessage());
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
        return "DBConnectionTestServlet checks the connection to the PostgreSQL database.";
    }
}
