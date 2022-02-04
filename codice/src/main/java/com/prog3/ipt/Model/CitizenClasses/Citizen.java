package com.prog3.ipt.Model.CitizenClasses;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Citizen is a class that extends User abstract class which rapresents a Citizen and his data registered
 * into the system
 */
public class Citizen extends User {
    private String citizenID;
    private LocalDate registrationDate;
    private String username;

    /**
     * Citizen constructor
     * @param citizenID Index which uniquely identifies a citizen
     * @param name Citizen name
     * @param surname Citizen surname
     * @param birthDate Citizen birth date
     * @param email Citizen email which is used to register and login into the system
     * @param password Citizen password which is used to login into the system
     * @param username Citizen username which is used to login into the system
     */
    public Citizen(String citizenID, String name, String surname, LocalDate birthDate, String email, String password, String username) {
        super(name, surname, birthDate, email, password);
        setCitizenID(citizenID);
        setRegistrationDate(LocalDate.now());
        setUsername(username);
    }

    /**
     * Citizen constructor
     * @param name Citizen name
     * @param surname Citizen surname
     * @param birthDate Citizen birth date
     * @param email Citizen email which is used to register and login into the system
     * @param password Citizen password which is used to login into the system
     * @param username Citizen username which is used to login into the system
     */
    public Citizen(String name, String surname, LocalDate birthDate, String email, String password, String username) {
        super(name, surname, birthDate, email, password);
        setCitizenID(UUID.randomUUID().toString().substring(0, 5));
        setRegistrationDate(LocalDate.now());
        setUsername(username);
    }

    // Setters
    void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }
    void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
    void setUsername(String username) {
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
    public int hashCode() { return Objects.hash(super.hashCode(), getCitizenID(), getRegistrationDate(), getUsername()); }

    @Override
    public String toString() { return super.toString() + "Citizen{ citizenID='" + citizenID + '\'' + ", registrationDate=" + registrationDate + ", username='" + username + '\'' + '}'; }
}
