/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.client;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 *
 * @author Wewt
 */
public class RuneStub implements AppletStub {

    private boolean useSigned;
    private int worldLoaded;
    public Applet theApplet;
    private HashMap<String, String> params = new HashMap<String, String>();
    private ClassLoader clientClassLoader;

    public RuneStub(boolean signed, int world, RuneClassLoader rcl) {
        params = new ClientParser().getRSParams(world);
        try {
            useSigned = signed;
            worldLoaded = world;
            String loadJar = (useSigned ? "loader" : "runescape");
            String loadClass = (useSigned ? "loader" : "client");
            //clientClassLoader = new URLClassLoader(new URL[]{new URL("http://world" + worldLoaded + ".runescape.com/" + loadJar + ".jar")});
            Class<?> clientClass = rcl.loadClass(loadClass);
            assert clientClass != null;
            theApplet = (Applet) clientClass.newInstance();
            theApplet.setStub(this);
        } catch (Exception e) {
        }


    }

    public Applet getApplet() {
        return this.theApplet;
    }

    public boolean isActive() {
        return false;
    }

    public URL getDocumentBase() {
        try {
            return new URL("http://world" + worldLoaded + ".runescape.com");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public URL getCodeBase() {
        return getDocumentBase();
    }

    public AppletContext getAppletContext() {
        return null;
    }

    public String getParameter(String key) {
        return params.get(key);
    }

    public void appletResize(int width, int height) {
    }
}
