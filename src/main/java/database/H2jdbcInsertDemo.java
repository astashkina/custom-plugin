package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2jdbcInsertDemo {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public static void insertDemo() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            // STEP 3: Execute a query
            System.out.println("INSERT VALUES");
            stmt = conn.createStatement();
            String sql = "INSERT INTO Vulnerabilities " +
                    "VALUES (1, 'java.lang.reflect.Array', 1, 'firstTest has 1 vulnerability')";

            stmt.executeUpdate(sql);
            sql = "INSERT INTO Vulnerabilities " +
                    "VALUES (2, 'java.nio.file.Paths', 2, 'secondTest has 2 vulnerabilities')";

            stmt.executeUpdate(sql);
            sql = "INSERT INTO Vulnerabilities " +
                    "VALUES (3, 'java.util.Arrays', 10, 'thirdTest has one vulnerability')";

            stmt.executeUpdate(sql);
            sql = "INSERT INTO Vulnerabilities " +
                    "VALUES (4, 'com.intellij.psi.PsiFile', 0, 'forthTest has no vulnerabilities')";

            stmt.executeUpdate(sql);
            System.out.println("Inserted records into the table...");

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
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
    }
}
