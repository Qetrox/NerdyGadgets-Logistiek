package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.web.AuthHelper;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // get query parameters
        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());
        try {
            String username = params.get("username");
            String password = params.get("password");

            if(username == null || password == null) {
                exchange.sendResponseHeaders(400, 0);
                exchange.getResponseBody().close();
                return;
            }

            WebHelper.WebToken token = tryLogin(username, password);
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
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }
    }

    private static WebHelper.WebToken tryLogin(String username, String password) {
        DatabaseConnector conn = Main.getDatabaseConnection();
        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = null;
        try {
            connection = conn.getConnection();
            stmt = connection.createStatement();
            rs = conn.query("SELECT * FROM employee WHERE username = '" + username + "' AND password = '" + password + "'");
            if (rs.next()) {
                // create a token
                WebHelper.WebToken token = AuthHelper.generateToken(rs.getInt("employeeId"), rs.getInt("isManager") == 1);
                DatabaseConnector.CloseVars(stmt, rs, connection);
                return token;
            }

            DatabaseConnector.CloseVars(stmt, rs, connection);
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
