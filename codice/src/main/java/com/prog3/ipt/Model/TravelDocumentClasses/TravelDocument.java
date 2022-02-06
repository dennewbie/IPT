package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * TravelDocument is an abstract class that represents a generic travel document
 */
public abstract class TravelDocument {
    private String travelDocumentID;
    private double price;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String transactionID;



    /**
     * TravelDocument constructor
     * @param price The price of the travel document
     * @param issueDate The issue date of the travel document
     * @param expirationDate The date of expire of the travel document
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     */
    public TravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID) {
        setTravelDocumentID(UUID.randomUUID().toString().substring(0, 5));
        setPrice(price);
        setIssueDate(issueDate);
        setExpirationDate(expirationDate);
        setTransactionID(transactionID);
    }

    // Setters
    protected void setPrice(double price) { this.price = price; }
    protected void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    protected void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    protected void setTravelDocumentID(String travelDocumentID) { this.travelDocumentID = travelDocumentID; }
    protected void setTransactionID(String transactionID) { this.transactionID = transactionID; }

    // Getters
    public String getTravelDocumentID() { return travelDocumentID; }
    public double getPrice() { return price; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getTransactionID() { return transactionID; }

    // Others
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelDocument)) return false;
        TravelDocument travelDocument = (TravelDocument) o;
        return travelDocument.getPrice() == this.getPrice() && getTravelDocumentID().equals(travelDocument.getTravelDocumentID()) && getIssueDate().equals(travelDocument.getIssueDate()) && Objects.equals(getExpirationDate(), travelDocument.getExpirationDate()) && Objects.equals(getTransactionID(), travelDocument.getTransactionID());
    }
    @Override
    public int hashCode() { return Objects.hash(getTravelDocumentID(), getPrice(), getIssueDate(), getExpirationDate(), getTransactionID()); }
    @Override
    public String toString() { return "TravelDocument{ travelDocumentID='" + travelDocumentID + '\'' + ", price=" + price + ", issueDate=" + issueDate + ", expirationDate=" + expirationDate + ", transactionID='" + transactionID + '\'' + '}'; }

    /**
     * Produces a TravelDocumentFX object according to the TravelDocument object calling this method
     * @return A reference to a TravelDocumentFX object
     */
    public TravelDocumentFX toTravelDocumentFX() {
        return new TravelDocumentFX(getTravelDocumentID(), getPrice(), getIssueDate(), getExpirationDate(), getTransactionID(), null, null, null, null);
    }

    /**
     * Updates TravelDocument object according to new parameters
     * @param price The price of the travel document
     * @param issueDate The issue date of the travel document
     * @param expirationDate The date of expire of the travel document
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param lineID The unique identifier of the line if the travel document is a single ticket
     * @param rideID The unique identifier of the ride if the travel document is a single ticket
     * @param stampDate The stamp date of the travel document, in particular a single ticket
     * @param startDate The validity start date of the travel document if it is a membership
     */
    public abstract void updateTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate);
}
