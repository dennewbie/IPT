package com.prog3.ipt.Model.FacadeClasses;

import java.sql.*;

/**
 * DatabaseConnectionSingleton interfaces with a MySQL Database Schema for IPT system, applying the Singleton design pattern
 */
public class DatabaseConnectionSingleton {
    private static DatabaseConnectionSingleton instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/ipt";
    private String username = "root";
    private String password = "rootroot";



    /**
     * DatabaseConnectionSingleton constructor.
     * Set up a connection passing url, username, password to DriverManager's getConnection method
     * Protect against instantiation via reflection
     * @see java.sql.DriverManager#getConnection(String, String, String) 
     */
    private DatabaseConnectionSingleton() {
        try {
            if (instance != null) throw new IllegalStateException("Already initialized.");
        } catch (IllegalStateException e) { e.printStackTrace(); }

        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Getter
    public Connection getConnection() {
        return connection;
    }

    /**
     * Returns the DatabaseConnectionSingleton instance
     * The instance doesn't get created until the method is called for the first time.
     * @return A reference to a DatabaseConnectionSingleton object
     */
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
