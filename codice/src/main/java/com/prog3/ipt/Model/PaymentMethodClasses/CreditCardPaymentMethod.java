package com.prog3.ipt.Model.PaymentMethodClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CreditCardPaymentMethod is a concrete class that implements PaymentMethodStrategy interface. This class
 * represents a credit card payment
 */
public class CreditCardPaymentMethod implements PaymentMethodStrategy {
    private String creditCardNumber;
    private LocalDate creditCardExpirationDate;
    private String creditCardCVV;



    /**
     * CreditCardPaymentMethod constructor
     * @param creditCardNumber Numeric code that uniquely identifies a credit card
     * @param creditCardExpirationDate Credit card expiration date
     * @param creditCardCVV Security code consisting of 3 digits
     */
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

    /**
     * Checks that the credit card number entered by user is correct
     * @return boolean value true if card number is correct or false if not
     */
    private boolean checkCreditCardNumber() {
        // check if credit card number is null or is less than 16 digits
        if (creditCardNumber == null || creditCardNumber.length() != 16) { return false; }
        // check if credit card number contains only digits
        Pattern creditCardPattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
        Matcher creditCardMatcher = creditCardPattern.matcher(creditCardNumber);
        if (creditCardMatcher.find()) return false;
        return true;
    }

    /**
     * Checks that the credit card expiration date entered by user is valid
     * @return True if card expiration date is valid or false if not
     */
    private boolean checkCreditCardExpirationDate() {
        if (creditCardExpirationDate.isAfter(LocalDate.now())) return true;
        return false;
    }

    /**
     * Checks that the credit card CCV entered by user is valid
     * @return True if card CCV is valid or false if not
     */
    private boolean checkCreditCardCVV() {
        // check if credit card cvv is null or is less than 3 digits
        if (creditCardCVV == null || creditCardCVV.length() != 3)  return false;
        // check if credit card cvv contains any letter
        Pattern creditCardCVVPattern = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
        Matcher creditCardCVVMatcher = creditCardCVVPattern.matcher(creditCardCVV);
        if (creditCardCVVMatcher.find()) return false;
        return true;
    }

    /**
     * Makes the payment by the user
     * @param paymentAmount Total amount to be paid by the user
     * @return True if transaction was successful or false if not
     */
    @Override
    public boolean pay(double paymentAmount) {
        // check validity
        if (!checkPaymentMethodData()) return false;
        // Metodo pagamento valido. Contatta il gestore della carta, richiedi transazione, etc...
        return true;
    }

    /**
     * Checks that the data entered by the user for the payment have been made successfully
     * @return True if data are valid or false if not
     */
    @Override
    public boolean checkPaymentMethodData() {
        // check validity
        if (!checkCreditCardNumber()) return false;
        if (!checkCreditCardExpirationDate()) return false;
        if (!checkCreditCardCVV()) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCardPaymentMethod)) return false;
        CreditCardPaymentMethod creditCardPaymentMethod = (CreditCardPaymentMethod) o;
        return getCreditCardNumber().equals(creditCardPaymentMethod.getCreditCardNumber()) && getCreditCardExpirationDate().equals(creditCardPaymentMethod.getCreditCardExpirationDate()) && getCreditCardCVV().equals(creditCardPaymentMethod.getCreditCardCVV());
    }
    @Override
    public int hashCode() { return Objects.hash(getCreditCardNumber(), getCreditCardExpirationDate(), getCreditCardCVV()); }
    @Override
    public String toString() { return "CreditCardPaymentMethod{ creditCardNumber='" + creditCardNumber + '\'' + ", creditCardExpirationDate=" + creditCardExpirationDate +  ", creditCardCVV='" + creditCardCVV + '\'' +  '}'; }
}
