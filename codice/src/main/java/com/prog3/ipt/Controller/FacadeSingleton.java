package com.prog3.ipt.Controller;

/** Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization mechanism. */
public class FacadeSingleton {
    // metodo connnessione DB, richiesta di operazioni maggiore alle classi Util dei sottosistemi, etc.
    private static volatile FacadeSingleton instance;

    private FacadeSingleton() {
        /** Protect against instantiation via reflection */
        if (instance != null) throw new IllegalStateException("Already initialized.");
    }

    /** The instance doesn't get created until the method is called for the first time. */
    public static FacadeSingleton getInstance() {
        if (instance == null) {
            synchronized (FacadeSingleton.class) {
                if (instance == null) instance = new FacadeSingleton();
            }
        }
        return instance;
    }
}
