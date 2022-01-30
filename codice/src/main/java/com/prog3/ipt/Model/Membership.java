package com.prog3.ipt.Model;

import java.time.LocalDate;

public class Membership extends TravelDocument {
    public Membership(Double price, LocalDate issueDate, LocalDate expirationDate) {
        super(price, issueDate, expirationDate);
    }

    // Abstract method implementation
    public void calculatePrice() {

    }

    @Override
    public String toString() {
        return "Membership{" +
                "idTravelDocument='" + super.getTravelDocumentID() + '\'' +
                ", price=" + super.getPrice() +
                ", issueDate=" + super.getIssueDate() +
                ", expirationDate=" + super.getExpirationDate() +
                '}';
    }
}
