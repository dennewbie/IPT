package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Purchase {
    private String transactionCode;
    private LocalDate purchaseDate;
    private PaymentStrategy paymentStrategy;
    private ArrayList<TravelDocument> travelDocumentsList;
    private Double price;

    // Constructor
    public Purchase(String transactionCode, LocalDate purchaseDate, PaymentStrategy paymentStrategy) {
        setTransactionCode(UUID.randomUUID().toString());
        setPurchaseDate(purchaseDate);
        setPaymentStrategy(paymentStrategy);
        this.price = .0;
    }
    // Getters
    public String getTransactionCode() { return transactionCode; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public PaymentStrategy getPaymentStrategy() { return paymentStrategy; }
    public ArrayList<TravelDocument> getTravelDocumentsList() { return travelDocumentsList; }
    public Double getPrice() { return price; }

    // Setters
    private void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    private void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate;}
    private void setPaymentStrategy(PaymentStrategy paymentStrategy) { this.paymentStrategy = paymentStrategy; }
    private void setPrice(Double price) { this.price = price; }
    private void setTravelDocumentsList(ArrayList<TravelDocument> travelDocumentsList) { this.travelDocumentsList = travelDocumentsList; }


    public void addTravelDocument(TravelDocument travelDocumentObject) {
        getTravelDocumentsList().add(travelDocumentObject);
    }
    public boolean removeTravelDocument(TravelDocument travelDocumentObject) {
        return getTravelDocumentsList().remove(travelDocumentObject);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;
        Purchase purchase = (Purchase) o;
        return getTransactionCode().equals(purchase.getTransactionCode()) && getPurchaseDate().equals(purchase.getPurchaseDate()) && getPaymentStrategy().equals(purchase.getPaymentStrategy()) && getTravelDocumentsList().equals(purchase.getTravelDocumentsList()) && getPrice().equals(purchase.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionCode(), getPurchaseDate(), getPaymentStrategy(), getTravelDocumentsList(), getPrice());
    }

    @Override
    public String toString() {
        String travelDocumentInfo = null;
        String purchaseString = "Purchase{" +
                "transactionCode='" + transactionCode + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", paymentStrategy=" + paymentStrategy.toString() +
                ", price=" + price +
        "} %n";

        for (TravelDocument travelDocumentObject : travelDocumentsList) {
            travelDocumentInfo += travelDocumentObject.toString();
        }
        return purchaseString + travelDocumentInfo;
    }
}
