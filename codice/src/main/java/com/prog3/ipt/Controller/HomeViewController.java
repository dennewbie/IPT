package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeViewController {
    @FXML
    private AnchorPane homeViewAnchorPane;
    @FXML
    private Label usernameWelcomeLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button searchPathButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button avvisiUtenzaButton;

    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Stage stage;

    @FXML
    void onInfoButtonClick(ActionEvent event) {

    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("LoginRegisterView.fxml"));
            scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("SearchPathView.fxml"));
            scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAvvisiUtenzaButtonClick(ActionEvent event) {

    }
}
