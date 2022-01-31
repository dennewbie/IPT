package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;

public class SingleTicketConcreteFactory extends TravelDocumentFactory {
    @Override
    public TravelDocument createTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        return new SingleTicket(price, issueDate, expirationDate, transactionID, lineID, rideID, stampDate);
    }
}
