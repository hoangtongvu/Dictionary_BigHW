package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static void connect() throws SQLException {
        String url = "jdbc:sqlite:" + ProjectDirectory.resourcesPath + "/data/anh_viet";

        connection = DriverManager.getConnection(url);
        System.out.println("Connected!");
    }
}
