package com.prog3.ipt.Model.PaymentMethodClasses;

import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;

import java.util.Objects;

/**
 * PayPalPaymentMethod is a concrete class that implements PaymentMethodStrategy interface. This class
 * represents a PayPal payment
 */
public class PayPalPaymentMethod implements PaymentMethodStrategy {
    private String email;
    private String password;

    /**
     * PayPalPaymentMethod constructor
     * @param email Email associated with the user Paypal account
     * @param password Password associated with the user Paypal account
     */
    public PayPalPaymentMethod(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Setters
    private void setEmail(String email) { this.email = email; }
    private void setPassword(String password) { this.password = password; }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    /**
     * Makes the payment by the user
     * @param paymentAmount Total amount to be paid by the user
     * @return boolean value true if transaction was successful or false if not
     */
    @Override
    public boolean pay(double paymentAmount) {
        if (!this.checkPaymentMethodData()) return false;
        return true;
    }

    /**
     * Checks that the data entered by the user for the payment have been made correctly
     * @return boolean value true if data are correct or false if not
     */
    @Override
    public boolean checkPaymentMethodData() {
        if (!this.getPassword().equals(((PayPalPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getPassword())) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayPalPaymentMethod)) return false;
        PayPalPaymentMethod that = (PayPalPaymentMethod) o;
        return email.equals(that.email) && password.equals(that.password);
    }
    @Override
    public int hashCode() {return Objects.hash(email, password);}

    @Override
    public String toString() {return "PayPalPaymentMethod{ email='" + email + '\'' +  ", password='" + password + '\'' +  '}';}
}