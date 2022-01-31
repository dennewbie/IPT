package com.prog3.ipt.Model.PaymentMethodClasses;

import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private String transactionCode;
    private LocalDate purchaseDate;
    private double purchasePrice;
    private String citizenID;
    private PaymentMethodStrategy paymentMethodStrategy;
    private ArrayList<TravelDocument> purchaseList = new ArrayList<>();



    public Order(LocalDate purchaseDate, double purchasePrice, String citizenID, PaymentMethodStrategy paymentMethodStrategy, ArrayList<TravelDocument> purchaseList) {
        setTransactionCode(UUID.randomUUID().toString());
        setPurchaseDate(purchaseDate);
        setPurchasePrice(purchasePrice);
        setCitizenID(citizenID);
        setPaymentMethodStrategy(paymentMethodStrategy);
        setPurchaseList(purchaseList);
    }

    // Setters
    private void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    private void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate;}
    private void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    private void setCitizenID(String citizenID) { this.citizenID = citizenID; }
    private void setPaymentMethodStrategy(PaymentMethodStrategy paymentMethodStrategy) { this.paymentMethodStrategy = paymentMethodStrategy; }
    private void setPurchaseList(ArrayList<TravelDocument> purchaseList) { this.purchaseList = purchaseList; }

    // Getters
    public String getTransactionCode() { return transactionCode; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public double getPurchasePrice() { return purchasePrice; }
    public String getCitizenID() { return citizenID; }
    public PaymentMethodStrategy getPaymentMethodStrategy() { return paymentMethodStrategy; }
    public ArrayList<TravelDocument> getPurchaseList() { return purchaseList; }

    // Others
    public void addTravelDocument(TravelDocument travelDocumentObject) {
        getPurchaseList().add(travelDocumentObject);
        setPurchasePrice(getPurchasePrice() + travelDocumentObject.getPrice());
    }

    public void removeTravelDocument(TravelDocument travelDocumentObject) {
        if (!(getPurchaseList().remove(travelDocumentObject))) return;
        setPurchasePrice(getPurchasePrice() - travelDocumentObject.getPrice());
    }

    @Override
    public String toString() {
        String travelDocumentsInfo = null;
        for (TravelDocument travelDocumentObject : purchaseList) travelDocumentsInfo += travelDocumentObject.toString();
        return "Order{" +
                "transactionCode='" + transactionCode + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                ", citizenID='" + citizenID + '\'' +
                ", paymentMethodStrategy=" + paymentMethodStrategy +
                ", purchaseList=" + purchaseList + travelDocumentsInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPurchasePrice(), getPurchasePrice()) == 0 && getTransactionCode().equals(order.getTransactionCode()) && getPurchaseDate().equals(order.getPurchaseDate()) && getCitizenID().equals(order.getCitizenID()) && getPaymentMethodStrategy().equals(order.getPaymentMethodStrategy()) && getPurchaseList().equals(order.getPurchaseList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTransactionCode(), getPurchaseDate(), getPurchasePrice(), getCitizenID(), getPaymentMethodStrategy(), getPurchaseList());
    }
}
