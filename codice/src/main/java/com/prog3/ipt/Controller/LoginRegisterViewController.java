package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
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
        if (!super.checkTextFieldsConent(passwordSignInField, usernameSignInTextField)) return;
        // controllo validità credenziali

        // recupera dati citizen dal DB e salva in Observer
        // SELECT name, etc from cittadino where username = textfield. Si mette tutto nella variable sottostante e via.
        Citizen loggedCitizen = new Citizen("Pino", "Giogrgietti", LocalDate.of(1999, 12, 31), "pino.giorgietti@gmail.com", "acciderbolina01", "pinogiorg");
        ObservableSingleton.setCitizen(loggedCitizen);
        super.onButtonClickNavigateToView(signInButton, "HomeView.fxml");
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        if (!super.checkTextFieldsConent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField)) return;
        if (!super.checkDatePickersContent(birthDatePicker)) return;

        // controllo validità credenziali
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
        String usernameSignUp = usernameSignUpTextField.getText(), passwordSignUp = passwordSignUpField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        if (!localDate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi inserire una data di nascita uguale o successiva ad oggi.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // controlli dati estratti...
        // creo utente, db, etc.
        Citizen newCitizen = new Citizen(name, surname, localDate, email, passwordSignUp, usernameSignUp);
        // salvataggio nel db...
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Il tuo account è stato creato correttamente. Effettua il login", ButtonType.OK);
        alert.showAndWait();
        initializeViewComponents();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        super.clearTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField);
        super.clearDatePickersContent(birthDatePicker);
    }
}
