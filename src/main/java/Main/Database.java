package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    private static Connection userDataBase;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    public static Connection getUserDB() throws SQLException, ClassNotFoundException {
        if (userDataBase == null) {
            connectUserDB();
        }
        return userDataBase;
    }

    public static void connect() throws SQLException {
        String url = "jdbc:sqlite:" + ProjectDirectory.resourcesPath + "/data/anh_viet";

        connection = DriverManager.getConnection(url);
        System.out.println("Connected!");
    }

    public static void connectUserDB() throws SQLException {
        String url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12665551";
        userDataBase = DriverManager.getConnection(url, "sql12665551", "TdR5CUfcyf");
        System.out.println("Connected!");
    }


}
