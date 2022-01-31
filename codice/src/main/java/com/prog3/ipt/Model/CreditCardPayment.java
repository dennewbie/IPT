package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardPayment implements PaymentStrategy{
    private String creditCardNumber;
    private LocalDate expirationDate;
    private String creditCardCVV;



    public CreditCardPayment(String creditCardNumber, LocalDate expirationDate, String creditCardCVV) {
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
        this.creditCardCVV = creditCardCVV;
    }

    // Setters
    public void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
    public void setCreditCardCVV(String creditCardCVV) { this.creditCardCVV = creditCardCVV; }

    // Getters
    public String getCreditCardNumber() { return creditCardNumber; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getCreditCardCVV() { return creditCardCVV; }


    // Others
    // check creditCardNumber validity
    private boolean checkCreditCardNumber() {
        // check if credit card number is null or is less than 16 digits
        if (creditCardNumber == null || creditCardNumber.length() != 16) { return false; }

        // check if credit card number contains only digits
        Pattern creditCardPattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
        Matcher creditCardMatcher = creditCardPattern.matcher(creditCardNumber);
        if (creditCardMatcher.find()) return false;

        return true;
    }

    // check expirationDate validity
    private boolean checkExpirationDate() {
        if (expirationDate.isAfter(LocalDate.now())) return true;
        return false;
    }

    // check creditCardCVV validity
    private boolean checkCreditCardCVV() {
        // check if credit card cvv is null or is less than 3 digits
        if (creditCardCVV == null || creditCardCVV.length() != 3)  return false;

        // check if credit card cvv contains any letter
        Pattern creditCardCVVPattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
        Matcher creditCardCVVMatcher = creditCardCVVPattern.matcher(creditCardCVV);
        if (creditCardCVVMatcher.find()) return false;

        return true;
    }

    // Payment Algorithm
    public boolean pay() {
        // check validity
        if (!checkCreditCardNumber()) return false;
        if (!checkExpirationDate()) return false;
        if (!checkCreditCardCVV()) return false;

        // valid payment method
        return true;
    }

    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof CreditCardPayment)) return false;
        CreditCardPayment creditCardPaymentObject = (CreditCardPayment) singleObject;
        return getCreditCardNumber().equals(creditCardPaymentObject.getCreditCardNumber()) && getExpirationDate().equals(creditCardPaymentObject.getExpirationDate()) && getCreditCardCVV().equals(creditCardPaymentObject.getCreditCardCVV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreditCardNumber(), getExpirationDate(), getCreditCardCVV());
    }
}
