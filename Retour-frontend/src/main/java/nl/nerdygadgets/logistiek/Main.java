package nl.nerdygadgets.logistiek;

import nl.nerdygadgets.logistiek.gui.RetourLoadGUI;
import nl.nerdygadgets.logistiek.gui.RetourLoginGUI;
//import nl.nerdygadgets.logistiek.gui.RetourGUI;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //RetourGUI frame = new RetourGUI();
        RetourLoadGUI frame = new RetourLoadGUI();
        new RetourLoginGUI(frame);

    }
}