package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.CitizenEditProfileOriginator;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
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
        if (!checkTextFieldsContent(nameTextField, surnameTextField, emailTextField, passwordField) || !checkDatePickersContent(birthDatePicker)) return;

        // controllo validità credenziali
        String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText(), password = passwordField.getText();
        LocalDate localDate = birthDatePicker.getValue();
        if (!localDate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Non puoi inserire una data di nascita uguale o successiva ad oggi.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        ObservableSingleton.updateCitizen(name, surname, localDate, email, password);
        if (checkChanges(ObservableSingleton.getCitizen(), citizenEditProfileOriginator.getCurrentCitizen()) != false) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "I dati non risultano essere stati modificati.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        citizenEditProfileOriginator.setCurrentCitizen(new Citizen(name, surname, localDate, email, password, ObservableSingleton.getCitizen().getUsername()));
        citizenEditProfileOriginator.save();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Dati aggiornati correttamente.", ButtonType.OK);
        alert.showAndWait();
        updateTextFields();
        undoButton.setDisable(false);
    }

    @FXML
    void onUndoButtonClick(ActionEvent event) {
        boolean returnValue = citizenEditProfileOriginator.restore();
        if (returnValue != false) {
            ObservableSingleton.updateCitizen(citizenEditProfileOriginator.getCurrentCitizen().getName(), citizenEditProfileOriginator.getCurrentCitizen().getSurname(), citizenEditProfileOriginator.getCurrentCitizen().getBirthDate(), citizenEditProfileOriginator.getCurrentCitizen().getEmail(), citizenEditProfileOriginator.getCurrentCitizen().getPassword());
            updateTextFields();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Annullamento modifica avvenuto con successo", ButtonType.OK);
            alert.showAndWait();
        } else {
            undoButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Non è possibile tornare ulteriormente indietro con le modifiche", ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void updateTextFields() {
        nameTextField.setText(ObservableSingleton.getCitizen().getName());
        surnameTextField.setText(ObservableSingleton.getCitizen().getSurname());
        emailTextField.setText(ObservableSingleton.getCitizen().getEmail());
        passwordField.setText(ObservableSingleton.getCitizen().getPassword());
        birthDatePicker.setValue(ObservableSingleton.getCitizen().getBirthDate());
    }

    private boolean checkChanges(Citizen firstCitizen, Citizen secondCitizen) {
        return (firstCitizen.getName().equals(secondCitizen.getName())  && firstCitizen.getSurname().equals(secondCitizen.getSurname()) && firstCitizen.getEmail().equals(secondCitizen.getEmail()) && firstCitizen.getBirthDate().isEqual(secondCitizen.getBirthDate()) && firstCitizen.getPassword().equals(secondCitizen.getPassword()));
    }

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
