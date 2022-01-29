package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Citizen extends User {
    private String citizenID;
    private LocalDate registrationDate;
    private String username;



    public Citizen(String name, String surname, LocalDate birthDate, String email, String password, String username) {
        super(name, surname, birthDate, email, password);
        setCitizenID(UUID.randomUUID().toString());
        setRegistrationDate(LocalDate.now());
        setUsername(username);
    }



    // Setters
    private void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }
    private void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    private void setUsername(String username) {
        this.username = username;
    }



    // Getters
    public String getCitizenID() {
        return citizenID;
    }
    public LocalDate getRegistrationDate() {
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
                ", email='" + super.getEmail() + '\'' +
                ", name='" + super.getName() + '\'' +
                ", surname='" + super.getSurname() + '\'' +
                ", birthDate='" + super.getBirthDate() + '\'' +
                '}';
    }
}
