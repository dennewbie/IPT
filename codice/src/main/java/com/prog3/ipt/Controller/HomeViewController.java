package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CitizenClasses.Citizen;
import com.prog3.ipt.Model.CitizenClasses.ObservableSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.action.Action;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * HomeViewController is the controller that handles LoginRegister view.
 */
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


    @FXML @Override
    protected void onBackButtonClick(ActionEvent event) { }
    /**
     * Move on Info view
     * @param event Button clicked
     */
    @FXML
    private void onInfoButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(infoButton, "InfoView.fxml"); }

    /**
     * Login Citizen account
     * @param event Button clicked
     */
    @FXML
    private void onLoginButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(loginButton, "LoginRegisterView.fxml"); }

    /**
     * Logout from Citizen account
     * @see ObservableSingleton#setCitizen(Citizen)
     * @param event Button clicked
     */
    @FXML
    private void onLogoutButtonClick(ActionEvent event) { ObservableSingleton.setCitizen(null); enableGuestUserView(); }

    /**
     * Move on Search Path view
     * @param event Button clicked
     */
    @FXML
    private void onSearchPathButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(searchPathButton,"SearchPathView.fxml"); }

    /**
     * Move on Notices view
     * @param event Button clicked
     */
    @FXML
    private void onNoticesButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(noticesButton, "NoticesView.fxml"); }

    /**
     * Move on Manage Travel Documents view
     * @param event Button clicked
     */
    @FXML
    private void onManageTravelDocumentsButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(manageTravelDocumentsButton, "TicketsManagementView.fxml"); }

    /**
     * Move on Edit Profile view
     * @param event Button clicked
     */
    @FXML
    private void onEditProfileButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(editProfileButton, "EditProfileView.fxml"); }

    /**
     * Set up a logged User view
     * @param loggedUsername The string that represents the username of the logged User
     */
    private void enableLoggedUserView(String loggedUsername) {
        loginButton.setDisable(true);
        usernameWelcomeLabel.setText("Benvenuto "  + loggedUsername);
        logoutButton.setVisible(true);
        manageTravelDocumentsButton.setDisable(false);
        editProfileButton.setDisable(false);
    }

    /**
     * Set up the Guest User view
     */
    private void enableGuestUserView() {
        loginButton.setDisable(false);
        logoutButton.setVisible(false);
        manageTravelDocumentsButton.setDisable(true);
        editProfileButton.setDisable(true);
        usernameWelcomeLabel.setText("Ospite");
    }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     * @see ObservableSingleton#getCitizen()
     */
    @Override
    protected void initializeViewComponents() {
        Citizen tempCitizen = ObservableSingleton.getCitizen();
        if (tempCitizen.getUsername() != null) enableLoggedUserView(tempCitizen.getUsername());
    }
}
