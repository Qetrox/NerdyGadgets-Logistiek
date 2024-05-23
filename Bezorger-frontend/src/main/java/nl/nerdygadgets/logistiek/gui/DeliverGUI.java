package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.panels.DeliverInfoPanel;
import nl.nerdygadgets.logistiek.gui.panels.MapPanel;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.io.IOException;

/**
 * Deze GUI klasse is voor het scherm op het moment dat de bezorger aan het leveren is.
 */
public class DeliverGUI extends DefaultJFrame {

    public DeliverGUI() throws IOException {
        super("Logistiek");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(12000, 8000);

        };
        setResizable(false);


        // We kunnen niet direct de mapviewer en de deliverinfo panel
        // toevoegen aan de contentpane doordat we mogelijk info moeten doorgeven en ophalen.

        MapPanel mapViewer = new MapPanel();
        DeliverInfoPanel deliverInfoPanel = new DeliverInfoPanel();

        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.3843322878619, 5.137340734129197), new Package("Idk laan 2", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.377011026016035, 5.1755491265934825), new Package("Idk laan 1", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.37556412545035, 5.2157946499848915), new Package("Idk laan 3", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.50798283349655, 5.469818870113745), new Package("Idk laan 4", "Persoon", 1, 1, 1, 1, 1)));


        setLayout(new BorderLayout(0,0));


        getContentPane().add(mapViewer, BorderLayout.WEST);
        getContentPane().add(deliverInfoPanel, BorderLayout.EAST);

        setVisible(true);
    }

}
