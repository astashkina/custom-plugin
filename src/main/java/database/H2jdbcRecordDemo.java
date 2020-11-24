package database;

import java.sql.*;
import java.util.HashMap;
import java.util.List;

public class H2jdbcRecordDemo {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static HashMap<String, Integer> recordDemo(List<String> libList) {
        Connection conn = null;
        Statement stmt = null;
        HashMap<String, Integer> map = new HashMap<>();

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            System.out.println("Connected database successfully...");
            stmt = conn.createStatement();
            String sql = "SELECT id, libName, vulCount, vulDescription FROM Vulnerabilities";
            ResultSet rs = stmt.executeQuery(sql);

            // STEP 4: Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                int id = rs.getInt("id");
                String libName = rs.getString("libName");
                int vulCount = rs.getInt("vulCount");
                String vulDescription = rs.getString("vulDescription");

                // Display values
                System.out.print("ID: " + id);
                System.out.print(", Library Name: " + libName);
                System.out.print(", Vulnerability count: " + vulCount);
                System.out.println(", Vulnerability description: " + vulDescription);

                for (String s : libList) {
                    if (s.strip().equals(libName.strip())) {
                        System.out.println(libName);
                        map.put(libName, vulCount);
                    }
                }
            }

            // STEP 5: Clean-up environment
            rs.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // finally block used to close resources
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            } // nothing we can do
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            } // end finally try
        } // end try
        System.out.println("Goodbye!");

        return map;
    }
}
