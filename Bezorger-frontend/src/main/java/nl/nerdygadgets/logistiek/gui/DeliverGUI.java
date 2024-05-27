package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.panels.DeliverInfoPanel;
import nl.nerdygadgets.logistiek.gui.panels.MapPanel;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.web.WebHelper;
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

        WebHelper.WebDelivery route = CacheManager.getCurrentDelivery();

        //zet start waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));


        for(WebHelper.WebPackage p : route.packages) {

            mapViewer.addWaypoint(new Waypoint(new GeoPosition(p.latitude, p.longitude), new Package(p.address, p.name, 1, 1, 1, 1, 1)));

        }

        //zet eind waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));

        setLayout(new BorderLayout(0,0));


        getContentPane().add(mapViewer, BorderLayout.WEST);
        getContentPane().add(deliverInfoPanel, BorderLayout.EAST);

        setVisible(true);
    }

}
