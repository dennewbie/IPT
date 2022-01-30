package com.prog3.ipt.Model;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/** Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization mechanism. */
public class FacadeSingleton {
    // metodo connnessione DB, richiesta di operazioni maggiore alle classi Util dei sottosistemi, etc.
    private static volatile FacadeSingleton instance;
    private static DatabaseConnectionSingleton databaseConnection;
    private static Statement statement;
    private static Result resultSet;

    private FacadeSingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
        connect();
    }

    /** The instance doesn't get created until the method is called for the first time. */
    public static FacadeSingleton getInstance() {
        if (instance == null) {
            synchronized (FacadeSingleton.class) {
                if (instance == null) instance = new FacadeSingleton();
            }
        }
        return instance;
    }

    /** Connection to IPT-db */
    private static void connect() {
        try {
            databaseConnection = DatabaseConnectionSingleton.getInstance();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * vari metodi che eseguono le query e che restituiscono un risultato (credo un nuovo e singolo ResultSet)
    * */
}
