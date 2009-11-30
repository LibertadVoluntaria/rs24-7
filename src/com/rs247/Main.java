/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs247;

import com.rs247.ui.BotFrame;
import com.rs247.util.Download;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Michael
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        File f = new File(System.getProperty("user.home") + "\\rs247\\runescape.jar");
        File f2 = new File(System.getProperty("user.home") + "\\rs247\\loader.jar");
        File dir = new File(System.getProperty("user.home") + "\\rs247\\");
        dir.mkdir();
        if (!f.exists() || !f2.exists()) {
            int i = JOptionPane.showConfirmDialog(null, "RuneScape Jar does not exist. Download now?", "Download RuneScape Jar?", JOptionPane.YES_NO_OPTION);

            switch (i) {
                case 0:
                    JOptionPane.showMessageDialog(null, "This window will dissapear while the jar is downloaded");
                    Download.download("http://world99.runescape.com/runescape.jar", System.getProperty("user.home") + "\\rs247\\runescape.jar");
                    Download.download("http://world99.runescape.com/loader.jar", System.getProperty("user.home") + "\\rs247\\loader.jar");
                    startBot();
                    break;
                case 1:
                    System.exit(0);
                    break;
            }

        } else {
            startBot();
        }

    }

    public static void startBot() {
        new BotFrame().setVisible(true);
        try {
            Thread.sleep(400);
        } catch (Exception e) {
        }
        System.out.println("Welcome to rs24-7");
    }
}
