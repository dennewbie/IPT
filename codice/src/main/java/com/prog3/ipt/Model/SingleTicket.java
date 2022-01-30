package com.prog3.ipt.Model;

import java.time.LocalDate;

public class SingleTicket extends TravelDocument{
    public SingleTicket(Double price, LocalDate issueDate, LocalDate expirationDate) {
        super(price, issueDate, expirationDate);
    }

    // Abstract method implementation
    public void calculatePrice() {

    }

    @Override
    public String toString() {
        return "SingleTicket{" +
                "idTravelDocument='" + super.getTravelDocumentID() + '\'' +
                ", price=" + super.getPrice() +
                ", issueDate=" + super.getIssueDate() +
                ", expirationDate=" + super.getExpirationDate() +
                '}';
    }

}
