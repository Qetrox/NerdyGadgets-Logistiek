package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.Headpanel;
import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class RetourGUI extends DefaultJFrame {

    public RetourGUI() throws IOException {
        super("Retour");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        RetourInfoPanel retourInfoPanel = new RetourInfoPanel();
        mainPanel.add(retourInfoPanel, BorderLayout.EAST);

        RMATable rmaTable = new RMATable(retourInfoPanel);
        mainPanel.add(rmaTable, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        Headpanel headpanel = new Headpanel();
        getContentPane().add(headpanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RetourGUI frame = new RetourGUI();
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
