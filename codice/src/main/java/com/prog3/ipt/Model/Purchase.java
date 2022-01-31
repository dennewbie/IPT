package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Purchase {
    private String transactionCode;
    private LocalDate purchaseDate;
    private PaymentStrategy paymentStrategy;
    private ArrayList<TravelDocument> purchaseList;
    private Double purchasePrice;

    // Constructor
    public Purchase() {
        setTransactionCode(UUID.randomUUID().toString());
        setPurchaseDate(LocalDate.now());
        setPurchasePrice(0.0);
        setPaymentStrategy(null);
        purchaseList = new ArrayList<TravelDocument>();
    }

    // Constructor with params
    public Purchase(String transactionCode, LocalDate purchaseDate, PaymentStrategy paymentStrategy, ArrayList<TravelDocument> purchaseList) {
        setTransactionCode(UUID.randomUUID().toString());
        setPurchaseDate(purchaseDate);
        setPaymentStrategy(paymentStrategy);
        setPurchaseList(purchaseList);
        this.purchasePrice = .0;
    }
    // Getters
    public String getTransactionCode() { return transactionCode; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public PaymentStrategy getPaymentStrategy() { return paymentStrategy; }
    public ArrayList<TravelDocument> getPurchaseList() { return purchaseList; }
    public Double getPurchasePrice() { return purchasePrice; }

    // Setters
    private void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    private void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate;}
    private void setPaymentStrategy(PaymentStrategy paymentStrategy) { this.paymentStrategy = paymentStrategy; }
    private void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }
    private void setPurchaseList(ArrayList<TravelDocument> purchaseList) { this.purchaseList = purchaseList; }


    public void addTravelDocument(TravelDocument travelDocumentObject) {
        getPurchaseList().add(travelDocumentObject);
        setPurchasePrice(getPurchasePrice() + travelDocumentObject.getPrice());
    }
    public void removeTravelDocument(TravelDocument travelDocumentObject) {
        if (!(getPurchaseList().remove(travelDocumentObject))) return;
        setPurchasePrice(getPurchasePrice() - travelDocumentObject.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        Purchase purchase = (Purchase) o;
        return getTransactionCode().equals(purchase.getTransactionCode()) && getPurchaseDate().equals(purchase.getPurchaseDate()) && getPaymentStrategy().equals(purchase.getPaymentStrategy()) && getPurchaseList().equals(purchase.getPurchaseList()) && getPurchasePrice().equals(purchase.getPurchasePrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionCode(), getPurchaseDate(), getPaymentStrategy(), getPurchaseList(), getPurchasePrice());
    }

    @Override
    public String toString() {
        String travelDocumentInfo = null;
        String purchaseString = "Purchase{" +
                "transactionCode='" + transactionCode + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", paymentStrategy=" + paymentStrategy.toString() +
                ", purchasePrice=" + purchasePrice +
        "} %n";

        for (TravelDocument travelDocumentObject : purchaseList) {
            travelDocumentInfo += travelDocumentObject.toString();
        }
        return purchaseString + travelDocumentInfo;
    }
}
