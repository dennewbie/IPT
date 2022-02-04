package com.prog3.ipt.Model.PaymentMethodClasses;

public interface PaymentMethodStrategy {
    public boolean pay(double paymentAmount);
    public boolean checkPaymentMethodData();
}
