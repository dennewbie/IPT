package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class TravelDocument {
    private String travelDocumentID;
    private double price;
    private LocalDate issueDate;
    private LocalDate expirationDate;
    private String transactionID;



    public TravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID) {
        setTravelDocumentID(UUID.randomUUID().toString());
        setPrice(price);
        setIssueDate(issueDate);
        setExpirationDate(expirationDate);
        setTransactionID(transactionID);
    }

    // Setters
    private void setPrice(double price) { this.price = price; }
    private void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    private void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    private void setTravelDocumentID(String travelDocumentID) { this.travelDocumentID = travelDocumentID; }
    private void setTransactionID(String transactionID) { this.transactionID = transactionID; }

    // Getters
    public String getTravelDocumentID() { return travelDocumentID; }
    public double getPrice() { return price; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getTransactionID() { return transactionID; }

    // Others
    @Override
    public String toString() {
        return "TravelDocument{" +
                "travelDocumentID='" + travelDocumentID + '\'' +
                ", price=" + price +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                ", transactionCode='" + transactionID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelDocument)) return false;
        TravelDocument travelDocument = (TravelDocument) o;
        return travelDocument.getPrice() == this.getPrice() && getTravelDocumentID().equals(travelDocument.getTravelDocumentID()) && getIssueDate().equals(travelDocument.getIssueDate()) && Objects.equals(getExpirationDate(), travelDocument.getExpirationDate()) && Objects.equals(getTransactionID(), travelDocument.getTransactionID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTravelDocumentID(), getPrice(), getIssueDate(), getExpirationDate(), getTransactionID());
    }
}
