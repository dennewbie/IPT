package com.prog3.ipt.Controller.TravelDocumentsManagement;

import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;
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
    private TableView<?> myMembershipTableView;

    @FXML
    private TableColumn<TravelDocumentFX, String> mySingleTicketsTransactionIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> mySingleTicketsIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> mySingleTicketsLineIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> mySingleTicketsRideIDTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> mySingleTicketsIssueDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> mySingleTicketsStartDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> mySingleTicketsExpirationDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, Double> mySingleTicketsPriceTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, LocalDate> mySingleTicketsStampDateTableColumn;
    @FXML
    private TableColumn<TravelDocumentFX, String> myMembershipDeleteTableColumn;
    @FXML
    private Button deleteRowButton;

    @FXML
    void onBackButtonClick(ActionEvent event) { super.onButtonClickNavigateToView(backButton, "TicketsManagementView.fxml"); }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        deleteRowButton = new Button("Elimina Abbonamento");
        myMembershipDeleteTableColumn.setCellValueFactory(new PropertyValueFactory<TravelDocumentFX, String>("deleteRowButton"));
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
                                    btn.setOnAction(event -> {
                                        TravelDocumentFX travelDocumentFX = getTableView().getItems().get(getIndex());
                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        myMembershipDeleteTableColumn.setCellFactory(cellFactory);
    }
}
