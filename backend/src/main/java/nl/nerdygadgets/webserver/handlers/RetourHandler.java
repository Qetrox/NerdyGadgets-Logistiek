package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class RetourHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        /*if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }*/

        System.out.println("RetourHandler");

        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());

        String retourOrderId = null;
        String retourResolution = null;
        String retourReason = null;
        String retourHandled = null;

        if(params != null) {

            try {
                retourOrderId = params.get("retourOrderId");
                retourResolution = params.get("retourResolution");
                retourReason = params.get("retourReason");
                retourHandled = params.get("retourHandled");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("retourOrderId: " + retourOrderId);
            System.out.println("retourResolution: " + retourResolution);
            System.out.println("retourReason: " + retourReason);
            System.out.println("retourHandled: " + retourHandled);

        }

        if(retourOrderId == null || retourResolution == null || retourReason == null || retourHandled == null) {

            // just send info

            System.out.println("RetourHandler: just send info");

            DatabaseConnector conn = new DatabaseConnector();
            ResultSet rs = null;

            try {
                rs = conn.query("SELECT retourID, ResolutionType, Handled, ReturnReason, orderId, Created, CONCAT(first_name, ' ', surname) as name FROM u127250p176374_nerdygadgets.retour LEFT JOIN `order` as b ON b.id = orderId LEFT JOIN user on b.user_id = user.id");

                ArrayList<WebHelper.WebRetour> retours = new ArrayList<>();

                while(rs.next()) {
                    WebHelper.WebRetour retour = new WebHelper.WebRetour(
                            rs.getInt("retourID"),
                            rs.getString("ResolutionType"),
                            rs.getBoolean("Handled"),
                            rs.getString("ReturnReason"),
                            rs.getInt("orderId"),
                            rs.getLong("Created"),
                            rs.getString("name")
                    );
                    retours.add(retour);
                }

                DatabaseConnector.CloseVars(null, rs, null);

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                String res = gson.toJson(retours, ArrayList.class);

                exchange.sendResponseHeaders(200, res.length());
                exchange.getResponseBody().write(res.getBytes());
                exchange.getResponseBody().flush();
                exchange.getResponseBody().close();


            } catch (SQLException e) {
                e.printStackTrace();
            }


        } else {

            //update retour
            DatabaseConnector conn = new DatabaseConnector();
            PreparedStatement stmt = null;
            Connection connection = null;

            try {
                connection = conn.getConnection();
                stmt = connection.prepareStatement("INSERT INTO retour (ResolutionType, Handled, ReturnReason, orderId, Created) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE ResolutionType = ?, Handled = ?, ReturnReason = ?");

                stmt.setString(1, retourResolution);
                stmt.setBoolean(2, Boolean.parseBoolean(retourHandled));
                stmt.setString(3, retourReason);
                stmt.setInt(4, Integer.parseInt(retourOrderId));
                stmt.setLong(5, System.currentTimeMillis());
                stmt.setString(6, retourResolution);
                stmt.setBoolean(7, Boolean.parseBoolean(retourHandled));
                stmt.setString(8, retourReason);

                stmt.executeUpdate();

                stmt.close();
                connection.close();

                DatabaseConnector.CloseVars(stmt, null, connection);

                conn = new DatabaseConnector();
                ResultSet rs;

                try {
                    rs = conn.query("SELECT retourID, ResolutionType, Handled, ReturnReason, orderId, Created, CONCAT(first_name, ' ', surname) as name FROM u127250p176374_nerdygadgets.retour LEFT JOIN `order` as b ON b.id = orderId LEFT JOIN user on b.user_id = user.id");

                    ArrayList<WebHelper.WebRetour> retours = new ArrayList<>();

                    while(rs.next()) {
                        WebHelper.WebRetour retour = new WebHelper.WebRetour(
                                rs.getInt("retourID"),
                                rs.getString("ResolutionType"),
                                rs.getBoolean("Handled"),
                                rs.getString("ReturnReason"),
                                rs.getInt("orderId"),
                                rs.getLong("Created"),
                                rs.getString("name")
                        );
                        retours.add(retour);
                    }

                    DatabaseConnector.CloseVars(null, rs, null);

                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    Gson gson = builder.create();

                    String res = gson.toJson(retours, ArrayList.class);

                    exchange.sendResponseHeaders(200, res.length());
                    exchange.getResponseBody().write(res.getBytes());
                    exchange.getResponseBody().flush();
                    exchange.getResponseBody().close();


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
