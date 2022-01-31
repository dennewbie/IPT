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
    private PasswordField passwordSignUpField;
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
        if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty() ||
                surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty() ||
                emailTextField.getText() == null || emailTextField.getText().trim().isEmpty() ||
                passwordSignUpField.getText() == null || passwordSignUpField.getText().trim().isEmpty() ||
                birthDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità credenziali
            String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText(), password = passwordSignUpField.getText();
            LocalDate localDate = birthDatePicker.getValue();
            ObservableSingleton.updateInstance(name, surname, localDate, email, password);

            if (checkChanges(ObservableSingleton.getInstance(), citizenEditProfileOriginator.getCurrentCitizen()) == false) {
                citizenEditProfileOriginator.setCurrentCitizen(new Citizen(name, surname, localDate, email, password, ObservableSingleton.getInstance().getUsername()));
                citizenEditProfileOriginator.save();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Dati aggiornati correttamente.", ButtonType.OK);
                alert.showAndWait();
                updateTextFields();
                undoButton.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "I dati non risultano essere stati modificati.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onUndoButtonClick(ActionEvent event) {
        boolean returnValue = citizenEditProfileOriginator.restore();
        if (returnValue != false) {
            ObservableSingleton.updateInstance(citizenEditProfileOriginator.getCurrentCitizen().getName(), citizenEditProfileOriginator.getCurrentCitizen().getSurname(), citizenEditProfileOriginator.getCurrentCitizen().getBirthDate(), citizenEditProfileOriginator.getCurrentCitizen().getEmail(), citizenEditProfileOriginator.getCurrentCitizen().getPassword());
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
        nameTextField.setText(ObservableSingleton.getInstance().getName());
        surnameTextField.setText(ObservableSingleton.getInstance().getSurname());
        emailTextField.setText(ObservableSingleton.getInstance().getEmail());
        passwordSignUpField.setText(ObservableSingleton.getInstance().getPassword());
        birthDatePicker.setValue(ObservableSingleton.getInstance().getBirthDate());
    }

    private boolean checkChanges(Citizen firstCitizen, Citizen secondCitizen) {
        return (firstCitizen.getName().equals(secondCitizen.getName())  && firstCitizen.getSurname().equals(secondCitizen.getSurname()) && firstCitizen.getEmail().equals(secondCitizen.getEmail()) && firstCitizen.getBirthDate().isEqual(secondCitizen.getBirthDate()) && firstCitizen.getPassword().equals(secondCitizen.getPassword()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        undoButton.setDisable(true);
        citizenEditProfileOriginator = new CitizenEditProfileOriginator();
        citizenEditProfileOriginator.setCurrentCitizen(new Citizen(ObservableSingleton.getInstance().getName(), ObservableSingleton.getInstance().getSurname(),
                ObservableSingleton.getInstance().getBirthDate(), ObservableSingleton.getInstance().getEmail(), ObservableSingleton.getInstance().getPassword(), ObservableSingleton.getInstance().getUsername()));
        citizenEditProfileOriginator.save();
        updateTextFields();
    }
}
