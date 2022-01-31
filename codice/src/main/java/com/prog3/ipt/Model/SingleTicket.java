package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;

public class SingleTicket extends TravelDocument{
    private String lineID;
    private String raceID;

    public SingleTicket(LocalDate issueDate) {
        super(1.5, issueDate, issueDate.plusDays(1));
        setLineID(null);
        setRaceID(null);
    }

    public SingleTicket(LocalDate issueDate, String lineID, String raceID) {
        super(1.5, issueDate, issueDate.plusDays(1));
        setLineID(lineID);
        setRaceID(raceID);
    }

    // Getters
    public String getRaceID() { return raceID; }
    public String getLineID() { return lineID; }

    // Setters
    public void setRaceID(String raceID) { this.raceID = raceID; }
    public void setLineID(String lineID) { this.lineID = lineID; }

    // Abstract method implementation
    // public void calculatePrice() {}
    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof SingleTicket)) return false;
        if (!super.equals(singleObject)) return false;
        SingleTicket singleTicketObject = (SingleTicket) singleObject;
        return getRaceID().equals(singleTicketObject.getRaceID()) && getLineID().equals(singleTicketObject.getLineID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRaceID(), getLineID());
    }

    @Override
    public String toString() {
        return "SingleTicket{" +
                "travelDocumentID='" + getTravelDocumentID() + '\'' +
                ", price=" + getPrice() +
                ", issueDate=" + getIssueDate() +
                ", expirationDate=" + getExpirationDate() +
                ", lineID='" + lineID + '\'' +
                ", raceID='" + raceID + '\'' +
                '}';
    }
}
