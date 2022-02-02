package com.prog3.ipt.Controller;

import com.prog3.ipt.Model.CorsaLineaFX;
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
    // Navigation Bar
    @FXML
    private Button backButton;

    // Pane
    @FXML
    private TableView<CorsaLineaFX> CorsaLineaTableView;


    //Table Column Linea
    @FXML
    private TableColumn<CorsaLineaFX, String> lineaIDTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, Integer> lineaLunghezzaTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, String> lineaFermataInizioTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, String> lineaFermataFineTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, LocalDate> lineaDataAttivazioneTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, Time> lineaOrarioAperturaTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, Time> lineaOrarioChiusuraTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, String> corsaIDTableColumn;

    @FXML
    private TableColumn<CorsaLineaFX, String> corsaStatoTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, LocalDate> corsaOraInizioTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, LocalDate> corsaOraFineTableColumn;
    @FXML
    private TableColumn<CorsaLineaFX, Integer> corsaPrioritàTableColumn;


    @FXML
    void onBackButtonClick(ActionEvent event) {
        super.onButtonClickNavigateToView(backButton, "HomeView.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { initializeViewComponents(); }

    @Override
    protected void initializeViewComponents() {
        ObservableList<CorsaLineaFX> CorsaLineaFXObservableList = FacadeSingleton.getCorsaLineaViewContent();

        lineaIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaID"));
        lineaLunghezzaTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaLunghezza"));
        lineaFermataInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaFermataInizio"));
        lineaFermataFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaFermataFine"));
        lineaDataAttivazioneTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaDataAttivazione"));
        lineaOrarioAperturaTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaOrarioApertura"));
        lineaOrarioChiusuraTableColumn.setCellValueFactory(new PropertyValueFactory<>("lineaOrarioChiusura"));

        corsaIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaID"));
        corsaStatoTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaStato"));
        corsaOraInizioTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaOraInizio"));
        corsaOraFineTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaOraFine"));
        corsaPrioritàTableColumn.setCellValueFactory(new PropertyValueFactory<>("corsaPriorità"));

        CorsaLineaTableView.setItems(CorsaLineaFXObservableList);
    }


}
