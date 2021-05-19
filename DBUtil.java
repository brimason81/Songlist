/**
 * This application conatains all DB login info
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final String USERNAME = "root";   
    private static final String PASSWORD = "";   
    private static final String M_CONN_STRING= 
        "jdbc:mysql://localhost/repetoire";   


    public static Connection getConnection(DBType dbType) throws SQLException {
        return DriverManager.getConnection(M_CONN_STRING, USERNAME, PASSWORD);
    }

    public static void processException(SQLException e) {
        System.err.println("Error Message: " + e.getMessage());
        System.err.println("Error Message: " + e.getErrorCode());
        System.err.println("SQL state: " + e.getSQLState());
    }

}