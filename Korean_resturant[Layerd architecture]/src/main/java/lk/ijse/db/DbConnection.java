package lk.ijse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private Connection connection = null;
    private static DbConnection dbConnection = null;
    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/korean_resturant",
                "root",
                "2002"
        );
    }

    public static DbConnection getInstance() throws SQLException {
        return(dbConnection==null ? (dbConnection = new DbConnection()):dbConnection);

    }
    public Connection getConnection(){return connection;}
}
