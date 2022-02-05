package com.prog3.ipt.Model.FacadeClasses;

import com.prog3.ipt.Controller.InfoViewController;
import com.prog3.ipt.Controller.NoticesViewController;
import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.LineRide.RideLineFX;
import com.prog3.ipt.Model.LineRide.Notice;
import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import com.prog3.ipt.Model.TravelDocumentClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
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

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    // Generate Salt. The generated value can be stored in DB.
    public static String salt;

    /**
     * FacadeSingleton constructor
     */
    private FacadeSingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
    }

    /**
     * Recovers the salt key from the Salt table of IPT database and returns the FacadeSingleton instance.
     * The instance doesn't get created until the method is called for the first time.
     * @see FacadeSingleton#getSaltFromDB() 
     * @return A reference to a FacadeSingleton object.
     */
    public static FacadeSingleton getInstance() {
        if (instance == null) {
            synchronized (FacadeSingleton.class) {
                if (instance == null) {
                    instance = new FacadeSingleton();
                    FacadeSingleton.getSaltFromDB();
                }
            }
        }
        return instance;
    }

    /**
     * Set up a connection to IPT MySQL Database using a DatabaseConnectionSingleton object.
     */
    private static void connect() {
        try {
            databaseConnection = DatabaseConnectionSingleton.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close a connection previously opened
     * @see Statement#close() 
     * @see Connection#close() 
     */
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

    /**
     * Wrapper method that allow to connect to IPT database, initialize a Statement object and get the result set as output of the Statement object.
     * The returning ResultSet object is put in a static ResultSet object, such as queryOutput variable.
     * @see java.sql.Statement#executeQuery(String)
     * @see DatabaseConnectionSingleton#getConnection() 
     * @see Connection#createStatement()  
     * @param queryString an SQL statement to be sent to the database, typically a static SQL SELECT statement
     * @return True if the given query is syntactically correct, otherwise false.
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

    /**
     * Wrapper method that allow to connect to IPT database, initialize and set a PreparedStatement object
     * @see java.sql.Connection#prepareStatement(String)
     * @see DatabaseConnectionSingleton#getConnection()
     * @param queryTemplate an SQL statement to be sent to the database, typically a static SQL INSERT statement
     * @return True if the given query is syntactically correct, otherwise false
     */
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

    /**
     * Allows to make a DML operation to the Ticket table in IPT database, SQL INSERT in this case, and get the result of the given SQL INSERT operation
     * @see java.sql.PreparedStatement#executeUpdate(String)
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see PreparedStatement#executeUpdate(String) 
     * @param queryTemplate an SQL statement to be sent to the database, typically a static SQL INSERT statement
     * @param singleTicket A reference to a SingleTicket object that provides its fields as parameters to a PreparedStatement object
     * @return True if the result of given query is syntactically correct and is not zero, otherwise false
     */
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

    /**
     * Allows to make a DML operation to the Membership table in IPT database, SQL INSERT in this case, and get the result of the given SQL INSERT operation
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see java.sql.PreparedStatement#executeUpdate()
     * @param queryTemplate an SQL statement to be sent to the database, typically a static SQL INSERT statement
     * @param membership A reference to a Membership object that provides its fields as parameters to a PreparedStatement object
     * @return True if the result of given query is syntactically correct and is not zero, otherwise false
     */
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

    /**
     * Allows to make a DML operation to the Ticket table in IPT database, SQL DELETE in this case, and get the result of the given SQL DELETE operation
     * The returning integer value is put in a static int object, such as queryOutputDML variable.
     * @see FacadeSingleton#updatePreparedStatement(String)
     * @see PreparedStatement#executeUpdate() 
     * @param queryTemplate An SQL statement to be sent to the database, typically a static SQL DELETE statement
     * @return True if the result of given query is syntactically correct and is not zero, otherwise false
     */
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

    /**
     * Allows to make a DML operation to the Membership table in IPT database, SQL DELETE in this case, and get the result of the given SQL DELETE operation
     * The returning integer value is put in a static int object, such as queryOutputDML variable.
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see PreparedStatement#executeUpdate() 
     * @param queryTemplate An SQL statement to be sent to the database, typically a static SQL DELETE statement
     * @return True if the result of given query is syntactically correct and is not zero, otherwise false
     */
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

    /**
     * Allows to make a DML operation to the Transaction table in IPT database, SQL INSERT in this case, and get the result of the given SQL INSERT operation
     * The returning integer value is put in a static int object, such as queryOutputDML variable.
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see PreparedStatement#executeUpdate() 
     * @param queryTemplate An SQL statement to be sent to the database, typically a static SQL INSERT statement
     * @param localOrder A reference to a Order object that provides its fields as parameters to a PreparedStatement object
     * @return True if the result of given query is syntactically correct and is not zero, otherwise false
     */
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

    /**
     * Validates a 5-chars transaction code generated using randomUUID method and converted to a String object.
     * Checks if the transaction code string is not already used by a record in the Transaction table of IPT database
     * @see UUID#randomUUID()
     * @see FacadeSingleton#executeQuery(String)
     * @param transactionCode A 5-chars transaction code generated using randomUUID method and converted to a String object.
     * @return True if the transaction code is not already used, so it is a valid transaction code, otherwise false
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

    /**
     * Inserts an order made by the logged citizen as a transaction record to the Transaction table of IPT database.
     * This operation obviously involves the insertion of the travel documents, bought by the logged citizen, in the Ticket and Membership tables of IPT database.
     * @see UUID#randomUUID() 
     * @see ObservableSingleton#updateOrderWithOrderID(String, LocalDate, double, String, PaymentMethodStrategy, ArrayList, ObservableList) 
     * @see FacadeSingleton#updateTransactionStatement(String, Order) 
     * @see TravelDocument#updateTravelDocument(double, LocalDate, LocalDate, String, String, String, LocalDate, LocalDate) 
     * @see FacadeSingleton#updateTicketStatement(String, SingleTicket) 
     * @see FacadeSingleton#updateMembershipStatement(String, Membership) 
     * @return True on success, otherwise false
     */
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

    /**
     * Validates an issue date for a SingleTicket object.
     * Tickets cannot be bought for an inactive line.
     * @see FacadeSingleton#executeQuery(String)
     * @param ticketLineID A string that represents a unique identifier of a line
     * @param ticketIssueDate An issue date for the ticket
     * @return True if the issue date of the ticket is valid, otherwise false
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

    /**
     * Checks if the ticket that a citizen is going to buy is a ticket for a valid line and ride.
     * @see FacadeSingleton#executeQuery(String) 
     * @param ticketLineID A unique identifier of the line to validate
     * @param ticketRideID A unique identifier of the ride to validate
     * @return True if the line and the ride are valid, otherwise false
     */
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

    /**
     * Returns the tickets bought by the logged citizen that are displayed in the MyTickets view
     * @see FXCollections#observableArrayList() 
     * @see FacadeSingleton#executeQuery(String) 
     * @see ObservableList#add(Object) 
     * @return An ObservableList<TravelDocumentFX> used for displaying items in a TableView JavaFX object
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

    /**
     * Deletes a ticket bought by the logged citizen from the Ticket table of IPT database
     * @see FacadeSingleton#deleteSingleTicketStatement(String) 
     * @param localSingleTicket A reference to a TravelDocumentFX object, representing a ticket
     * @return True on success, otherwise false
     */
    public static boolean deleteMySingleTicket(TravelDocumentFX localSingleTicket) {

        String deleteTemplateQuery = "delete from biglietto where biglietto.id_biglietto = \"" + localSingleTicket.getTravelDocumentID() + "\";";
        if (!FacadeSingleton.deleteSingleTicketStatement(deleteTemplateQuery)) return false;
        return true;
    }

    /**
     * Query richieste da MyMembershipViewController
     */

    /**
     * Returns the memberships bought by the logged citizen that are displayed in the MyMemberships view
     * @see FacadeSingleton#executeQuery(String) 
     * @return An ObservableList<TravelDocumentFX> used for displaying items in a TableView JavaFX object
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

    /**
     * Deletes a membership bought by the logged citizen from the Ticket table of IPT database
     * @see FacadeSingleton#deleteMembershipStatement(String) 
     * @param localMembership A reference to a TravelDocumentFX object, representing a membership
     * @return True on success, otherwise false
     */
    public static boolean deleteMyMembership(TravelDocumentFX localMembership) {
        String deleteTemplateQuery = "delete from abbonamento where abbonamento.id_abbonamento = \"" + localMembership.getTravelDocumentID() + "\";";
        if (!FacadeSingleton.deleteMembershipStatement(deleteTemplateQuery)) return false;
        return true;
    }

    /**
     * Query richieste da NoticesViewController
     */


    /**
     * Returns the notices issued by IPT that are displayed in the Notices view
     * @see FacadeSingleton#executeQuery(String) 
     * @return An ObservableList<Notice> used for displaying items in a TableView JavaFX object
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

    /**
     * Returns information about the IPT lines and rides that are displayed in Info view
     * @see FacadeSingleton#executeQuery(String) 
     * @return An ObservableList<RideLineFX> used for displaying items in a TableView JavaFX object
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

    /**
     * Returns the result of an SQL INSERT operation in the Citizen table of IPT database
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see PreparedStatement#executeUpdate() 
     * @param queryTemplate An SQL statement to be sent to the database, typically a static SQL INSERT statement
     * @param newCitizen A reference to a Citizen object that provides its fields to a PreparedStatement object
     * @return True if the given query is syntactically correct and the result is not zero, otherwise false
     */
    private static boolean insertCitizenQuerySender(String queryTemplate, Citizen newCitizen) {
        try {
            if (!FacadeSingleton.updatePreparedStatement(queryTemplate)) return false;

            FacadeSingleton.preparedStatement.setString(1, newCitizen.getCitizenID());
            FacadeSingleton.preparedStatement.setString(2, newCitizen.getUsername());
            FacadeSingleton.preparedStatement.setString(3, FacadeSingleton.generateSecurePassword(newCitizen.getPassword()));
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

    /**
     * Updates logged citizen's data in the Citizen table of IPT database
     * @see FacadeSingleton#updatePreparedStatement(String) 
     * @see PreparedStatement#executeUpdate() 
     * @param queryTemplate An SQL statement to be sent to the database, typically a static SQL UPDATE statement
     * @param newCitizen A reference to a Citizen object, typically an IPT user
     * @return
     */
    private static boolean updateCitizenQuerySender(String queryTemplate, Citizen newCitizen) {
        try {
            if (!FacadeSingleton.updatePreparedStatement(queryTemplate)) return false;
            FacadeSingleton.preparedStatement.setString(1, FacadeSingleton.generateSecurePassword(newCitizen.getPassword()));
            FacadeSingleton.preparedStatement.setString(2, newCitizen.getEmail());
            FacadeSingleton.preparedStatement.setString(3, newCitizen.getName());
            FacadeSingleton.preparedStatement.setString(4, newCitizen.getSurname());
            FacadeSingleton.preparedStatement.setDate(5, Date.valueOf(newCitizen.getBirthDate()));
            FacadeSingleton.preparedStatement.setString(6, newCitizen.getCitizenID());
            FacadeSingleton.queryOutputDML = FacadeSingleton.preparedStatement.executeUpdate();

            System.out.println("NUOVA PASS: " + newCitizen.getPassword());
            if (FacadeSingleton.queryOutputDML == 0) return false;
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Retrieves data about citizen signed in with a given username and a given password
     * @see FacadeSingleton#executeQuery(String) 
     * @see FacadeSingleton#verifyUserPassword(String, String) 
     * @param citizenUsername A string that represents the username of a Citizen
     * @param citizenPassword A string that represents the password of a Citizen
     * @return A reference to a Citizen object if the given username and the given password are valid, otherwise null
     */
    public static Citizen retrieveCitizen(String citizenUsername, String citizenPassword) {
        Citizen retrievedCitizen;
        String queryCitizen = "select * from cittadino where cittadino.username = \"" + citizenUsername + "\";";
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

            if (!FacadeSingleton.verifyUserPassword(citizenPassword, queryPassword)) return null;
            retrievedCitizen = new Citizen(queryCitizenID, queryName, querySurname, queryBirthDate, queryEmail, citizenPassword, queryUsername);
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return null;
        }
        return retrievedCitizen;
    }

    /**
     * Inserts a Citizen record in the Citizen table of IPT database
     * @see FacadeSingleton#insertCitizenQuerySender(String, Citizen) 
     * @param localCitizen A reference to a Citizen object, typically a new user
     * @return True on success, otherwise false
     */
    public static boolean insertCitizen(Citizen localCitizen) {
        String insertQueryTemplate = "INSERT INTO cittadino (id_cittadino, username, password, email, nome, cognome, data_registrazione, data_nascita) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        if (!FacadeSingleton.insertCitizenQuerySender(insertQueryTemplate, localCitizen)) return false;
        return true;
    }

    /**
     ** Validates a 5-chars citizen unique identifier generated using randomUUID method and converted to a String object.
     * Checks if the citizen unique identifier string is not already used by a record in the Citizen table of IPT database
     * @see UUID#randomUUID()
     * @see FacadeSingleton#executeQuery(String) 
     * @param localCitizen A 5-chars citizen unique identifier generated using randomUUID method and converted to a String object.
     * @return True if the citizen unique identifier is not already used, so it is a valid citizen unique identifier, otherwise false
     */
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

    /**
     * Updates logged citizen's data in the Citizen table of IPT database
     * @see FacadeSingleton#updateCitizenQuerySender(String, Citizen)
     * @return True on success, otherwise false
     */
    public static boolean updateCitizenData(Citizen localCitizen) {
        String updateQueryTemplate = "UPDATE cittadino SET password = ?, email = ?, nome = ?, cognome = ?, data_nascita = ? where id_cittadino = ?";
        if (!FacadeSingleton.updateCitizenQuerySender(updateQueryTemplate, localCitizen)) return false;
        return true;
    }


    /**
     * Query richieste da SingleTicket e Membership
     */

    /**
     * Validates a 5-chars ticket unique identifier generated using randomUUID method and converted to a String object.
     * Checks if the ticket unique identifier string is not already used by a record in the Ticket table of IPT database
     * @see UUID#randomUUID()
     * @see FacadeSingleton#executeQuery(String) 
     * @param singleTicketID A 5-chars ticket unique identifier generated using randomUUID method and converted to a String object.
     * @return True if the ticket unique identifier is not already used, so it is a valid ticket unique identifier, otherwise false
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

    /**
     * Validates a 5-chars membership unique identifier generated using randomUUID method and converted to a String object.
     * Checks if the membership unique identifier string is not already used by a record in the Ticket table of IPT database
     * @see UUID#randomUUID()
     * @see FacadeSingleton#executeQuery(String) 
     * @param membershipID A 5-chars membership unique identifier generated using randomUUID method and converted to a String object.
     * @return True if the membership unique identifier is not already used, so it is a valid membership unique identifier, otherwise false
     */
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

    /**
     * Password encrypt
     */

    /**
     * Return the salt key stored into the salt field in the Salt table of IPT database.
     * @see FacadeSingleton#executeQuery(String) 
     * @return True on success, otherwise false
     */
    private static boolean getSaltFromDB() {
        String templateQuerySelectSalt = "select salt from salttable;";
        if (!executeQuery(templateQuerySelectSalt)) return false;
        try {
            if (!FacadeSingleton.queryOutput.next()) return false;
            salt  = queryOutput.getString("salt");
        } catch (SQLException e) {
            Logger.getLogger(NoticesViewController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Builds the salt key as string
     * @param length The length of the salt key as integer value
     * @return A string that represents a new salt key string
     */
    private static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        return new String(returnValue);
    }

    /**
     * Hash function to generete a secret key
     * @see PBEKeySpec
     * @see SecretKeyFactory#getInstance(String)
     * @see SecretKeyFactory#generateSecret(KeySpec) 
     * @see PBEKeySpec#clearPassword() 
     * @param password An array of characters representing a password string
     * @param salt An array of bytes representing the salt key
     * @return A secret key as an array of bytes
     */
    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Generates a secure password stored in the password field in the Citizen table of IPT database
     * @see FacadeSingleton#hash(char[], byte[]) 
     * @param password A string that represents a user password
     * @return A coded string for the given password
     */
    private static String generateSecurePassword(String password) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    /**
     * Verifies the given user password is valid
     * @see FacadeSingleton#generateSecurePassword(String) 
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param providedPassword A string that represents a user password
     * @param securedPassword A string that represents a secured password stored in the password filed of a Citizen record in the Citizen table of IPT database
     * @return True if the given user password is valid, otherwise false
     */
    private static boolean verifyUserPassword(String providedPassword, String securedPassword) {
        boolean returnValue = false;
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword);
        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        return returnValue;
    }
}
