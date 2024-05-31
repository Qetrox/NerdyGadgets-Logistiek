package nl.nerdygadgets.logistiek.util;

import javax.swing.*;

/**
 * Extend deze inplaats van JFrame zodat de instellingen van hier altijd worden gebruikt.
 */
public class DefaultJFrame extends JFrame {

    public DefaultJFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(ColorUtil.BACKGROUND_COLOR);
    }
    public DefaultJFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(ColorUtil.BACKGROUND_COLOR);
    }
}