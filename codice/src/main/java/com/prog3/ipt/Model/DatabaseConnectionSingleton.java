package com.prog3.ipt.Model;

import java.sql.*;

public class DatabaseConnectionSingleton {
    private static DatabaseConnectionSingleton instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "dominick";


    private DatabaseConnectionSingleton() throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }
    public Connection getConnection() {
        return connection;
    }
    public static DatabaseConnectionSingleton getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnectionSingleton();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnectionSingleton();
        }
        return instance;
    }
}
