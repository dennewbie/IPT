package com.prog3.ipt.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;



    // Pane
    @FXML
    private TableView<?> infoTableView;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
