package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;

/**
 * TravelDocumentFactory is an abstract class, created to implement Factory Method design pattern.
 */
public abstract class TravelDocumentFactory {
    /**
     * Returns a TravelDocument object
     * @param price The price of the travel document
     * @param issueDate The issue date of the travel document
     * @param expirationDate The date of expire of the travel document
     * @param transactionID The unique identifier of the transaction which contains travel documents bought by the citizen
     * @param lineID The unique identifier of the line if the travel document is a single ticket
     * @param rideID The unique identifier of the ride if the travel document is a single ticket
     * @param stampDate The stamp date of the travel document, in particular a single ticket
     * @param startDate The validity start date of the travel document if it is a membership
     * @return A reference to a TravelDocument object
     */
    public abstract TravelDocument createTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate);
}
