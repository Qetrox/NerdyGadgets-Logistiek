package nl.nerdygadgets.webserver.handlers.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class BestellingenHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleManagerRequirement(exchange)) {
            return;
        }

        Map<Integer, WebHelper.WebOrder> orders = new HashMap<>();

        DatabaseConnector conn = Main.getDatabaseConnection();
        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = conn.getConnection();
            stmt = connection.createStatement();
            rs = conn.query("SELECT `order`.id, order_date, email, first_name, surname, street_name, apartment_nr, postal_code, city FROM `order` JOIN user on user_id = user.id");
            while (rs.next()) {
                WebHelper.WebOrder order = new WebHelper.WebOrder(
                        rs.getInt("id"),
                        rs.getString("order_date"),
                        rs.getString("first_name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("street_name"),
                        rs.getString("apartment_nr"),
                        rs.getString("postal_code"),
                        rs.getString("city")
                );
                orders.put(order.id, order);
            }

            DatabaseConnector.CloseVars(stmt, rs, connection);

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            Type mapType = new TypeToken<Map<Integer, WebHelper.WebOrder>>() {}.getType();
            String res = gson.toJson(orders, mapType);

            // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
            exchange.sendResponseHeaders(200,res.length());
            exchange.getResponseBody().write(res.getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
