package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.EmailUtil;
import nl.nerdygadgets.util.web.AuthHelper;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    private static Map<String, Integer> managerTempCodes = new HashMap<>();
    private static Map<String, Long> managerTempCodeTime = new HashMap<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // get query parameters
        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());
        try {
            String username = params.get("username");
            String password = params.get("password");

            Boolean wantManager = Boolean.parseBoolean(params.get("manager"));

            if(wantManager) {

                if(params.get("code") != null) {
                    int code;
                    try {
                        code = Integer.parseInt(params.get("code"));
                    } catch (NumberFormatException e) {
                        exchange.sendResponseHeaders(400, 0);
                        exchange.getResponseBody().close();
                        return;
                    }

                    if (code != managerTempCodes.get(username)) {
                        exchange.sendResponseHeaders(403, 0);
                        exchange.getResponseBody().close();
                        return;
                    }

                    if (System.currentTimeMillis() - managerTempCodeTime.get(username) > 60000) {
                        exchange.sendResponseHeaders(403, 0);
                        exchange.getResponseBody().close();
                        return;
                    }

                    managerTempCodes.remove(username);
                    managerTempCodeTime.remove(username);

                    WebHelper.WebToken token = tryLogin(username, password, true);
                    if(token == null) {
                        exchange.sendResponseHeaders(403, 0);
                        exchange.getResponseBody().close();
                        return;
                    }

                    System.out.println("Logged in: " + token.id + " as manager: " + token.isManager);

                    try {

                        GsonBuilder builder = new GsonBuilder();
                        builder.setPrettyPrinting();
                        Gson gson = builder.create();

                        String res = gson.toJson(token, WebHelper.WebToken.class);


                        // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
                        exchange.sendResponseHeaders(200, res.length());

                        // Stuur alle data
                        exchange.getResponseBody().write(res.toString().getBytes());
                        exchange.getResponseBody().flush();
                        exchange.getResponseBody().close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {

                    System.out.println("Manager login code requested");

                    // check if the user is a manager
                    String email = tryManagerEmail(username, password);
                    if(email == null) {
                        exchange.sendResponseHeaders(403, 0);
                        exchange.getResponseBody().close();
                        return;
                    }

                    System.out.println("Manager email: " + email);

                    int code = (int) Math.round(Math.random() * 1000000);

                    managerTempCodes.put(username, code);
                    managerTempCodeTime.put(username, System.currentTimeMillis());

                    System.out.println("Manager code: " + code);

                    // send email
                    EmailUtil.sendEmail(email, "Nerdy Gadgets Manager Login Code", "Je login code is: " + code);

                    System.out.println("Manager login code sent");

                    exchange.sendResponseHeaders(200, 0);
                    exchange.getResponseBody().close();

                    return;
                }
                return;
            }

            if(username == null || password == null) {
                exchange.sendResponseHeaders(400, 0);
                exchange.getResponseBody().close();
                return;
            }

            WebHelper.WebToken token = tryLogin(username, password, false);
            if(token == null) {
                exchange.sendResponseHeaders(403, 0);
                exchange.getResponseBody().close();
                return;
            }

            System.out.println("Logged in: " + token.id + " as manager: " + token.isManager);

            try {

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                String res = gson.toJson(token, WebHelper.WebToken.class);


                // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
                exchange.sendResponseHeaders(200, res.length());

                // Stuur alle data
                exchange.getResponseBody().write(res.toString().getBytes());
                exchange.getResponseBody().flush();
                exchange.getResponseBody().close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }
    }

    private static String tryManagerEmail(String username, String password) {
        DatabaseConnector conn = Main.getDatabaseConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = conn.getConnection();
            stmt = connection.prepareStatement("SELECT * FROM employee WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                if(!rs.getBoolean("isManager")) {
                    DatabaseConnector.CloseVars(stmt, rs, connection);
                    return null;
                }
                String email = rs.getString("email");
                DatabaseConnector.CloseVars(stmt, rs, connection);
                return email;
            }
            DatabaseConnector.CloseVars(stmt, rs, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static WebHelper.WebToken tryLogin(String username, String password, boolean isManager) {
        DatabaseConnector conn = Main.getDatabaseConnection();
        ResultSet rs = null;
        PreparedStatement stmt = null;
        Connection connection = null;
        try {
            connection = conn.getConnection();
            stmt = connection.prepareStatement("SELECT * FROM employee WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                WebHelper.WebToken token = AuthHelper.generateToken(rs.getInt("employeeId"), isManager);
                DatabaseConnector.CloseVars(stmt, rs, connection);
                return token;
            }
            DatabaseConnector.CloseVars(stmt, rs, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
