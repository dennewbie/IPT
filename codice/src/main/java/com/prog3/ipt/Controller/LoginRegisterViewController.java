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
        if (!super.checkTextFieldsContent(passwordSignInField, usernameSignInTextField)) return;
        // controllo validità credenziali

        // recupera dati citizen dal DB e salva in Observer
        // SELECT name, etc from cittadino where username = textfield. Si mette tutto nella variable sottostante e via.
        Citizen loggedCitizen = new Citizen("Alfredo", "Mungari", LocalDate.of(2000, 12, 20), "alfredo@libero.it", "alfred00", "mungowz");
        ObservableSingleton.setCitizen(loggedCitizen);
        super.onButtonClickNavigateToView(signInButton, "HomeView.fxml");
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        if (!super.checkTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField)) return;
        if (!super.checkDatePickersContent(birthDatePicker)) return;

        // controllo validità credenziali
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
        String usernameSignUp = usernameSignUpTextField.getText(), passwordSignUp = passwordSignUpField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        if (!localDate.isBefore(LocalDate.now())) { super.raiseErrorAlert("Non puoi inserire una data di nascita uguale o successiva ad oggi."); return; }
        if (!super.validateEmail(email)) { super.raiseErrorAlert("Formato mail non valido."); return; }

        // creo utente, db, etc.
        Citizen newCitizen = new Citizen(name, surname, localDate, email, passwordSignUp, usernameSignUp);
        // salvataggio nel db...
        super.raiseConfirmationAlert("Il tuo account è stato creato correttamente. Effettua il login");
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
