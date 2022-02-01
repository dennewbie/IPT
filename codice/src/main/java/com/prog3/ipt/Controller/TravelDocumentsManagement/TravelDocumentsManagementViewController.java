package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.TravelDocumentClasses.SingleTicketConcreteFactory;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocument;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

import javax.net.ssl.SSLContext;

/*
    Aggiungere gestione tabella con interfacciamento DB, possibilità di rimozione dalla table view con puslante dedicato per ogni riga
 */
public class TravelDocumentsManagementViewController extends ViewController {
    protected TravelDocumentFactory myTravelDocumentFactory;
    // NavigationBar
    @FXML
    private Button backButton;



    // Left Vbox
    @FXML
    private TableView<?> myTicketsTableView;
    @FXML
    private ComboBox<?> paymentMethodsDropDownList;
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
        // check payment method (validity)
        // if valid then add transaction to user transactions else transaction error
    }

    @FXML
    void onSavePaymentMethodButtonClick(ActionEvent event) {
        // add payment method to user payment methods
        if (creditCardNumberTextField.getText() == null || creditCardNumberTextField.getText().trim().isEmpty() || CVV_TextField.getText() == null
                || CVV_TextField.getText().trim().isEmpty() || expirationCreditCardDatePicker.getValue() == null || paymentMethodsDropDownList.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            String currentCreditCartCVV = CVV_TextField.getText(), currentCreditCardNumber = creditCardNumberTextField.getText();
            LocalDate currentCreditCardExpirationDate = expirationCreditCardDatePicker.getValue();
            String paymentMethodStrategy = (String) paymentMethodsDropDownList.getValue();
            System.out.println(paymentMethodStrategy);

            if (!currentCreditCardExpirationDate.isAfter(LocalDate.now())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi inserire una carta di credito scaduta.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

        }
    }

    @FXML
    void onSelectedDropDownListElement(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //aggiorno la table view
        double totalOrderValue = 0.0;
        for (TravelDocument singleTravelDocument : ObservableSingleton.getOrder().getPurchaseList()) totalOrderValue += singleTravelDocument.getPrice();
        totalPriceLabel.setText("€" + String.valueOf(totalOrderValue));
    }

    protected void setOrder(Order order) {
        ObservableSingleton.updateOrder(order.getPurchaseDate(), order.getPurchasePrice(), order.getCitizenID(), order.getPaymentMethodStrategy(), order.getPurchaseList());
    }

    protected Order getOrder() { return ObservableSingleton.getOrder(); }
}
