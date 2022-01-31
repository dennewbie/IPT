package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

/*
    Aggiungere gestione tabella con interfacciamento DB, possibilità di rimozione dalla table view con puslante dedicato per ogni riga
 */
public class TravelDocumentsManagementViewController extends ViewController {
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
        // add ticket to temporary transaction
        // add price to total
    }

    @FXML
    void onAddMembershipsButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(addMembershipsButton, "AddMembershipView.fxml");
        // add membership to temporary transaction
        // add price to total
    }

    @FXML
    void onBuyCartItemsButtonClick(ActionEvent event) {
        // check payment method (validity)
        // if valid then add transaction to user transactions else transaction error
    }

    @FXML
    void onSavePaymentMethodButtonClick(ActionEvent event) {
        // add payment method to user payment methods
    }

    @FXML
    void onSelectedDropDownListElement(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize transaction, maybe store temporary transaction
    }
}
