package com.prog3.ipt.Model.CitizenClasses;

import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Order is a class that represents a purchase commit by a citizen due to buy tickets and season tickets
 * offered for sale by the system
 */
public class Order {
    private String transactionCode;
    private LocalDate purchaseDate;
    private double purchasePrice;
    private String citizenID;
    private PaymentMethodStrategy paymentMethodStrategy;
    private ArrayList<TravelDocument> purchaseList;
    private ObservableList<TravelDocumentFX> purchaseObservableList; // Java FX

    /**
     * Order constructor
     * @param transactionCode The unique identifier of the transaction commit by a citizen
     * @param purchaseDate Date on which the transaction took place
     * @param purchasePrice Total cost of the transaction
     * @param citizenID Citizien who commit transaction
     * @param paymentMethodStrategy Method used to pay for the transaction
     * @param purchaseList List of purchases made by the transaction
     * @param purchaseObservableList FX Object which represents list of purchases made by the transaction
     */
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

    /**
     * Adds a reference to a TravelDocument object into an ArrayList<TravelDocument></TravelDocument>
     * object, then gets the equivalent FX TravelDocument object of TravelDocument object and adds
     * reference to it into an ObservableList<TravelDocumentFX></TravelDocumentFX>. In the end adds the
     * cost of the TravelDocument object in question to the total amount of the transaction.
     * @param travelDocumentObject Travel document bought by transaction
     */
    public void addTravelDocument(TravelDocument travelDocumentObject) {
        getPurchaseList().add(travelDocumentObject);
        getPurchaseObservableList().add(travelDocumentObject.toTravelDocumentFX());
        setPurchasePrice(getPurchasePrice() + travelDocumentObject.getPrice());
    }

    /**
     * Removes a reference to a TravelDocument object into an ArrayList<TravelDocument></TravelDocument>
     * object, then gets the equivalent FX TravelDocument object of TravelDocument object and removes
     * reference to it into an ObservableList<TravelDocumentFX></TravelDocumentFX>. In the end removes
     * the cost of the TravelDocument object in question to the total amount of the transaction.
     * @param travelDocumentObject Travel document to remove from transaction
     */
    public void removeTravelDocument(TravelDocument travelDocumentObject) {
        getPurchaseList().remove(travelDocumentObject);
        getPurchaseObservableList().remove(travelDocumentObject.toTravelDocumentFX());
        setPurchasePrice(getPurchasePrice() - travelDocumentObject.getPrice());
    }

    /**
     * Gets the equivalent TravelDocument object of FX TravelDocument object and adds
     * reference to it into an ArrayList<TravelDocument></TravelDocument>. Adds a reference to a FX
     * TravelDocument object into an ObservableList<TravelDocumentFX></TravelDocumentFX> object. In the
     * end adds the cost of the FX TravelDocument object in question to the total amount of
     * the transaction.
     * @param travelDocumentFXObject FX object of Travel document bought by transaction
     */
    public void addTravelDocumentFX(TravelDocumentFX travelDocumentFXObject) {
        getPurchaseList().add(travelDocumentFXObject.toTravelDocument());
        getPurchaseObservableList().add(travelDocumentFXObject);
        setPurchasePrice(getPurchasePrice() + travelDocumentFXObject.getPrice());
    }

    /**
     * Gets the equivalent TravelDocument object of FX TravelDocument object and removes
     * reference to it into an ArrayList<TravelDocument></TravelDocument>. Removes a reference to a FX
     * TravelDocument object into an ObservableList<TravelDocumentFX></TravelDocumentFX> object. In the
     * end removes the cost of the FX TravelDocument object in question to the total amount of
     * the transaction.
     * @param travelDocumentFXObject FX object of Travel document to remove from transaction
     */
    public void removeTravelDocumentFX(TravelDocumentFX travelDocumentFXObject) {
        getPurchaseList().remove(travelDocumentFXObject.toTravelDocument());
        getPurchaseObservableList().remove(travelDocumentFXObject);
        setPurchasePrice(getPurchasePrice() - travelDocumentFXObject.getPrice());
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
    public String toString() { return "Order{ transactionCode='" + transactionCode + '\'' + ", purchaseDate=" + purchaseDate + ", purchasePrice=" + purchasePrice + ", citizenID='" + citizenID + '\'' + ", paymentMethodStrategy=" + paymentMethodStrategy + ", purchaseList=" + purchaseList + ", purchaseObservableList=" + purchaseObservableList + '}'; }
}
