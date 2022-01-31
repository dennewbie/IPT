package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchPathViewController extends ViewController implements Initializable {
    // Navigation Bar
    @FXML
    private Button backButton;



    // VBox
    @FXML
    private TextField startingPointTextField;
    @FXML
    private TextField destinationPointTextField;
    @FXML
    private Button buyTicketButton;
    @FXML
    private Button searchPathButton;

    private String urlResource;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onBuyTicketButtonClick(ActionEvent event) {

    }

    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        // ricerca percorso e quando terminato si attiva il pulsante di acquisto titolo viaggio
        if (destinationPointTextField.getText() == null || destinationPointTextField.getText().trim().isEmpty() ||
                startingPointTextField.getText() == null || startingPointTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità campi, avvia ricerca...
            String startingPoint = startingPointTextField.getText().replace(" ", "+");
            String destinationPoint = destinationPointTextField.getText().replace(" ", "+");

            // build url string
            urlResource = "https://www.google.it/maps/dir/" + startingPoint + "/" + destinationPoint + "/";
            super.onButtonClickNavigateToView(searchPathButton, "GoogleMapsView.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buyTicketButton.setDisable(ObservableSingleton.getInstance().getUsername() == null);
    }

    @Override
    protected void onButtonClickNavigateToView(Button clickedButton, String destinationView) {
        try {
            super.setFxmlLoader(new FXMLLoader(IPT_Application.class.getResource(destinationView)));
            super.setLocalScene(new Scene(super.getFxmlLoader().load(), 1080, 720));
            GoogleMapsViewController tempGoogleMapsViewController =  super.getFxmlLoader().getController();
            tempGoogleMapsViewController.setUrl(urlResource);
            super.setStage((Stage) clickedButton.getScene().getWindow());
            super.getStage().setScene(super.getLocalScene());
            super.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
