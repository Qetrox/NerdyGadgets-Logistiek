package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.panels.DeliverInfoPanel;
import nl.nerdygadgets.logistiek.gui.panels.MapPanel;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.web.HttpUtil;
import nl.nerdygadgets.logistiek.util.web.PackageStatus;
import nl.nerdygadgets.logistiek.util.web.WebHelper;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Deze GUI klasse is voor het scherm op het moment dat de bezorger aan het leveren is.
 */
public class DeliverGUI extends DefaultJFrame {

    public DeliverGUI() throws IOException {
        super("Logistiek");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            //setUndecorated(true);
            //gd.setFullScreenWindow(this); //dit verneukt de halve applicatie
            setSize(1200, 800);
        } else {
            setSize(1200, 800);

        };
        setResizable(false);


        // We kunnen niet direct de mapviewer en de deliverinfo panel
        // toevoegen aan de contentpane doordat we mogelijk info moeten doorgeven en ophalen.

        MapPanel mapViewer = new MapPanel();

        WebHelper.WebDelivery route = CacheManager.getCurrentDelivery();

        //zet start waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));


        for(WebHelper.WebPackage p : route.packages) {

            if(CacheManager.getCurrentPackage() == null) {
                CacheManager.setCurrentPackage(p);
            }
            mapViewer.addWaypoint(new Waypoint(new GeoPosition(p.latitude, p.longitude), new Package(p.address, p.name, 1, 1, 1, 1, 1)));
        }

        CacheManager.updatePackageStatus(CacheManager.getCurrentPackage(), PackageStatus.IN_TRANSIT);
        HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatepackage?id=" + CacheManager.getCurrentDelivery().id + "&status=IN_TRANSIT&token=" + CacheManager.getToken().token + "&packageId=" + CacheManager.getCurrentPackage().id));

        //zet eind waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));

        setLayout(new BorderLayout(0,0));

        DeliverInfoPanel deliverInfoPanel = new DeliverInfoPanel(mapViewer);

        getContentPane().add(mapViewer, BorderLayout.WEST);
        getContentPane().add(deliverInfoPanel, BorderLayout.EAST);

        setVisible(true);
    }

}
