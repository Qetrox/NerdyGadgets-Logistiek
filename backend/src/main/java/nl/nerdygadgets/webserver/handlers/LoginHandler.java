package nl.nerdygadgets.webserver.handlers;

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
import java.util.Map;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // get query parameters
        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());

        for(String key : params.keySet()) {
            System.out.println(key + " = " + params.get(key));
        }

        String username = exchange.getRequestHeaders().getFirst("username");
        String password = exchange.getRequestHeaders().getFirst("password");

        exchange.sendResponseHeaders(400, 0);
        exchange.getResponseBody().close();
        return;

        /*if(username == null || password == null) {
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();
            return;
        }

        String token = tryLogin(username, password);
        if(token == null) {
            exchange.sendResponseHeaders(401, 0);
            exchange.getResponseBody().close();
            return;
        }

        try {
            StringBuilder res = new StringBuilder();
            res.append(token);

            // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
            exchange.sendResponseHeaders(200, res.length());

            // Stuur alle data
            exchange.getResponseBody().write(res.toString().getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static String tryLogin(String username, String password) {
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
                DatabaseConnector.CloseVars(stmt, rs, connection);
                return AuthHelper.generateToken(rs.getInt("id"));
            }

            DatabaseConnector.CloseVars(stmt, rs, connection);
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
