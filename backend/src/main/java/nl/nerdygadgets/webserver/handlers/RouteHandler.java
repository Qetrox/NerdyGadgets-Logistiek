package nl.nerdygadgets.webserver.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.AuthHelper;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.WebHelper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class RouteHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
