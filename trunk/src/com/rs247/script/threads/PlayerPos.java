/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.script.threads;

import com.rs247.ui.BotFrame;
import com.rs247.util.Echo;

/**
 *
 * @author Wewt
 */
public class PlayerPos extends RuneThread implements Runnable {

    public boolean running = true;
    public BotFrame frame;
    public Echo echo;

    public PlayerPos(BotFrame r) {
        this.frame = r;
        echo = frame.getEcho();
    }

    public void run() {
        while (running) {
            try {
                int playerCount = (Integer) frame.getObjectValue("getPlayerCount()").getValue();
                echo.echoThread("Playercount is currently: " + playerCount);
                try {
                    Thread.sleep(5000);
                } catch(Exception e) { }
            } catch (Exception e) {
            }

        }
    }
}
