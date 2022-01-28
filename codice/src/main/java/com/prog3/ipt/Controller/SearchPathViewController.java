package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPathViewController extends ViewController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button buyTicket;
    @FXML
    private TextField destinationPointTextField;
    @FXML
    private TextField hourTextField;
    @FXML
    private Button searchPathButton;
    @FXML
    private TextField startingPointTextField;

    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Stage stage;

    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onBuyTicketClick(ActionEvent event) {
        // passaggio alla View di acquisto biglietto con passaggio dati delle text field
    }

    @FXML
    void onSearchPathButton(ActionEvent event) {
        // ricerca percorso e quando terminato si attiva il pulsante di acquisto titolo viaggio
        if (destinationPointTextField.getText() == null || destinationPointTextField.getText().trim().isEmpty() ||
                startingPointTextField.getText() == null || startingPointTextField.getText().trim().isEmpty() ||
                hourTextField.getText() == null || hourTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // avvia ricerca...

            buyTicket.setDisable(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buyTicket.setDisable(true);
    }
}

