package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * LoadingViewController is the controller that handles LoadingView view.
 */

public class LoadingViewController extends ViewController {
    @FXML
    private ImageView loadingImage;
    @FXML
    private Label percentageLabel;



    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     * @see File
     * @see Image
     * @see AtomicInteger
     * @see Timeline
     * @see KeyFrame
     */
    @Override
    protected void initializeViewComponents() {
        File file = new File("src/main/java/images/spinner.gif");
        Image image = new Image(file.toURI().toString());
        loadingImage.setImage(image);
        AtomicInteger i = new AtomicInteger();
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(30),
                        event -> {
                            i.getAndIncrement();
                            percentageLabel.setText(i.toString() + "%");
                        }
                )
        );

        timeline.setCycleCount(100);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(IPT_Application.class.getResource("HomeView.fxml"));
                    Scene localScene = new Scene(fxmlLoader.load(), 1080, 720);
                    Stage stage = (Stage) percentageLabel.getScene().getWindow();
                    stage.setScene(localScene);
                    stage.show();
                } catch (IOException e) { e.printStackTrace(); }
            }
        });
        timeline.play();
    }
}
