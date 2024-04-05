package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.LoadPanel;
import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.io.IOException;

public class LoadGUI extends DefaultJFrame {

    public LoadGUI() throws IOException {
        super("Logistiek");
        setSize(800, 600);
        setResizable(false);
        getContentPane().add(new LoadPanel());
        setVisible(true);
    }

}
