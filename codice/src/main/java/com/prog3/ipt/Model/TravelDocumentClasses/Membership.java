package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;
import java.util.Objects;

public class Membership extends TravelDocument {
    private LocalDate startDate;



    public Membership(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, LocalDate startDate) {
        super(price, issueDate, expirationDate, transactionID);
        setStartDate(startDate);
    }

    // Setters
    private void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    // Getters
    public LocalDate getStartDate() { return startDate; }

    // Others
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Membership)) return false;
        if (!super.equals(o)) return false;
        Membership membership = (Membership) o;
        return getStartDate().equals(membership.getStartDate());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStartDate());
    }
    @Override
    public String toString() { return "Membership{ startDate=" + startDate +  '}'; }

    @Override
    public TravelDocumentFX convertToFX() {
        TravelDocumentFX travelDocumentFX =  super.convertToFX();
        travelDocumentFX.setStartDate(getStartDate());
        return travelDocumentFX;
    }

    @Override
    public void updateTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        super.setPrice(price); super.setIssueDate(issueDate); super.setExpirationDate(expirationDate); super.setTransactionID(transactionID); setStartDate(startDate);
    }
}
