package nl.nerdygadgets.logistiek.gui;

import com.google.gson.GsonBuilder;
import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.panels.DeliverInfoPanel;
import nl.nerdygadgets.logistiek.gui.panels.MapPanel;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.web.HttpUtil;
import nl.nerdygadgets.logistiek.util.web.WebHelper;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class DeliverPreviewGUI extends DefaultJFrame {

    private JPanel smallerMapPanel(MapPanel mapViewer) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(1200, 100));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(mapViewer);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    private JPanel smallerButton(JButton button) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(1200, 200));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(button);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    public DeliverPreviewGUI() throws IOException {
        super("Logistiek");
        setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(12000, 8000);

        };

        MapPanel mapViewer = new MapPanel();
        mapViewer.setPreferredSize(new Dimension(900, 800));

        //Vraag route op aan server
        String response = HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/getroute?token=" + CacheManager.getToken().token));

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        WebHelper.WebDelivery route = builder.create().fromJson(response, WebHelper.WebDelivery.class);

        CacheManager.setCurrentDelivery(route);

        //zet start waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));


        for(WebHelper.WebPackage p : route.packages) {

            mapViewer.addWaypoint(new Waypoint(new GeoPosition(p.latitude, p.longitude), new Package(p.address, p.name, 1, 1, 1, 1, 1)));

        }

        //zet eind waypoint
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(route.startLatitude, route.startLongitude)));

        setLayout(new BorderLayout(0,0));

        JLabel label = new JLabel("Route (Order #1)");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(ColorUtil.TEXT_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JButton button = new JButton("Start");

        button.setBackground(ColorUtil.TEXT_COLOR);
        button.setForeground(ColorUtil.BACKGROUND_COLOR);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setPreferredSize(new Dimension(200, 100));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        button.addActionListener(e -> {
            try {
                new DeliverGUI();
                dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(smallerMapPanel(mapViewer), BorderLayout.CENTER);
        getContentPane().add(smallerButton(button), BorderLayout.SOUTH);


        setVisible(true);
    }

}
