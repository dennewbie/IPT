package com.prog3.ipt.Model.CitizenClasses;

public class CitizenEditProfileOriginator {
    private Citizen currentCitizen;
    private CitizenEditProfileMemento savedStates = new CitizenEditProfileMemento();



    public void setCurrentCitizen(Citizen currentCitizen) {
        this.currentCitizen = currentCitizen;
        this.currentCitizen.setCitizenID(ObservableSingleton.getCitizen().getCitizenID());
        this.currentCitizen.setRegistrationDate(ObservableSingleton.getCitizen().getRegistrationDate());
    }

    public Citizen getCurrentCitizen() { return this.currentCitizen; }

    public void save() {
        savedStates.addState(currentCitizen);
    }

    public boolean restore() {
        Citizen lastCitizen = savedStates.getLastCitizenData();
        if (lastCitizen != null) {
            setCurrentCitizen(lastCitizen);
            return true;
        }
        return false;
    }
}
