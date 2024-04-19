package nl.nerdygadgets.webserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Een voorbeeld handler
 */
public class TestHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Wat we terug sturen
        String res = "NerdyGadgets is cool";

        // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
        exchange.sendResponseHeaders(200, res.length());

        // Stuur alle data
        exchange.getResponseBody().write(res.getBytes());
        exchange.getResponseBody().flush();
        exchange.getResponseBody().close();
    }
}
