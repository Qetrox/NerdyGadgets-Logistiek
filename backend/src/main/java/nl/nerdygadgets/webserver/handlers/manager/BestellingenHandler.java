package nl.nerdygadgets.webserver.handlers.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.AuthHelper;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.WebHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BestellingenHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("BestellingenHandler");

        Map<Integer, WebHelper.WebOrder> orders = new HashMap<>();

        DatabaseConnector conn = Main.getDatabaseConnection();
        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = conn.getConnection();
            stmt = connection.createStatement();
            rs = conn.query("SELECT * FROM u127250p176374_nerdygadgets.order JOIN order_item ON id = order_id JOIN product ON productId = product_id LIMIT 50");
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int productId = rs.getInt("productId");
                String productName = rs.getString("productName");
                int productAmount = rs.getInt("quantity");

                WebHelper.WebProduct product = new WebHelper.WebProduct(productName, productAmount, productId);

                if (orders.containsKey(orderId)) {
                    orders.get(orderId).producten.add(product);
                } else {
                    WebHelper.WebOrder order = new WebHelper.WebOrder(orderId, new WebHelper.WebProduct[]{product});
                    orders.put(orderId, order);
                }
            }

            DatabaseConnector.CloseVars(stmt, rs, connection);

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            //didnt send any data?
            String res = gson.toJson(orders, WebHelper.WebOrder.class);

            // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
            exchange.sendResponseHeaders(200,0);
            exchange.getResponseBody().write(res.getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
