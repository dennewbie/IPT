package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.CitizenEditProfileOriginator;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditProfileViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;



    // VBox
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button undoButton;
    @FXML
    private Button saveInformationButton;

    private CitizenEditProfileOriginator citizenEditProfileOriginator;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onSaveInformationButtonClick(ActionEvent event) {
        // check text field validity
        if (!checkTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordField) || !checkDatePickersContent(birthDatePicker)) return;
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText(), password = passwordField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        // check email validity
        if (!super.validateEmail(email)) { super.raiseErrorAlert("Formato mail non valido."); return; }
        // check birth date validity
        if (!localDate.isBefore(LocalDate.now())) { super.raiseErrorAlert("Non puoi inserire una data di nascita uguale o successiva ad oggi."); return; }
        // update citizen in observer
        ObservableSingleton.updateCitizen(name, surname, localDate, email, password);
        // check if there are no changes
        if (checkChanges(ObservableSingleton.getCitizen(), citizenEditProfileOriginator.getCurrentCitizen()) != false) { super.raiseErrorAlert("I dati non risultano essere stati modificati."); return; }
        // save current citizen state
        citizenEditProfileOriginator.setCurrentCitizen(new Citizen(name, surname, localDate, email, password, ObservableSingleton.getCitizen().getUsername()));
        citizenEditProfileOriginator.save();

        // update citizen data into database
        if (!FacadeSingleton.updateCitizenData(ObservableSingleton.getCitizen())) { raiseErrorAlert("Non è stato possibile aggiornare i tuoi dati. Il server potrebbe non essere raggiungibile al momento. Riprovare più tardi."); return; }
        super.raiseConfirmationAlert("Dati aggiornati correttamente.");
        // reset text fields
        updateTextFields();
        undoButton.setDisable(false);
    }

    @FXML
    void onUndoButtonClick(ActionEvent event) {
        boolean returnValue = citizenEditProfileOriginator.restore();
        if (returnValue != false) {
            // reset previous citizen state
            ObservableSingleton.updateCitizen(citizenEditProfileOriginator.getCurrentCitizen().getName(), citizenEditProfileOriginator.getCurrentCitizen().getSurname(), citizenEditProfileOriginator.getCurrentCitizen().getBirthDate(), citizenEditProfileOriginator.getCurrentCitizen().getEmail(), citizenEditProfileOriginator.getCurrentCitizen().getPassword());
            // update citizen data into database
            if (!FacadeSingleton.updateCitizenData(ObservableSingleton.getCitizen())) { raiseErrorAlert("Non è stato possibile aggiornare i tuoi dati. Il server potrebbe non essere raggiungibile al momento. Riprovare più tardi."); return; }
            // reset text fields
            updateTextFields();
            super.raiseConfirmationAlert("Annullamento modifica avvenuto con successo");
        } else {
            undoButton.setDisable(true);
            super.raiseErrorAlert("Non è possibile tornare ulteriormente indietro con le modifiche");
        }
    }

    private void updateTextFields() {
        nameTextField.setText(ObservableSingleton.getCitizen().getName());
        surnameTextField.setText(ObservableSingleton.getCitizen().getSurname());
        emailTextField.setText(ObservableSingleton.getCitizen().getEmail());
        birthDatePicker.setValue(ObservableSingleton.getCitizen().getBirthDate());
        passwordField.setText(ObservableSingleton.getCitizen().getPassword());
    }

    private boolean checkChanges(Citizen firstCitizen, Citizen secondCitizen) { return (firstCitizen.getName().equals(secondCitizen.getName())  && firstCitizen.getSurname().equals(secondCitizen.getSurname()) && firstCitizen.getEmail().equals(secondCitizen.getEmail()) && firstCitizen.getBirthDate().isEqual(secondCitizen.getBirthDate()) && firstCitizen.getPassword().equals(secondCitizen.getPassword())); }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        undoButton.setDisable(true);
        citizenEditProfileOriginator = new CitizenEditProfileOriginator();
        citizenEditProfileOriginator.setCurrentCitizen(new Citizen(ObservableSingleton.getCitizen().getName(), ObservableSingleton.getCitizen().getSurname(),
                ObservableSingleton.getCitizen().getBirthDate(), ObservableSingleton.getCitizen().getEmail(), ObservableSingleton.getCitizen().getPassword(), ObservableSingleton.getCitizen().getUsername()));
        citizenEditProfileOriginator.save();
        updateTextFields();
    }
}
