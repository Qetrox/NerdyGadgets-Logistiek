package nl.nerdygadgets.logistiek.gui.waypoint;

import nl.nerdygadgets.logistiek.delivery.Package;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;

public class Waypoint extends DefaultWaypoint {

    private Package packageToDeliver;
    private boolean delivered;
    private GeoPosition coordinates;
    private JButton button;

    public Waypoint(double latitude, double longitude, Package packageToDeliver) {
        super(latitude, longitude);
        this.packageToDeliver = packageToDeliver;
        this.delivered = false;
        this.coordinates = new GeoPosition(latitude, longitude);
        initButton(packageToDeliver);
    }

    public Waypoint(GeoPosition coordinates) {
        super(coordinates);
        this.coordinates = coordinates;
        initButton();
    }

    public Waypoint(GeoPosition coordinates, Package packageToDeliver) {
        super(coordinates);
        this.packageToDeliver = packageToDeliver;
        this.delivered = false;
        this.coordinates = coordinates;
        initButton(packageToDeliver);
    }

    public Package getPackageToDeliver() {
        return packageToDeliver;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
        packageToDeliver.setDelivered(delivered);
    }

    public GeoPosition getCoordinates() {
        return coordinates;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public JButton getButton() {
        return button;
    }

    private void initButton() {
        button = new WaypointIcon();
    }
    private void initButton(Package packageToDeliver) {
        button = new WaypointIcon(packageToDeliver);
    }

    private static enum PointType {
        START, END
    }

}
