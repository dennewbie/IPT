package com.prog3.ipt.Model.CitizenClasses;

import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.ObservableList;

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
    private ArrayList<TravelDocument> purchaseList;

    // Java FX
    private ObservableList<TravelDocumentFX> purchaseObservableList;



    public Order(String transactionCode, LocalDate purchaseDate, double purchasePrice, String citizenID, PaymentMethodStrategy paymentMethodStrategy, ArrayList<TravelDocument> purchaseList, ObservableList<TravelDocumentFX> purchaseObservableList) {
        setTransactionCode(transactionCode);
        setPurchaseDate(purchaseDate);
        setPurchasePrice(purchasePrice);
        setCitizenID(citizenID);
        setPaymentMethodStrategy(paymentMethodStrategy);
        setPurchaseList(purchaseList);
        setPurchaseObservableList(purchaseObservableList);
    }

    // Setters
    void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate;}
    void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    void setCitizenID(String citizenID) { this.citizenID = citizenID; }
    void setPaymentMethodStrategy(PaymentMethodStrategy paymentMethodStrategy) { this.paymentMethodStrategy = paymentMethodStrategy; }
    void setPurchaseList(ArrayList<TravelDocument> purchaseList) { this.purchaseList = purchaseList; }
    void setPurchaseObservableList(ObservableList<TravelDocumentFX> purchaseObservableList) { this.purchaseObservableList = purchaseObservableList; }

    // Getters
    public String getTransactionCode() { return transactionCode; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public double getPurchasePrice() { return purchasePrice; }
    public String getCitizenID() { return citizenID; }
    public PaymentMethodStrategy getPaymentMethodStrategy() { return paymentMethodStrategy; }
    public ArrayList<TravelDocument> getPurchaseList() { return purchaseList; }
    public ObservableList<TravelDocumentFX> getPurchaseObservableList() { return purchaseObservableList; }

    // Others
    public void addTravelDocument(TravelDocument travelDocumentObject) {
        getPurchaseList().add(travelDocumentObject);
        getPurchaseObservableList().add(travelDocumentObject.convertToFX());

        setPurchasePrice(getPurchasePrice() + travelDocumentObject.getPrice());
    }

    // test this
    public void removeTravelDocument(TravelDocument travelDocumentObject) {
        if (!(getPurchaseList().remove(travelDocumentObject)) || !(getPurchaseObservableList().remove(travelDocumentObject.convertToFX()))) return;
        setPurchasePrice(getPurchasePrice() - travelDocumentObject.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPurchasePrice(), getPurchasePrice()) == 0 && getTransactionCode().equals(order.getTransactionCode()) && getPurchaseDate().equals(order.getPurchaseDate()) && getCitizenID().equals(order.getCitizenID()) && getPaymentMethodStrategy().equals(order.getPaymentMethodStrategy()) && getPurchaseList().equals(order.getPurchaseList()) && getPurchaseObservableList().equals(order.getPurchaseObservableList());
    }
    @Override
    public int hashCode() { return Objects.hash(getTransactionCode(), getPurchaseDate(), getPurchasePrice(), getCitizenID(), getPaymentMethodStrategy(), getPurchaseList()); }
    @Override
    public String toString() {
        String travelDocumentsInfo = null;
        for (TravelDocument travelDocumentObject : purchaseList) travelDocumentsInfo += travelDocumentObject.toString();
        return "Order{ transactionCode='" + transactionCode + '\'' +  ", purchaseDate=" + purchaseDate +  ", purchasePrice=" + purchasePrice +  ", citizenID='" + citizenID + '\'' +  ", paymentMethodStrategy=" + paymentMethodStrategy +  ", purchaseList=" + purchaseList + travelDocumentsInfo +  '}';
    }
}
