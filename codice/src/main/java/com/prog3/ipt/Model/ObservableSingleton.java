package com.prog3.ipt.Model;

// Variante di Observer con aggiunta di Singleton
public class ObservableSingleton {
    private static volatile Citizen sessionUser;

    private ObservableSingleton() {
        /** Protect against instantiation via reflection */
        if (sessionUser != null) throw new IllegalStateException("Already initialized.");
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

    public static void setInstance(Citizen newUser) {
        sessionUser = newUser;
    }
}
