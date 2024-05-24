package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Een voorbeeld handler
 */
public class MainHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        DatabaseConnector conn = Main.getDatabaseConnection();

        String res = "{\"success\":true}";

            // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
            exchange.sendResponseHeaders(200, res.length());

            // Stuur alle data
            exchange.getResponseBody().write(res.toString().getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();
    }
}
