package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.gui.modals.SupportModal;
import nl.nerdygadgets.logistiek.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RetourInfoPanel extends JPanel {

    private static final int buttonWidth = 160;
    private static final int buttonHeight = 50;

    private JPanel smallerButton(JButton button) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(button);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    public RetourInfoPanel() throws IOException {

        setLayout(new BorderLayout());
        setBackground(ColorUtil.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(400, 800));
        JLabel label = new JLabel("Retour Informatie");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JLabel title = new JLabel("Order #12345");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(ColorUtil.TEXT_COLOR);

        JLabel address = new JLabel("Idk laan 2");
        address.setFont(new Font("Arial", Font.PLAIN, 18));
        address.setForeground(ColorUtil.TEXT_COLOR);

        JLabel name = new JLabel("Persoon Achternaam");
        name.setFont(new Font("Arial", Font.PLAIN, 18));
        name.setForeground(ColorUtil.TEXT_COLOR);

        /*
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
        */

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));
        infoPanel.add(title);
        infoPanel.add(address);
        infoPanel.add(name);

        /*JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        closePanel.add(closeButton);
         */

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        topPanel.add(infoPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 140));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(ColorUtil.BACKGROUND_COLOR);

        JButton resolve = new JButton("Resolve");
        resolve.setBackground(ColorUtil.TEXT_COLOR);
        resolve.setForeground(ColorUtil.BACKGROUND_COLOR);
        resolve.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        buttonPanel.add(smallerButton(resolve));

        JButton support = new JButton("Support");
        support.setBackground(ColorUtil.TEXT_COLOR);
        support.setForeground(ColorUtil.BACKGROUND_COLOR);
        support.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        support.addActionListener(e -> {
            new SupportModal();
        });
        buttonPanel.add(smallerButton(support));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
