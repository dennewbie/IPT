package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchPathViewController extends ViewController {
    @FXML
    private Button backButton;
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
    void onSearchPathButton(ActionEvent event) {

    }
}

