package com.prog3.ipt.Model;

import java.time.LocalDate;
import java.util.Objects;

public abstract class User {
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String email;
    private String password;



    protected User(String name, String surname, LocalDate birthDate, String email, String password) {
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setEmail(email);
        setPassword(password);
    }

    // Setters with package visibility
    void setName(String name) {
        this.name = name;
    }
    void setSurname(String surname) {
        this.surname = surname;
    }
    void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    void setEmail(String email) {
        this.email = email;
    }
    void setPassword(String password) {
        this.password = password;
    }

    // Getters
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }
    public LocalDate getBirthDate() {
        return this.birthDate;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object singleObject) {
        if (this == singleObject) return true;
        if (!(singleObject instanceof User)) return false;
        User user = (User) singleObject;
        return getName().equals(user.getName()) && getSurname().equals(user.getSurname()) && getBirthDate().equals(user.getBirthDate()) && getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getBirthDate(), getEmail());
    }
}



