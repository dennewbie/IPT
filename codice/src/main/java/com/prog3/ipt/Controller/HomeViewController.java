package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.Citizen;
import com.prog3.ipt.Model.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class HomeViewController extends ViewController {
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
    private Button noticesButton;
    @FXML
    private Button buyTicketButton;
    @FXML
    private Button editProfileButton;

    @FXML
    void onInfoButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(infoButton, "InfoView.fxml");
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(loginButton, "LoginRegisterView.fxml");
    }

    @FXML
    void onLogoutButtonClick(ActionEvent event) {
        ObservableSingleton.setInstance(null);
        enableGuestUserView();
    }

    @FXML
    void onSearchPathButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(searchPathButton,"SearchPathView.fxml");
    }

    @FXML
    void onAvvisiUtenzaButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(noticesButton, "NoticesView.fxml");
    }

    @FXML
    void onBuyTicketButtonClick(ActionEvent event) {

    }

    @FXML
    void onEditProfileButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(editProfileButton, "EditProfileView.fxml");
    }

    void enableLoggedUserView(String loggedUsername) {
        loginButton.setDisable(true);
        usernameWelcomeLabel.setText("Benvenuto "  + loggedUsername);
        logoutButton.setVisible(true);
        buyTicketButton.setDisable(false);
        editProfileButton.setDisable(false);
    }

    void enableGuestUserView() {
        loginButton.setDisable(false);
        logoutButton.setVisible(false);
        buyTicketButton.setDisable(true);
        editProfileButton.setDisable(true);
        usernameWelcomeLabel.setText("Ospite");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Citizen tempCitizen = ObservableSingleton.getInstance();
        if (tempCitizen.getUsername() != null) enableLoggedUserView(tempCitizen.getUsername());
    }
}
