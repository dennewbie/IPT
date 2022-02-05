package com.prog3.ipt.Model.TravelDocumentClasses;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Membership is a class that extends TravelDocument abstract class
 */
public class Membership extends TravelDocument {
    private LocalDate startDate;



    /**
     * Membership constructor
     * @param price The price of the membership
     * @param issueDate The issue date of the membership
     * @param expirationDate The date of expire of the membership
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param startDate The validity start date of the membership
     */
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

    /**
     * Produces a Membership object according to the TravelDocumentFX object calling this method
     * @return A reference to a TravelDocumentFX object
     */
    @Override
    public TravelDocumentFX toTravelDocumentFX() {
        TravelDocumentFX travelDocumentFX =  super.toTravelDocumentFX();
        travelDocumentFX.setStartDate(getStartDate());
        return travelDocumentFX;
    }

    /**
     * Updates Membership object according to new parameters
     * @param price The price of the membership
     * @param issueDate The issue date of the membership
     * @param expirationDate The date of expire of the membership
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param lineID The unique identifier of the line if the travel document is a single ticket
     * @param rideID The unique identifier of the ride if the travel document is a single ticket
     * @param stampDate The stamp date of the single ticket
     * @param startDate The validity start date of the membership
     */
    @Override
    public void updateTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        super.setPrice(price); super.setIssueDate(issueDate); super.setExpirationDate(expirationDate); super.setTransactionID(transactionID); setStartDate(startDate);
    }
}
