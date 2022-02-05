package com.prog3.ipt.Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * GoogleMapsViewController is the controller that handles GoogleMaps view.
 */
public class GoogleMapsViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;


    // Pane
    @FXML
    private WebView webView;

    private WebEngine engine;
    private String url;


    // Setter
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Back to previous view
     * @param event Button clicked
     */
    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "SearchPathView.fxml");
    }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     * @see Platform#runLater(Runnable)
     * @see WebEngine
     * @see WebView
     */
    @Override
    protected void initializeViewComponents() {
        Platform.runLater(() -> {
            engine = webView.getEngine();
            webView.setZoom(0.65);
            // load google maps web page
            engine.load(this.url);
        });
    }
}

