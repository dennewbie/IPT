package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginRegisterViewController {
    @FXML
    private Button backButton;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;

    private FXMLLoader fxmlLoader;
    private Scene scene;
    private Stage stage;

    @FXML
    void onBackButtonClick(ActionEvent event) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("HomeView.fxml"));
            scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignInButtonClick(ActionEvent event) {

    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {

    }

}
