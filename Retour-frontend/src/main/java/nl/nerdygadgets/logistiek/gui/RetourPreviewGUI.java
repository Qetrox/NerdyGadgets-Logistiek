package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RetourPreviewGUI extends DefaultJFrame {

        private JPanel smallerButton(JButton button) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(1200, 200));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(button);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    public RetourPreviewGUI() throws IOException {
        super("Retour");
        setResizable(false);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(12000, 8000);

        }

        setLayout(new BorderLayout(0,0));

        JLabel label = new JLabel("Retour");
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
                new RetourGUI();
                dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        getContentPane().add(label, BorderLayout.NORTH);
        getContentPane().add(smallerButton(button), BorderLayout.SOUTH);

        setVisible(true);
    }

}
