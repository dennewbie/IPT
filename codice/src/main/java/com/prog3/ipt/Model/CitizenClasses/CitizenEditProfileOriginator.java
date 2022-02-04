package com.prog3.ipt.Model.CitizenClasses;

/**
 * CitizenEditProfileOriginator is a class, created to implement Originator object included into Memento
 * Method design pattern.
 */
public class CitizenEditProfileOriginator {
    private Citizen currentCitizen;
    private CitizenEditProfileMemento savedStates = new CitizenEditProfileMemento();

    //Setter
    public void setCurrentCitizen(Citizen currentCitizen) {
        this.currentCitizen = currentCitizen;
        this.currentCitizen.setCitizenID(ObservableSingleton.getCitizen().getCitizenID());
        this.currentCitizen.setRegistrationDate(ObservableSingleton.getCitizen().getRegistrationDate());
    }

    //Getter
    public Citizen getCurrentCitizen() { return this.currentCitizen; }

    /**
     * Add reference to a Citizen object into CitizenEditProfileMemento object to save last status about
     * a Citizen
     */
    public void save() {savedStates.addState(currentCitizen);}

    /**
     * Return boolean value true meaning last status about a Citizen is restored or false meaning last
     * status about Citizen isn't restored because it wasn't saved
     * @return boolean value true or false
     */
    public boolean restore() {
        Citizen lastCitizen = savedStates.getLastCitizenData();
        //Check if was saved a citizen last status
        if (lastCitizen != null) {
            setCurrentCitizen(lastCitizen);
            return true;
        }
        return false;
    }
}
