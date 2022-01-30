package com.prog3.ipt.Model;

import java.sql.*;

public class DatabaseConnectionSingleton {
    private static DatabaseConnectionSingleton instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/IPT";
    private String username = "root";
    private String password = "ipt22";



    /** Protect against instantiation via reflection */
    private DatabaseConnectionSingleton() throws SQLException {
        if (instance != null) throw new IllegalStateException("Already initialized.");
        this.connection = DriverManager.getConnection(url, username, password);
    }

    private Connection getConnection() {
        return connection;
    }

    /** The instance doesn't get created until the method is called for the first time. */
    public static DatabaseConnectionSingleton getInstance() throws SQLException {
        synchronized (DatabaseConnectionSingleton.class) {
            if (instance == null) {
                instance = new DatabaseConnectionSingleton();
            } else if (instance.getConnection().isClosed()) {
                instance = new DatabaseConnectionSingleton();
            }
        }
        return instance;
    }
}
