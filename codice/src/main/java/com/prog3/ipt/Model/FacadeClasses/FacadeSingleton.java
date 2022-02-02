package com.prog3.ipt.Model.FacadeClasses;

import com.prog3.ipt.Controller.InfoViewController;
import com.prog3.ipt.Controller.NoticesViewController;
import com.prog3.ipt.Model.Corsa;
import com.prog3.ipt.Model.Linea;
import com.prog3.ipt.Model.Notice;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization mechanism. */
public class FacadeSingleton {
    // metodo connnessione DB, richiesta di operazioni maggiori alle classi Util dei sottosistemi, etc.
    private static volatile FacadeSingleton instance;
    private static DatabaseConnectionSingleton databaseConnection;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet queryOutput;
    private static ObservableList<Notice> noticeObservableList;
    private static ObservableList<Linea> lineaObservableList;
    private static ObservableList<Corsa> corsaObservableList;
    private static ObservableList<TravelDocument> travelDocumentObservableList;


    private FacadeSingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
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

    public static void closeConnection() {
        try { statement.close(); } catch (Exception e) { /* Ignored */ }
        try { databaseConnection.getConnection().close(); } catch (Exception e) { /* Ignored */ }
    }

    /*
    * vari metodi che eseguono le query e che restituiscono un risultato (credo un nuovo e singolo ResultSet)
    * */
    private static boolean executeQuery(String queryString) {
        queryOutput = null;
        statement = null;
        connection = null;

        try {
            connect();
            connection = databaseConnection.getConnection();

            statement = connection.createStatement();
            queryOutput = statement.executeQuery(queryString);

            return true;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validateRide(String lineID, String rideID) {
        // SQL query
        String lineQuery = "select corsa.id_corsa, corsa.id_linea, corsa.stato, corsa.ora_inizio, corsa.ora_fine, corsa.priorita " +
                "from linea join corsa on linea.id_linea = corsa.id_linea " +
                "where linea.id_linea = \""+ lineID + "\" and corsa.id_corsa = \""+ rideID +"\";";

        if (!executeQuery(lineQuery)) return false;
        try {

            if (!queryOutput.next()) { return false; }

            String queryLineID = queryOutput.getString("id_linea");
            String queryRideID = queryOutput.getString("id_corsa");
            String queryState = queryOutput.getString("stato");
            Time queryStartTime = queryOutput.getTime("ora_inizio");
            Time queryStopTime = queryOutput.getTime("ora_fine");
            Integer queryPriority = queryOutput.getInt("priorita");

            if (!queryLineID.equals(lineID) || !(queryRideID.equals(rideID))) { return false;  }

        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace(); }


        return true;
    }

    //  `
    public static ObservableList<Notice> getNoticesViewContent() {
        noticeObservableList = FXCollections.observableArrayList();

        // SQL Query
        String noticeViewQuery = "select * from avviso_utenza";

        try {
            if (!executeQuery(noticeViewQuery)) return null;
            while (queryOutput.next()) {

                String queryNoticeID = queryOutput.getString("id_avviso_utenza");
                String queryLineID = queryOutput.getString("id_linea");
                String queryRideID = queryOutput.getString("id_corsa");
                String queryNoticeName = queryOutput.getString("nome_avviso");
                String queryNoticeText = queryOutput.getString("testo");
                LocalDate queryNoticeDate = queryOutput.getDate("data").toLocalDate();

                noticeObservableList.add(new Notice(queryNoticeID, queryNoticeDate, queryNoticeName, queryNoticeText, queryRideID, queryLineID));
            }
        } catch(SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return noticeObservableList;
    }


    public static ObservableList<Linea> getLineaViewContent() {
        lineaObservableList = FXCollections.observableArrayList();

        // SQL Query
        String lineaViewQuery = "select * from linea";

        try {
            if (!executeQuery(lineaViewQuery)) return null;

            while (queryOutput.next()) {

                String queryLineaID = queryOutput.getString("id_linea");
                Integer queryLineaLunghezza = queryOutput.getInt("lunghezza");
                String queryLineaFermataInizio = queryOutput.getString("fermata_inizio");
                String queryLineaFermataFine = queryOutput.getString("fermata_fine");
                LocalDate queryLineaDataAttivazione = queryOutput.getDate("data_attivazione").toLocalDate();
                Time queryLineaOrarioApertura = queryOutput.getTime("orario_apertura");
                Time queryLineaOrarioChiusura = queryOutput.getTime("orario_chiusura");

                lineaObservableList.add(new Linea(queryLineaID, queryLineaLunghezza, queryLineaFermataInizio, queryLineaFermataFine, queryLineaDataAttivazione, null,null));


            }

        } catch(SQLException ex) {
            Logger.getLogger(InfoViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }


        return lineaObservableList;







    }
    public static ObservableList<Corsa> getCorsaViewContent() {
       corsaObservableList = FXCollections.observableArrayList();

        // SQL Query
        String corsaViewQuery = "select * from corsa";

        try {
            if (!executeQuery(corsaViewQuery)) return null;
            while (queryOutput.next()) {

                String queryCorsaID = queryOutput.getString("id_corsa");
                String queryLineaID = queryOutput.getString("id_linea");
                String queryCorsaStato = queryOutput.getString("stato");
                LocalDate queryCorsaOraInizio = queryOutput.getDate("ora_inizio").toLocalDate();
                LocalDate queryCorsaOraFine = queryOutput.getDate("ora_fine").toLocalDate();
                Integer queryCorsaPriorità = queryOutput.getInt("priorita");


                corsaObservableList.add(new Corsa(queryCorsaID, queryLineaID, queryCorsaStato, queryCorsaOraInizio, queryCorsaOraFine, queryCorsaPriorità));
            }
        } catch(SQLException ex) {
            Logger.getLogger(InfoViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return corsaObservableList;
    }
    public static ObservableList<TravelDocument> getTravelDocumentsViewContent() {
        travelDocumentObservableList = FXCollections.observableArrayList();

        // SQL Query
        String travelDocumentsViewQuery = "";

        try {
            if (!executeQuery(travelDocumentsViewQuery)) return null;
            while (queryOutput.next()) {


                // retrive information on travel documents bought by citizenID

                //

                // add travel documents to observable list

            }
        } catch(SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return travelDocumentObservableList;
    }

}
