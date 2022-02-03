package com.prog3.ipt.Model.FacadeClasses;

import com.prog3.ipt.Controller.InfoViewController;
import com.prog3.ipt.Controller.NoticesViewController;
import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.LineRide.RideLineFX;
import com.prog3.ipt.Model.LineRide.Notice;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
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
    private static PreparedStatement preparedStatement;
    private static ResultSet queryOutput;
    private static int queryOutputDML;
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

    private static boolean updatePreparedStatement(String queryTemplate) {
        queryOutput = null;
        preparedStatement = null;
        connection = null;

        try {
            connect();
            connection = databaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(queryTemplate);
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean updateTransactionStatement(String queryTemplate) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;

            preparedStatement.setString(1, ObservableSingleton.getOrder().getTransactionCode());
            preparedStatement.setString(2, ObservableSingleton.getOrder().getCitizenID());
            preparedStatement.setDouble(3, ObservableSingleton.getOrder().getPurchasePrice());
            preparedStatement.setString(4, ObservableSingleton.getPaymentMethodString());
            preparedStatement.setDate(5, Date.valueOf(ObservableSingleton.getOrder().getPurchaseDate()));

            queryOutputDML = preparedStatement.executeUpdate();

            if (queryOutputDML == 0) return false;
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



        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ObservableList<Notice> getNoticesViewContent() {
        noticeObservableList = FXCollections.observableArrayList();
        String noticeViewQuery = "select * from avviso_utenza where DATE(data) >= CURDATE()";

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
        String allLineRideQuery = "select * from linea left join corsa on linea.id_linea = corsa.id_linea union all " +
                "select * from linea left join corsa on linea.id_linea = corsa.id_linea " +
                "where linea.id_linea is null";

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

    public static ObservableList<TravelDocumentFX> getMyTicketsViewContent() {
        travelDocumentObservableList = FXCollections.observableArrayList();
        String myTicketsViewQuery = "select transazione.id_transazione, biglietto.id_biglietto, biglietto.id_linea, biglietto.id_corsa, biglietto.data_emissione, biglietto.data_timbro, biglietto.data_scadenza, biglietto.prezzo " +
                "from biglietto join transazione on biglietto.id_transazione = transazione.id_transazione and biglietto.id_cittadino = transazione.id_cittadino "+
                "where transazione.id_transazione in (" +
                    "select transazione.id_transazione "+
                    "from transazione join cittadino on cittadino.id_cittadino = transazione.id_cittadino "+
                    "where cittadino.id_cittadino = \""+ ObservableSingleton.getCitizen().getCitizenID() +"\");";

        try {
            if (!executeQuery(myTicketsViewQuery)) return null;
            while (queryOutput.next()) {

                // retrive information on tickets bought by citizenID
                String queryTransactionID = queryOutput.getString("id_transazione");
                String queryTravelDocumentID = queryOutput.getString("id_biglietto");
                String queryLineID = queryOutput.getString("id_linea");
                String queryRideID = queryOutput.getString("id_corsa");
                LocalDate queryIssueDate = queryOutput.getDate("data_emissione").toLocalDate();
                LocalDate queryStampDate = queryOutput.getDate("data_timbro").toLocalDate();
                LocalDate queryExpirationDate = queryOutput.getDate("data_scadenza").toLocalDate();
                Double queryPrice = queryOutput.getDouble("prezzo");


                // add travel documents to observable list
                travelDocumentObservableList.add(new TravelDocumentFX(queryTravelDocumentID, queryPrice, queryIssueDate, queryExpirationDate, queryTransactionID, queryLineID, queryRideID, queryStampDate, null));

            }
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return travelDocumentObservableList;
    }
    public static ObservableList<TravelDocumentFX> getMyMembershipsViewContent() {
        travelDocumentObservableList = FXCollections.observableArrayList();
        String myMembershipsViewQuery = "select transazione.id_transazione, abbonamento.id_abbonamento, abbonamento.data_emissione, abbonamento.data_inizio_validita, abbonamento.data_scadenza, abbonamento.prezzo "+
                "from abbonamento join transazione on abbonamento.id_cittadino = transazione.id_cittadino and abbonamento.id_transazione = transazione.id_transazione "+
                "where transazione.id_transazione in ("+
                "select transazione.id_transazione "+
                "from transazione join cittadino on cittadino.id_cittadino = transazione.id_cittadino "+
                "where cittadino.id_cittadino = \""+ ObservableSingleton.getCitizen().getCitizenID() +"\");";

        try {
            if (!executeQuery(myMembershipsViewQuery)) return null;
            while (queryOutput.next()) {

                // retrive information on tickets bought by citizenID
                String queryTransactionID = queryOutput.getString("id_transazione");
                String queryTravelDocumentID = queryOutput.getString("id_abbonamento");
                LocalDate queryIssueDate = queryOutput.getDate("data_emissione").toLocalDate();
                LocalDate queryStartDate = queryOutput.getDate("data_inizio_validita").toLocalDate();
                LocalDate queryExpirationDate = queryOutput.getDate("data_scadenza").toLocalDate();
                Double queryPrice = queryOutput.getDouble("prezzo");

                // add travel documents to observable list
                travelDocumentObservableList.add(new TravelDocumentFX(queryTravelDocumentID, queryPrice, queryIssueDate, queryExpirationDate, queryTransactionID, null, null, null, queryStartDate));

            }
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return travelDocumentObservableList;
    }


    public static boolean insertTransaction() {
        // generate transaction code;
        String transactionCode = UUID.randomUUID().toString().substring(0, 5);

        // set transaction code to a temporary order
        Order temporaryOrder = new Order(UUID.randomUUID().toString().substring(0, 5), LocalDate.now(), ObservableSingleton.getOrder().getPurchasePrice(), ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), ObservableSingleton.getOrder().getPurchaseList(), ObservableSingleton.getOrder().getPurchaseObservableList());

        // for each travel document in purchase list, set transaction code
        for (TravelDocument travelDocumentObject : ObservableSingleton.getOrder().getPurchaseList()) {

            // create new travel document using Factory, set transaction code to new travel document

            // add travel document to temporary order's purchase list

        }
        // set temporary order to observable singleton order
        ObservableSingleton.setOrder(new Order(UUID.randomUUID().toString().substring(0, 5), LocalDate.now(), ObservableSingleton.getOrder().getPurchasePrice(), ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), ObservableSingleton.getOrder().getPurchaseList(), ObservableSingleton.getOrder().getPurchaseObservableList()));

        // Template Insert Query
        String insertQueryTemplate = "INSERT INTO transazione (id_transazione, id_cittadino, costo, metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?);";

        if (!updateTransactionStatement(insertQueryTemplate)) return false;
        return true;
    }










    // Sezione @dennewbie
    private static boolean insertCitizenQuerySender(String queryTemplate, Citizen newCitizen) {
        try {
            if (!FacadeSingleton.updatePreparedStatement(queryTemplate)) return false;
            FacadeSingleton.preparedStatement.setString(1, newCitizen.getCitizenID());
            FacadeSingleton.preparedStatement.setString(2, newCitizen.getUsername());
            FacadeSingleton.preparedStatement.setString(3, newCitizen.getPassword());
            FacadeSingleton.preparedStatement.setString(4, newCitizen.getEmail());
            FacadeSingleton.preparedStatement.setString(5, newCitizen.getName());
            FacadeSingleton.preparedStatement.setString(6, newCitizen.getSurname());
            FacadeSingleton.preparedStatement.setDate(7, Date.valueOf(newCitizen.getRegistrationDate()));
            FacadeSingleton.preparedStatement.setDate(8, Date.valueOf(newCitizen.getBirthDate()));
            FacadeSingleton.queryOutputDML = FacadeSingleton.preparedStatement.executeUpdate();


            if (FacadeSingleton.queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean updateCitizenQuerySender(String queryTemplate, Citizen newCitizen) {
        try {
            if (!FacadeSingleton.updatePreparedStatement(queryTemplate)) return false;

            //System.out.print(newCitizen);
            FacadeSingleton.preparedStatement.setString(1, newCitizen.getPassword());
            FacadeSingleton.preparedStatement.setString(2, newCitizen.getEmail());
            FacadeSingleton.preparedStatement.setString(3, newCitizen.getName());
            FacadeSingleton.preparedStatement.setString(4, newCitizen.getSurname());
            FacadeSingleton.preparedStatement.setDate(5, Date.valueOf(newCitizen.getBirthDate()));
            FacadeSingleton.preparedStatement.setString(6, newCitizen.getCitizenID());
            FacadeSingleton.queryOutputDML = FacadeSingleton.preparedStatement.executeUpdate();

            if (FacadeSingleton.queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Citizen retrieveCitizen(String citizenUsername, String citizenPassword) {
        Citizen retrievedCitizen;
        String queryCitizen = "select * from cittadino where username = \""+ citizenUsername +"\" and password = \"" + citizenPassword + "\";";
        if (!FacadeSingleton.executeQuery(queryCitizen)) return null;
        try {
            if (!FacadeSingleton.queryOutput.next()) return null;
            String queryCitizenID = FacadeSingleton.queryOutput.getString("id_cittadino");
            String queryUsername = FacadeSingleton.queryOutput.getString("username");
            String queryPassword = FacadeSingleton.queryOutput.getString("password");
            String queryEmail = FacadeSingleton.queryOutput.getString("email");
            String queryName = FacadeSingleton.queryOutput.getString("nome");
            String querySurname = FacadeSingleton.queryOutput.getString("cognome");
            LocalDate queryBirthDate = FacadeSingleton.queryOutput.getDate("data_nascita").toLocalDate();

            retrievedCitizen = new Citizen(queryName, querySurname, queryBirthDate, queryEmail, queryPassword, queryUsername);
            retrievedCitizen.setCitizenID(queryCitizenID);
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
        }
        return retrievedCitizen;
    }

    public static boolean insertCitizen(Citizen localCitizen) {
        String insertQueryTemplate = "INSERT INTO cittadino (id_cittadino, username, password, email, nome, cognome, data_registrazione, data_nascita) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        if (!FacadeSingleton.insertCitizenQuerySender(insertQueryTemplate, localCitizen)) return false;
        return true;
    }

    public static boolean validateGeneratedCitizenID(Citizen localCitizen) {
        String checkCitizenQueryTemplate = "select * from cittadino where cittadino.id_cittadino = \"" + localCitizen.getCitizenID() + "\";";
        try {
            if (!FacadeSingleton.executeQuery(checkCitizenQueryTemplate)) return true;
            if (FacadeSingleton.queryOutput.next()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean updateCitizenData(Citizen localCitizen) {
        String updateQueryTemplate = "UPDATE cittadino SET password = ?, email = ?, nome = ?, cognome = ?, data_nascita = ? where id_cittadino = ?";
        if (!FacadeSingleton.updateCitizenQuerySender(updateQueryTemplate, localCitizen)) return false;
        return true;
    }
}