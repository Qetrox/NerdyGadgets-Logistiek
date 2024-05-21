package nl.nerdygadgets.webserver.handlers;

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
public class TestHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {

        DatabaseConnector conn = Main.getDatabaseConnection();

        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = null;

        try {
            connection = conn.getConnection();
            stmt = connection.createStatement();

            // Use the database
            conn.execute("USE u127250p176374_nerdygadgets");

            // Query the database
            rs = conn.query("SELECT * FROM brand");

            StringBuilder res = new StringBuilder();

            while (rs.next()) {
                res.append(rs.getString("brandName")).append(" ");
            }



            // Code 200 om aan te geven dat het OK is, en de lengte van wat we gaan terugsturen
            exchange.sendResponseHeaders(200, res.length());

            // Stuur alle data
            exchange.getResponseBody().write(res.toString().getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnector.CloseVars(stmt, rs, connection);
        }
    }
}
