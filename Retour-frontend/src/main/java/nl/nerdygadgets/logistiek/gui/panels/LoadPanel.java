package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.util.ColorUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadPanel extends JPanel {

    final BufferedImage image = ImageIO.read(new File("src/main/resources/images/logo.png"));

    public LoadPanel() throws IOException {
        setLayout(new GridLayout(1, 1));
        setBackground(ColorUtil.BACKGROUND_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Inter", Font.PLAIN, 20));

        int x = (getWidth() - image.getWidth()) / 2;
        int y = ((getHeight() - image.getHeight()) / 2) - 20;

        int textX = (getWidth() - g.getFontMetrics().stringWidth("Loading...")) / 2;

        g.drawImage(image, x, y, null);
        g.setColor(ColorUtil.TEXT_COLOR);
        g.drawString("Loading...", textX, y + image.getHeight() + 40);
    }



}
