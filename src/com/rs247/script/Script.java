/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.script;

/**
 *
 * @author Wewt
 */
public abstract class Script implements Runnable {

    public Script() {
        active = true;
    }

    public abstract String getName();

    public abstract boolean onStart();

    public abstract String getVersion();
    public boolean active = false;

    public abstract int loop();

    public abstract String getDescription();

    public void run() {
        if (!onStart()) {
            active = false;
            System.err.println("Error starting script: " + getName());
        }
        while (active) {
            loop();
        }
    }

    public void disguise(String toChange) {
    }
}
