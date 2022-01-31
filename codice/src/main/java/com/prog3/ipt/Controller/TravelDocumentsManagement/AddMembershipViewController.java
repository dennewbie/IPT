package com.prog3.ipt.Controller.TravelDocumentsManagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddMembershipViewController extends TravelDocumentsManagementViewController {
    // NavigationBar
    @FXML
    private Button backButton;



    // VBox
    @FXML
    private DatePicker startDatePicker;
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
        LocalDate startDate = startDatePicker.getValue();
        if (startDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai dimenticato di inserire la data di inizio validità dell'abbonamento", ButtonType.OK);
            alert.showAndWait();
        } else {
            if (!startDate.isBefore(LocalDate.now())) {
                // aggiunta al carrello
                int quantity = Integer.valueOf(quantityTextField.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi comprare un abbonamento che ha una data di inizio validità nel passato.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onIncreaseMembershipQuantityButtonClick(ActionEvent event) {
        quantityTextField.setText(String.valueOf(Integer.parseInt(quantityTextField.getText()) + 1));
    }

    @FXML
    void onDecreaseMembershipQuantityButtonClick(ActionEvent event) {
        int currentQuantity = Integer.parseInt(quantityTextField.getText());
        if (currentQuantity > 0) quantityTextField.setText(String.valueOf(currentQuantity - 1));
    }

    @FXML
    void onHelpButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "È possibile aggiungere uno o più abbonamenti al carrello. " +
                "L'abbonamneto avrà la data di inizio validità pari a quella da te inserita e sarà valido per un anno. " +
                "Se selezioni più abbonamenti avranno tutti la stessa data di inizio validità. Se è necessario che siano diverse, aggiungi" +
                "separatamente gli abbonamenti al carrello.", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quantityTextField.setText(String.valueOf(0));
        priceResultLabel.setText("€" + String.valueOf(0.00));
    }
}