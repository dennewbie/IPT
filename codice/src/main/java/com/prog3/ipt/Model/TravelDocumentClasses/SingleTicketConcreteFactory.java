package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;

/**
 * SingleTicketConcreteFactory is a concrete class that extends TravelDocumentFactory abstract class, made to implement Factory Method design pattern
 */
public class SingleTicketConcreteFactory extends TravelDocumentFactory {
    /**
     * Returns a SingleTicket object according to the passing parameters
     * @param price The price of the travel document
     * @param issueDate The issue date of the travel document
     * @param expirationDate The date of expire of the travel document
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param lineID The unique identifier of the line if the travel document is a single ticket
     * @param rideID The unique identifier of the ride if the travel document is a single ticket
     * @param stampDate The stamp date of the travel document, in particular a single ticket
     * @param startDate The validity start date of the travel document if it is a membership
     * @return A reference to a SingleTicket object
     */
    @Override
    public TravelDocument createTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate) {
        return new SingleTicket(price, issueDate, expirationDate, transactionID, lineID, rideID, stampDate);
    }
}
