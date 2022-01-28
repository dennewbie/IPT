package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginRegisterViewController {
    @FXML
    private Button backButton;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private PasswordField passwordSignInField;
    @FXML
    private PasswordField passwordSignUpField;
    @FXML
    private CheckBox showSignUpPasswordCheckBox;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField usernameSignInTextField;
    @FXML
    private TextField usernameSignUpTextField;

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
        // controllo validità credenziali
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        // verifica campi e validità
    }

}
