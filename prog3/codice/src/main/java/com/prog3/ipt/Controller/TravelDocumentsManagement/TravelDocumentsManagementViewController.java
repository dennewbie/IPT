package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.PaymentMethodClasses.*;
import com.prog3.ipt.Model.TravelDocumentClasses.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodEnum.*;

/**
 * TravelDocumentsManagementViewController class extends ViewController class.
 * This class handles TravelDocumentManagement view
 */
public class TravelDocumentsManagementViewController extends ViewController {
    protected TravelDocumentFactory myTravelDocumentFactory;
    private String convertedDropDownListString;
    private boolean isValidTransaction;

    // NavigationBar
    @FXML
    private Button backButton;

    // Left Vbox
    @FXML
    private Button myMembershipButton;
    @FXML
    private Button mySingleTicketsButton;
    @FXML
    private ComboBox<String> paymentMethodsDropDownList;
    @FXML
    private TextField creditCardNumberTextField;
    @FXML
    private TextField CVV_TextField;
    @FXML
    private DatePicker expirationCreditCardDatePicker;
    @FXML
    private Button savePaymentMethodButton;

    // Right VBox
    @FXML
    private TableView<TravelDocumentFX> myCartTableView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button addSingleTicketsButton;
    @FXML
    private Button addMembershipsButton;
    @FXML
    private Button buyCartItemsButton;
    @FXML
    private TableColumn<TravelDocumentFX, String> travelDocumentIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> lineIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> rideIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> issueDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> startDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> expirationDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Double> priceTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Button> deleteRowTableColumn;



    /**
     * Back to previous view
     * @param event Button clicked
     */
    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "HomeView.fxml"); }

    /**
     * Move on MySingleTickets view
     * @param event Button clicked
     */
    @FXML
    private void onMySingleTicketsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(mySingleTicketsButton, "MySingleTicketsView.fxml"); }

    /**
     * Move on MyMemberships view
     * @param event Button clicked
     */
    @FXML
    private void onMyMembershipButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(myMembershipButton, "MyMembershipView.fxml"); }

    /**
     * Move on AddSingleTickets view
     * @param event Button clicked
     */
    @FXML
    private void onAddSingleTicketsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(addSingleTicketsButton, "AddSingleTicketsView.fxml"); }

    /**
     * Move on AddMemberships view
     * @param event Button clicked
     */
    @FXML
    private void onAddMembershipsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(addMembershipsButton, "AddMembershipView.fxml"); }

    /**
     * Creates and inserts a transaction to the Transaction table according to the logged Citizen order
     * @param event Button clicked
     */
    @FXML
    private void onBuyCartItemsButtonClick(ActionEvent event) {
        if (ObservableSingleton.getOrder().getPurchaseList().size() <= 0) { super.raiseErrorAlert("Il tuo carrello ?? vuoto. Non puoi procedere con l'acquisto."); return; }
        // save a valid payment method
        onSavePaymentMethodButtonClick(new ActionEvent());
        if (!isValidTransaction || !ObservableSingleton.getPaymentMethodStrategy().pay(ObservableSingleton.getOrder().getPurchasePrice())) { raiseErrorAlert("Non ?? possibile procedere con l'acquisto: metodo di pagamento non valido."); return; }

        // insert record into transaction table
        if (!FacadeSingleton.insertTransaction()) { raiseErrorAlert("Non ?? possibile portare a termine la transazione. Riprovare pi?? tardi."); return; }
        super.raiseConfirmationAlert("Il tuo acquisto ?? andato a buon fine. Costo totale: " + ObservableSingleton.getOrder().getPurchasePrice() + " euro. Modalit?? pagamento: " + ObservableSingleton.getPaymentMethodString());

        // reset view
        ObservableSingleton.setOrder(null);
        initializeViewComponents();
    }

    /**
     * Saves payment method
     * @param event Button clicked
     */
    @FXML
    private void onSavePaymentMethodButtonClick(ActionEvent event) {
        String currentCreditCardNumber = null, currentCreditCartCVV = null;
        LocalDate currentCreditCardExpirationDate = null;
        // add payment method to user payment methods
        if (paymentMethodsDropDownList.getSelectionModel().isEmpty()) { super.raiseErrorAlert("Hai lasciato uno o pi?? campi vuoti."); return; }
        setConvertedDropDownListString(PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue()).toString());
        try {
            switch (PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue())) {
                case PAYPAL -> {
                    if (!super.checkTextFieldsContent(creditCardNumberTextField) || (!super.validateEmail(creditCardNumberTextField.getText()))) { raiseErrorAlert("Formato mail non valido."); return; }
                    super.generatePayPalAlert(currentCreditCardNumber, currentCreditCartCVV, creditCardNumberTextField);
                }
                case CREDIT_CARD -> {
                    if (!super.checkTextFieldsContent(creditCardNumberTextField, CVV_TextField)) return;
                    if (!super.checkDatePickersContent(expirationCreditCardDatePicker)) return;
                    currentCreditCardNumber = creditCardNumberTextField.getText();
                    currentCreditCartCVV = CVV_TextField.getText();
                    currentCreditCardExpirationDate = expirationCreditCardDatePicker.getValue();

                    if (!currentCreditCardExpirationDate.isAfter(LocalDate.now())) { super.raiseErrorAlert("Non puoi inserire una carta di credito scaduta."); return; }
                    ObservableSingleton.setPaymentMethodStrategy(new CreditCardPaymentMethod(currentCreditCardNumber, currentCreditCardExpirationDate, currentCreditCartCVV));
                    ObservableSingleton.setPaymentMethodString(new String("Carta di Credito"));
                }
                case PHONE_NUMBER_BILL -> {
                    if (!super.checkTextFieldsContent(creditCardNumberTextField)) return;
                    currentCreditCardNumber = creditCardNumberTextField.getText();
                    ObservableSingleton.setPaymentMethodStrategy(new PhoneNumberBillPaymentMethod(currentCreditCardNumber));
                    ObservableSingleton.setPaymentMethodString(new String("Addebito numero di telefono"));
                }
                default -> throw new IllegalStateException("Unexpected value: " + valueOf(paymentMethodsDropDownList.getValue()));
            }
        } catch (IllegalStateException e) { e.printStackTrace(); return; }
        // check if payment method is valid
        if (!ObservableSingleton.getPaymentMethodStrategy().checkPaymentMethodData()) { raiseErrorAlert("Non ?? possibile procedere con il salvataggio del metodo di pagamento: metodo di pagamento non valido."); return; }
        isValidTransaction = true;
        if (event.getSource() == savePaymentMethodButton) super.raiseConfirmationAlert("Metodo di pagamento salvato con successo!");
    }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     */
    @Override
    protected void initializeViewComponents() {
        // initialize left side
        isValidTransaction = false;
        totalPriceLabel.setText("???  " + String.valueOf(ObservableSingleton.getOrder().getPurchasePrice()));
        paymentMethodsDropDownList.getItems().removeAll("CREDIT_CARD", "PAYPAL", "PHONE_NUMBER_BILL");
        paymentMethodsDropDownList.getItems().addAll("CREDIT_CARD", "PAYPAL", "PHONE_NUMBER_BILL");
        paymentMethodsDropDownList.setPromptText("Seleziona un metodo di pagamento...");

        CVV_TextField.setVisible(false);
        creditCardNumberTextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);

        // initialize right side. Update right table view with new items
        travelDocumentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("travelDocumentID"));
        lineIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineID"));
        rideIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideID"));
        issueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expirationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        myCartTableView.setItems(ObservableSingleton.getOrder().getPurchaseObservableList());
        deleteRowTableColumn.setCellFactory(ActionDeleteButtonTableCell.<TravelDocumentFX>forTableColumn("Elimina Titolo Viaggio", (TravelDocumentFX singleTravelDocumentFX) -> {
            myCartTableView.getItems().remove(singleTravelDocumentFX);
            // remove from purchase list
            ObservableSingleton.getOrder().removeTravelDocumentFX(singleTravelDocumentFX);
            initializeViewComponents();
            return singleTravelDocumentFX;
        }));
    }

    /**
     * Order setter
     * @see ObservableSingleton#updateOrder(LocalDate, double, String, PaymentMethodStrategy, ArrayList, ObservableList)
     * @param order Reference to Order object
     */
    protected void setOrder(Order order) { ObservableSingleton.updateOrder(order.getPurchaseDate(), order.getPurchasePrice(), order.getCitizenID(), order.getPaymentMethodStrategy(), order.getPurchaseList(), order.getPurchaseObservableList()); }

    /**
     * Order getter
     * @see ObservableSingleton#getOrder()
     * @return reference to Order object
     */
    protected Order getOrder() { return ObservableSingleton.getOrder(); }

    /**
     * ConverterDropDownListString setter
     * @param convertedDropDownListString Reference to a String object
     */
    private void setConvertedDropDownListString(String convertedDropDownListString) { this.convertedDropDownListString = convertedDropDownListString; }

    /**
     * ConverterDropDownListString getter
     * @return Reference to a String object
     */
    private String getConvertedDropDownListString() { return this.convertedDropDownListString; }

    /**
     * ComboBox / DropDownList payment methods handling
     * @param event
     */
    @FXML
    private void onSelectedDropDownListElement(ActionEvent event) {
        super.clearTextFieldsContent(creditCardNumberTextField, CVV_TextField);
        super.clearDatePickersContent(expirationCreditCardDatePicker);
        try {
            switch (PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue())) {
                case PAYPAL -> payPalPaymentMethodSelected();
                case CREDIT_CARD -> creditCardPaymentMethodSelected();
                case PHONE_NUMBER_BILL -> phoneNumberBillPaymentMethodSelected();
                default -> throw new IllegalStateException("Unexpected value: " + valueOf(paymentMethodsDropDownList.getValue()));
            }
        } catch (IllegalStateException e) { e.printStackTrace(); }
    }

    /** Display content on view when credit card payment method was chosen
     * @see ObservableSingleton
     */
    private void creditCardPaymentMethodSelected() {
        creditCardNumberTextField.setPromptText("Inserisci il numero di carta di credito...");
        CVV_TextField.setPromptText("Inserisci il CVV...");
        creditCardNumberTextField.setVisible(true);
        CVV_TextField.setVisible(true);
        expirationCreditCardDatePicker.setVisible(true);

        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(CREDIT_CARD.toString())) {
            creditCardNumberTextField.setText(((CreditCardPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getCreditCardNumber());
            CVV_TextField.setText(((CreditCardPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getCreditCardCVV());
            expirationCreditCardDatePicker.setValue(((CreditCardPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getCreditCardExpirationDate());
        }
    }

    /** Display content on view when PayPal payment method was chosen
     * @see ObservableSingleton
     */
    private void payPalPaymentMethodSelected() {
        creditCardNumberTextField.setPromptText("Inserisci l'email di PayPal...");
        creditCardNumberTextField.setVisible(true);
        CVV_TextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);
        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PAYPAL.toString())) creditCardNumberTextField.setText(((PayPalPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getEmail());
    }

    /** Display content on view when phone number bill payment method was chosen
     * @see ObservableSingleton
     */
    private void phoneNumberBillPaymentMethodSelected() {
        creditCardNumberTextField.setPromptText("Inserisci il tuo numero di telefono...");
        creditCardNumberTextField.setVisible(true);
        expirationCreditCardDatePicker.setVisible(false);
        CVV_TextField.setVisible(false);
        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PHONE_NUMBER_BILL.toString())) creditCardNumberTextField.setText(((PhoneNumberBillPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getPhoneNumber());
    }
}
