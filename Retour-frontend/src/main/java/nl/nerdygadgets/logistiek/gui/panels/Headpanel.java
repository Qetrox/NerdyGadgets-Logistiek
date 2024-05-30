package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.gui.modals.SupportModal;

import javax.swing.*;
import java.awt.*;

public class Headpanel extends JPanel {

    public Headpanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));


        add(Box.createHorizontalStrut(10));
        JButton homeButton = createStyledButton("Home");
        add(homeButton);

        add(Box.createHorizontalStrut(10));
        JButton returnsButton = createStyledButton("Returns");
        add(returnsButton);

        add(Box.createHorizontalStrut(10));
        JButton reportsButton = createStyledButton("Reports");
        add(reportsButton);

        add(Box.createHorizontalStrut(10));
        JButton settingsButton = createStyledButton("Settings");
        add(settingsButton);

        add(Box.createHorizontalStrut(10));
        JButton supportButton = createStyledButton("Get Support");
        add(supportButton);

        supportButton.addActionListener(e -> {
            new SupportModal();
        });

        /* add(Box.createHorizontalGlue());
        JButton closeButton = createStyledButton("Close");
        add(closeButton);
         */

        setBorder(BorderFactory.createEmptyBorder());



        // Style alleen close knop
        /* closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(false);
        closeButton.setFont(new Font("", Font.PLAIN, 20));
        closeButton.setFont(closeButton.getFont().deriveFont(Font.BOLD));
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();
        }
        ); */
    }

    // Style alle knoppen
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.BLACK);
        button.setMargin(new Insets(10, 10, 10, 10));
        return button;
    }

    @Override
    public Dimension getPreferredSize() {
        int currentWidth = super.getPreferredSize().width;
        return new Dimension(currentWidth, 60);
    }
}
