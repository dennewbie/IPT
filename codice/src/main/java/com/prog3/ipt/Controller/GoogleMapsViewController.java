package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class GoogleMapsViewController extends ViewController implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private Button backButton;

    /*
    private String url;
    public void setUrl(String url) {
        this.url = url;
    }
    */
    private WebEngine engine;

    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "SearchPathView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        // load google maps web page
        engine.load("https://google.it/maps/dir/");
    }
}
