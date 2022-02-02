package com.prog3.ipt.Model.FacadeClasses;

import com.prog3.ipt.Controller.InfoViewController;
import com.prog3.ipt.Controller.NoticesViewController;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.LineRide.RideLineFX;
import com.prog3.ipt.Model.LineRide.Notice;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
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
    private static ObservableList<RideLineFX> lineRideObservableList;

    private static ObservableList<TravelDocumentFX> travelDocumentObservableList;


    private FacadeSingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
    }

    /**
     * The instance doesn't get created until the method is called for the first time.
     */
    public static FacadeSingleton getInstance() {
        if (instance == null) {
            synchronized (FacadeSingleton.class) {
                if (instance == null) instance = new FacadeSingleton();
            }
        }
        return instance;
    }

    /**
     * Connection to IPT-db
     */
    private static void connect() {
        try {
            databaseConnection = DatabaseConnectionSingleton.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            statement.close();
        } catch (Exception e) { /* Ignored */ }
        try {
            databaseConnection.getConnection().close();
        } catch (Exception e) { /* Ignored */ }
    }

    /*
     * vari metodi che eseguono le query e che restituiscono un risultato
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
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean validateRide(String lineID, String rideID) {
        // SQL query
        String lineQuery = "select corsa.id_corsa, corsa.id_linea, corsa.stato, corsa.ora_inizio, corsa.ora_fine, corsa.priorita " +
                "from linea join corsa on linea.id_linea = corsa.id_linea " +
                "where linea.id_linea = \"" + lineID + "\" and corsa.id_corsa = \"" + rideID + "\";";

        if (!executeQuery(lineQuery)) return false;
        try {
            if (!queryOutput.next()) return false;
            String queryLineID = queryOutput.getString("id_linea");
            String queryRideID = queryOutput.getString("id_corsa");
            String queryState = queryOutput.getString("stato");
            Time queryStartTime = queryOutput.getTime("ora_inizio");
            Time queryStopTime = queryOutput.getTime("ora_fine");
            Integer queryPriority = queryOutput.getInt("priorita");

            //if (!queryLineID.equals(lineID) || !(queryRideID.equals(rideID))) return false;

        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ObservableList<Notice> getNoticesViewContent() {
        noticeObservableList = FXCollections.observableArrayList();
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
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return noticeObservableList;
    }


    public static ObservableList<RideLineFX> getCorsaLineaViewContent() {
        lineRideObservableList = FXCollections.observableArrayList();
        // SQL Query
        // TODO: aggiungere full outer join
        String allLineRideQuery = "select * from linea join corsa on linea.id_linea = corsa.id_linea";

        try {
            if (!executeQuery(allLineRideQuery)) return null;
            while (queryOutput.next()) {
                String queryLineID = queryOutput.getString("id_linea");
                Integer queryLineLength = queryOutput.getInt("lunghezza");
                String queryLineStartingStation = queryOutput.getString("fermata_inizio");
                String queryLineEndingStation = queryOutput.getString("fermata_fine");
                LocalDate queryLineActivationDate = queryOutput.getDate("data_attivazione").toLocalDate();
                Time queryLineOpeningHour = queryOutput.getTime("orario_apertura");
                Time queryLineClosingHour = queryOutput.getTime("orario_chiusura");

                String queryRideID = queryOutput.getString("id_corsa");
                String queryRideStatus = queryOutput.getString("stato");
                Time queryRideStartingHour = queryOutput.getTime("ora_inizio");
                Time queryRideEndingHour = queryOutput.getTime("ora_fine");
                Integer queryRidePriority = queryOutput.getInt("priorita");
                lineRideObservableList.add(new RideLineFX(queryRideID, queryRideStatus, queryRideStartingHour, queryRideEndingHour, queryRidePriority, queryLineID, queryLineLength, queryLineStartingStation, queryLineEndingStation, queryLineActivationDate, queryLineOpeningHour, queryLineClosingHour));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InfoViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return lineRideObservableList;
    }

    public static ObservableList<TravelDocumentFX> getMyTicketsViewContent(String citizenID) {
        travelDocumentObservableList = FXCollections.observableArrayList();
        String myTicketsViewQuery = "";
        try {
            if (!executeQuery(myTicketsViewQuery)) return null;
            while (queryOutput.next()) {
                // retrive information on travel documents bought by citizenID
                String queryTransactionID = queryOutput.getString("id_transizione");

                // alias for the column in SQL query
                String queryTravelDocumentID = queryOutput.getString("id_");

                String queryLineID = queryOutput.getString("id_linea");
                String queryRideID = queryOutput.getString("id_corsa");
                LocalDate queryIssueDate = queryOutput.getDate("data_emissione").toLocalDate();
                LocalDate queryStartDate = queryOutput.getDate("data_inizio_validita").toLocalDate();
                LocalDate queryStampDate = queryOutput.getDate("data_timbro").toLocalDate();
                LocalDate queryExpirationDate = queryOutput.getDate("data_scadenza").toLocalDate();
                Double queryPrice = queryOutput.getDouble("prezzo");

                // add travel documents to observable list
                travelDocumentObservableList.add(new TravelDocumentFX(queryTravelDocumentID, queryPrice, queryIssueDate, queryExpirationDate, queryTransactionID, queryLineID, queryRideID, queryStampDate, queryStartDate));

            }
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return travelDocumentObservableList;
    }

    public static boolean insertTransaction() {
        // set transaction code;
        ObservableSingleton.setOrder(new Order(UUID.randomUUID().toString(), ObservableSingleton.getOrder().getPurchaseDate(), ObservableSingleton.getOrder().getPurchasePrice(), ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), ObservableSingleton.getOrder().getPurchaseList(), ObservableSingleton.getOrder().getPurchaseObservableList()));

        // SQL Query
        String insertQuery = "INSERT INTO transazione (id_transazione, id_cittadino, costo, metodo_pagamento, data_pagamento) VALUES (\"" +
                ObservableSingleton.getOrder().getTransactionCode() + "\", \"" +
                ObservableSingleton.getOrder().getCitizenID() + "\", " +
                ObservableSingleton.getOrder().getPurchasePrice() + ", \"" +
                ObservableSingleton.getPaymentMethodString() + "\", " +
                ObservableSingleton.getOrder().getPurchaseDate() + ");";

        if (!executeQuery(insertQuery)) return false;
        return true;
    }
}
