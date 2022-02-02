package com.prog3.ipt.Model;

import com.prog3.ipt.Model.TravelDocumentClasses.TravelDocumentFX;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;

public class CorsaLineaFX  {

    //Corsa
    private String corsaID;
    private String corsaStato;
    private Time corsaOraInizio;
    private Time corsaOraFine;
    private Integer corsaPriorità;


    //Linea
    private String lineaID;
    private Integer lineaLunghezza;
    private String lineaFermataInizio;
    private String lineaFermataFine;
    private LocalDate lineaDataAttivazione;
    private Time lineaOrarioApertura;
    private Time lineaOrarioChiusura;

    //Constructor
    public CorsaLineaFX (String corsaID,   String corsaStato, Time corsaOraInizio, Time corsaOraFine, Integer corsaPriorità, String lineaID, Integer lineaLunghezza, String lineaFermataInizio, String lineaFermataFine, LocalDate lineaDataAttivazione, Time lineaOrarioApertura, Time lineaOrarioChiusura){
        this.corsaID = corsaID;
        this.corsaStato = corsaStato;
        this.corsaOraInizio = corsaOraInizio;
        this.corsaOraFine = corsaOraFine;
        this.corsaPriorità = corsaPriorità;
        this.lineaID = lineaID;
        this.lineaLunghezza = lineaLunghezza;
        this.lineaFermataInizio = lineaFermataInizio;
        this.lineaFermataFine = lineaFermataFine;
        this.lineaDataAttivazione = lineaDataAttivazione;
        this.lineaOrarioApertura = lineaOrarioApertura;
        this.lineaOrarioChiusura = lineaOrarioChiusura;
    }

    public CorsaLineaFX(Corsa corsa){
        this.corsaID = corsaID;
        this.corsaStato = corsaStato;
        this.corsaOraInizio = corsaOraInizio;
        this.corsaOraFine = corsaOraFine;
        this.corsaPriorità = corsaPriorità;
    }
    public CorsaLineaFX(Linea linea){
        this.lineaID = lineaID;
        this.lineaLunghezza = lineaLunghezza;
        this.lineaFermataInizio = lineaFermataInizio;
        this.lineaFermataFine = lineaFermataFine;
        this.lineaDataAttivazione = lineaDataAttivazione;
        this.lineaOrarioApertura = lineaOrarioApertura;
        this.lineaOrarioChiusura = lineaOrarioChiusura;
    }
    //Getters
    public String getCorsaID() {return corsaID;}


    public String getCorsaStato() {return corsaStato;}

    public Time getCorsaOraInizio() {return corsaOraInizio;}

    public Time getCorsaOraFine() {return corsaOraFine;}

    public Integer getCorsaPriorità() {return corsaPriorità;}

    public String getLineaID(){ return lineaID;}
    public Integer getLineaLunghezza(){ return  lineaLunghezza;}
    public String getLineaFermataInizio(){return lineaFermataInizio;}

    public String getLineaFermataFine() {return lineaFermataFine;}

    public LocalDate getLineaDataAttivazione() {return lineaDataAttivazione;}

    public Time getLineaOrarioApertura() {return lineaOrarioApertura;}

    public Time getLineaOrarioChiusura() {return lineaOrarioChiusura;}


    //Setters
    public void setCorsaID(String corsaID) {this.corsaID = corsaID;}


    public void setCorsaStato(String corsaStato) {this.corsaStato = corsaStato;}

    public void setCorsaOraInizio(Time corsaOraInizio) {this.corsaOraInizio = corsaOraInizio;}

    public void setCorsaOraFine(Time corsaOraFine) {this.corsaOraFine = corsaOraFine;}

    public void setCorsaPriorità(Integer corsaPriorità) {this.corsaPriorità = corsaPriorità;}
    public void setLineaID(String lineaID) {this.lineaID = lineaID;}

    public void setLineaLunghezza(Integer lineaLunghezza) {this.lineaLunghezza = lineaLunghezza;}

    public void setLineaFermataInizio(String lineaFermataInizio) {this.lineaFermataInizio = lineaFermataInizio;}

    public void setLineaFermataFine(String lineaFermataFine) {this.lineaFermataFine = lineaFermataFine;}

    public void setLineaDataAttivazione(LocalDate lineaDataAttivazione) {this.lineaDataAttivazione = lineaDataAttivazione;}

    public void setLineaOrarioApertura(Time lineaOrarioApertura) {this.lineaOrarioApertura = lineaOrarioApertura;}

    public void setLineaOrarioChiusura(Time lineaOrarioChiusura) {this.lineaOrarioChiusura = lineaOrarioChiusura;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CorsaLineaFX)) return false;
        CorsaLineaFX that = (CorsaLineaFX) o;
        return getCorsaID().equals(that.getCorsaID()) &&  getCorsaStato().equals(that.getCorsaStato()) && getCorsaOraInizio().equals(that.getCorsaOraInizio()) && getCorsaOraFine().equals(that.getCorsaOraFine()) && getCorsaPriorità().equals(that.getCorsaPriorità()) && getLineaID().equals(that.getLineaID()) && getLineaLunghezza().equals(that.getLineaLunghezza()) && getLineaFermataInizio().equals(that.getLineaFermataInizio()) && getLineaFermataFine().equals(that.getLineaFermataFine()) && getLineaDataAttivazione().equals(that.getLineaDataAttivazione()) && getLineaOrarioApertura().equals(that.getLineaOrarioApertura()) && getLineaOrarioChiusura().equals(that.getLineaOrarioChiusura());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCorsaID(), getCorsaStato(), getCorsaOraInizio(), getCorsaOraFine(), getCorsaPriorità(),getLineaID(), getLineaLunghezza(), getLineaFermataInizio(), getLineaFermataFine(), getLineaDataAttivazione(), getLineaOrarioApertura(), getLineaOrarioChiusura());

    }


    @Override
    public String toString() {
        return "CorsaLineaFX{" +
                "corsaID='" + corsaID + '\'' +
                ", corsaStato=" + corsaStato +
                ", corsaOraInizio=" + corsaOraInizio +
                ", corsaOraFine='" + corsaOraFine + '\'' +
                ", corsaPriorità='" + corsaPriorità + '\'' +
                ", lineaID='" + lineaID + '\'' +
                ", lineaLunghezza=" + lineaLunghezza + '\'' +
                ", lineaFermataInizio=" + lineaFermataInizio + '\'' +
                ", lineaFermataFine=" + lineaFermataFine + '\'' +
                ", lineaDataAttivazione=" + lineaDataAttivazione + '\'' +
                ", lineaOrarioApertura=" + lineaOrarioApertura + '\'' +
                ", lineaOrarioChiusura=" + lineaOrarioChiusura + '\'' +
                '}';
    }



}
