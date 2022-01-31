package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;
import java.util.Objects;

public class SingleTicket extends TravelDocument {
    private String lineID;
    private String rideID;
    private LocalDate stampDate;



    public SingleTicket(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate) {
        super(price, issueDate, expirationDate, transactionID);
        setLineID(lineID);
        setRideID(rideID);
        setStampDate(stampDate);
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
    // Abstract method implementation
    // public void calculatePrice() {}
    @Override
    public String toString() {
        return super.toString() + "SingleTicket{" +
                "lineID='" + lineID + '\'' +
                ", rideID='" + rideID + '\'' +
                ", stampDate=" + stampDate +
                '}';
    }

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
}
