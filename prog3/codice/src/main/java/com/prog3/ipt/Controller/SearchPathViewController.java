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

/**
 * SearchPathViewController is the controller that handles SearchPath view.
 */
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



    /**
     * Return to the previous view
     * @see ViewController#onButtonClickNavigateToView(Button, String)
     * @param event Button clicked
     */
    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "HomeView.fxml"); }

    /**
     * Builds the web url view for the starting and destination point
     * @see #onButtonClickNavigateToView(Button, String)
     * @param event Button clicked
     */
    @FXML
    private void onSearchPathButtonClick(ActionEvent event) {
        if (!super.checkTextFieldsContent(destinationPointTextField, startingPointTextField)) { super.raiseErrorAlert("Hai lasciato uno o più campi vuoti."); return; }
        // build starting point and destination point as strings
        String startingPoint = startingPointTextField.getText().replace(" ", "+");
        String destinationPoint = destinationPointTextField.getText().replace(" ", "+");
        // build url string
        urlResource = "https://www.google.it/maps/dir/" + startingPoint + "/" + destinationPoint + "/";
        onButtonClickNavigateToView(searchPathButton, "GoogleMapsView.fxml");
    }

    /**
     * @see Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     */
    @Override
    protected void initializeViewComponents() { super.clearTextFieldsContent(startingPointTextField, destinationPointTextField); }

    /**
     * @see ViewController#onButtonClickNavigateToView(Button, String)
     * @param clickedButton A reference to a Button JavaFX object
     * @param destinationView A string that represents the filename of the destination view
     */
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
