package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Controller.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSingleTicketsViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;



    // VBox
    @FXML
    private TextField ID_RideTextField;
    @FXML
    private TextField ID_LineTextField;
    @FXML
    private Button decreaseMonoTicketQuantityButton;
    @FXML
    private TextField quantityTextField;
    @FXML
    private Button increaseMonoTicketQuantityButton;
    @FXML
    private Label priceResultLabel;
    @FXML
    private Button helpButton;
    @FXML
    private Button addMonoTicketsToCart;



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