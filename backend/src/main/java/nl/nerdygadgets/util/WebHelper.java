package nl.nerdygadgets.util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebHelper {

    /**
     * * Hulp functie voor data opvragen uit requests
     * @param query De query string
     * @return Een map met de data
     */
    public static Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    /**
     * Zorgt ervoor dat er een geldige token in de query string zit, indien niet dan wordt er een 403 gestuurd
     * @param exchange De exchange
     * @return Of de token geldig is
     */
    public static boolean handleTokenRequirement(HttpExchange exchange) {
        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        if(params == null) {

            try {
                exchange.sendResponseHeaders(400, 0);
                exchange.getResponseBody().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
        if(params.containsKey("token")) {
            String token = params.get("token");
            if(AuthHelper.checkToken(AuthHelper.getToken(token))) {
                return true;
            } else {

                try {
                    exchange.sendResponseHeaders(403, 0);
                    exchange.getResponseBody().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            exchange.sendResponseHeaders(403, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean handleManagerRequirement(HttpExchange exchange) {
        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
        if(params == null) {

            try {
                exchange.sendResponseHeaders(400, 0);
                exchange.getResponseBody().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        if(params.containsKey("token")) {
            String token = params.get("token");
            if(AuthHelper.checkManager(AuthHelper.getToken(token))) {
                return true;
            } else {

                    try {
                        exchange.sendResponseHeaders(403, 0);
                        exchange.getResponseBody().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }

        try {
            exchange.sendResponseHeaders(403, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static class WebDelivery {
        public int id;
        public int driverId;
        public ArrayList<WebPackage> packages= new ArrayList<>();
        public int startLatitude;
        public int startLongitude;
        public int endLatitude;
        public int endLongitude;

        public WebDelivery(int id, int driverId, int startLatitude, int startLongitude, int endLatitude, int endLongitude) {
            this.id = id;
            this.driverId = driverId;
            this.startLatitude = startLatitude;
            this.startLongitude = startLongitude;
            this.endLatitude = endLatitude;
            this.endLongitude = endLongitude;
        }

        public void addPackage(WebPackage p) {
            packages.add(p);
        }

    }

    public static class WebPackage {
        public int id;
        public String name;
        public int latitude;
        public int longitude;
    }

    public static class WebToken {
        public String token;
        public boolean isManager;
        public int id;

        public WebToken(String token, boolean isManager, int id) {
            this.token = token;
            this.isManager = isManager;
            this.id = id;
        }

    }

    public static class WebOrder {
        public int id;
        public String date;

        public String firstName;
        public String lastName;
        public String email;
        public String streetName;
        public String apartmentNumber;
        public String postalCode;
        public String city;

        public WebOrder(int id, String date, String firstName, String lastName, String email, String streetname, String apartmentNumber, String postalCode, String city) {
            this.id = id;
            this.date = date;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.streetName = streetname;
            this.apartmentNumber = apartmentNumber;
            this.postalCode = postalCode;
            this.city = city;
        }

    }
}
