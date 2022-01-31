package com.prog3.ipt.Model.CitizenClasses;

import java.util.LinkedList;

public class CitizenEditProfileMemento {
    private LinkedList<Citizen> citizensData = new LinkedList<>();



    public void addState(Citizen newCitizen) {
        citizensData.addFirst(newCitizen);
    }

    private LinkedList<Citizen> getCitizensData() {
        return this.citizensData;
    }

    public Citizen getLastCitizenData() {
        if (citizensData.size() > 1) {
            getCitizensData().removeFirst();
            Citizen lastCitizenData = getCitizensData().getFirst();
            return lastCitizenData;
        }
        return null;
    }
}
