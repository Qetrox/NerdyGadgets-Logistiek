package nl.nerdygadgets.logistiek;

import nl.nerdygadgets.logistiek.gui.LoadGUI;
import nl.nerdygadgets.logistiek.gui.LoginGUI;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        LoadGUI frame = new LoadGUI();
        new LoginGUI(frame);

        //Test some connections and stuff then close loadgui and open login gui

    }
}