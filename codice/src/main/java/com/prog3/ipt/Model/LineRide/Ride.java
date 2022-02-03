package com.prog3.ipt.Model.LineRide;

import java.sql.Time;
import java.util.Objects;

public class Ride {
    private String rideID;
    private String rideStatus;
    private Time rideStartingHour;
    private Time rideEndingHour;
    private Integer ridePriority;

    public Ride(String rideID, String rideStatus, Time rideStartingHour, Time rideEndingHour, Integer ridePriority) {
        this.rideID = rideID;
        this.rideStatus = rideStatus;
        this.rideStartingHour = rideStartingHour;
        this.rideEndingHour = rideEndingHour;
        this.ridePriority = ridePriority;
    }

    // Setters
    private void setRideID(String rideID) { this.rideID = rideID; }
    private void setRideStatus(String rideStatus) { this.rideStatus = rideStatus; }
    private void setRideStartingHour(Time rideStartingHour) { this.rideStartingHour = rideStartingHour; }
    private void setRideEndingHour(Time rideEndingHour) { this.rideEndingHour = rideEndingHour; }
    private void setRidePriority(Integer ridePriority) { this.ridePriority = ridePriority; }

    // Getters
    public String getRideID() { return rideID; }
    public String getRideStatus() { return rideStatus; }
    public Time getRideStartingHour() { return rideStartingHour; }
    public Time getRideEndingHour() { return rideEndingHour; }
    public Integer getRidePriority() { return ridePriority; }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ride)) return false;
        Ride ride = (Ride) o;
        return getRideID().equals(ride.getRideID()) && getRideStatus().equals(ride.getRideStatus()) && getRideStartingHour().equals(ride.getRideStartingHour()) && getRideEndingHour().equals(ride.getRideEndingHour()) && getRidePriority().equals(ride.getRidePriority());
    }
    @Override
    public int hashCode() { return Objects.hash(getRideID(), getRideStatus(), getRideStartingHour(), getRideEndingHour(), getRidePriority()); }
    @Override
    public String toString() { return "Ride{ rideID='" + rideID + ", rideStatus='" + rideStatus + ", rideStartingHour=" + rideStartingHour + ", rideEndingHour=" + rideEndingHour + ", ridePriority=" + ridePriority + '}'; }

}