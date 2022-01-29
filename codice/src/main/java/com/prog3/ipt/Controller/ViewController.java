package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class ViewController implements Initializable {
    protected FXMLLoader fxmlLoader;
    protected Scene scene;
    protected Stage stage;

    protected void onButtonClickNavigateToView(Button clickedButton, String destinationView) {
        try {
            fxmlLoader = new FXMLLoader(IPT_Application.class.getResource(destinationView));
            scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage = (Stage) clickedButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
