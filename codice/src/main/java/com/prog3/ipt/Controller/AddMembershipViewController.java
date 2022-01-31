package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMembershipViewController extends ViewController {
    // NavigationBar
    @FXML
    private Button backButton;



    // VBox
    @FXML
    private DatePicker issueDatePicker;
    @FXML
    private Button decreaseMembershipQuantityButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Button increaseMembershipQuantityButton;
    @FXML
    private Label priceResultLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Button addMembershipToCart;



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