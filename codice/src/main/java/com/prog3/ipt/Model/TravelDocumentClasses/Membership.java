package com.prog3.ipt.Model.TravelDocumentClasses;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Membership extends TravelDocument {
    private LocalDate startDate;



    public Membership(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, LocalDate startDate) {
        super(price, issueDate, expirationDate, transactionID);
        setStartDate(startDate);

        // check generated membership ID validity
        while (!FacadeSingleton.validateGeneratedMembershipID(getTravelDocumentID())) {
            setTravelDocumentID(UUID.randomUUID().toString().substring(0, 5));
        }
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
    public TravelDocumentFX toTravelDocumentFX() {
        TravelDocumentFX travelDocumentFX =  super.toTravelDocumentFX();
        travelDocumentFX.setStartDate(getStartDate());
        return travelDocumentFX;
    }

    @Override
    public void updateTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        super.setPrice(price); super.setIssueDate(issueDate); super.setExpirationDate(expirationDate); super.setTransactionID(transactionID); setStartDate(startDate);
    }
}
