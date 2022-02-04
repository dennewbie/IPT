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
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization mechanism. */
public class FacadeSingleton {
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
        if (instance == null) { synchronized (FacadeSingleton.class) { if (instance == null) instance = new FacadeSingleton(); } }
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

    /**
     * Metodi che eseguono gli handler per eseguire la query e per restituire un risultato al chiamante
     */
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
            preparedStatement.setNull(3, Types.DATE);
            preparedStatement.setNull(4, Types.DATE);
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
    private static boolean deleteSingleTicketStatement(String queryTemplate) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;
            queryOutputDML = preparedStatement.executeUpdate();
            if (queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private static boolean deleteMembershipStatement(String queryTemplate) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;
            queryOutputDML = preparedStatement.executeUpdate();
            if (queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private static boolean updateTransactionStatement(String queryTemplate, Order localOrder) {
        try {
            if (!updatePreparedStatement(queryTemplate)) return false;
            preparedStatement.setString(1, localOrder.getTransactionCode());
            preparedStatement.setString(2, localOrder.getCitizenID());
            preparedStatement.setDouble(3, localOrder.getPurchasePrice());
            preparedStatement.setString(4, ObservableSingleton.getPaymentMethodString());
            preparedStatement.setDate(5, Date.valueOf(localOrder.getPurchaseDate()));
            queryOutputDML = preparedStatement.executeUpdate();
            if (queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Query richieste da TravelDocumentManagementViewController
     */
    private static boolean validateGeneratedTransactionID(String transactionCode) {
        // SQL query
        String transactionQueryTemplate = "select * from transazione where id_transazione = \"" + transactionCode +"\";";
        try {
            if (!FacadeSingleton.executeQuery(transactionQueryTemplate)) return true;
            if (FacadeSingleton.queryOutput.next()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return true;
    }
    public static boolean insertTransaction() {
        // Template Insert Query
        String insertTransactionQueryTemplate = "INSERT INTO transazione (id_transazione, id_cittadino, costo, metodo_pagamento, data_pagamento) VALUES (?, ?, ?, ?, ?);";
        String insertTicketQueryTemplate = "INSERT INTO biglietto(id_biglietto, data_emissione, data_scadenza, data_timbro, prezzo, id_corsa, id_linea, id_transazione, id_cittadino) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertMembershipQueryTemplate = "INSERT INTO abbonamento(id_abbonamento, data_emissione, data_scadenza, data_inizio_validita, prezzo, id_transazione, id_cittadino) VALUES (?, ?, ?, ?, ?, ?, ?);";
        // generate transaction code
        String transactionCode = null;
        do {
            transactionCode = UUID.randomUUID().toString().substring(0, 5);
        } while(!FacadeSingleton.validateGeneratedTransactionID(transactionCode));

        LocalDate issueDate = LocalDate.now();
        ObservableSingleton.updateOrderWithOrderID(transactionCode, issueDate,ObservableSingleton.getOrder().getPurchasePrice(), ObservableSingleton.getCitizen().getCitizenID(), ObservableSingleton.getOrder().getPaymentMethodStrategy(), ObservableSingleton.getOrder().getPurchaseList(), FXCollections.observableArrayList());

        // insert transaction
        if (!updateTransactionStatement(insertTransactionQueryTemplate, ObservableSingleton.getOrder())) return false;
        // for each travel document in purchase list, set transaction code
        for (TravelDocument travelDocumentObject : ObservableSingleton.getOrder().getPurchaseList()) {
            // check travel document type
            // add travel document to temporary order's purchase list
            if (travelDocumentObject instanceof SingleTicket) {
                // create new single ticket, set transaction code to new single ticket and insert single ticket
                travelDocumentObject.updateTravelDocument(travelDocumentObject.getPrice(), travelDocumentObject.getIssueDate(), null, transactionCode, ((SingleTicket) travelDocumentObject).getLineID(), ((SingleTicket) travelDocumentObject).getRideID(), null, null);
                if (!updateTicketStatement(insertTicketQueryTemplate, (SingleTicket) travelDocumentObject)) return false;
            } else if (travelDocumentObject instanceof  Membership) {
                // create new membership, set transaction code to new membership and insert membership
                travelDocumentObject.updateTravelDocument(travelDocumentObject.getPrice(), travelDocumentObject.getIssueDate(), ((Membership) travelDocumentObject).getStartDate().plusYears(1), transactionCode, null, null, null, ((Membership) travelDocumentObject).getStartDate());
                if (!updateMembershipStatement(insertMembershipQueryTemplate, (Membership) travelDocumentObject)) return false;
            }
        }
        return true;
    }


    /**
     * Query richieste da AddSingleTicketViewController
     */
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

    /**
     * Query richieste da MySingleTicketsViewController
     */
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
                LocalDate queryStampDate = null, queryExpirationDate = null;
                if (queryOutput.getDate("data_timbro") != null) queryStampDate = queryOutput.getDate("data_timbro").toLocalDate();
                if (queryOutput.getDate("data_scadenza") != null) queryExpirationDate = queryOutput.getDate("data_scadenza").toLocalDate();
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
    public static boolean deleteMySingleTicket(TravelDocumentFX localSingleTicket) {

        String deleteTemplateQuery = "delete from biglietto where biglietto.id_biglietto = \"" + localSingleTicket.getTravelDocumentID() + "\";";
        if (!FacadeSingleton.deleteSingleTicketStatement(deleteTemplateQuery)) return false;
        return true;
    }

    /**
     * Query richieste da MyMembershipViewController
     */
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
    public static boolean deleteMyMembership(TravelDocumentFX localMembership) {
        String deleteTemplateQuery = "delete from abbonamento where abbonamento.id_abbonamento = \"" + localMembership.getTravelDocumentID() + "\";";
        if (!FacadeSingleton.deleteMembershipStatement(deleteTemplateQuery)) return false;
        return true;
    }

    /**
     * Query richieste da NoticesViewController
     */
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

    /**
     * Query richieste da InfoViewController
     */
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

    /**
     * Query richieste da LoginRegisterViewController e EditProfileViewController
     */
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

            retrievedCitizen = new Citizen(queryCitizenID, queryName, querySurname, queryBirthDate, queryEmail, queryPassword, queryUsername);
            if (!citizenUsername.equals(queryUsername) || !citizenPassword.equals(queryPassword)) return null;
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


    /**
     * Query richieste da SingleTicket e Membership
     */
    public static boolean validateGeneratedSingleTicketID(String singleTicketID) {
        // SQL query
        String singleTicketQueryTemplate = "select * from biglietto where id_biglietto = \"" + singleTicketID +"\";";
        try {
            if (!FacadeSingleton.executeQuery(singleTicketQueryTemplate)) return true;
            if (FacadeSingleton.queryOutput.next()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return true;
    }
    public static boolean validateGeneratedMembershipID(String membershipID) {
        // SQL query
        String membershipQueryTemplate = "select * from abbonamento where id_abbonamento = \"" + membershipID +"\";";
        try {
            if (!FacadeSingleton.executeQuery(membershipQueryTemplate)) return true;
            if (FacadeSingleton.queryOutput.next()) return false;
        } catch (SQLException ex) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return true;
    }
}
