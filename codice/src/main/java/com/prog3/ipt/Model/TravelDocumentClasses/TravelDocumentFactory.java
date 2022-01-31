package com.prog3.ipt.Model.TravelDocumentClasses;

import java.time.LocalDate;

public abstract class TravelDocumentFactory {
    // Altri metodi eventuali
    public abstract TravelDocument createTravelDocument(double price, LocalDate issueDate, LocalDate expirationDate, String transactionID, String lineID, String rideID, LocalDate stampDate, LocalDate startDate);
}
