package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.PaymentMethodClasses.PayPalPaymentMethod;
import com.prog3.ipt.Model.PaymentMethodClasses.PaymentMethodStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ViewController is an abstract class that implements Initializable interface and handles IPT View Controllers
 */
public abstract class ViewController implements Initializable {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private FXMLLoader fxmlLoader;
    private Scene localScene;
    private Stage stage;



    // Setters
    protected void setFxmlLoader(FXMLLoader fxmlLoader) { this.fxmlLoader = fxmlLoader; }
    protected void setLocalScene(Scene scene) { this.localScene = scene; }
    protected void setStage(Stage stage) { this.stage = stage; }
    // Getters
    protected FXMLLoader getFxmlLoader() { return fxmlLoader; }
    protected Scene getLocalScene() { return localScene; }
    protected Stage getStage() { return stage; }

    // Others
    /**
     * Loads a .fxml file, creates a scene and set up a stage for a clicked button and a destination view
     * @see FXMLLoader
     * @see Scene
     * @see Stage
     * @param clickedButton A reference to a Button JavaFX object
     * @param destinationView A string that represents the filename of the destination view
     */
    protected void onButtonClickNavigateToView(Button clickedButton, String destinationView) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource(destinationView));
            localScene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) clickedButton.getScene().getWindow();
            stage.setScene(localScene);
            stage.show();
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Checks if any textFields is blank
     * @see ViewController#raiseErrorAlert(String)
     * @param textFields A collection of TextField objects
     * @return True if there is no blank text field, otherwise false
     */
    protected boolean checkTextFieldsContent(TextField ... textFields) {
        for (TextField singleTextField : textFields) if (singleTextField.getText() == null || singleTextField.getText().trim().isEmpty()) {
            raiseErrorAlert("Hai lasciato uno o più campi vuoti.");
            return false;
        }
        return true;
    }
    /**
     * Checks if any DatePicker is blank
     * @see ViewController#raiseErrorAlert(String)
     * @param datePickers A collection of DatePicker objects
     * @return True if there is no blank text field, otherwise false
     */
    protected boolean checkDatePickersContent(DatePicker... datePickers) {
        for (DatePicker singleDatePicker : datePickers) if (singleDatePicker.getValue() == null ) {
            raiseErrorAlert("Hai lasciato uno o più campi vuoti.");
            return false;
        }
        return true;
    }

    /**
     * Clears any TextFields content
     * @param textFields A collection of TextField objects
     */
    protected void clearTextFieldsContent(TextField ... textFields) { for (TextField singleTextField : textFields) singleTextField.clear(); }
    /**
     * Clears any TextFields content
     * @param datePickers A collection of DataPicker objects
     */
    protected void clearDatePickersContent(DatePicker... datePickers) { for (DatePicker singleDatePicker : datePickers)  singleDatePicker.setValue(null); }

    /**
     * Initializes View Component
     */
    protected abstract void initializeViewComponents();

    /**
     * Shows an error alert on screen
     * @param errorMessage The text of an error message
     */
    protected void raiseErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation alert on screen
     * @param confirmationMessage The text of a confirmation alert
     */
    protected void raiseConfirmationAlert(String confirmationMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Shows an information alert on screen
     * @param informationMessage The text of an information alert
     */
    protected void raiseInformationAlert(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, informationMessage, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Generate a PayPal alert to require a PayPal password
     * @see ObservableSingleton#setPaymentMethodStrategy(PaymentMethodStrategy)
     * @see ObservableSingleton#setPaymentMethodString(String)
     * @param currentEmail A string that represents a PayPal email
     * @param currentPassword A string that represents a PayPal password
     * @param emailTextField The text field that contains PayPal email
     */
    protected void generatePayPalAlert(String currentEmail, String currentPassword, TextField emailTextField) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("PayPal Request");
        dialog.setHeaderText("Attenzione. È richiesta la tua password PayPal per continuare");
        dialog.setGraphic(new Circle(15, Color.RED)); // Custom graphic
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        PasswordField pwd = new PasswordField();
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(10);
        content.getChildren().addAll(new Label("Inserisci la tua password PayPal per procedere con il salvataggio:"), pwd);
        dialog.getDialogPane().setContent(content);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) return pwd.getText();
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            currentEmail = emailTextField.getText();
            currentPassword = pwd.getText();
            ObservableSingleton.setPaymentMethodStrategy(new PayPalPaymentMethod(currentEmail, currentPassword));
            ObservableSingleton.setPaymentMethodString(new String("PayPal"));
        }
    }

    /**
     * Validates email structure
     * @see Pattern#matcher(CharSequence)
     * @see Matcher#find()
     * @param emailString A string that represents an email
     * @return True if it is valid, otherwise false
     */
    protected boolean validateEmail(String emailString) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailString);
        return matcher.find();
    }
    @FXML
    protected abstract void onBackButtonClick(ActionEvent event);

}
