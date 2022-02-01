package com.prog3.ipt.Model;
import com.prog3.ipt.Controller.InfoViewController;

import java.time.LocalDate;
import java.util.Objects;
public class Linea extends InfoViewController {
    private String lineaID;
    private Integer lineaLunghezza;
    private String lineaFermataInizio;
    private String lineaFermataFine;
    private LocalDate lineaDataAttivazione;
    private LocalDate lineaOrarioApertura;
    private LocalDate lineaOrarioChiusura;

    //Constructor
    public Linea(String lineaID, Integer lineaLunghezza, String lineaFermataInizio, String lineaFermataFine, LocalDate lineaDataAttivazione, LocalDate lineaOrarioApertura, LocalDate lineaOrarioChiusura){
        this.lineaID = lineaID;
        this.lineaLunghezza = lineaLunghezza;
        this.lineaFermataInizio = lineaFermataInizio;
        this.lineaFermataFine = lineaFermataFine;
        this.lineaDataAttivazione = lineaDataAttivazione;
        this.lineaOrarioApertura = lineaOrarioApertura;
        this.lineaOrarioChiusura = lineaOrarioChiusura;
    }

    //Getters
    public String getLineaID(){ return lineaID;}
    public Integer getLineaLunghezza(){ return  lineaLunghezza;}
    public String getLineaFermataInizio(){return lineaFermataInizio;}

    public String getLineaFermataFine() {return lineaFermataFine;}

    public LocalDate getLineaDataAttivazione() {return lineaDataAttivazione;}

    public LocalDate getLineaOrarioApertura() {return lineaOrarioApertura;}

    public LocalDate getLineaOrarioChiusura() {return lineaOrarioChiusura;}

    //Setters
    public void setLineaID(String lineaID) {this.lineaID = lineaID;}

    public void setLineaLunghezza(Integer lineaLunghezza) {this.lineaLunghezza = lineaLunghezza;}

    public void setLineaFermataInizio(String lineaFermataInizio) {this.lineaFermataInizio = lineaFermataInizio;}

    public void setLineaFermataFine(String lineaFermataFine) {this.lineaFermataFine = lineaFermataFine;}

    public void setLineaDataAttivazione(LocalDate lineaDataAttivazione) {this.lineaDataAttivazione = lineaDataAttivazione;}

    public void setLineaOrarioApertura(LocalDate lineaOrarioApertura) {this.lineaOrarioApertura = lineaOrarioApertura;}

    public void setLineaOrarioChiusura(LocalDate lineaOrarioChiusura) {this.lineaOrarioChiusura = lineaOrarioChiusura;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Linea)) return false;
        Linea linea = (Linea) o;
        return getLineaID().equals(linea.getLineaID()) && getLineaLunghezza().equals(linea.getLineaLunghezza()) && getLineaFermataInizio().equals(linea.getLineaFermataInizio()) && getLineaFermataFine().equals(linea.getLineaFermataFine()) && getLineaDataAttivazione().equals(linea.getLineaDataAttivazione()) && getLineaOrarioApertura().equals(linea.getLineaOrarioApertura()) && getLineaOrarioChiusura().equals(linea.getLineaOrarioChiusura());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getLineaID(), getLineaLunghezza(), getLineaFermataInizio(), getLineaFermataFine(), getLineaDataAttivazione(), getLineaOrarioApertura(), getLineaOrarioChiusura());
    }

    @Override
    public String toString() {
        return "Linea{" +
                "LineaID='" + lineaID + '\'' +
                ", LineaLunghezza=" + lineaLunghezza +
                ", LineaFermataInizio='" + lineaFermataInizio + '\'' +
                ", LineaFermataFine='" + lineaFermataFine + '\'' +
                ", LineaDataAttivazione='" + lineaDataAttivazione + '\'' +
                ", LineaOrarioApertura='" + lineaOrarioApertura + '\'' +
                ", LineaOrarioChiusura='" +  lineaOrarioChiusura + '\''+
                '}';
    }




}