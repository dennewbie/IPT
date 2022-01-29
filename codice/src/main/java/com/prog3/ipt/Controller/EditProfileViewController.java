package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.Citizen;
import com.prog3.ipt.Model.CitizenEditProfileOriginator;
import com.prog3.ipt.Model.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditProfileViewController extends ViewController {
    @FXML
    private Button backButton;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private PasswordField passwordSignUpField;
    @FXML
    private Button saveInformationButton;
    @FXML
    private TextField surnameTextField;
    @FXML
    private Button undoButton;

    CitizenEditProfileOriginator citizenEditProfileOriginator;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @FXML
    void onSaveInformationButtonClick(ActionEvent event) {
        // salva, memento, etc
        // non dovrebbe controllare null di tutti i campi
        if (nameTextField.getText() == null || nameTextField.getText().trim().isEmpty() ||
                surnameTextField.getText() == null || surnameTextField.getText().trim().isEmpty() ||
                emailTextField.getText() == null || emailTextField.getText().trim().isEmpty() ||
                passwordSignUpField.getText() == null || passwordSignUpField.getText().trim().isEmpty() ||
                birthDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
        } else {
            // controllo validità credenziali
            String name = nameTextField.getText(), surname = surnameTextField.getText(), email = emailTextField.getText();
            String password = passwordSignUpField.getText();
            LocalDate localDate = birthDatePicker.getValue();

            ObservableSingleton.getInstance().setName(name);
            ObservableSingleton.getInstance().setSurname(surname);
            ObservableSingleton.getInstance().setEmail(email);
            ObservableSingleton.getInstance().setPassword(password);
            ObservableSingleton.getInstance().setBirthDate(localDate);
            citizenEditProfileOriginator.setCurrentCitizen(new Citizen(name, surname, localDate, email, password, ObservableSingleton.getInstance().getUsername()));
            citizenEditProfileOriginator.save();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Dati aggiornati correttamente.", ButtonType.OK);
            alert.showAndWait();
            updateTextFields();
            undoButton.setDisable(false);
        }
    }

    @FXML
    void onUndoButtonClick(ActionEvent event) {
        int returnValue = citizenEditProfileOriginator.restore();
        if (returnValue != 0) {
            ObservableSingleton.getInstance().setName(citizenEditProfileOriginator.getCurrentCitizen().getName());
            ObservableSingleton.getInstance().setSurname(citizenEditProfileOriginator.getCurrentCitizen().getSurname());
            ObservableSingleton.getInstance().setEmail(citizenEditProfileOriginator.getCurrentCitizen().getEmail());
            ObservableSingleton.getInstance().setPassword(citizenEditProfileOriginator.getCurrentCitizen().getPassword());
            ObservableSingleton.getInstance().setBirthDate(citizenEditProfileOriginator.getCurrentCitizen().getBirthDate());
            updateTextFields();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Annullamento modifica avvenuto con successo", ButtonType.OK);
            alert.showAndWait();
        } else {
            undoButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Non è possibile tornare ulteriormente indietro con le modifiche", ButtonType.OK);
            alert.showAndWait();
        }

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

    private void updateTextFields() {
        nameTextField.setText(ObservableSingleton.getInstance().getName());
        surnameTextField.setText(ObservableSingleton.getInstance().getSurname());
        emailTextField.setText(ObservableSingleton.getInstance().getEmail());
        passwordSignUpField.setText(ObservableSingleton.getInstance().getPassword());
        birthDatePicker.setValue(ObservableSingleton.getInstance().getBirthDate());
    }
}
