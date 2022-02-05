package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import com.prog3.ipt.Model.LineRide.Notice;
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
 * NoticeViewController is the controller that handles Notices view.
 */
public class NoticesViewController extends ViewController {
    // Navigation Bar
    @FXML
    private Button backButton;

    // Table View Pane
    @FXML
    private TableView<Notice> noticesTableView;

    // Table Column
    @FXML
    private TableColumn<Notice, String> noticeIDTableColumn;
    @FXML
    private TableColumn<Notice, String> lineIDTableColumn;
    @FXML
    private TableColumn<Notice, String> rideIDTableColumn;
    @FXML
    private TableColumn<Notice, LocalDate> noticeDateTableColumn;
    @FXML
    private TableColumn<Notice, String> noticeNameTableColumn;
    @FXML
    private TableColumn<Notice, String> noticeTextTableColumn;

    /**
     * Back to previous view
     * @see ViewController#onButtonClickNavigateToView(Button, String)
     * @param event Button clicked
     */
    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    /**
     * @see javafx.fxml.Initializable#initialize(URL, ResourceBundle)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    /**
     * @see ViewController#initializeViewComponents()
     * @see FacadeSingleton#getNoticesViewContent()
     */
    @Override
    protected void initializeViewComponents() {
        ObservableList<Notice> noticeObservableList = FacadeSingleton.getNoticesViewContent();

        noticeIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("noticeID"));
        lineIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineID"));
        rideIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("rideID"));
        noticeDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("noticeDate"));
        noticeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("noticeName"));
        noticeTextTableColumn.setCellValueFactory(new PropertyValueFactory<>("noticeText"));

        // set items to notice table view
        noticesTableView.setItems(noticeObservableList);
    }
}
