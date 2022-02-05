package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
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
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameSignUpTextField;
    @FXML
    private PasswordField passwordSignUpField;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameSignInTextField;
    @FXML
    private PasswordField passwordSignInField;
    @FXML
    private Button signInButton;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }
    @FXML
    void onSignInButtonClick(ActionEvent event) {
        Citizen loggedCitizen;
        // check text fields
        if (!super.checkTextFieldsContent(passwordSignInField, usernameSignInTextField)) return;
        // retrieve citizen data from database
        loggedCitizen = FacadeSingleton.retrieveCitizen(usernameSignInTextField.getText(), passwordSignInField.getText());
        if (loggedCitizen == null) { raiseErrorAlert("Username e/o password sbagliata.");  return; }
        // save citizen data in observer-singleton
        ObservableSingleton.setCitizen(loggedCitizen);
        super.onButtonClickNavigateToView(signInButton, "HomeView.fxml");
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        Citizen newCitizen;
        if (!super.checkTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField)) return;
        if (!super.checkDatePickersContent(birthDatePicker)) return;

        // check text fields
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
        String usernameSignUp = usernameSignUpTextField.getText(), passwordSignUp = passwordSignUpField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        // check birth-date validity
        if (!localDate.isBefore(LocalDate.now())) { super.raiseErrorAlert("Non puoi inserire una data di nascita uguale o successiva ad oggi."); return; }
        // check email validity
        if (!super.validateEmail(email)) { super.raiseErrorAlert("Formato mail non valido."); return; }

        // check generated citizenID validity
        do {
            newCitizen = new Citizen(name, surname, localDate, email, passwordSignUp, usernameSignUp);
        } while (!FacadeSingleton.validateGeneratedCitizenID(newCitizen));

        // insert citizen into database
        if (!FacadeSingleton.insertCitizen(newCitizen)) { super.raiseErrorAlert("Non è possibile effettuare la registrazione. È possibile che lo username o la mail risultano essere associate ad un account già esistente. Oppure il server non è attualmente raggiungibile."); return; }
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
