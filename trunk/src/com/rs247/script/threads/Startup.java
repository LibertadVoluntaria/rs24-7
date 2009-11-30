/*
 * Thread to check if RuneScape is loaded
 */
package com.rs247.script.threads;

import com.rs247.ui.*;
import com.rs247.util.Echo;

/**
 *
 * @author Wewt
 */
public class Startup extends RuneThread implements Runnable {

    public boolean running = true;
    public BotFrame frame;
    public Echo echo;

    public Startup(BotFrame r) {
        this.frame = r;
        echo = frame.getEcho();
    }

    public void run() {
        echo.echoThread("RuneScape is Loading....");
        try {
            Thread.sleep(600);
        } catch (Exception e) {
        }
        while (running) {
            try {
                int i = (Integer) frame.getObjectValue("getLoginIndex()").getValue();
                if (i == 10) {
                    echo.echoThread("RuneScape is Loaded");
                    frame.setTitle("rs24-7 - Loaded");
                    running = false;
                }
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            } catch (Exception e) {
                System.err.println("THREAD ERROR: isLoaded");

                e.printStackTrace();
                try {
                    Thread.sleep(8000);
                } catch (Exception ex) {
                }
            }

        }
    }
}
