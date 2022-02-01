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
    private Button searchPathButton;

    private String urlResource;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }
    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        if (!super.checkTextFieldsConent(destinationPointTextField, startingPointTextField)) return;
        // avvia ricerca...
        String startingPoint = startingPointTextField.getText().replace(" ", "+");
        String destinationPoint = destinationPointTextField.getText().replace(" ", "+");
        // build url string
        urlResource = "https://www.google.it/maps/dir/" + startingPoint + "/" + destinationPoint + "/";
        onButtonClickNavigateToView(searchPathButton, "GoogleMapsView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() { super.clearTextFieldsContent(startingPointTextField, destinationPointTextField); }

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
