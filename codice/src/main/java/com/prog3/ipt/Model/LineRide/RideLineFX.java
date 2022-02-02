package com.prog3.ipt.Model.LineRide;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;

public class RideLineFX {
    //Ride
    private String rideID;
    private String rideStatus;
    private Time rideStartingHour;
    private Time rideEndingHour;
    private Integer ridePriority;
    //Line
    private String lineID;
    private Integer lineLength;
    private String lineStartStation;
    private String lineStopStation;
    private LocalDate lineActivationDate;
    private Time lineOpeningHour;
    private Time lineClosingHour;



    public RideLineFX(String rideID, String rideStatus, Time rideStartingHour, Time rideEndingHour, Integer ridePriority, String lineID, Integer lineLength, String lineStartStation, String lineStopStation, LocalDate lineActivationDate, Time lineOpeningHour, Time lineClosingHour) {
        this.rideID = rideID; this.rideStatus = rideStatus; this.rideStartingHour = rideStartingHour;
        this.rideEndingHour = rideEndingHour; this.ridePriority = ridePriority; this.lineID = lineID;
        this.lineLength = lineLength; this.lineStartStation = lineStartStation; this.lineStopStation = lineStopStation;
        this.lineActivationDate = lineActivationDate; this.lineOpeningHour = lineOpeningHour; this.lineClosingHour = lineClosingHour;
    }
    public RideLineFX(Ride ride) {
        this.rideID = rideID; this.rideStatus = rideStatus; this.rideStartingHour = rideStartingHour;
        this.rideEndingHour = rideEndingHour; this.ridePriority = ridePriority;
    }
    public RideLineFX(Line line) {
        this.lineID = lineID; this.lineLength = lineLength; this.lineStartStation = lineStartStation; this.lineStopStation = lineStopStation;
        this.lineActivationDate = lineActivationDate; this.lineOpeningHour = lineOpeningHour; this.lineClosingHour = lineClosingHour;
    }

    // Setters
    private void setRideID(String rideID) { this.rideID = rideID; }
    private void setRideStatus(String rideStatus) { this.rideStatus = rideStatus; }
    private void setRideStartingHour(Time rideStartingHour) { this.rideStartingHour = rideStartingHour; }
    private void setRideEndingHour(Time rideEndingHour) { this.rideEndingHour = rideEndingHour; }
    private void setRidePriority(Integer ridePriority) { this.ridePriority = ridePriority; }
    private void setLineID(String lineID) { this.lineID = lineID; }
    private void setLineLength(Integer lineLength) { this.lineLength = lineLength; }
    private void setLineStartStation(String lineStartStation) { this.lineStartStation = lineStartStation; }
    private void setLineStopStation(String lineStopStation) { this.lineStopStation = lineStopStation; }
    private void setLineActivationDate(LocalDate lineActivationDate) { this.lineActivationDate = lineActivationDate; }
    private void setLineOpeningHour(Time lineOpeningHour) { this.lineOpeningHour = lineOpeningHour; }
    private void setLineClosingHour(Time lineClosingHour) { this.lineClosingHour = lineClosingHour; }

    //Getters
    public String getRideID() { return rideID; }
    public String getRideStatus() { return rideStatus; }
    public Time getRideStartingHour() { return rideStartingHour; }
    public Time getRideEndingHour() { return rideEndingHour; }
    public Integer getRidePriority() { return ridePriority; }
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
        if (!(o instanceof RideLineFX)) return false;
        RideLineFX rideLi = (RideLineFX) o;
        return Objects.equals(getRideID(), rideLi.getRideID()) && Objects.equals(getRideStatus(), rideLi.getRideStatus()) && Objects.equals(getRideStartingHour(), rideLi.getRideStartingHour()) && Objects.equals(getRideEndingHour(), rideLi.getRideEndingHour()) && Objects.equals(getRidePriority(), rideLi.getRidePriority()) && Objects.equals(getLineID(), rideLi.getLineID()) && Objects.equals(getLineLength(), rideLi.getLineLength()) && Objects.equals(getLineStartStation(), rideLi.getLineStartStation()) && Objects.equals(getLineStopStation(), rideLi.getLineStopStation()) && Objects.equals(getLineActivationDate(), rideLi.getLineActivationDate()) && Objects.equals(getLineOpeningHour(), rideLi.getLineOpeningHour()) && Objects.equals(getLineClosingHour(), rideLi.getLineClosingHour());
    }
    @Override
    public String toString() { return "RideLineFX{ rideID='" + rideID + ", rideStatus='" + rideStatus + ", rideStartingHour=" + rideStartingHour + ", rideEndingHour=" + rideEndingHour +  ", ridePriority=" + ridePriority + ", lineID='" + lineID + ", lineLength=" + lineLength + ", lineStartStation='" + lineStartStation + ", lineStopStation='" + lineStopStation + ", lineActivationDate=" + lineActivationDate + ", lineOpeningHour=" + lineOpeningHour + ", lineClosingHour=" + lineClosingHour + '}'; }
    @Override
    public int hashCode() { return Objects.hash(getRideID(), getRideStatus(), getRideStartingHour(), getRideEndingHour(), getRidePriority(), getLineID(), getLineLength(), getLineStartStation(), getLineStopStation(), getLineActivationDate(), getLineOpeningHour(), getLineClosingHour()); }
}
