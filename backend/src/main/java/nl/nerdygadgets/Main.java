package nl.nerdygadgets;

import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.webserver.WebServer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;


public class Main {

    private static DatabaseConnector conn;

    public static void main(String[] args) throws IOException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("JDBC Driver not found!");
            System.out.println(e.toString());
        }

        // TODO: Error handler, als er nu een URL word gevraagd die niet bestaat crashed het.
        WebServer s = new WebServer();
        s.addRoutes();
        s.start();

        /* Database stuff */
        conn = new DatabaseConnector();
        conn.execute("USE u127250p176374_nerdygadgets");

    }

    public static DatabaseConnector getDatabaseConnection() {
        return conn;
    }
}
