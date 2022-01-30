package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.Citizen;
import com.prog3.ipt.Model.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

            // recupera dati citizen dal DB e salva nella variabile di classe.
            // SELECT name, etc from cittadino where username = textfield. Si mette tutto nella variable sottostante e via.
            Citizen loggedCitizen = new Citizen("Pino", "Giogrgietti", LocalDate.of(1999, 12, 31), "pino.giorgietti@gmail.com", "acciderbolina01", "pinogiorg");
            ObservableSingleton.setInstance(loggedCitizen);
            onButtonClickNavigateToView(signInButton, "HomeView.fxml");
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

            // controlli dati estratti...
            // creo utente, db, etc.
            Citizen newCitizen = new Citizen(name, surname, localDate, email, passwordSignUp, usernameSignUp);
            // salvataggio nel db...
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
