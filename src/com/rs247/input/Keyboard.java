/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.input;

/**
 *
 * @author Wewt
 */
import com.rs247.ui.BotFrame;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;

public class Keyboard implements KeyListener {

    private final BotFrame client;
    private final KeyListener keyDispatcher;
    private final Component source;
    private final int minSleepTime, maxSleepTime, minReleaseSleepTime, maxReleaseSleepTime;

    /**
     *
     * @param c
     * @param oldKListener
     * @param minWPS The minimum words per minute this keyboard emulator will attempt to achieve.
     * @param maxWPS The maximum words per minute this keyboard emulator will attempt to achieve.
     */
    public Keyboard(BotFrame c, KeyListener oldKListener, int minWPS, int maxWPS) {
        this.client = c;
        this.keyDispatcher = oldKListener;
        this.source = c.getClientPane().getComponentAt(1, 1);
        this.minSleepTime = 1000 / ((Math.max(minWPS, maxWPS) * 5) / 60);
        this.maxSleepTime = 1000 / ((Math.min(minWPS, maxWPS) * 5) / 60);
        this.minReleaseSleepTime = minSleepTime / 4;
        this.maxReleaseSleepTime = maxSleepTime / 4;
    }

    public void keyTyped(KeyEvent e) {
        keyDispatcher.keyTyped(duplicate(e));
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F1: // enable all randoms

                System.out.println("All randoms enabled");
                break;
            case KeyEvent.VK_F2: // disable all randoms

                System.out.println("All randoms disabled");
                break;
            case KeyEvent.VK_F3: // enable a script

                break;
            case KeyEvent.VK_F4: // stop a script

                break;
            case KeyEvent.VK_F5: // stop all scripts

                break;
        }
        keyDispatcher.keyPressed(duplicate(e));
    }

    public void keyReleased(KeyEvent e) {
        keyDispatcher.keyReleased(duplicate(e));
    }

    private final KeyEvent duplicate(KeyEvent another) {
        return new KeyEvent(source, another.getID(), another.getWhen(),
                another.getModifiers(), another.getKeyCode(), another.getKeyChar(), another.getKeyLocation());
    }

    private final KeyEvent create(int id, int keyCode, char c) {
        return new KeyEvent(source, id, System.currentTimeMillis(), 0,
                keyCode, c, id != KeyEvent.KEY_TYPED ? KeyEvent.KEY_LOCATION_STANDARD
                : KeyEvent.KEY_LOCATION_UNKNOWN);
    }

    public boolean sendText(String text) {
        try {
            Thread.sleep(random(250, 500));
            for (char c : text.toCharArray()) {
                int code = Character.toUpperCase(c);
                keyPressed(create(KeyEvent.KEY_PRESSED, code, c));
                Thread.sleep(random(minReleaseSleepTime, maxReleaseSleepTime));
                keyTyped(create(KeyEvent.KEY_TYPED, 0, c));
                keyReleased(create(KeyEvent.KEY_RELEASED, code, c));
                Thread.sleep(random(minSleepTime, maxSleepTime));
            }
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean pressEnter() {
        try {
            Thread.sleep(random(250, 500));
            keyPressed(create(KeyEvent.KEY_PRESSED, KeyEvent.VK_ENTER, '\uFFFF'));
            Thread.sleep(random(minReleaseSleepTime, maxReleaseSleepTime));
            keyReleased(create(KeyEvent.KEY_RELEASED, KeyEvent.VK_ENTER, '\uFFFF'));
            Thread.sleep(random(minSleepTime, maxSleepTime));
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public int random(int min, int max) {
        return ((int) (Math.random() * (max - min))) + min;
    }

    /*public boolean sendText(String keys) {
    //Component rs = client.BotFrame.getComponentAt(1, 1);
    try {
    int keynumber = 0;
    char ca[] = keys.toCharArray();

    for (char aCa : ca) {
    Character c = valueOf(toUpperCase(aCa));

    if ((int) aCa == 38 || (int) aCa == 40 || (int) aCa == 37
    || (int) aCa == 39) {
    /*rs.dispatchEvent(new KeyEvent(rs, 401, System
    .currentTimeMillis()
    + (long) (keynumber * 100), 0, c.charValue(),
    '\uFFFF', 1));
    keyPressed(create(KeyEvent.KEY_PRESSED, c.charValue(), '\uFFFF', keynumber));
    Thread.sleep((int) ((((System.nanoTime() >> 256) / 1000000000) / 10000) * 261D));
    keyReleased(create(KeyEvent.KEY_RELEASED, c.charValue(), '\uFFFF', keynumber));
    /*rs.dispatchEvent(new KeyEvent(rs, 402, System
    .currentTimeMillis()
    + (long) ((keynumber + 1) * 100), 0, c.charValue(),
    '\uFFFF', 1));
    } else {
    /*rs.dispatchEvent(new KeyEvent(rs, 401, System
    .currentTimeMillis()
    + (long) (keynumber * 100), 0, c.charValue(), aCa,
    1));
    keyReleased(create(KeyEvent.KEY_PRESSED, c.charValue(), aCa, keynumber));
    Thread.sleep((int) ((((System.nanoTime() >> 256) / 1000000000) / 10000) * 261D));
    keyReleased(create(KeyEvent.KEY_TYPED, c.charValue(), KeyEvent.VK_UNDEFINED, keynumber));
    keyReleased(create(KeyEvent.KEY_RELEASED, c.charValue(), aCa, keynumber));
    /*rs.dispatchEvent(new KeyEvent(rs, 400, System
    .currentTimeMillis()
    + (long) ((keynumber + 1) * 100), 0, 0, aCa, 0));
    rs.dispatchEvent(new KeyEvent(rs, 402, System
    .currentTimeMillis()
    + (long) ((keynumber + 1) * 100), 0, c.charValue(),
    aCa, 1));
    }
    keynumber++;
    }
    sendKey('\n');
    return true;
    } catch (Exception e) {
    e.printStackTrace();
    return false;
    }
    }*/
    public void sendKey(char c) {
        pressKey(c);
        try {
            Thread.sleep((int) ((((System.nanoTime() >> 256) / 1000000000) / 10000) * 261D));
            releaseKey(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void holdKey(char c, int ms) {
        pressKey(c);
        try {
            Thread.sleep(ms + (int) ((((System.nanoTime() >> 256) / 1000000000) / 10000) * 261D));
            releaseKey(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pressKey(char c) {
        client.getClientPane().getComponentAt(1, 1).dispatchEvent(
                new KeyEvent(client.getClientPane().getComponentAt(1, 1),
                KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0,
                (int) ((Character) c).charValue(), c));
    }

    public void releaseKey(char c) {
        java.lang.Character ch;
        ch = c;
        client.getClientPane().getComponentAt(1, 1).dispatchEvent(
                new KeyEvent(client.getClientPane().getComponentAt(1, 1),
                KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0,
                (int) ch.charValue(), c));
    }
}

