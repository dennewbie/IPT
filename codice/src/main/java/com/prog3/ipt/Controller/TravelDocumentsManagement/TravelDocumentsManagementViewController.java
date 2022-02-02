package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.PaymentMethodClasses.*;
import com.prog3.ipt.Model.TravelDocumentClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import static com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodEnum.*;

// TODO: Aggiungere gestione tabella con interfacciamento DB, possibilità di rimozione dalla table view con puslante dedicato per ogni riga
public class TravelDocumentsManagementViewController extends ViewController {
    protected TravelDocumentFactory myTravelDocumentFactory;
    private String convertedDropDownListString;
    private boolean isValidTransaction;



    // NavigationBar
    @FXML
    private Button backButton;



    // Left Vbox
    @FXML
    private TableView<TravelDocumentFX> myTicketsTableView;
    @FXML
    private TableColumn<TravelDocumentFX, String> myTicketsTransactionIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> myTicketsTravelDocumentIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> myTicketsLineIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> myTicketsRideIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> myTicketsIssueDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> myTicketsStartDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> myTicketsExpirationDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Double> myTicketsPriceTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> myTicketsStampDateTableColumn;



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
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "HomeView.fxml"); }
    @FXML
    void onAddSingleTicketsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(addSingleTicketsButton, "AddSingleTicketsView.fxml"); }
    @FXML
    void onAddMembershipsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(addMembershipsButton, "AddMembershipView.fxml"); }
    @FXML
    void onBuyCartItemsButtonClick(ActionEvent event) {
        if (ObservableSingleton.getOrder().getPurchaseList().size() <= 0) { super.raiseErrorAlert("Il tuo carrello è vuoto. Non puoi procedere con l'acquisto."); return; }
        onSavePaymentMethodButtonClick(new ActionEvent());
        if (!isValidTransaction || !ObservableSingleton.getPaymentMethodStrategy().pay(ObservableSingleton.getOrder().getPurchasePrice())) { raiseErrorAlert("Non è possibile procedere con l'acquisto: metodo di pagamento non valido."); return; }
        // TODO: query DB transazione, svuota table view
        // insert record into transaction table

        //if (!FacadeSingleton.insertRecord(ObservableSingleton.getOrder(), ""));

        super.raiseConfirmationAlert("Il tuo acquisto è andato a buon fine. Costo totale: " + ObservableSingleton.getOrder().getPurchasePrice() + " euro. Modalità pagamento: " + ObservableSingleton.getPaymentMethodString());
        ObservableSingleton.setOrder(null);
        initializeViewComponents();
    }

    @FXML
    void onSavePaymentMethodButtonClick(ActionEvent event) {
        String currentCreditCardNumber = null, currentCreditCartCVV = null;
        LocalDate currentCreditCardExpirationDate = null;
        // add payment method to user payment methods
        if (paymentMethodsDropDownList.getSelectionModel().isEmpty()) { super.raiseErrorAlert("Hai lasciato uno o più campi vuoti."); return; }
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
        isValidTransaction = true;
        super.raiseConfirmationAlert("Metodo di pagamento salvato con successo!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        // initialize left side
        isValidTransaction = false;
        totalPriceLabel.setText("€  " + String.valueOf(ObservableSingleton.getOrder().getPurchasePrice()));
        paymentMethodsDropDownList.setPromptText("Seleziona un metodo di pagamento...");
        paymentMethodsDropDownList.getItems().addAll("CREDIT_CARD", "PAYPAL", "PHONE_NUMBER_BILL");
        CVV_TextField.setVisible(false);
        creditCardNumberTextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);

 /*     GENERA ERRORI
        // create observable list for myTicketsView according to citizenID
        ObservableList<TravelDocumentFX> myTicketsObservableList = FacadeSingleton.getMyTicketsViewContent(ObservableSingleton.getCitizen().getCitizenID());

        // update left table view with new items
        myTicketsTransactionIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        myTicketsTravelDocumentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("travelDocumentID"));
        myTicketsLineIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineID"));
        myTicketsRideIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideID"));
        myTicketsIssueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        myTicketsStartDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        myTicketsExpirationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        myTicketsPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        myTicketsStampDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("stampDate"));

        myTicketsTableView.setItems(null);
*/
        // initialize right side. Update right table view with new items

        travelDocumentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("travelDocumentID"));
        lineIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineID"));
        rideIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideID"));
        issueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expirationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        myCartTableView.setItems(ObservableSingleton.getOrder().getPurchaseObservableList());
    }

    protected void setOrder(Order order) { ObservableSingleton.updateOrder(order.getPurchaseDate(), order.getPurchasePrice(), order.getCitizenID(), order.getPaymentMethodStrategy(), order.getPurchaseList(), order.getPurchaseObservableList()); }
    protected Order getOrder() { return ObservableSingleton.getOrder(); }
    public void setConvertedDropDownListString(String convertedDropDownListString) { this.convertedDropDownListString = convertedDropDownListString; }
    public String getConvertedDropDownListString() { return this.convertedDropDownListString; }

    // ComboBox / DropDownList payment methods handling
    @FXML
    void onSelectedDropDownListElement(ActionEvent event) {
        super.clearTextFieldsContent(creditCardNumberTextField, CVV_TextField);
        super.clearDatePickersContent(expirationCreditCardDatePicker);
        try {
            switch (PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue())) {
                case PAYPAL -> payPalPaymentMethodSelected();
                case CREDIT_CARD -> creditCardPaymentMethodSelected();
                case PHONE_NUMBER_BILL -> phoneNumberBillPaymentMethodSelected();
                default -> throw new IllegalStateException("Unexpected value: " + valueOf(paymentMethodsDropDownList.getValue()));
            }
        } catch (IllegalStateException e) { e.printStackTrace(); return; }
    }

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

    private void payPalPaymentMethodSelected() {
        creditCardNumberTextField.setPromptText("Inserisci l'email di PayPal...");
        creditCardNumberTextField.setVisible(true);
        CVV_TextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);
        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PAYPAL.toString())) creditCardNumberTextField.setText(((PayPalPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getEmail());
    }

    private void phoneNumberBillPaymentMethodSelected() {
        creditCardNumberTextField.setPromptText("Inserisci il tuo numero di telefono...");
        creditCardNumberTextField.setVisible(true);
        expirationCreditCardDatePicker.setVisible(false);
        CVV_TextField.setVisible(false);
        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PHONE_NUMBER_BILL.toString())) creditCardNumberTextField.setText(((PhoneNumberBillPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getPhoneNumber());
    }
}
