package com.prog3.ipt.Model.LineRide;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;

public class Line {
    private String lineID;
    private Integer lineLength;
    private String lineStartStation;
    private String lineStopStation;
    private LocalDate lineActivationDate;
    private Time lineOpeningHour;
    private Time lineClosingHour;



    public Line(String lineID, Integer lineLength, String lineStartStation, String lineStopStation, LocalDate lineActivationDate, Time lineOpeningHour, Time lineClosingHour) {
        setLineID(lineID); setLineLength(lineLength); setLineStartStation(lineStartStation);
        setLineStopStation(lineStopStation); setLineActivationDate(lineActivationDate);
        setLineOpeningHour(lineOpeningHour); setLineClosingHour(lineClosingHour);
    }

    // Setters
    private void setLineID(String lineID) { this.lineID = lineID; }
    private void setLineLength(Integer lineLength) { this.lineLength = lineLength; }
    private void setLineStartStation(String lineStartStation) { this.lineStartStation = lineStartStation; }
    private void setLineStopStation(String lineStopStation) { this.lineStopStation = lineStopStation; }
    private void setLineActivationDate(LocalDate lineActivationDate) { this.lineActivationDate = lineActivationDate; }
    private void setLineOpeningHour(Time lineOpeningHour) { this.lineOpeningHour = lineOpeningHour; }
    private void setLineClosingHour(Time lineClosingHour) { this.lineClosingHour = lineClosingHour; }

    // Getters
    public String getLineID(){ return lineID; }
    public Integer getLineLength(){ return lineLength; }
    public String getLineStartStation(){ return lineStartStation; }
    public String getLineStopStation() { return lineStopStation; }
    public LocalDate getLineActivationDate() { return lineActivationDate; }
    public Time getLineOpeningHour() { return lineOpeningHour; }
    public Time getLineClosingHour() { return lineClosingHour; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getLineID().equals(line.getLineID()) && getLineLength().equals(line.getLineLength()) && getLineStartStation().equals(line.getLineStartStation()) && getLineStopStation().equals(line.getLineStopStation()) && getLineActivationDate().equals(line.getLineActivationDate()) && getLineOpeningHour().equals(line.getLineOpeningHour()) && getLineClosingHour().equals(line.getLineClosingHour());
    }
    @Override
    public int hashCode() { return Objects.hash(getLineID(), getLineLength(), getLineStartStation(), getLineStopStation(), getLineActivationDate(), getLineOpeningHour(), getLineClosingHour()); }
    @Override
    public String toString() { return "Line{ lineID='" + lineID + '\'' +  ", lineLength=" + lineLength + ", lineStartStation='" + lineStartStation + '\'' + ", lineStopStation='" + lineStopStation + '\'' + ", lineActivationDate=" + lineActivationDate + ", lineOpeningHour=" + lineOpeningHour + ", lineClosingHour=" + lineClosingHour + '}'; }
}