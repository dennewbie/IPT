package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import com.prog3.ipt.Model.Citizen;
import com.prog3.ipt.Model.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPathViewController extends ViewController {
    @FXML
    private Button backButton;
    @FXML
    private Button buyTicketButton;
    @FXML
    private TextField destinationPointTextField;
    @FXML
    private TextField hourTextField;
    @FXML
    private Button searchPathButton;
    @FXML
    private TextField startingPointTextField;

    private String urlResource;

    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onBuyTicketButtonClick(ActionEvent event) {
        Citizen tempCitizen = ObservableSingleton.getInstance();
        if (tempCitizen == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Per acquistare un titolo di viaggio è necessario effettuare il login.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // verificare dati inseriti e poi cambio view

            //onButtonClickNavigateToView(buyTicketButton, "BuyTicketView.fxml");
        }
    }

    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        // ricerca percorso e quando terminato si attiva il pulsante di acquisto titolo viaggio
        if (destinationPointTextField.getText() == null || destinationPointTextField.getText().trim().isEmpty() ||
                startingPointTextField.getText() == null || startingPointTextField.getText().trim().isEmpty() ||
                hourTextField.getText() == null || hourTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità campi, avvia ricerca...

            /*
            String startingPoint = startingPointTextField.getText().replace(" ", "+");
            String destinationPoint = destinationPointTextField.getText().replace(" ", "+");

            // build url string
            urlResource = "https://www.google.it/maps/dir/" + startingPoint + "/" + destinationPoint + "/";
            */

            try {
                fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("GoogleMapsView.fxml"));
                scene = new Scene(fxmlLoader.load(), 1080, 720);
                //GoogleMapsViewController tempGoogleMapsViewController =  fxmlLoader.getController();
                //tempGoogleMapsViewController.setUrl(urlResource);
                stage = (Stage) searchPathButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        urlResource = "https//google.com/";
    }
}
