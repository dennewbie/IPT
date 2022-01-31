package com.prog3.ipt.Model.PaymentMethodClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardPaymentMethod implements PaymentMethodStrategy {
    private String creditCardNumber;
    private LocalDate creditCardExpirationDate;
    private String creditCardCVV;



    public CreditCardPaymentMethod(String creditCardNumber, LocalDate creditCardExpirationDate, String creditCardCVV) {
        setCreditCardNumber(creditCardNumber);
        setCreditCardExpirationDate(creditCardExpirationDate);
        setCreditCardCVV(creditCardCVV);
    }

    // Setters
    private void setCreditCardNumber(String creditCardNumber) { this.creditCardNumber = creditCardNumber; }
    private void setCreditCardExpirationDate(LocalDate creditCardExpirationDate) { this.creditCardExpirationDate = creditCardExpirationDate; }
    private void setCreditCardCVV(String creditCardCVV) { this.creditCardCVV = creditCardCVV; }

    // Getters
    public String getCreditCardNumber() { return creditCardNumber; }
    public LocalDate getCreditCardExpirationDate() { return creditCardExpirationDate; }
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
        if (creditCardExpirationDate.isAfter(LocalDate.now())) return true;
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
    @Override
    public boolean pay(double paymentAmount) {
        // check validity
        if (!checkCreditCardNumber()) return false;
        if (!checkExpirationDate()) return false;
        if (!checkCreditCardCVV()) return false;
        // Metodo pagamento valido. Contatta il gestore della carta, richiedi transazione, etc...
        System.out.println("Paying " + paymentAmount + " using Credit Card.");

        return true;
    }

    @Override
    public String toString() {
        return "CreditCardPaymentMethod{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", creditCardExpirationDate=" + creditCardExpirationDate +
                ", creditCardCVV='" + creditCardCVV + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCardPaymentMethod)) return false;
        CreditCardPaymentMethod creditCardPaymentMethod = (CreditCardPaymentMethod) o;
        return getCreditCardNumber().equals(creditCardPaymentMethod.getCreditCardNumber()) && getCreditCardExpirationDate().equals(creditCardPaymentMethod.getCreditCardExpirationDate()) && getCreditCardCVV().equals(creditCardPaymentMethod.getCreditCardCVV());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreditCardNumber(), getCreditCardExpirationDate(), getCreditCardCVV());
    }
}
