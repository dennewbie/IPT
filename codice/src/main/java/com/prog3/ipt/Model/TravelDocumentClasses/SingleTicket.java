package com.prog3.ipt.Model.TravelDocumentClasses;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class SingleTicket extends TravelDocument {
    private String lineID;
    private String rideID;
    private LocalDate stampDate;



    public SingleTicket(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate) {
        super(price, issueDate, expirationDate, transactionID);
        setLineID(lineID);
        setRideID(rideID);
        setStampDate(stampDate);

        // check generated ticket ID validity
        while (!FacadeSingleton.validateGeneratedSingleTicketID(getTravelDocumentID())) {
             setTravelDocumentID(UUID.randomUUID().toString().substring(0, 5));
        }

    }

    // Setters
    private void setRideID(String rideID) { this.rideID = rideID; }
    private void setLineID(String lineID) { this.lineID = lineID; }
    private void setStampDate(LocalDate stampDate) { this.stampDate = stampDate; }

    // Getters
    public String getRideID() { return rideID; }
    public String getLineID() { return lineID; }
    public LocalDate getStampDate() { return stampDate; }



    // Others
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleTicket)) return false;
        if (!super.equals(o)) return false;
        SingleTicket singleTicket = (SingleTicket) o;
        return getLineID().equals(singleTicket.getLineID()) && getRideID().equals(singleTicket.getRideID()) && Objects.equals(getStampDate(), singleTicket.getStampDate());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLineID(), getRideID(), getStampDate());
    }
    @Override
    public String toString() { return "SingleTicket{ lineID='" + lineID + '\'' + ", rideID='" + rideID + '\'' + ", stampDate=" + stampDate + '}';}

    @Override
    public TravelDocumentFX toTravelDocumentFX() {
        TravelDocumentFX travelDocumentFX = super.toTravelDocumentFX();
        travelDocumentFX.setLineID(getLineID());
        travelDocumentFX.setRideID(getRideID());
        travelDocumentFX.setStampDate(getStampDate());
        return travelDocumentFX;
    }

    @Override
    public void updateTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        super.setPrice(price); super.setIssueDate(issueDate); super.setExpirationDate(expirationDate); super.setTransactionID(transactionID); setRideID(rideID); setLineID(lineID); setStampDate(stampDate);
    }
}
