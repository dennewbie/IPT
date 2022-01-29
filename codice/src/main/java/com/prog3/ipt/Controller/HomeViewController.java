package com.prog3.ipt.Controller;

import com.prog3.ipt.IPT_Application;
import com.prog3.ipt.Model.Citizen;
import com.prog3.ipt.Model.FacadeSingleton;
import com.prog3.ipt.Model.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
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
    private Button avvisiUtenzaButton;
    @FXML
    private Button buyTicketButton;
    @FXML
    private Button editProfileButton;

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
        ObservableSingleton.setInstance(null);
        enableGuestUserView();
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
        super.onButtonClickNavigateToView(editProfileButton, "EditProfileView.fxml");
    }

    void enableLoggedUserView(String loggedUsername) {
        usernameWelcomeLabel.setText("Benvenuto "  + loggedUsername);
        logoutButton.setVisible(true);
        buyTicketButton.setDisable(false);
        editProfileButton.setDisable(false);
    }

    void enableGuestUserView() {
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
