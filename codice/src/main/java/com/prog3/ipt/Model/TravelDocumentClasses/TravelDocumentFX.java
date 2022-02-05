package com.prog3.ipt.Model.TravelDocumentClasses;

import com.prog3.ipt.Model.MyConstants;

import java.time.LocalDate;
import java.util.Objects;

/**
 * TravelDocumentFX is a class made to display content in a TableView JavaFX object.
 */
public class TravelDocumentFX {
    // Travel Document
    private String travelDocumentID;
    private double price;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String transactionID;
    // SingleTicket
    private String lineID;
    private String rideID;
    private LocalDate stampDate;
    // Membership
    private LocalDate startDate;



    // Constructor
    /**
     * TravelDocumentFX constructor
     * @param travelDocumentID The unique identifier of the travel document
     * @param price The price of the travel document
     * @param issueDate The issue date of the travel document
     * @param expirationDate The date of expire of the travel document
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param lineID The unique identifier of the line if the travel document is a single ticket
     * @param rideID The unique identifier of the ride if the travel document is a single ticket
     * @param stampDate The stamp date of the travel document, in particular a single ticket
     * @param startDate The validity start date of the travel document if it is a membership
     */
    public TravelDocumentFX(String travelDocumentID, double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        this.travelDocumentID = travelDocumentID;
        this.price = price;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
        this.transactionID = transactionID;
        this.lineID = lineID;
        this.rideID = rideID;
        this.stampDate = stampDate;
        this.startDate = startDate;
    }

    /**
     * TravelDocumentFX constructor
     * Builds a TravelDocumentFX object according to the TravelDocument object
     * @param travelDocument A reference to a generic TravelDocument object
     */
    public TravelDocumentFX(TravelDocument travelDocument) {
        this.travelDocumentID = travelDocument.getTravelDocumentID();
        this.price = travelDocument.getPrice();
        this.issueDate = travelDocument.getIssueDate();
        this.expirationDate = travelDocument.getExpirationDate();
        this.transactionID = travelDocument.getTransactionID();
        this.lineID = null;
        this.rideID = null;
        this.stampDate = null;
        this.startDate = null;
    }

    /**
     * TravelDocumentFX constructor
     * Builds a TravelDocumentFX object according to a SingleTicket object
     * @param travelDocument A reference to a SingleTicket object
     */
    public TravelDocumentFX(SingleTicket travelDocument) {
        this.travelDocumentID = travelDocument.getTravelDocumentID();
        this.price = travelDocument.getPrice();
        this.issueDate = travelDocument.getIssueDate();
        this.expirationDate = travelDocument.getExpirationDate();
        this.transactionID = travelDocument.getTransactionID();
        this.lineID = travelDocument.getLineID();
        this.rideID = travelDocument.getRideID();
        this.stampDate = travelDocument.getStampDate();
        this.startDate = null;
    }

    /**
     * TravelDocumentFX constructor
     * Builds a TravelDocumentFX object according to a Membership object
     * @param travelDocument A reference to a Membership object
     */
    public TravelDocumentFX(Membership travelDocument) {
        this.travelDocumentID = travelDocument.getTravelDocumentID();
        this.price = travelDocument.getPrice();
        this.issueDate = travelDocument.getIssueDate();
        this.expirationDate = travelDocument.getExpirationDate();
        this.transactionID = travelDocument.getTransactionID();
        this.lineID = null;
        this.rideID = null;
        this.stampDate = null;
        this.startDate = travelDocument.getStartDate();
    }

    // Setters
    protected void setTravelDocumentID(String travelDocumentID) { this.travelDocumentID = travelDocumentID; }
    protected void setPrice(double price) { this.price = price;}
    protected void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    protected void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    protected void setTransactionID(String transactionID) { this.transactionID = transactionID; }
    protected void setLineID(String lineID) { this.lineID = lineID; }
    protected void setRideID(String rideID) { this.rideID = rideID; }
    protected void setStampDate(LocalDate stampDate) { this.stampDate = stampDate; }
    protected void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    // Getters
    public String getTravelDocumentID() { return travelDocumentID; }
    public double getPrice() { return price; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getTransactionID() { return transactionID; }
    public String getLineID() { return lineID; }
    public String getRideID() { return rideID; }
    public LocalDate getStampDate() { return stampDate; }
    public LocalDate getStartDate() { return startDate; }

    // Others
    /**
     * Produces a TravelDocument object according to the TravelDocumentFX object calling this method
     * @return A reference to a TravelDocument object
     */
    public TravelDocument toTravelDocument() {
        TravelDocumentFactory travelDocumentFactory;
        TravelDocument travelDocument;
        travelDocumentFactory = (this.getStampDate() == null) ? new SingleTicketConcreteFactory() : new MembershipConcreteFactory();
        travelDocument = travelDocumentFactory.createTravelDocument(this.getPrice(), this.getIssueDate(), this.getExpirationDate(), this.getTransactionID(), this.getLineID(), this.getRideID(), this.getStampDate(), this.getStartDate());
        travelDocument.setTravelDocumentID(this.getTravelDocumentID());
        return travelDocument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelDocumentFX)) return false;
        TravelDocumentFX that = (TravelDocumentFX) o;
        return Double.compare(that.getPrice(), getPrice()) == 0 && getTravelDocumentID().equals(that.getTravelDocumentID()) && getIssueDate().equals(that.getIssueDate()) && getExpirationDate().equals(that.getExpirationDate()) && getTransactionID().equals(that.getTransactionID()) && getLineID().equals(that.getLineID()) && getRideID().equals(that.getRideID()) && getStampDate().equals(that.getStampDate()) && getStartDate().equals(that.getStartDate());
    }
    @Override
    public int hashCode() { return Objects.hash(getTravelDocumentID(), getPrice(), getIssueDate(), getExpirationDate(), getTransactionID(), getLineID(), getRideID(), getStampDate(), getStartDate()); }
    @Override
    public String toString() { return "TravelDocumentFX{ travelDocumentID='" + travelDocumentID + '\'' + ", price=" + price + ", issueDate=" + issueDate + ", expirationDate=" + expirationDate + ", transactionID='" + transactionID + '\'' + ", lineID='" + lineID + '\'' + ", rideID='" + rideID + '\'' + ", stampDate=" + stampDate + ", startDate=" + startDate + '}';}
}
