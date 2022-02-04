package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.LineRide.RideLineFX;
import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InfoViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;



    // Pane
    @FXML
    private TableView<RideLineFX> lineRideTableView;



    // Line Columns
    @FXML
    private TableColumn<RideLineFX, String> lineIDTableColumn;
    @FXML
    private TableColumn<RideLineFX, Integer> lineLenghtTableColumn;
    @FXML
    private TableColumn<RideLineFX, String> lineStartingStationTableColumn;
    @FXML
    private TableColumn<RideLineFX, String> lineEndingStationTableColumn;
    @FXML
    private TableColumn<RideLineFX, LocalDate> lineActivationDateTableColumn;
    @FXML
    private TableColumn<RideLineFX, Time> lineOpeningHourTableColumn;
    @FXML
    private TableColumn<RideLineFX, Time> lineClosingHourTableColumn;


    // Ride Columns
    @FXML
    private TableColumn<RideLineFX, String> rideIDTableColumn;
    @FXML
    private TableColumn<RideLineFX, String> rideStatusTableColumn;
    @FXML
    private TableColumn<RideLineFX, LocalDate> rideStartingHourTableColumn;
    @FXML
    private TableColumn<RideLineFX, LocalDate> rideEndingHourTableColumn;
    @FXML
    private TableColumn<RideLineFX, Integer> ridePriorityTableColumn;



    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }
    @Override
    protected void initializeViewComponents() {
        ObservableList<RideLineFX> rideLineFXObservableList = FacadeSingleton.getCorsaLineaViewContent();
        lineIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineID"));
        lineLenghtTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineLength"));
        lineStartingStationTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineStartStation"));
        lineEndingStationTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineStopStation"));
        lineActivationDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineActivationDate"));
        lineOpeningHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineOpeningHour"));
        lineClosingHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineClosingHour"));

        rideIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideID"));
        rideStatusTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideStatus"));
        rideStartingHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideStartingHour"));
        rideEndingHourTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideEndingHour"));
        ridePriorityTableColumn.setCellValueFactory(new PropertyValueFactory<>("ridePriority"));

        // set items to table view
        lineRideTableView.setItems(rideLineFXObservableList);
    }
}
