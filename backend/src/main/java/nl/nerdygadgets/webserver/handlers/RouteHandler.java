package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.AuthHelper;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.DeliveryRoutes;
import nl.nerdygadgets.util.WebHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class RouteHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }

        try {

            WebHelper.WebDelivery route = DeliveryRoutes.getRoute();

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            String res = gson.toJson(route);

            exchange.sendResponseHeaders(200, res.length());
            exchange.getResponseBody().write(res.getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();

            System.out.println("RouteHandler");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
