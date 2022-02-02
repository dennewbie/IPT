package com.prog3.ipt.Model.PaymentMethodClasses;

import java.util.Objects;

public class PhoneNumberBillPaymentMethod implements PaymentMethodStrategy {
    private String phoneNumber;



    public PhoneNumberBillPaymentMethod(String phoneNumber) { setPhoneNumber(phoneNumber); }
    
    // Setters
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Others
    @Override
    public boolean pay(double paymentAmount) { return true; }

    @Override
    public String toString() { return "PhoneNumberBillPaymentMethod{ phoneNumber='" + phoneNumber + '\'' + '}'; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumberBillPaymentMethod)) return false;
        PhoneNumberBillPaymentMethod that = (PhoneNumberBillPaymentMethod) o;
        return getPhoneNumber().equals(that.getPhoneNumber());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getPhoneNumber());
    }
}
