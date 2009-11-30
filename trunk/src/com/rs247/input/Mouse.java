/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.input;

import com.rs247.ui.BotFrame;
import java.awt.Component;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Wewt
 */
public class Mouse {

    private final MouseListener mouseDispatcher;
    private final MouseMotionListener mouseMotionDispatcher;
    private final Component source;
    private final BotFrame frame;
    public int botX, botY;

    public Mouse(BotFrame f, MouseListener oldMListener, MouseMotionListener oldMMListener) {
        this.mouseDispatcher = oldMListener;
        this.frame = f;
        this.mouseMotionDispatcher = oldMMListener;
        this.source = frame.getComponentAt(1, 1);
    }
}
