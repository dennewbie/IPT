package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Date;

public abstract class ViewController implements Initializable {
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
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }


    protected boolean checkDatePickersContent(DatePicker... datePickers) {
        for (DatePicker singleDatePicker : datePickers) if (singleDatePicker.getValue() == null ) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hai lasciato uno o più campi vuoti.", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    protected void clearTextFieldsContent(TextField ... textFields) {
        for (TextField singleTextField : textFields) singleTextField.clear();
    }

    protected void clearDatePickersContent(DatePicker... datePickers) {
        for (DatePicker singleDatePicker : datePickers)  singleDatePicker.setValue(null);
    }

    protected abstract void initializeViewComponents();
}
