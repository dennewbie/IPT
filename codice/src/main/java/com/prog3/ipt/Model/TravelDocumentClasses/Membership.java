package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;
import java.util.Objects;

public class Membership extends TravelDocument {
    private LocalDate startDate;
    /* TODO: data convalida va messo qui, se non si sceglie di metterlo sulla M:N */



    public Membership(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, LocalDate startDate) {
        super(price, issueDate, expirationDate, transactionID);
        setStartDate(startDate);
    }

    // Setter
    private void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    // Getter
    public LocalDate getStartDate() { return startDate; }

    // Others
    @Override
    public String toString() {
        return super.toString() + "Membership{" +
                "startDate=" + startDate +
                '}';
    }

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
}
