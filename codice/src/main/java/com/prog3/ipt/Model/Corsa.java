package com.prog3.ipt.Model;
import java.time.LocalDate;
import java.util.Objects;
public class Corsa  {
    private String corsaID;
    private String lineaID;
    private String corsaStato;
    private LocalDate corsaOraInizio;
    private LocalDate corsaOraFine;
    private Integer corsaPriorità;

    //Constructor
    public Corsa(String corsaID, String lineaID, String corsaStato, LocalDate corsaOraInizio, LocalDate corsaOraFine, Integer corsaPriorità){
        this.corsaID = corsaID;
        this.lineaID = lineaID;
        this.corsaStato = corsaStato;
        this.corsaOraInizio = corsaOraInizio;
        this.corsaOraFine = corsaOraFine;
        this.corsaPriorità = corsaPriorità;
    }

    //Getters

    public String getCorsaID() {return corsaID;}

    public String getLineaID() {return lineaID;}

    public String getCorsaStato() {return corsaStato;}

    public LocalDate getCorsaOraInizio() {return corsaOraInizio;}

    public LocalDate getCorsaOraFine() {return corsaOraFine;}

    public Integer getCorsaPriorità() {return corsaPriorità;}

    //Setters


    public void setCorsaID(String corsaID) {this.corsaID = corsaID;}

    public void setLineaID(String lineaID) {this.lineaID = lineaID;}

    public void setCorsaStato(String corsaStato) {this.corsaStato = corsaStato;}

    public void setCorsaOraInizio(LocalDate corsaOraInizio) {this.corsaOraInizio = corsaOraInizio;}

    public void setCorsaOraFine(LocalDate corsaOraFine) {this.corsaOraFine = corsaOraFine;}

    public void setCorsaPriorità(Integer corsaPriorità) {this.corsaPriorità = corsaPriorità;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Corsa)) return false;
        Corsa corsa = (Corsa) o;
        return getCorsaID().equals(corsa.getCorsaID()) && getLineaID().equals(corsa.getLineaID()) && getCorsaStato().equals(corsa.getCorsaStato()) && getCorsaOraInizio().equals(corsa.getCorsaOraInizio()) && getCorsaOraFine().equals(corsa.getCorsaOraFine()) && getCorsaPriorità().equals(corsa.getCorsaPriorità());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCorsaID(), getLineaID(), getCorsaStato(), getCorsaOraInizio(), getCorsaOraFine(), getCorsaPriorità());
    }


    @Override
    public String toString() {
        return "Corsa{" +
                "CorsaID='" + corsaID + '\'' +
                ", LineaID=" + lineaID +
                ", CorsaStato='" + corsaStato + '\'' +
                ", CorsaOraInizio='" + corsaOraInizio + '\'' +
                ", CorsaOraFine='" + corsaOraFine + '\'' +
                ", CorsaPriorità='" + corsaPriorità + '\'' +
                '}';
    }

}