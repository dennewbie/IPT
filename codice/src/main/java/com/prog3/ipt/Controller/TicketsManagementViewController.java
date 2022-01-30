package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class TicketsManagementViewController extends ViewController {
    @FXML
    private Button backButton;
    @FXML
    private TextField CVV_TextField;
    @FXML
    private Button addMonoTicketsButton;
    @FXML
    private Button addSubscriptionButton;
    @FXML
    private Button buyCartItemsButton;
    @FXML
    private TextField creditCardNumberTextField;
    @FXML
    private DatePicker expirationCreditCardDatePicker;
    @FXML
    private ComboBox<?> paymentMethodsDropDownList;
    @FXML
    private Button savePaymentMethodButton;
    @FXML
    private TableView<?> myCartTableView;
    @FXML
    private TableView<?> myTicketsTableView;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onAddMonoTicketsButtonClick(ActionEvent event) {

    }

    @FXML
    void onAddSubscriptionButtonClick(ActionEvent event) {

    }

    @FXML
    void onBuyCartItemsButtonClick(ActionEvent event) {

    }

    @FXML
    void onSavePaymentMethodButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
