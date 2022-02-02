package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.FacadeClasses.FacadeSingleton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.prog3.ipt.Model.Corsa;
import com.prog3.ipt.Model.Linea;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class InfoViewController extends ViewController {
    //protected void initializeViewComponents(){};
    // Navigation Bar
    @FXML
    private Button backButton;

    // Pane
    @FXML
    private TableView<Linea> lineaTableView;
    @FXML
    private TableView<Corsa> corsaTableView;

    //Table Column Linea
    @FXML
    private TableColumn<Linea, String> lineaIDTableColumn;
    @FXML
    private TableColumn<Linea, Integer> lineaLunghezzaTableColumn;
    @FXML
    private TableColumn<Linea, String> lineaFermataInizioTableColumn;
    @FXML
    private TableColumn<Linea, String> lineaFermataFineTableColumn;
    @FXML
    private TableColumn<Linea, LocalDate> lineaDataAttivazioneTableColumn;
    @FXML
    private TableColumn<Linea, Time> lineaOrarioAperturaTableColumn;
    @FXML
    private TableColumn<Linea, Time> lineaOrarioChiusuraTableColumn;

    //Table Column Corsa
    @FXML
    private TableColumn<Corsa, String> corsaIDTableColumn;
    @FXML
    private TableColumn<Corsa, String> corsaLineaIDTableColumn;
    @FXML
    private TableColumn<Corsa, String> corsaStatoTableColumn;
    @FXML
    private TableColumn<Corsa, LocalDate> corsaOraInizioTableColumn;
    @FXML
    private TableColumn<Corsa, LocalDate> corsaOraFineTableColumn;
    @FXML
    private TableColumn<Corsa, Integer> corsaPrioritàTableColumn;


    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {initializeViewComponents(); }

    @Override
    protected void initializeViewComponents() {
        ObservableList<Linea> lineaObservableList = FacadeSingleton.getLineaViewContent();
       // ObservableList<Corsa> corsaObservableList = FacadeSingleton.getCorsaViewContent();

        lineaIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaID"));
        lineaLunghezzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaLunghezza"));
        lineaFermataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaFermataInizio"));
        lineaFermataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaFermataFine"));
        lineaDataAttivazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaDataAttivazione"));
        lineaOrarioAperturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaOrarioApertura"));
        lineaOrarioChiusuraTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaOrarioChiusura"));

/*
        corsaIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaID"));
        corsaLineaIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaLineaID"));
        corsaStatoTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaStato"));
        corsaOraInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaOraInizio"));
        corsaOraFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaOraFine"));
        corsaPrioritàTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaPriorità"));

 */
         lineaTableView.setItems(lineaObservableList);
         //corsaTableView.setItems(corsaObservableList);
    }


}
