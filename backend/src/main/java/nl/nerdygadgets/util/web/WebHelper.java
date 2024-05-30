package nl.nerdygadgets.util.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
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
                        return false;
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
        public long id;
        public Integer driverId;
        public ArrayList<WebPackage> packages= new ArrayList<>();
        public double startLatitude;
        public double startLongitude;
        public double endLatitude;
        public double endLongitude;

        public WebDelivery(long id, Integer driverId, double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
            this.id = id;
            this.driverId = driverId;
            this.startLatitude = startLatitude;
            this.startLongitude = startLongitude;
            this.endLatitude = endLatitude;
            this.endLongitude = endLongitude;
        }
        @Override
        public String toString() {
            return "Delivery: " + id + " Driver: " + driverId + " Start: " + startLatitude + ", " + startLongitude + " End: " + endLatitude + ", " + endLongitude;
        }

        public void addPackage(WebPackage p) {
            packages.add(p);
        }

    }

    public static class WebPackage {
        public int id;
        public int userId;
        public String name;
        public double latitude;
        public double longitude;
        public String address;

        public WebPackage(int id, String name, double latitude, double longitude, String adress, int userId) {
            this.id = id;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = adress;
            this.userId = userId;
        }

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

        @Override
        public String toString() {
            return "Token: " + token + " Manager: " + isManager + " ID: " + id;
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

    public static class WebRetour {
        public int retourId;
        public String resolutionType;
        public boolean handled;
        public String returnReason;
        public int orderId;
        public long created;
        public String name;

        public WebRetour(int retourId, String resolutionType, boolean handled, String returnReason, int orderId, long created, String name) {
            this.retourId = retourId;
            this.resolutionType = resolutionType;
            this.handled = handled;
            this.returnReason = returnReason;
            this.orderId = orderId;
            this.created = created;
            this.name = name;
        }
    }

}
