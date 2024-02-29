// Import required Java and JDBC packages
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

// Extend HttpServlet class for Servlet
public class TestDatabaseConnection extends HttpServlet {

    // Override the doGet method to handle GET request
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type to HTML
        response.setContentType("text/html");
        // JDBC driver name and database URL
        final String JDBC_DRIVER = "org.postgresql.Driver";  
        final String DB_URL; // Replace yourDatabaseName with your actual database name
        DB_URL = "jdbc:postgresql://localhost:5432/postgres";
        
        // Database credentials
        final String USER = "postgres"; // Database username
        final String PASS = "admin"; // Database password
        
        // Prepare the HTML response
        PrintWriter out = response.getWriter();
        String title = "Database Connection Test";
        out.println("<html><head><title>" + title + "</title></head><body>");
        
        try {
            // Load JDBC driver
            Class.forName(JDBC_DRIVER);
            // Establish connection to the database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // If connection is successful
            out.println("<h1>Connection to PostgreSQL database successful!</h1>");
            
            // Close the database connection
            conn.close();
        } catch(SQLException se) {
            // Handle JDBC errors
            out.println("<h1>SQLException: " + se.getMessage() + "</h1>");
            se.printStackTrace(System.err);
        } catch(Exception e) {
            // Handle Class.forName errors
            out.println("<h1>Exception: " + e.getMessage() + "</h1>");
            e.printStackTrace(System.err);
        } finally {
            // Finalize the HTML response
            out.println("</body></html>");
            // Close the PrintWriter
            out.close();
        }
    }
}
