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

/**
 * LoginRegisterViewController is the controller that handles LoginRegister view.
 */
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



    /**
     * Back to previous view
     * @see ViewController#onButtonClickNavigateToView(Button, String)
     * @param event Button clicked
     */
    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "HomeView.fxml"); }

    /**
     * Citizen signs in his IPT account
     * @see FacadeSingleton#retrieveCitizen(String, String)
     * @see ObservableSingleton#setCitizen(Citizen)
     * @param event Button Clicked
     */
    @FXML
    private void onSignInButtonClick(ActionEvent event) {
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

    /**
     * Citizen signs up to IPT Application
     * @see FacadeSingleton#validateGeneratedCitizenID(Citizen)
     * @see FacadeSingleton#insertCitizen(Citizen)
     * @param event Button clicked
     */
    @FXML
    private void onSignUpButtonClick(ActionEvent event) {
        Citizen newCitizen;
        if (!super.checkTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField)) return;
        if (!super.checkDatePickersContent(birthDatePicker)) return;

        // check text fields
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
        String usernameSignUp = usernameSignUpTextField.getText(), passwordSignUp = passwordSignUpField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        // check birthdate validity
        if (!localDate.isBefore(LocalDate.now())) { super.raiseErrorAlert("Non puoi inserire una data di nascita uguale o successiva ad oggi."); return; }
        // check email validity
        if (!super.validateEmail(email)) { super.raiseErrorAlert("Formato mail non valido."); return; }

        // check generated citizenID validity
        do {
            newCitizen = new Citizen(name, surname, localDate, email, passwordSignUp, usernameSignUp);
        } while (!FacadeSingleton.validateGeneratedCitizenID(newCitizen));

        // insert citizen into database
        if (!FacadeSingleton.insertCitizen(newCitizen)) { super.raiseErrorAlert("Non ?? possibile effettuare la registrazione. ?? possibile che lo username o la mail risultano essere associate ad un account gi?? esistente. Oppure il server non ?? attualmente raggiungibile."); return; }
        super.raiseConfirmationAlert("Il tuo account ?? stato creato correttamente. Effettua il login");
        initializeViewComponents();
    }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     */
    @Override
    protected void initializeViewComponents() {
        super.clearTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordSignUpField, usernameSignUpTextField);
        super.clearDatePickersContent(birthDatePicker);
    }
}
