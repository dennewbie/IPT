package com.prog3.ipt.Model.FacadeClasses;

import java.sql.*;

public class DatabaseConnectionSingleton {
    private static DatabaseConnectionSingleton instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/ipt";
    private String username = "root";
    private String password = "rootroot";



    /** Protect against instantiation via reflection */
    private DatabaseConnectionSingleton() {
        try {
            if (instance != null) throw new IllegalStateException("Already initialized.");
        } catch (IllegalStateException e) { e.printStackTrace(); }

        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Connection getConnection() {
        return connection;
    }

    /** The instance doesn't get created until the method is called for the first time. */
    public static DatabaseConnectionSingleton getInstance() {
        try {
            synchronized (DatabaseConnectionSingleton.class) {
                if (instance == null) {
                    instance = new DatabaseConnectionSingleton();
                } else if (instance.getConnection().isClosed()) {
                    instance = new DatabaseConnectionSingleton();
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return instance;
    }
}
