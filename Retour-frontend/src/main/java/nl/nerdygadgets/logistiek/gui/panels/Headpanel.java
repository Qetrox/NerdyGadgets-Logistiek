package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.gui.HomeScreen;
import nl.nerdygadgets.logistiek.gui.RetourGUI;
import nl.nerdygadgets.logistiek.gui.modals.SupportModal;

import javax.swing.*;
import java.awt.*;

public class Headpanel extends JPanel {

    public RetourGUI parentFrame;

    public Headpanel(RetourGUI parentFrame) {
        this.parentFrame = parentFrame;
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

        homeButton.addActionListener(e -> {
            new HomeScreen().setVisible(true);
            parentFrame.close();
        });

        supportButton.addActionListener(e -> {
            new SupportModal();
        });

        setBorder(BorderFactory.createEmptyBorder());
    }

    // Style all buttons
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
