package com.prog3.ipt.Model.PaymentMethodClasses;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PhoneNumberBillPaymentMethod is a concrete class that implements PaymentMethodStrategy interface. This
 * class represents a phone number bill payment
 */
public class PhoneNumberBillPaymentMethod implements PaymentMethodStrategy {
    private String phoneNumber;

    /**
     * PhoneNumberBillPaymentMethod constructor
     * @param phoneNumber Phone number associated with phoneNumber bill payment
     */
    public PhoneNumberBillPaymentMethod(String phoneNumber) {setPhoneNumber(phoneNumber);}
    
    // Setters
    private void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    // Getters
    public String getPhoneNumber() {return phoneNumber;}

    /**
     * Makes the payment by the user
     * @param paymentAmount Total amount to be paid by the user
     * @return boolean value true if transaction was successful or false if not
     */
    @Override
    public boolean pay(double paymentAmount) {return checkPaymentMethodData();}

    /**
     * Checks that the data entered by the user for the payment have been made correctly
     * @return boolean value true if data are correct or false if not
     */
    @Override
    public boolean checkPaymentMethodData() {
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
    public int hashCode() {return Objects.hash(getPhoneNumber());}
    @Override
    public String toString() {return "PhoneNumberBillPaymentMethod{ phoneNumber='" + phoneNumber + '\'' +  '}';}
}
