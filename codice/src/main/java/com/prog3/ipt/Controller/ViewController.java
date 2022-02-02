package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.PaymentMethodClasses.PayPalPaymentMethod;
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

public abstract class ViewController implements Initializable {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
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
    protected void onButtonClickNavigateToView(Button clickedButton, String destinationView) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource(destinationView));
            localScene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) clickedButton.getScene().getWindow();
            stage.setScene(localScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkTextFieldsContent(TextField ... textFields) {
        for (TextField singleTextField : textFields) if (singleTextField.getText() == null || singleTextField.getText().trim().isEmpty()) {
            raiseErrorAlert("Hai lasciato uno o più campi vuoti.");
            return false;
        }
        return true;
    }


    protected boolean checkDatePickersContent(DatePicker... datePickers) {
        for (DatePicker singleDatePicker : datePickers) if (singleDatePicker.getValue() == null ) {
            raiseErrorAlert("Hai lasciato uno o più campi vuoti.");
            return false;
        }
        return true;
    }

    protected void clearTextFieldsContent(TextField ... textFields) { for (TextField singleTextField : textFields) singleTextField.clear(); }
    protected void clearDatePickersContent(DatePicker... datePickers) { for (DatePicker singleDatePicker : datePickers)  singleDatePicker.setValue(null); }
    protected abstract void initializeViewComponents();

    protected void raiseErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK);
        alert.showAndWait();
    }

    protected void raiseConfirmationAlert(String confirmationMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, confirmationMessage, ButtonType.OK);
        alert.showAndWait();
    }

    protected void raiseInformationAlert(String informationMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, informationMessage, ButtonType.OK);
        alert.showAndWait();
    }

    protected void generatePayPalAlert(String currentUsername, String currentPassword, TextField usernameTextField) {
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
            currentUsername = usernameTextField.getText();
            currentPassword = pwd.getText();
            if (ObservableSingleton.getPaymentMethodString().equals("PayPal")) if (((PayPalPaymentMethod) ObservableSingleton.getPaymentMethodStrategy()).getPassword().equals(currentPassword)) { raiseErrorAlert("Password non valida."); return; }
            ObservableSingleton.setPaymentMethodStrategy(new PayPalPaymentMethod(currentUsername, currentPassword));
            ObservableSingleton.setPaymentMethodString(new String("PayPal"));
        }
    }

    protected boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
