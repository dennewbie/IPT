package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public abstract class TravelDocument {
    private String travelDocumentID;
    private Double price;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    // Constructor
    public TravelDocument(Double price, LocalDate issueDate, LocalDate expirationDate) {
        setTravelDocumentID(UUID.randomUUID().toString());
        setPrice(price);
        setIssueDate(issueDate);
        setExpirationDate(expirationDate);
    }

    // Getters
    public String getTravelDocumentID() { return travelDocumentID; }
    public Double getPrice() { return price; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getExpirationDate() { return expirationDate; }

    // Setters
    private void setPrice(Double price) { this.price = price; }
    private void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    private void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    private void setTravelDocumentID(String travelDocumentID) { this.travelDocumentID = travelDocumentID; }

    // Abstract method
    //protected abstract void calculatePrice();

    @Override
    public String toString() {
        return "TravelDocument{" +
                "travelDocumentID='" + travelDocumentID + '\'' +
                ", price=" + price +
                ", issueDate=" + issueDate +
                ", expirationDate=" + expirationDate +
                '}';
    }

    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof TravelDocument)) return false;
        TravelDocument travelDocumentObject = (TravelDocument) singleObject;
        return getTravelDocumentID().equals(travelDocumentObject.getTravelDocumentID()) && getPrice().equals(travelDocumentObject.getPrice()) && getIssueDate().equals(travelDocumentObject.getIssueDate()) && getExpirationDate().equals(travelDocumentObject.getExpirationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTravelDocumentID(), getPrice(), getIssueDate(), getExpirationDate());
    }
}
