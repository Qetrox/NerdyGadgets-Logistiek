package nl.nerdygadgets.logistiek.gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import nl.nerdygadgets.logistiek.gui.panels.LoadPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;

public class LoadGUI extends DefaultJFrame {

    private final JFXPanel jfxPanel = new JFXPanel();

    public LoadGUI() throws IOException {
        super("Logistiek");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        /*if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(1200, 800);

        };*/
        setSize(1200, 800);

        setResizable(false);
        getContentPane().add(new LoadPanel());
        setVisible(true);
    }

}
