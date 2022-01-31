package com.prog3.ipt.Model;

import java.time.LocalDate;

public class CreditCardPayment implements PaymentStrategy{
    private String creditCardNumber;
    private LocalDate expirationDate;
    private String creditCartCVV;

    // Constructor
    public CreditCardPayment(String creditCardNumber, LocalDate expirationDate, String creditCartCVV) {
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.creditCartCVV = creditCartCVV;
    }

    // Getters
    public String getCreditCardNumber() { return creditCardNumber; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getCreditCartCVV() { return creditCartCVV; }

    // Setters
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setCreditCartCVV(String creditCartCVV) { this.creditCartCVV = creditCartCVV; }

    // Payment Algorithm
    public void pay() {
        // check validity
    }
}
