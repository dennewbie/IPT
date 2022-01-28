package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class LoginRegisterViewController extends ViewController {
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



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onSignInButtonClick(ActionEvent event) {
        if (passwordSignInField.getText() == null || passwordSignInField.getText().trim().isEmpty() ||
                usernameSignInTextField.getText() == null || usernameSignInTextField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità credenziali
            try {
                fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("HomeView.fxml"));
                scene = new Scene(fxmlLoader.load(), 1080, 720);
                HomeViewController tempHomeViewController = fxmlLoader.getController();
                tempHomeViewController.enableLoggedUserView(usernameSignInTextField.getText());
                stage = (Stage) signInButton.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty() ||
                surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty() ||
                emailTextField.getText() == null || emailTextField.getText().trim().isEmpty() ||
                passwordSignUpField.getText() == null || passwordSignUpField.getText().trim().isEmpty() ||
                usernameSignUpTextField.getText() == null || usernameSignUpTextField.getText().trim().isEmpty() ||
                birthDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità credenziali
            String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
            String usernameSignUp = usernameSignUpTextField.getText(), passwordSignUp = passwordSignUpField.getText();
            LocalDate localDate = birthDatePicker.getValue();

            // controlli dati estratti.
            // creo utente, db, etc.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Il tuo account è stato creato correttamente. Effettua il login", ButtonType.OK);
            alert.showAndWait();
            nameTextField.clear();
            surnameTextField.clear();
            emailTextField.clear();
            passwordSignUpField.clear();
            usernameSignUpTextField.clear();
            birthDatePicker.setValue(null);
        }
    }

}
