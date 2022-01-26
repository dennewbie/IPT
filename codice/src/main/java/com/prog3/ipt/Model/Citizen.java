package com.prog3.ipt.Model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Citizen extends User {
    private String citizenID;
    private Date registrationDate;
    private String username;

    public Citizen(String name, String surname, Date birthDate, String email, String password, String citizenID, Date registrationDate, String username) {
        super(name, surname, birthDate, email, password);
        setCitizenID(UUID.randomUUID().toString());
        setRegistrationDate(registrationDate);
        setUsername(username);
    }

    // Setters
    private void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    private void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    private void setUsername(String username) {
        this.username = username;
    }



    // Getters
    public String getCitizenID() {
        return citizenID;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getUsername() {
        return username;
    }



    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof Citizen)) return false;
        if (!super.equals(singleObject)) return false;
        Citizen citizen = (Citizen) singleObject;
        return citizenID.equals(citizen.citizenID) && registrationDate.equals(citizen.registrationDate) && username.equals(citizen.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), citizenID, registrationDate, username);
    }

    @Override
    public String toString() {
        return "Citizen{" +
                "citizenID='" + citizenID + '\'' +
                ", registrationDate=" + registrationDate +
                ", username='" + username + '\'' +
                '}';
    }
}
