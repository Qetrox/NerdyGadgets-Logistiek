package nl.nerdygadgets.logistiek.gui.panels;

import com.graphhopper.util.shapes.GHPoint3D;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.gui.waypoint.WaypointPainter;
import nl.nerdygadgets.logistiek.routing.RoutingData;
import nl.nerdygadgets.logistiek.routing.RoutingService;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

public class MapPanel extends JXMapViewer {

    private Set<Waypoint> waypoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private boolean first = true;

    public MapPanel() throws IOException {

        setPreferredSize(new Dimension(800, 800));

        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo("", "https://tile.openstreetmap.de")));
        setAddressLocation(new GeoPosition(52.377011026016035, 5.1755491265934825));
        setZoom(7);

        WaypointPainter painter = new WaypointPainter();
        painter.setWaypoints(waypoints);

        setOverlayPainter(painter);


        MouseInputListener mm = new PanMouseInputListener(this);
        addMouseListener(mm);
        addMouseMotionListener(mm);
        addMouseWheelListener(new ZoomMouseWheelListenerCenter(this));


    }

    private void reRenderWaypoints() throws IOException {
        WaypointPainter painter = new WaypointPainter();
        painter.setWaypoints(waypoints);
        setOverlayPainter(painter);
        for (Waypoint waypoint : waypoints) {
            add(waypoint.getButton());
        }

        if (waypoints.size() >= 2) {
            // Clear previous routing data
            routingData.clear();

            Waypoint previousWaypoint = null;
            for (Waypoint currentWaypoint : waypoints) {
                if (previousWaypoint != null) {
                    GeoPosition startCoords = previousWaypoint.getCoordinates();
                    GeoPosition endCoords = currentWaypoint.getCoordinates();

                    // Compute the route for the current pair of waypoints
                    List<RoutingData> segmentData = RoutingService.getInstance().routing(
                            startCoords.getLatitude(), startCoords.getLongitude(),
                            endCoords.getLatitude(), endCoords.getLongitude());

                    // Add the computed route segment to the overall routing data
                    routingData.addAll(segmentData);
                }
                previousWaypoint = currentWaypoint;
            }

            setRoutingData(routingData);
        } else {
            routingData.clear();
        }
    }


    public void addWaypoint(Waypoint waypoint) throws IOException {

        Iterator<Waypoint> iterator = waypoints.iterator();
        while(iterator.hasNext()) {
            Waypoint w = iterator.next();
            if(w.getCoordinates().equals(waypoint.getCoordinates())) {
                iterator.remove();
            }
        }

        waypoints.add(waypoint);
        reRenderWaypoints();
    }

    public void removeWaypoint(Waypoint waypoint) throws IOException {
        waypoints.remove(waypoint);
        reRenderWaypoints();
    }

    public void clearWaypoints() throws IOException {
        waypoints = new HashSet<>();
        reRenderWaypoints();
    }

    public void setRoutingData(List<RoutingData> routingData) {
        this.routingData = routingData;
    }

    public List<RoutingData> getRoutingData() {
        return routingData;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(routingData != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Path2D path = new Path2D.Double();
            first = true;
            for(RoutingData data : routingData) {
                draw(path, data);
            }
            g2d.setColor(new Color(74, 23, 255, 80));
            g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.draw(path);
            g2d.dispose();
        }

    }

    private void draw(Path2D p2, RoutingData d) {
        d.getPointList().forEach(new Consumer<GHPoint3D>() {
            @Override
            public void accept(GHPoint3D t) {
                Point2D point = convertGeoPositionToPoint(new GeoPosition(t.getLat(), t.getLon()));
                if (first) {
                    first = false;
                    p2.moveTo(point.getX(), point.getY());
                } else {
                    p2.lineTo(point.getX(), point.getY());
                }
            }
        });
    }

}
