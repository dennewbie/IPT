package com.prog3.ipt;

import com.prog3.ipt.Model.FacadeSingleton;
import com.prog3.ipt.Model.ObservableSingleton;
import com.prog3.ipt.Model.Citizen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
    Creo il loader della view FXML, la assegno ad una scena e quest'ultima viene caricata a video.
    Si imposta il titolo della finestra, il suo contenuto, si disattiva la possibilità di resize e infine la si mostra.
 */
public class IPT_Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("HomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("IPT");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        FacadeSingleton myFacadeSingleton = FacadeSingleton.getInstance();
        Citizen myObservableCitizen = ObservableSingleton.getInstance();
    }
}