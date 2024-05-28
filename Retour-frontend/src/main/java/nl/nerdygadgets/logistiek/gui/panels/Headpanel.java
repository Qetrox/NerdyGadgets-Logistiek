package nl.nerdygadgets.logistiek.gui.panels;

import javax.swing.*;
import java.awt.*;

public class Headpanel extends JPanel {

    public Headpanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton homeButton = new JButton("Home");
        JButton returnsButton = new JButton("Returns");
        JButton reportsButton = new JButton("Reports");
        JButton settingsButton = new JButton("Settings");
        JButton supportButton = new JButton("Get Support");

        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.BLACK);
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

        add(homeButton);
        add(returnsButton);
        add(reportsButton);
        add(settingsButton);
        add(supportButton);

        add(Box.createHorizontalGlue());
        add(closeButton);

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    public Dimension getPreferredSize() {
        int currentWidth = super.getPreferredSize().width;
        return new Dimension(currentWidth, 60);
    }
}
