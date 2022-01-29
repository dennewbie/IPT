package com.prog3.ipt.Model;

import java.util.Date;
import java.util.UUID;

public class CitizenEditProfileOriginator {
    private Citizen currentCitizen;
    private CitizenEditProfileMemento savedStates = new CitizenEditProfileMemento();

    public Citizen getCurrentCitizen() { return this.currentCitizen; }

    public void setCurrentCitizen(Citizen currentCitizen) { this.currentCitizen = currentCitizen; }

    public void save() {
        savedStates.addState(currentCitizen);
    }

    public int restore() {
        Citizen lastCitizen = savedStates.getLastCitizenData();
        if (lastCitizen != null) {
            setCurrentCitizen(lastCitizen);
            return 1;
        }
        return 0;
    }
}
