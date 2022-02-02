package com.prog3.ipt.Model;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;
public class Corsa  {
    private String corsaID;
    private String corsaStato;
    private Time corsaOraInizio;
    private Time corsaOraFine;
    private Integer corsaPriorità;

    //Constructor
    public Corsa(String corsaID , String corsaStato, Time corsaOraInizio, Time corsaOraFine, Integer corsaPriorità){
        this.corsaID = corsaID;
        this.corsaStato = corsaStato;
        this.corsaOraInizio = corsaOraInizio;
        this.corsaOraFine = corsaOraFine;
        this.corsaPriorità = corsaPriorità;
    }

    //Getters

    public String getCorsaID() {return corsaID;}


    public String getCorsaStato() {return corsaStato;}

    public Time getCorsaOraInizio() {return corsaOraInizio;}

    public Time getCorsaOraFine() {return corsaOraFine;}

    public Integer getCorsaPriorità() {return corsaPriorità;}

    //Setters


    public void setCorsaID(String corsaID) {this.corsaID = corsaID;}


    public void setCorsaStato(String corsaStato) {this.corsaStato = corsaStato;}

    public void setCorsaOraInizio(Time corsaOraInizio) {this.corsaOraInizio = corsaOraInizio;}

    public void setCorsaOraFine(Time corsaOraFine) {this.corsaOraFine = corsaOraFine;}

    public void setCorsaPriorità(Integer corsaPriorità) {this.corsaPriorità = corsaPriorità;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Corsa)) return false;
        Corsa corsa = (Corsa) o;
        return getCorsaID().equals(corsa.getCorsaID()) && getCorsaStato().equals(corsa.getCorsaStato()) && getCorsaOraInizio().equals(corsa.getCorsaOraInizio()) && getCorsaOraFine().equals(corsa.getCorsaOraFine()) && getCorsaPriorità().equals(corsa.getCorsaPriorità());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCorsaID(), getCorsaStato(), getCorsaOraInizio(), getCorsaOraFine(), getCorsaPriorità());
    }


    @Override
    public String toString() {
        return "Corsa{" +
                "CorsaID='" + corsaID + '\'' +
                ", CorsaStato='" + corsaStato + '\'' +
                ", CorsaOraInizio='" + corsaOraInizio + '\'' +
                ", CorsaOraFine='" + corsaOraFine + '\'' +
                ", CorsaPriorità='" + corsaPriorità + '\'' +
                '}';
    }

}