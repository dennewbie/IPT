package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMonoTicketsViewController extends ViewController {
    @FXML
    private TextField ID_CorsaTextField;
    @FXML
    private TextField ID_LineaTextField;
    @FXML
    private Button addMonoTicketsToCart;
    @FXML
    private Button backButton;
    @FXML
    private Button decreaseMonoTicketQuantityButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button increaseMonoTicketQuantityButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Label priceResultLabel;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml");
    }

    @FXML
    void onAddMonoTicketsToCartButtonClick(ActionEvent event) {

    }

    @FXML
    void onDecreaseMonoTicketQuantityButtonClick(ActionEvent event) {

    }

    @FXML
    void onHelpButtonClick(ActionEvent event) {

    }

    @FXML
    void onIncreaseMonoTicketQuantityButtonClick(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}