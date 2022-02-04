package com.prog3.ipt.Model.PaymentMethodClasses;

import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;

import java.util.Objects;

public class PayPalPaymentMethod implements PaymentMethodStrategy {
    private String email;
    private String password;

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

    // Others
    @Override
    public boolean pay(double paymentAmount) {
        if (!this.checkPaymentMethodData()) return false;
        return true;
    }

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
    public int hashCode() { return Objects.hash(email, password); }
    @Override
    public String toString() { return "PayPalPaymentMethod{ email='" + email + '\'' +  ", password='" + password + '\'' +  '}';
    }
}