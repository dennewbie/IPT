package com.prog3.ipt.Model.PaymentMethodClasses;

/** PaymentMethodEnum is an enum used to declare a constant string used into PaymenthMethodClasses*/
public enum PaymentMethodEnum {
    PAYPAL("PayPal"),
    CREDIT_CARD("Carta di Credito"),
    PHONE_NUMBER_BILL("Addebito numero di telefono");

    private final String currentText;

    PaymentMethodEnum(final String text) {this.currentText = text;}

    @Override
    public String toString() {return this.currentText;}
}
