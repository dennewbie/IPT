package com.prog3.ipt.Model.CitizenClasses;

import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * ObersvableSingleton is a class that implements Observer Method design pattern variant with addition
 * of Singleton Method design pattern
 */
public class ObservableSingleton {
    private static volatile Citizen sessionUser;
    private static volatile Order sessionOrder;
    private static volatile PaymentMethodStrategy sessionPaymentMethodStrategy;
    private static volatile String paymentMethodString = null;



    /**
     * Protect against instantiation via reflection
     */
    private ObservableSingleton() {
        if (sessionUser != null) throw new IllegalStateException("Already initialized.");
        if (sessionOrder != null) throw new IllegalStateException("Already initialized.");
        if (sessionPaymentMethodStrategy != null) throw new IllegalStateException("Already initialized.");
    }

    // citizen setters
    public static void setCitizenID(String citizenID) { sessionUser.setCitizenID(citizenID);}
    public static void setCitizen(Citizen newUser) { sessionUser = newUser; }

    // citizen updaters
    public static void updateCitizen(String name, String surname, LocalDate birthDate, String email, String password) {
        getCitizen().setName(name);
        getCitizen().setSurname(surname);
        getCitizen().setBirthDate(birthDate);
        getCitizen().setEmail(email);
        getCitizen().setPassword(password);
    }

    // citizen getter
    /** The instance doesn't get created until the method is called for the first time. */
    public static Citizen getCitizen() {
        if (sessionUser == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionUser == null) sessionUser = new Citizen(null, null, null, null, null, null);
            }
        }
        return sessionUser;
    }

    // order setter
    public static void setOrder(Order newOrder) { sessionOrder = newOrder; }

    // order updaters
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

    // order getter
    public static Order getOrder() {
        if (sessionOrder == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionOrder == null) sessionOrder = new Order(null, null, 0.00, null, null,  new ArrayList<>(), FXCollections.observableArrayList());
            }
        }
        return sessionOrder;
    }

    // paymentMethod setter
    public static void setPaymentMethodStrategy(PaymentMethodStrategy newPaymentMethodStrategy) {
        sessionPaymentMethodStrategy = newPaymentMethodStrategy;
    }

    // paymentMethod getter
    /**
     * The instance doesn't get created until the method is called for the first time.
     */
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

    // paymentMethodString setter
    public static void setPaymentMethodString(String paymentMethodString) { ObservableSingleton.paymentMethodString = paymentMethodString; }

    // paymentMethodString getter
    public static String getPaymentMethodString() { return paymentMethodString; }
}
