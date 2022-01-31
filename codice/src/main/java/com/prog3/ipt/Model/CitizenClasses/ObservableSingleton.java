package com.prog3.ipt.Model.CitizenClasses;

import java.time.LocalDate;

// Variante di Observer con aggiunta di Singleton
public class ObservableSingleton {
    private static volatile Citizen sessionUser;



    private ObservableSingleton() {
        /** Protect against instantiation via reflection */
        if (sessionUser != null) throw new IllegalStateException("Already initialized.");
    }

    public static void setInstance(Citizen newUser) {
        sessionUser = newUser;
    }

    public static void updateInstance(String name, String surname, LocalDate birthDate, String email, String password) {
        getInstance().setName(name);
        getInstance().setSurname(surname);
        getInstance().setBirthDate(birthDate);
        getInstance().setEmail(email);
        getInstance().setPassword(password);
    }

    /** The instance doesn't get created until the method is called for the first time. */
    public static Citizen getInstance() {
        if (sessionUser == null) {
            synchronized (ObservableSingleton.class) {
                if (sessionUser == null) sessionUser = new Citizen(null, null, null, null, null, null);
            }
        }
        return sessionUser;
    }


}
