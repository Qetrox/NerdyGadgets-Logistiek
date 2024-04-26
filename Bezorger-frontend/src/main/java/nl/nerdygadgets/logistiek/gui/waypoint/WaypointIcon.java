package nl.nerdygadgets.logistiek.gui.waypoint;

import nl.nerdygadgets.logistiek.delivery.Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WaypointIcon extends JButton {

    private boolean isPackage;
    private Package aPackage;
    private static final ImageIcon packageIcon = new ImageIcon("src/main/resources/images/icon/package.png");
    private static final ImageIcon pinIcon = new ImageIcon("src/main/resources/images/icon/pin.png");

    public WaypointIcon(boolean isPackage, Package aPackage) {
        this.isPackage = isPackage;

        setContentAreaFilled(false);

        if(isPackage) setIcon(packageIcon);
        else setIcon(pinIcon);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(24, 24));
        setBorderPainted(false);
        setFocusPainted(false);

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isPackage) {
                    System.out.println(aPackage);
                }
            }
        });

    }

    public WaypointIcon(Package p) {
        this(true, p);
    }
    public WaypointIcon() {
        this(false, null);
    }

}
