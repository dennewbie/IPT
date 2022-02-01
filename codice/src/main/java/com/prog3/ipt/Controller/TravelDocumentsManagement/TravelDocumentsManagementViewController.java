package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.PaymentMethodClasses.*;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodEnum.*;

/*
    Aggiungere gestione tabella con interfacciamento DB, possibilità di rimozione dalla table view con puslante dedicato per ogni riga
 */
public class TravelDocumentsManagementViewController extends ViewController {
    protected TravelDocumentFactory myTravelDocumentFactory;
    private String convertedDropDownListString;
    // NavigationBar
    @FXML
    private Button backButton;



    // Left Vbox
    @FXML
    private TableView<?> myTicketsTableView;
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
    private TableView<?> myCartTableView;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button addSingleTicketsButton;
    @FXML
    private Button addMembershipsButton;
    @FXML
    private Button buyCartItemsButton;



    // create temporary transaction
    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onAddSingleTicketsButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(addSingleTicketsButton, "AddSingleTicketsView.fxml");
    }

    @FXML
    void onAddMembershipsButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(addMembershipsButton, "AddMembershipView.fxml");
    }

    @FXML
    void onBuyCartItemsButtonClick(ActionEvent event) {
        onSavePaymentMethodButtonClick(new ActionEvent());
        if (ObservableSingleton.getOrder().getPurchaseList() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Il tuo carrello è vuoto. Non puoi procedere con l'acquisto.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        // query DB transazione
        ObservableSingleton.setOrder(null);
    }

    @FXML
    void onSavePaymentMethodButtonClick(ActionEvent event) {
        String currentCreditCardNumber = null, currentCreditCartCVV = null;
        LocalDate currentCreditCardExpirationDate = null;
        // add payment method to user payment methods
        if (paymentMethodsDropDownList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        setConvertedDropDownListString(PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue()).toString());
        switch (PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue())) {
            case PAYPAL -> {
                if (creditCardNumberTextField.getText() == null || creditCardNumberTextField.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("PayPal Request");
                dialog.setHeaderText("Attenzione. È richiesta la tua password PayPal per continuare");
                dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                PasswordField pwd = new PasswordField();
                HBox content = new HBox();
                content.setAlignment(Pos.CENTER_LEFT);
                content.setSpacing(10);
                content.getChildren().addAll(new Label("Inserisci la tua password PayPal per procedere con il salvataggio:"), pwd);
                dialog.getDialogPane().setContent(content);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ButtonType.OK) return pwd.getText();
                    return null;
                });

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    currentCreditCardNumber = creditCardNumberTextField.getText();
                    currentCreditCartCVV = CVV_TextField.getText();
                    ObservableSingleton.setPaymentMethodStrategy(new PayPalPaymentMethod(currentCreditCardNumber, currentCreditCartCVV));
                    ObservableSingleton.setPaymentMethodString(new String("PayPal"));
                }
            }
            case CREDIT_CARD -> {
                if (creditCardNumberTextField.getText() == null || creditCardNumberTextField.getText().trim().isEmpty() || CVV_TextField.getText() == null
                        || CVV_TextField.getText().trim().isEmpty() || expirationCreditCardDatePicker.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                currentCreditCardNumber = creditCardNumberTextField.getText();
                currentCreditCartCVV = CVV_TextField.getText();
                currentCreditCardExpirationDate = expirationCreditCardDatePicker.getValue();

                if (!currentCreditCardExpirationDate.isAfter(LocalDate.now())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi inserire una carta di credito scaduta.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                ObservableSingleton.setPaymentMethodStrategy(new CreditCardPaymentMethod(currentCreditCardNumber, currentCreditCardExpirationDate, currentCreditCartCVV));
                ObservableSingleton.setPaymentMethodString(new String("Carta di Credito"));
            }
            case PHONE_NUMBER_BILL -> {
                if (creditCardNumberTextField.getText() == null || creditCardNumberTextField.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato il campo 'Numero di telefono' vuoto.", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
                currentCreditCardNumber = creditCardNumberTextField.getText();
                ObservableSingleton.setPaymentMethodStrategy(new PhoneNumberBillPaymentMethod(currentCreditCardNumber));
                ObservableSingleton.setPaymentMethodString(new String("Addebito numero di telefono"));
            }
            default -> throw new IllegalStateException("Unexpected value: " + valueOf(paymentMethodsDropDownList.getValue()));
        }
    }

    @FXML
    void onSelectedDropDownListElement(ActionEvent event) {
        switch (PaymentMethodEnum.valueOf(paymentMethodsDropDownList.getValue())) {
            case PAYPAL -> payPalPaymentMethodSelected();
            case CREDIT_CARD -> creditCardPaymentMethodSelected();
            case PHONE_NUMBER_BILL -> phoneNumberBillPaymentMethodSelected();
            default -> throw new IllegalStateException("Unexpected value: " + valueOf(paymentMethodsDropDownList.getValue()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //aggiorno la table view
        double totalOrderValue = 0.0;
        for (TravelDocument singleTravelDocument : ObservableSingleton.getOrder().getPurchaseList()) totalOrderValue += singleTravelDocument.getPrice();
        totalPriceLabel.setText("€" + String.valueOf(totalOrderValue));
        paymentMethodsDropDownList.getItems().addAll("CREDIT_CARD", "PAYPAL", "PHONE_NUMBER_BILL");
        paymentMethodsDropDownList.setPromptText("Seleziona un metodo di pagamento...");
        CVV_TextField.setVisible(false);
        creditCardNumberTextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);
    }

    protected void setOrder(Order order) { ObservableSingleton.updateOrder(order.getPurchaseDate(), order.getPurchasePrice(), order.getCitizenID(), order.getPaymentMethodStrategy(), order.getPurchaseList()); }
    protected Order getOrder() { return ObservableSingleton.getOrder(); }
    public void setConvertedDropDownListString(String convertedDropDownListString) { this.convertedDropDownListString = convertedDropDownListString; }
    public String getConvertedDropDownListString() { return this.convertedDropDownListString; }

    private void payPalPaymentMethodSelected() {
        creditCardNumberTextField.clear();
        CVV_TextField.clear();
        expirationCreditCardDatePicker.setValue(null);
        creditCardNumberTextField.setPromptText("Inserisci l'email di PayPal...");
        creditCardNumberTextField.setVisible(true);
        CVV_TextField.setVisible(false);
        expirationCreditCardDatePicker.setVisible(false);

        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PAYPAL.toString())) {
            creditCardNumberTextField.setText(((PayPalPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getEmail());
            return;
        }

    }

    private void creditCardPaymentMethodSelected() {
        creditCardNumberTextField.clear();
        CVV_TextField.clear();
        expirationCreditCardDatePicker.setValue(null);
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
            return;
        }
    }

    private void phoneNumberBillPaymentMethodSelected() {
        creditCardNumberTextField.clear();
        CVV_TextField.clear();
        expirationCreditCardDatePicker.setValue(null);
        creditCardNumberTextField.setPromptText("Inserisci il tuo numero di telefono...");
        creditCardNumberTextField.setVisible(true);
        expirationCreditCardDatePicker.setVisible(false);
        CVV_TextField.setVisible(false);

        if (ObservableSingleton.getPaymentMethodString() == null) return;
        if (ObservableSingleton.getPaymentMethodString().equals(PHONE_NUMBER_BILL.toString())) {
            creditCardNumberTextField.setText(((PhoneNumberBillPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getPhoneNumber());
            return;
        }
    }
}
