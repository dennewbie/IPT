package com.prog3.ipt.Model.CitizenClasses;

import java.util.LinkedList;

/**
 * CitizenEditProfileMemento is a class, created to implement Memento object included into Memento Method
 * design pattern.
 */
public class CitizenEditProfileMemento {
    private LinkedList<Citizen> citizensData = new LinkedList<>();

    /**
     * Add a reference to a Citizen into a LinkedList<Citizen></Citizen> object
     * @param newCitizen Object which represents data of one of the citizens registered into system
     */
    public void addState(Citizen newCitizen) {citizensData.addFirst(newCitizen);}

    /**
     * Returns a LinkedList<Citizen></Citizen> object
     * @return A reference to a LinkedList<Citizen></Citizen> object
     */
    private LinkedList<Citizen> getCitizensData() {return this.citizensData;}

    /**
     * Returns a Citizen object or null if linked list is empty
     * @return references to a Citizen object or null
     */
    public Citizen getLastCitizenData() {
        //Check if linked list is empty
        if (citizensData.size() > 1) {
            getCitizensData().removeFirst();
            Citizen lastCitizenData = getCitizensData().getFirst();
            return lastCitizenData;
        }
        return null;
    }
}
