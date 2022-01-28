package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeViewController extends ViewController {
    private boolean isUserLogged = false;

    @FXML
    private AnchorPane homeViewAnchorPane;
    @FXML
    private Label usernameWelcomeLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button searchPathButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button avvisiUtenzaButton;
    @FXML
    private Button buyTicketButton;

    @FXML
    private Button editProfile;

    @FXML
    void onInfoButtonClick(ActionEvent event) {
        // super.onButtonClickNavigateToView(infoButton, "InfoView.fxml");
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(loginButton, "LoginRegisterView.fxml");
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) {
        isUserLogged = false;
        logoutButton.setVisible(false);
        buyTicketButton.setDisable(true);
        editProfile.setDisable(true);
        usernameWelcomeLabel.setText("Ospite");
    }

    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(searchPathButton,"SearchPathView.fxml");
    }

    @FXML
    void onAvvisiUtenzaButtonClick(ActionEvent event) {
// super.onButtonClickNavigateToView(avvisiUtenzaButton, "AvvisiUtenzaView.fxml");
    }

    @FXML
    void onBuyTicketButtonClick(ActionEvent event) {

    }

    @FXML
    void onEditProfileButtonClick(ActionEvent event) {

    }

    void enableLoggedUserView(String loggedUsername) {
        usernameWelcomeLabel.setText("Benvenuto "  + loggedUsername);
        isUserLogged = true;
        logoutButton.setVisible(true);
        buyTicketButton.setDisable(false);
        editProfile.setDisable(false);
    }
}
