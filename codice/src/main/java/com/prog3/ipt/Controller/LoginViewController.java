package com.prog3.ipt.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.prog3.ipt.IPT_Application;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginViewController implements Initializable {

    @FXML
    private VBox vbox;
    private Parent fxml;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        /*
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                System.out.println("err3");
            }
        });

         */
    }
    @FXML
    private void open_signin(ActionEvent event){
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(vbox.getLayoutX() * 20);
        t.play();
        /*
        t.setOnFinished((e) ->{
            try {
                fxml = FXMLLoader.load(IPT_Application.class.getResource("SignIn.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                System.out.println("err2");
            }
        });

         */
    }
    @FXML
    private void open_signup(ActionEvent event){
        TranslateTransition t = new TranslateTransition(Duration.seconds(1), vbox);
        t.setToX(0);
        t.play();
        /*
        t.setOnFinished((e) ->{
            try{
                fxml = FXMLLoader.load(IPT_Application.class.getResource("SignUp.fxml"));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ex){
                System.out.println("err1");
            }
        });

         */
    }
}


