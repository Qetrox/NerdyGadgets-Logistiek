package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.panels.DeliverInfoPanel;
import nl.nerdygadgets.logistiek.gui.panels.MapPanel;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

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

        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.3843322878619, 5.137340734129197), new Package("Idk laan 2", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.377011026016035, 5.1755491265934825), new Package("Idk laan 1", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.37556412545035, 5.2157946499848915), new Package("Idk laan 3", "Persoon", 1, 1, 1, 1, 1)));
        mapViewer.addWaypoint(new Waypoint(new GeoPosition(52.50798283349655, 5.469818870113745), new Package("Idk laan 4", "Persoon", 1, 1, 1, 1, 1)));

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
