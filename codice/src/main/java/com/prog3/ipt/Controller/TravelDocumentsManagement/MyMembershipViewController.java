package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * MyMembershipViewController is a class that extends TravelDocumentsManagementViewController.
 * This class handles MyMembership view
 */
public class MyMembershipViewController extends TravelDocumentsManagementViewController {
    // Navigation Bar
    @FXML
    private Button backButton;
    @FXML
    private TableView<TravelDocumentFX> myMembershipTableView;
    @FXML
    private TableColumn<TravelDocumentFX, String> transactionIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> travelDocumentIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> issueDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> startDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> expirationDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Double> priceTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Button> deleteTableColumn;

    /**
     * Back to previous view
     * @param event Button clicked
     */
    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see com.prog3.ipt.Controller.ViewController#initializeViewComponents()
     * @see FacadeSingleton#getMyMembershipsViewContent()
     */
    @Override
    protected void initializeViewComponents() {
        // create observable list for myTicketsView according to citizenID
        ObservableList<TravelDocumentFX> myMembershipsObservableList = FacadeSingleton.getMyMembershipsViewContent();

        // update table view with new items
        transactionIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        travelDocumentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("travelDocumentID"));
        issueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expirationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        deleteTableColumn.setCellFactory(ActionDeleteButtonTableCell.<TravelDocumentFX>forTableColumn("Elimina Titolo Viaggio", (TravelDocumentFX singleTravelDocumentFX) -> {
            myMembershipTableView.getItems().remove(singleTravelDocumentFX);
            if (!FacadeSingleton.deleteMyMembership(singleTravelDocumentFX)) { raiseErrorAlert("Impossibile cancellare questo titolo di viaggio!"); return null; }
            return singleTravelDocumentFX;
        }));

        // set items into membership table view
        myMembershipTableView.setItems(myMembershipsObservableList);
    }
}
