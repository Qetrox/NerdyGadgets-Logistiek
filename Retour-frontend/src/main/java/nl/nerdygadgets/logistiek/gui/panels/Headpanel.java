package nl.nerdygadgets.logistiek.gui.panels;

import javax.swing.*;
import java.awt.*;

public class Headpanel extends JPanel {

    public Headpanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton homeButton = createStyledButton("Home");
        JButton returnsButton = createStyledButton("Returns");
        JButton reportsButton = createStyledButton("Reports");
        JButton settingsButton = createStyledButton("Settings");
        JButton supportButton = createStyledButton("Get Support");
        JButton closeButton = new JButton("X");

        add(homeButton);
        add(returnsButton);
        add(reportsButton);
        add(settingsButton);
        add(supportButton);

        add(Box.createHorizontalGlue());
        add(closeButton);

        setBorder(BorderFactory.createEmptyBorder());

        // Style van de Close button alleen
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
    }

    // Style van alle buttons excl. close
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("", Font.PLAIN, 20));
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        return button;
    }

    @Override
    public Dimension getPreferredSize() {
        int currentWidth = super.getPreferredSize().width;
        return new Dimension(currentWidth, 60);
    }
}
