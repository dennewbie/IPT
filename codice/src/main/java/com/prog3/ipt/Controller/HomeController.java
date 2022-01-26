package com.prog3.ipt.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

public class HomeController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Benvenuto in IPT!");
    }
}