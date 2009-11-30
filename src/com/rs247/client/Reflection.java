/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.client;

import java.lang.reflect.*;
import com.rs247.ui.BotFrame;

/**
 *
 * @author Wewt
 */
public class Reflection {

    String name = null;
    String clazz = null;
    String field = null;
    BotFrame frame = null;

    public Reflection(BotFrame f, String n, String c, String fi) {
        name = n;
        clazz = c;
        frame = f;
        field = fi;
    }

    public String toString() {
        return name + " gets " + clazz + "." + field;
    }

    private Class<?> getClass(String name) {
        return frame.rsLoader.findClass(name);
    }

    private Field getField() {
        try {
            Class<?> clz = getClass(clazz);
            Field f = clz.getDeclaredField(field);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public Object getValue() throws Exception {
        Field f = getField();
        if (f == null) {
            System.out.println("Null field");
            return null;
        }
        return f.get(null);
    }

    public Object getValue(Object obj) throws Exception {
        Field f = getField();
        return f.get(obj);
    }
}
