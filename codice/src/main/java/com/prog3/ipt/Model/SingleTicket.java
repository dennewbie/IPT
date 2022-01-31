package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;

public class SingleTicket extends TravelDocument{
    private String lineID;
    private String rideID;

    public SingleTicket(LocalDate issueDate) {
        super(1.5, issueDate, issueDate.plusDays(1));
        setLineID(null);
        setRideID(null);
    }

    public SingleTicket(LocalDate issueDate, String lineID, String rideID) {
        super(1.5, issueDate, issueDate.plusDays(1));
        setLineID(lineID);
        setRideID(rideID);
    }

    // Getters
    public String getRideID() { return rideID; }
    public String getLineID() { return lineID; }

    // Setters
    public void setRideID(String rideID) { this.rideID = rideID; }
    public void setLineID(String lineID) { this.lineID = lineID; }

    // Abstract method implementation
    // public void calculatePrice() {}
    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof SingleTicket)) return false;
        if (!super.equals(singleObject)) return false;
        SingleTicket singleTicketObject = (SingleTicket) singleObject;
        return getRideID().equals(singleTicketObject.getRideID()) && getLineID().equals(singleTicketObject.getLineID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRideID(), getLineID());
    }

    @Override
    public String toString() {
        return "SingleTicket{" +
                "travelDocumentID='" + getTravelDocumentID() + '\'' +
                ", price=" + getPrice() +
                ", issueDate=" + getIssueDate() +
                ", expirationDate=" + getExpirationDate() +
                ", lineID='" + lineID + '\'' +
                ", raceID='" + rideID + '\'' +
                '}';
    }
}
