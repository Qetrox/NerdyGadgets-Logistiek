package nl.nerdygadgets.logistiek;

import nl.nerdygadgets.logistiek.gui.RetourLoginGUI;
import nl.nerdygadgets.logistiek.gui.panels.LoadPanel;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        LoadPanel loadPanel = null;
        try {
            loadPanel = new LoadPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.add(loadPanel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new RetourLoginGUI(frame, loadPanel);
    }
}