package com.prog3.ipt.Model.CitizenClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Citizen extends User {
    private String citizenID;
    private LocalDate registrationDate;
    private String username;



    public Citizen(String name, String surname, LocalDate birthDate, String email, String password, String username) {
        super(name, surname, birthDate, email, password);
        setCitizenID("00003"/* UUID.randomUUID().toString().substring(0, 5 )*/);
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

    // Others
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Citizen)) return false;
        if (!super.equals(o)) return false;
        Citizen citizen = (Citizen) o;
        return getCitizenID().equals(citizen.getCitizenID()) && getRegistrationDate().equals(citizen.getRegistrationDate()) && getUsername().equals(citizen.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCitizenID(), getRegistrationDate(), getUsername());
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
