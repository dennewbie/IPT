package com.prog3.ipt.Model.PaymentMethodClasses;

/**
 * PaymentMethodStrategy is an interface that represents a generic payment method, this is used to
 * implement Strategy Method design pattern
 */
public interface PaymentMethodStrategy {
    public boolean pay(double paymentAmount);
    public boolean checkPaymentMethodData();
}
