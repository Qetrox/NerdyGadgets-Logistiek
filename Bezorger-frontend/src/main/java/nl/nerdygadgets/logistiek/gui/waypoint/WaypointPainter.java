package nl.nerdygadgets.logistiek.gui.waypoint;

import org.jxmapviewer.JXMapViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WaypointPainter extends org.jxmapviewer.viewer.WaypointPainter<Waypoint> {

    final BufferedImage pinImage = ImageIO.read(new File("src/main/resources/images/icon/pin.png"));

    public WaypointPainter() throws IOException {
    }

    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
        for(Waypoint waypoint : getWaypoints()) {
            Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());
            Rectangle rectangle = map.getViewportBounds();
            int x = (int) (point.getX() - rectangle.getX());
            int y = (int) (point.getY() - rectangle.getY());

            JButton cmd = waypoint.getButton();
            cmd.setLocation(x + 12 - cmd.getWidth(), y - cmd.getHeight());

            //System.out.println("Waypoint painted");
            //System.out.println("x: " + x + " y: " + y);



        }
    }

}
