package nl.nerdygadgets.webserver;

import com.sun.net.httpserver.HttpServer;
import nl.nerdygadgets.util.Log;
import nl.nerdygadgets.webserver.handlers.*;
import nl.nerdygadgets.webserver.handlers.manager.BestellingenHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

public class WebServer {

    public static int startTimestamp;

    private static final Logger log = Log.logger;
    private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private HttpServer server;

    /**
     * Maak een HTTP server aan, vergeet niet de benodigde functies te callen. Anders werkt het niet.
     * @throws IOException Er kunnen dingen misgaan dus vergeet geen try-catch te gebruiken
     */
    public WebServer() throws IOException {
        startTimestamp = (int) (System.currentTimeMillis());
        this.server = HttpServer.create(new InetSocketAddress(8083), 0);
        this.server.setExecutor(threadPoolExecutor);
    }

    /**
     * Voeg de routes/urls toe waarop de webserver moet reageren en data versturen
     */
    public void addRoutes() {
        /* TODO: voeg hierzo nieuwe routes/urls toe met de benodigde handler */

        this.server.createContext("/", new MainHandler());
        this.server.createContext("/login", new LoginHandler());
        this.server.createContext("/manager/bestellingen", new BestellingenHandler());
        this.server.createContext("/getroute", new RouteHandler());
        this.server.createContext("/updatepackage", new UpdatePackageHandler());
        this.server.createContext("/retour", new RetourHandler());
    }

    /**
     * Start de HTTP server
     */
    public void start() {
        this.server.start();
        log.info("Starting HTTP server on port 8080");
    }

}
