package com.prog3.ipt.Model;

import java.time.LocalDate;

public class TravelDocumentFactorySingleton {
    private static volatile TravelDocumentFactorySingleton instance;

    private TravelDocumentFactorySingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
    }
    /** The instance doesn't get created until the method is called for the first time. */
    public static TravelDocumentFactorySingleton getInstance() {
        if (instance == null) {
            synchronized (TravelDocumentFactorySingleton.class) {
                if (instance == null) instance = new TravelDocumentFactorySingleton();
            }
        }
        return instance;
    }

    // factory method
    public TravelDocument createTravelDocument(String typeTravelDocument, Double price, LocalDate issueDate, LocalDate expirationDate) {
        if (typeTravelDocument.equals("Membership")) {
            return new Membership(price, issueDate, expirationDate);
        } else if (typeTravelDocument.equals("SingleTicket")) {
            return new SingleTicket(price, issueDate, expirationDate);
        } else return null;
    }
}
