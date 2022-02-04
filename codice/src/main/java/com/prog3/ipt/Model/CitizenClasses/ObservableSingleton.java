package com.prog3.ipt.Model.CitizenClasses;

import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

// Variante di Observer con aggiunta di Singleton
public class ObservableSingleton {
    private static volatile Citizen sessionUser;
    private static volatile Order sessionOrder;
    private static volatile PaymentMethodStrategy sessionPaymentMethodStrategy;
    private static volatile String paymentMethodString = null;



    private ObservableSingleton() {
        /** Protect against instantiation via reflection */
        if (sessionUser != null) throw new IllegalStateException("Already initialized.");
        if (sessionOrder != null) throw new IllegalStateException("Already initialized.");
        if (sessionPaymentMethodStrategy != null) throw new IllegalStateException("Already initialized.");
    }

    // Citizen
    public static void setCitizenID(String citizenID) { sessionUser.setCitizenID(citizenID); }
    public static void setCitizen(Citizen newUser) { sessionUser = newUser; }
    public static void updateCitizen(String name, String surname, LocalDate birthDate, String email, String password) {
        getCitizen().setName(name);
        getCitizen().setSurname(surname);
        getCitizen().setBirthDate(birthDate);
        getCitizen().setEmail(email);
        getCitizen().setPassword(password);
    }
    /** The instance doesn't get created until the method is called for the first time. */
    public static Citizen getCitizen() {
        if (sessionUser == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionUser == null) sessionUser = new Citizen(null, null, null, null, null, null);
            }
        }
        return sessionUser;
    }



    // Order
    public static void setOrder(Order newOrder) { sessionOrder = newOrder; }
    public static void updateOrder(LocalDate purchaseDate, double purchasePrice, String citizenID, PaymentMethodStrategy paymentMethodStrategy, ArrayList<TravelDocument> purchaseList, ObservableList<TravelDocumentFX> observableList) {
        getOrder().setPurchaseDate(purchaseDate);
        getOrder().setPurchasePrice(purchasePrice);
        getOrder().setCitizenID(citizenID);
        getOrder().setPaymentMethodStrategy(paymentMethodStrategy);
        getOrder().setPurchaseList(purchaseList);
        getOrder().setPurchaseObservableList(observableList);
    }

    public static void updateOrderWithOrderID(String orderID, LocalDate purchaseDate, double purchasePrice, String citizenID, PaymentMethodStrategy paymentMethodStrategy, ArrayList<TravelDocument> purchaseList, ObservableList<TravelDocumentFX> observableList) {
        getOrder().setTransactionCode(orderID);
        updateOrder(purchaseDate, purchasePrice, citizenID, paymentMethodStrategy, purchaseList, observableList);
    }

    public static Order getOrder() {
        if (sessionOrder == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionOrder == null) sessionOrder = new Order(null, null, 0.00, null, null,  new ArrayList<>(), FXCollections.observableArrayList());
            }
        }
        return sessionOrder;
    }



    // PaymentMethod
    public static void setPaymentMethodStrategy(PaymentMethodStrategy newPaymentMethodStrategy) {
        sessionPaymentMethodStrategy = newPaymentMethodStrategy;
    }
    /** The instance doesn't get created until the method is called for the first time. */
    public static PaymentMethodStrategy getPaymentMethodStrategy() {
        if (sessionPaymentMethodStrategy == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionPaymentMethodStrategy == null) sessionPaymentMethodStrategy = new PaymentMethodStrategy() {
                    @Override
                    public boolean pay(double paymentAmount) { return false; }
                    @Override
                    public boolean checkPaymentMethodData() { return false; }
                };
            }
        }
        return sessionPaymentMethodStrategy;
    }



    public static void setPaymentMethodString(String paymentMethodString) { ObservableSingleton.paymentMethodString = paymentMethodString; }
    public static String getPaymentMethodString() { return paymentMethodString; }
}
