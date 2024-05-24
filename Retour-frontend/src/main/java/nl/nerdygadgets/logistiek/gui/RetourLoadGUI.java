package nl.nerdygadgets.logistiek.gui;

import javafx.embed.swing.JFXPanel;
import nl.nerdygadgets.logistiek.gui.panels.LoadPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.awt.*;
import java.io.IOException;

public class RetourLoadGUI extends DefaultJFrame {

    private final JFXPanel jfxPanel = new JFXPanel();

    public RetourLoadGUI() throws IOException {
        super("Logistiek");
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(12000, 8000);

        };
        setResizable(false);
        getContentPane().add(new LoadPanel());
        setVisible(true);
    }

}
