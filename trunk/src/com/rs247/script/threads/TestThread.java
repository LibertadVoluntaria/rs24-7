/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rs247.script.threads;

import com.rs247.ui.BotFrame;
import java.awt.Graphics;

/**
 *
 * @author Wewt
 */
public class TestThread implements Runnable {
    private BotFrame f;
    public TestThread(BotFrame frame) {
        this.f = frame;
    }

    public void run() {
        Graphics g = f.getGfx();
        for(;;) {
            g.drawString("hello there", 50, 100);

        }
    }
}
