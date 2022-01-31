package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMembershipViewController extends ViewController {
    @FXML
    private Button backButton;
    @FXML
    private Button addMembershipToCart;
    @FXML
    private Button decreaseMembershipQuantityButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button increaseMembershipQuantityButton;
    @FXML
    private DatePicker issueDatePicker;
    @FXML
    private Label priceResultLabel;
    @FXML
    private TextField quantityTextField;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml");
    }

    @FXML
    void onAddMembershipToCartButtonClick(ActionEvent event) {

    }

    @FXML
    void onDecreaseMembershipQuantityButtonClick(ActionEvent event) {

    }

    @FXML
    void onHelpButtonClick(ActionEvent event) {

    }

    @FXML
    void onIncreaseMembershipQuantityButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}