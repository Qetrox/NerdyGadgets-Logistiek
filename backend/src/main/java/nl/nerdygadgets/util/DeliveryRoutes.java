package nl.nerdygadgets.util;

import nl.nerdygadgets.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Deze klasse zorgt voor alles dat te maken heeft met nieuwe routes te maken en op te slaan.
 */
public class DeliveryRoutes {

    private static ArrayList<WebHelper.WebDelivery> routes = new ArrayList<>();

    private static void handleRoutesFromDatabase() throws SQLException {

        DatabaseConnector conn = Main.getDatabaseConnection();
        Connection connection = conn.getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = conn.query("SELECT * FROM delivery_route");
        while (rs.next()) {


        }
    }
}
