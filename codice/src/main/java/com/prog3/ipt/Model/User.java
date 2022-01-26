package com.prog3.ipt.Model;

import java.util.Date;
import java.util.Objects;

public abstract class User {
    private String name;
    private String surname;
    private Date birthDate;
    private String email;
    private String password;

    public User(String name, String surname, Date birthDate, String email, String password) {
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setEmail(email);
        setPassword(password);
    }

    // Setters
    private void setName(String name) {
        this.name = name;
    }

    private void setSurname(String surname) {
        this.surname = surname;
    }

    private void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setPassword(String password) {
        this.password = password;
    }


    // Getters
    private String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getEmail() {
        return this.email;
    }

    protected String getPassword() {
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



