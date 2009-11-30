/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.util;

import com.rs247.ui.BotFrame;
import com.rs247.ui.Console;
import java.awt.Color;

/**
 *
 * @author Wewt
 */
public class Echo {

    private BotFrame frame;
    private Console console;

    public Echo(BotFrame f) {
        frame = f;
        this.console = frame.getConsole();
    }

    public void echoBase(String s) {
        System.out.println(s);
    }

    public void echoThread(String s) {
        console.setOutColor(Color.ORANGE);
        System.out.println(s);
        console.revert();
    }

    public void echoSystem(String s) {
        console.setOutColor(Color.GRAY);
        System.out.println(s);
        console.revert();
    }
}
