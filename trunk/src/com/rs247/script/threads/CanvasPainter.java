/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.script.threads;

import com.rs247.ui.BotFrame;
import java.awt.*;
import java.awt.image.*;

/**
 *
 * @author Wewt
 */
public class CanvasPainter extends Thread implements Runnable {

    public BufferedImage buffer;
    public Graphics gfx;

    public CanvasPainter(BotFrame f) {
        this.frame = f;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gs = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gs.getDefaultConfiguration();

        this.buffer = gc.createCompatibleImage(766, 503, Transparency.BITMASK);
        this.gfx = buffer.getGraphics();
    }

    public void run() {
        try {
            canvas = (Canvas) frame.getObjectValue("getCanvas()").getValue();

        } catch (Exception e) {
        }
        for (;;) {
            String[] interfaceActions = null;
            try {
                interfaceActions = (String[]) frame.getObjectValue("getInterfaceActions()").getValue();
                
            }catch(Exception e) { }
            
            while (canvas.getGraphics() == null);

            g = canvas.getGraphics();
            int i=0;
            for(String s : interfaceActions) {
                int ypos = (17*i) + 15;
                g.drawString("Action: " + s, 15,ypos);
                i++;
            }

            this.setPriority(Thread.MIN_PRIORITY);
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }

    }

    public Graphics getGraphics() {
        return gfx;
    }

    public void doPaint(Graphics g) {

    }
    public Canvas canvas;
    public int playerCount;
    public BotFrame frame;
    public Graphics g;
}
