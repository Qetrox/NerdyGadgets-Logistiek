package nl.nerdygadgets;

import nl.nerdygadgets.webserver.WebServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // TODO: Error handler, als er nu een URL word gevraagd die niet bestaat crashed het.
        WebServer s = new WebServer();
        s.addRoutes();
        s.start();

    }
}