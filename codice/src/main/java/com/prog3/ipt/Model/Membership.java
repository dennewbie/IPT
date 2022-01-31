package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;

public class Membership extends TravelDocument {
    private LocalDate startDate;

    // Constructor
    public Membership(LocalDate issueDate) {
        super(150.0, issueDate, issueDate.plusDays(1).plusYears(1));
        setStartDate(issueDate.plusDays(1));
    }

    // Getter
    public LocalDate getStartDate() { return startDate; }

    // Setter
    private void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    // Abstract method implementation
    // public void calculatePrice() {}

    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof Membership)) return false;
        if (!super.equals(singleObject)) return false;
        Membership membershipObject = (Membership) singleObject;
        return getStartDate().equals(membershipObject.getStartDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getStartDate());
    }

    @Override
    public String toString() {

        return "Membership{" +
                "travelDocumentID='" + getTravelDocumentID() + '\'' +
                ", price=" + getPrice() +
                ", issueDate=" + getIssueDate() +
                ", expirationDate=" + getExpirationDate() +
                ", startDate=" + startDate +
                '}';
    }
}
