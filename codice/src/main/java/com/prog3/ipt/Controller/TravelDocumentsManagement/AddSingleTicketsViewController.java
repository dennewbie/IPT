package com.prog3.ipt.Controller.TravelDocumentsManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddSingleTicketsViewController extends TravelDocumentsManagementViewController {
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
    void onAddSingleTicketsToCartButtonClick(ActionEvent event) {
        // controllo esistenza corsa e linea
        String ID_Ride = ID_RideTextField.getText(), ID_Line = ID_LineTextField.getText();
        if (ID_Ride == null || ID_RideTextField.getText().trim().isEmpty() || ID_Line == null|| ID_LineTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // aggiunta al carrello
            int quantity = Integer.valueOf(quantityTextField.getText());
        }
    }

    @FXML
    void onIncreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    @FXML
    void onDecreaseSingleTicketQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
    }

    @FXML
    void onHelpButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "È possibile aggiungere uno o più biglietti singoli al carrello. " +
                "È possibile utilizzare il biglietto singolo per la corsa e per la linea da te specificata. La validità del titolo di viaggio è a partire" +
                "dalla data di timbratura del biglietto fino al giorno seguente allo stesso orario. Se selezioni più biglietti singoli, questi" +
                " saranno validi tutti per la stessa corsa e per la stessa linea. Se è necessario che siano diverse, aggiungi" +
                "separatamente i biglietti singoli al carrello.", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantityTextField.setText(String.valueOf(0));
        priceResultLabel.setText("€" + String.valueOf(0.00));
    }
}