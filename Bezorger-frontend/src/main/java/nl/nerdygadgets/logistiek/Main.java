package nl.nerdygadgets.logistiek;

import nl.nerdygadgets.logistiek.gui.DeliverGUI;
import nl.nerdygadgets.logistiek.gui.LoadGUI;
import nl.nerdygadgets.logistiek.gui.LoginGUI;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;



import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //LoadGUI frame = new LoadGUI();
        //new LoginGUI(frame);

        new DeliverGUI();


        //Test some connections and stuff then close loadgui and open login gui

    }
}