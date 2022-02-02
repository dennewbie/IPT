package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeViewController extends ViewController {
    // Welcome Top Bar
    @FXML
    private Label usernameWelcomeLabel;
    @FXML
    private Button logoutButton;



    // Pane
    @FXML
    private Button loginButton;
    @FXML
    private Button searchPathButton;
    @FXML
    private Button manageTravelDocumentsButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button noticesButton;
    @FXML
    private Button editProfileButton;



    @FXML
    void onInfoButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(infoButton, "InfoView.fxml");
    }
    @FXML
    void onLoginButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(loginButton, "LoginRegisterView.fxml"); }
    @FXML
    void onLogoutButtonClick(ActionEvent event) { ObservableSingleton.setCitizen(null); enableGuestUserView(); }
    @FXML
    void onSearchPathButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(searchPathButton,"SearchPathView.fxml"); }
    @FXML
    void onNoticesButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(noticesButton, "NoticesView.fxml"); }
    @FXML
    void onManageTravelDocumentsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(manageTravelDocumentsButton, "TicketsManagementView.fxml"); }
    @FXML
    void onEditProfileButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(editProfileButton, "EditProfileView.fxml"); }

    private void enableLoggedUserView(String loggedUsername) {
        loginButton.setDisable(true);
        usernameWelcomeLabel.setText("Benvenuto "  + loggedUsername);
        logoutButton.setVisible(true);
        manageTravelDocumentsButton.setDisable(false);
        editProfileButton.setDisable(false);
    }

    private void enableGuestUserView() {
        loginButton.setDisable(false);
        logoutButton.setVisible(false);
        manageTravelDocumentsButton.setDisable(true);
        editProfileButton.setDisable(true);
        usernameWelcomeLabel.setText("Ospite");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        Citizen tempCitizen = ObservableSingleton.getCitizen();
        if (tempCitizen.getUsername() != null) enableLoggedUserView(tempCitizen.getUsername());
    }
}
