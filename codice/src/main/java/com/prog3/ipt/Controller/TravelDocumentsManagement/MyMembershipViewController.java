package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
    private TableColumn<TravelDocumentFX, String> deleteTableColumn;
    @FXML
    private Button deleteRowButton;

    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        deleteRowButton = new Button("Elimina Abbonamento");
        deleteRowButton.setId("myMembershipViewButton");
        /*
        Probabilmente non necessarie le seguenti righe: lo scopriremo al popolamento della table view

        String viewStyle = getClass().getResource("myMembershipViewStyle.css").toExternalForm();
        super.getLocalScene().getStylesheets().add(viewStyle);
        */
        deleteTableColumn.setCellValueFactory(new PropertyValueFactory<TravelDocumentFX, String>("deleteRowButton"));
        Callback<TableColumn<TravelDocumentFX, String>, TableCell<TravelDocumentFX, String>> cellFactory =
                new Callback<TableColumn<TravelDocumentFX, String>, TableCell<TravelDocumentFX, String>>() {
                    @Override
                    public TableCell call(final TableColumn<TravelDocumentFX, String> param) {
                        final TableCell<TravelDocumentFX, String> cell = new TableCell<TravelDocumentFX, String>() {
                            final Button btn = new Button("Elimina Abbonamento");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> { TravelDocumentFX travelDocumentFX = getTableView().getItems().get(getIndex()); });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        deleteTableColumn.setCellFactory(cellFactory);

        // create observable list for myTicketsView according to citizenID
        ObservableList<TravelDocumentFX> myMembershipsObservableList = FacadeSingleton.getMyMembershipsViewContent();

        // update table view with new items
        transactionIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        travelDocumentIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("travelDocumentID"));
        issueDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        startDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        expirationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));
        priceTableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        myMembershipTableView.setItems(myMembershipsObservableList);
    }
}
