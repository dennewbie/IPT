package com.prog3.ipt.Model.FacadeClasses;

import com.prog3.ipt.Controller.InfoViewController;
import com.prog3.ipt.Controller.NoticesViewController;
import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.LineRide.RideLineFX;
import com.prog3.ipt.Model.LineRide.Notice;
import com.prog3.ipt.Model.TravelDocumentClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private static TravelDocumentFactory travelDocumentFactory;

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

    private static boolean updateTicketStatement(String queryTemplate, SingleTicket singleTicket) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;

            preparedStatement.setString(1, singleTicket.getTravelDocumentID());
            preparedStatement.setDate(2, Date.valueOf(singleTicket.getIssueDate()));
            preparedStatement.setDate(3, Date.valueOf(singleTicket.getExpirationDate()));
            preparedStatement.setDate(4, Date.valueOf(singleTicket.getStampDate()));
            preparedStatement.setDouble(5, singleTicket.getPrice());
            preparedStatement.setString(6, singleTicket.getRideID());
            preparedStatement.setString(7, singleTicket.getLineID());
            preparedStatement.setString(8, singleTicket.getTransactionID());
            preparedStatement.setString(9, ObservableSingleton.getCitizen().getCitizenID());

            queryOutputDML = preparedStatement.executeUpdate();

            if (queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean updateMembershipStatement(String queryTemplate, Membership membership) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;

            preparedStatement.setString(1, membership.getTravelDocumentID());
            preparedStatement.setDate(2, Date.valueOf(membership.getIssueDate()));
            preparedStatement.setDate(3, Date.valueOf(membership.getExpirationDate()));
            preparedStatement.setDate(4, Date.valueOf(membership.getStartDate()));
            preparedStatement.setDouble(5, membership.getPrice());
            preparedStatement.setString(6, membership.getTransactionID());
            preparedStatement.setString(7, ObservableSingleton.getCitizen().getCitizenID());

            queryOutputDML = preparedStatement.executeUpdate();

            if (queryOutputDML == 0) return false;
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

    public static boolean checkTicketIssueDateValidity(String ticketLineID, LocalDate ticketIssueDate) {

        // SQL Query - retrieve activationDate from linea
        String activationDateQuery = "select linea.data_attivazione from linea where linea.id_linea = \""+ ticketLineID +"\" LIMIT 1;";

        if (!executeQuery(activationDateQuery)) return false;
        try {
            if (!queryOutput.next()) return false;

            LocalDate queryActivationDate = queryOutput.getDate("data_attivazione").toLocalDate();

            // check if ticket issue date is after line activation date
            if (ticketIssueDate.isBefore(queryActivationDate)) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static boolean validateRide(String ticketLineID, String ticketRideID) {
        // SQL query
        String lineQuery = "select corsa.id_corsa, corsa.id_linea, corsa.stato, corsa.ora_inizio, corsa.ora_fine, corsa.priorita " +
                "from linea join corsa on linea.id_linea = corsa.id_linea " +
                "where linea.id_linea = \"" + ticketLineID + "\" and corsa.id_corsa = \"" + ticketRideID + "\";";

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

        // Template Insert Query
        String insertTransactionQueryTemplate = "INSERT INTO transazione (id_transazione, id_cittadino, costo, metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?);";
        String insertTicketQueryTemplate = "INSERT INTO biglietto(id_biglietto, data_emissione, data_scadenza, data_timbro, prezzo, id_corsa, id_linea, id_transazione, id_cittadino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertMembershipQueryTemplate = "INSERT INTO abbonamento(id_abbonamento, data_emissione, data_scadenza, data_inizio_validita, prezzo, id_transazione, id_cittadino) VALUES (?, ?, ?, ?, ?, ?, ?);";

        // generate transaction code;
        String transactionCode = UUID.randomUUID().toString().substring(0, 5);

        // set transaction code to a temporary order
        Order observedOrder = ObservableSingleton.getOrder();
        double observedPrice = ObservableSingleton.getOrder().getPurchasePrice();
        Order temporaryOrder = new Order(transactionCode, LocalDate.now(), ObservableSingleton.getOrder().getPurchasePrice(), ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), new ArrayList<>(), FXCollections.observableArrayList());

        // insert transaction
        ObservableSingleton.setOrder(temporaryOrder);
        if (!updateTransactionStatement(insertTransactionQueryTemplate)) return false;

        // for each travel document in purchase list, set transaction code
        for (TravelDocument travelDocumentObject : observedOrder.getPurchaseList()) {

            // check travel document type
            // add travel document to temporary order's purchase list
            if (travelDocumentObject instanceof SingleTicket) {
                travelDocumentFactory = new SingleTicketConcreteFactory();

                // create new single ticket, set transaction code to new single ticket
                SingleTicket singleTicket = (SingleTicket) travelDocumentFactory.createTravelDocument(travelDocumentObject.getPrice(),travelDocumentObject.getIssueDate(), travelDocumentObject.getExpirationDate(), transactionCode, ((SingleTicket) travelDocumentObject).getLineID(), ((SingleTicket) travelDocumentObject).getRideID(), ((SingleTicket) travelDocumentObject).getStampDate(), null);
                temporaryOrder.addTravelDocument(singleTicket);

                // insert single ticket
                if (!updateTicketStatement(insertTicketQueryTemplate, singleTicket)) return false;
            }
            else if (travelDocumentObject instanceof  Membership) {
                travelDocumentFactory = new MembershipConcreteFactory();

                // create new membership using Factory, set transaction code to new membership
                Membership membership = (Membership) travelDocumentFactory.createTravelDocument(travelDocumentObject.getPrice(), travelDocumentObject.getIssueDate(),travelDocumentObject.getExpirationDate(), transactionCode, null, null, null, ((Membership) travelDocumentObject).getStartDate());
                temporaryOrder.addTravelDocument(membership);

                // insert membership
                if (!updateMembershipStatement(insertMembershipQueryTemplate, membership)) return false;
            }


        }

        // set temporary order to observable singleton order
        ObservableSingleton.setOrder(new Order(ObservableSingleton.getOrder().getTransactionCode(), ObservableSingleton.getOrder().getPurchaseDate(), observedPrice, ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), temporaryOrder.getPurchaseList(), temporaryOrder.getPurchaseObservableList()));

        return true;
    }










    // Sezione @dennewbie
    private static boolean updateCitizenPreparedStatement(String queryTemplate, Citizen newCitizen) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;
            preparedStatement.setString(1, newCitizen.getCitizenID());
            preparedStatement.setString(2, newCitizen.getUsername());
            preparedStatement.setString(3, newCitizen.getPassword());
            preparedStatement.setString(4, newCitizen.getEmail());
            preparedStatement.setString(5, newCitizen.getName());
            preparedStatement.setString(6, newCitizen.getSurname());
            preparedStatement.setDate(7, Date.valueOf(newCitizen.getRegistrationDate()));
            preparedStatement.setDate(8, Date.valueOf(newCitizen.getBirthDate()));
            queryOutputDML = preparedStatement.executeUpdate();

            if (queryOutputDML == 0) return false;
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
        if (!executeQuery(queryCitizen)) return null;
        try {
            if (!queryOutput.next()) return null;
            String queryCitizenID = queryOutput.getString("id_cittadino");
            String queryUsername = queryOutput.getString("username");
            String queryPassword = queryOutput.getString("password");
            String queryEmail = queryOutput.getString("email");
            String queryName = queryOutput.getString("nome");
            String querySurname = queryOutput.getString("cognome");
            LocalDate queryBirthDate = queryOutput.getDate("data_nascita").toLocalDate();

            retrievedCitizen = new Citizen(queryName, querySurname, queryBirthDate, queryEmail, queryPassword, queryUsername);
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
        }
        return retrievedCitizen;
    }

    public static boolean insertCitizen(Citizen localCitizen) {
        String insertQueryTemplate = "INSERT INTO cittadino (id_cittadino, username, password, email, nome, cognome, data_registrazione, data_nascita) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        if (!updateCitizenPreparedStatement(insertQueryTemplate, localCitizen)) return false;
        return true;
    }

    public static boolean validateGeneratedCitizenID(Citizen localCitizen) {
        String checkCitizenQueryTemplate = "select * from cittadino where cittadino.id_cittadino = \"" + localCitizen.getCitizenID() + "\";";
        try {
            if (!executeQuery(checkCitizenQueryTemplate)) return true;
            if (queryOutput.next()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return true;
    }
}