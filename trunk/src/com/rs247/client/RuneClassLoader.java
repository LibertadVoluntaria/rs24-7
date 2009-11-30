/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247.client;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 *
 * @author Wewt
 */
public class RuneClassLoader extends ClassLoader {

    private HashMap<String, Class<?>> classes = new HashMap<String, Class<?>>();
    private JarFile jar;
    private ClassLoader cl;

    public RuneClassLoader(boolean signed) {
        String loadJar = (signed ? "loader" : "runescape");
        try {
            File f = new File(System.getProperty("user.home") + "\\rs247\\" + loadJar + ".jar");
            try {
                jar = new JarFile(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Class<?> findClass(String name) {
        return classes.get(name);
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        //System.out.println("LOADING " + className);
        Class<?> c;
        try {
            c = super.findSystemClass(className);
            classes.put(className, c);
            return c;
        } catch (ClassNotFoundException classException) {
            ZipEntry zip = jar.getEntry(className.replaceAll("\\.", "/") + ".class");
            byte[] buffer;
            try {
                buffer = new ClassParser(jar.getInputStream(zip), className).parse().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
                Class<?> classlc = super.loadClass(className);
                classes.put(className, classlc);
                return classlc;
            }
            Class<?> cd = defineClass(className, buffer, 0, buffer.length);
            classes.put(className, cd);
            return cd;
        }
    }
}
