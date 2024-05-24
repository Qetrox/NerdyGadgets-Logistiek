package nl.nerdygadgets.logistiek;

import nl.nerdygadgets.logistiek.gui.RetourLoadGUI;
import nl.nerdygadgets.logistiek.gui.RetourLoginGUI;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        RetourLoadGUI frame = new RetourLoadGUI();
        new RetourLoginGUI(frame);

    }
}