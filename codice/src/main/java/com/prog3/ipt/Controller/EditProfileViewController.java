package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
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
            String passwordSignUp = passwordSignUpField.getText();
            LocalDate localDate = birthDatePicker.getValue();

            undoButton.setDisable(false);
        }
    }

    @FXML
    void onUndoButtonClick(ActionEvent event) {
    // restore, memento, etc
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        undoButton.setDisable(true);
    }
}
