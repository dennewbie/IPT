package com.prog3.ipt;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import com.prog3.ipt.Model.CitizenClasses.Order;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
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
    public void start(Stage stage)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("HomeView.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
            stage.setTitle("IPT");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FacadeSingleton myFacadeSingleton = FacadeSingleton.getInstance();
        Citizen myObservableCitizen = ObservableSingleton.getCitizen();
        Order myObservableOrder = ObservableSingleton.getOrder();
        launch();
    }

    @Override
    public void stop(){ FacadeSingleton.closeConnection(); }
}