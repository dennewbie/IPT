package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

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
}
