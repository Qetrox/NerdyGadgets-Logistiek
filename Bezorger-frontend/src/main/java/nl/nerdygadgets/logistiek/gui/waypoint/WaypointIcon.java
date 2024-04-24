package nl.nerdygadgets.logistiek.gui.waypoint;

import nl.nerdygadgets.logistiek.delivery.Package;

import javax.swing.*;
import java.awt.*;

public class WaypointIcon extends JButton {

    public WaypointIcon() {

        setContentAreaFilled(false);
        setIcon(new ImageIcon("src/main/resources/images/pin.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(24, 24));
        setBorderPainted(false);
        setFocusPainted(false);

    }

    public WaypointIcon(Package p) {
        this();
    }

}
