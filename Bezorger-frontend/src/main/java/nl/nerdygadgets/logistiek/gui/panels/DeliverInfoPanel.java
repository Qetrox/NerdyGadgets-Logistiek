package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.delivery.Package;
import nl.nerdygadgets.logistiek.gui.modals.SupportModal;
import nl.nerdygadgets.logistiek.gui.waypoint.Waypoint;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.web.WebHelper;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DeliverInfoPanel extends JPanel {

    private static final int buttonWidth = 160;
    private static final int buttonHeight = 50;

    private static long currentDeliveryId = CacheManager.getCurrentDelivery().id;

    private JPanel smallerButton(JButton button) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(button);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    public DeliverInfoPanel(MapPanel mapViewer) throws IOException {

        setLayout(new BorderLayout());
        setBackground(ColorUtil.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(400, 800));

        JLabel title = new JLabel("Order #" + CacheManager.getCurrentPackage().id);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(ColorUtil.TEXT_COLOR);

        JLabel address = new JLabel(CacheManager.getCurrentPackage().address);
        address.setFont(new Font("Arial", Font.PLAIN, 18));
        address.setForeground(ColorUtil.TEXT_COLOR);

        JLabel name = new JLabel(CacheManager.getCurrentPackage().name);
        name.setFont(new Font("Arial", Font.PLAIN, 18));
        name.setForeground(ColorUtil.TEXT_COLOR);

        //
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(false);
        closeButton.setFont(new Font("", Font.PLAIN, 20));
        closeButton.setFont(closeButton.getFont().deriveFont(Font.BOLD));
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        });
        //

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));
        infoPanel.add(title);
        infoPanel.add(address);
        infoPanel.add(name);

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        closePanel.add(closeButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(closePanel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 140));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.setBackground(ColorUtil.BACKGROUND_COLOR);

        JButton complete = new JButton("Complete Order");
        complete.setBackground(ColorUtil.TEXT_COLOR);
        complete.setForeground(ColorUtil.BACKGROUND_COLOR);
        complete.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        complete.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #"+ CacheManager.getCurrentPackage().id + " completed", "Order Completed", JOptionPane.INFORMATION_MESSAGE);
            try {
                CacheManager.deliverPackage();
                resetWaypoints(mapViewer, new GeoPosition(CacheManager.getCurrentDelivery().startLatitude, CacheManager.getCurrentDelivery().startLongitude), new GeoPosition(CacheManager.getCurrentDelivery().endLatitude, CacheManager.getCurrentDelivery().endLongitude));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
            title.setText("Order #" + CacheManager.getCurrentPackage().id);
            address.setText(CacheManager.getCurrentPackage().address);
            name.setText(CacheManager.getCurrentPackage().name);
        });
        buttonPanel.add(smallerButton(complete));

        JButton notHome = new JButton("Not Home");
        notHome.setBackground(ColorUtil.TEXT_COLOR);
        notHome.setForeground(ColorUtil.BACKGROUND_COLOR);
        notHome.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        notHome.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #"+ CacheManager.getCurrentPackage().id + " is marked as not home", "Not Home", JOptionPane.INFORMATION_MESSAGE);
            try {
                CacheManager.notHomePackage();
                resetWaypoints(mapViewer, new GeoPosition(CacheManager.getCurrentDelivery().startLatitude, CacheManager.getCurrentDelivery().startLongitude), new GeoPosition(CacheManager.getCurrentDelivery().endLatitude, CacheManager.getCurrentDelivery().endLongitude));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
            title.setText("Order #" + CacheManager.getCurrentPackage().id);
            address.setText(CacheManager.getCurrentPackage().address);
            name.setText(CacheManager.getCurrentPackage().name);
        });
        buttonPanel.add(smallerButton(notHome));

        JButton cancel = new JButton("Cancel Order");
        cancel.setBackground(ColorUtil.TEXT_COLOR);
        cancel.setForeground(ColorUtil.BACKGROUND_COLOR);
        cancel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        cancel.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #"+ CacheManager.getCurrentPackage().id + " is cancelled", "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
            try {
                CacheManager.skipPackage();
                resetWaypoints(mapViewer, new GeoPosition(CacheManager.getCurrentDelivery().startLatitude, CacheManager.getCurrentDelivery().startLongitude), new GeoPosition(CacheManager.getCurrentDelivery().endLatitude, CacheManager.getCurrentDelivery().endLongitude));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            repaint();
            title.setText("Order #" + CacheManager.getCurrentPackage().id);
            address.setText(CacheManager.getCurrentPackage().address);
            name.setText(CacheManager.getCurrentPackage().name);
        });
        buttonPanel.add(smallerButton(cancel));

        JButton support = new JButton("Support");
        support.setBackground(ColorUtil.TEXT_COLOR);
        support.setForeground(ColorUtil.BACKGROUND_COLOR);
        support.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        support.addActionListener(e -> {
            new SupportModal();
            repaint();
            title.setText("Order #" + CacheManager.getCurrentPackage().id);
            address.setText(CacheManager.getCurrentPackage().address);
            name.setText(CacheManager.getCurrentPackage().name);
        });
        buttonPanel.add(smallerButton(support));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void resetWaypoints(MapPanel panel, GeoPosition start, GeoPosition end) throws IOException {
        /*panel.clearWaypoints(); // Clear the old waypoints

        // Add new waypoints
        panel.addWaypoint(new Waypoint(start));
        for (WebHelper.WebPackage p : CacheManager.getCurrentDelivery().packages) {
            panel.addWaypoint(new Waypoint(new GeoPosition(p.latitude, p.longitude), new Package(p.address, p.name, 1, 1, 1, 1, 1)));
        }
        panel.addWaypoint(new Waypoint(end));*/
    }
}
