package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoViewController extends ViewController {
    @FXML
    private Button backButton;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
