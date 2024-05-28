package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.util.web.AuthHelper;
import nl.nerdygadgets.util.delivery.DeliveryRoutes;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.util.Objects;

public class RouteHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }

        try {

            WebHelper.WebDelivery route = DeliveryRoutes.getRoute();
            if(route == null) {
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
                return;
            }

            WebHelper.WebToken token = AuthHelper.getToken(WebHelper.queryToMap(exchange.getRequestURI().getQuery()).get("token"));

            assert token != null;
            route.driverId = token.id;

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
