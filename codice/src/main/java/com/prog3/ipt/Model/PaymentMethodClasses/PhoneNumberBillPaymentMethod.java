package com.prog3.ipt.Model.PaymentMethodClasses;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public boolean pay(double paymentAmount) {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(this.getPhoneNumber());
        return matcher.matches();
    }


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
    @Override
    public String toString() { return "PhoneNumberBillPaymentMethod{ phoneNumber='" + phoneNumber + '\'' +  '}'; }
}
