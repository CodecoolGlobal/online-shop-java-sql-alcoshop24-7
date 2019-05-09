package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connection = null;

    public Connection setConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:alcoshop2.db");
            connection.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
