package nl.nerdygadgets.util.delivery;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.web.HttpUtil;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Deze klasse zorgt voor alles dat te maken heeft met nieuwe routes te maken en op te slaan.
 */
public class DeliveryRoutes {

    /**
     * Een lijst van alle routes die gemaakt zijn.
     * De key is de route en de value is of de route al opgevraagd is.
     */
    private static Map<WebHelper.WebDelivery, Boolean> routes = new HashMap<>();
    private static ArrayList<WebHelper.WebPackage> packagesToDeliver = new ArrayList<>();
    private static ArrayList<WebHelper.WebPackage> packagesInRoute = new ArrayList<>();

    public static void handlePackagesFromDatabase() throws SQLException, IOException {

        DatabaseConnector conn = Main.getDatabaseConnection();
        Connection connection = conn.getConnection();

        Statement stmt = connection.createStatement();
        ResultSet rs = conn.query("SELECT a.id, a.order_date, CONCAT(b.first_name, ' ', b.surname) as name, a.user_id, b.street_name, b.apartment_nr, b.postal_code, b.city FROM `order` as a JOIN user as b ON a.user_id = b.id;");

        int resultCount = 0;
        int validCount = 0;

        while (rs.next()) {
            if(validCount >= 21) { //TODO: alleen voor testen gebruiken!!!!
                break;
            }
            resultCount++;
            String street = rs.getString("street_name");
            String apartment = rs.getString("apartment_nr");
            String postal = rs.getString("postal_code");
            String city = rs.getString("city");

            String address = street + " " + apartment + ", " + postal + " " + city;

            URL url = new URL("https://nominatim.openstreetmap.org/search?format=json&street=" + street + "%20" + apartment + "&city=" + city + "&postalcode=" + postal + "&country=NL&addressdetails=1");

            String response = HttpUtil.getRequest(url);

            if(response.equals("[]")) {
                System.out.println("[" + validCount + "/" + resultCount + "]");
                continue;
            }

            JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);

            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            double latitude = jsonObject.get("lat").getAsDouble();
            double longitude = jsonObject.get("lon").getAsDouble();

            WebHelper.WebPackage pack = new WebHelper.WebPackage(
                    rs.getInt("id"),
                    rs.getString("name"),
                    latitude,
                    longitude,
                    address,
                    rs.getInt("user_id")
            );

            packagesToDeliver.add(pack);
            validCount++;
            System.out.println("[" + validCount + "/" + resultCount + "]");
        }

        System.out.println("Total packages: " + resultCount + " Valid packages: " + validCount);

    }

    public static void createRoutes() {
        // maak een route van 20 pakketten.

        if(packagesToDeliver.size() < 20) {
            System.out.println("Not enough packages to create a route");
            return;
        }

        Date date = new Date();
        long time = date.getTime();

        WebHelper.WebDelivery delivery = new WebHelper.WebDelivery(
                time,
                null,
                52.66358733694468,
                5.597219287948651,
                52.66358733694468,
                5.597219287948651
        );

        for(int i = 0; i < 20; i++) {
            WebHelper.WebPackage pack = packagesToDeliver.get(i);
            packagesInRoute.add(pack);
            delivery.addPackage(pack);
        }
        packagesToDeliver.subList(0, 20).clear();

        routes.put(delivery, false);
        System.out.println("Route created: " + delivery.id);
        createRoutes();
    }

    public static WebHelper.WebDelivery getRoute(int driverId) {
        for(WebHelper.WebDelivery delivery : routes.keySet()) {
            System.out.println(delivery.driverId + " " + driverId);
            if(delivery.driverId == driverId) {
                return delivery;
            }
        }
        return null;
    }

    public static WebHelper.WebDelivery getRoute() {
        for(WebHelper.WebDelivery delivery : routes.keySet()) {
            return delivery;
            /*if(!routes.get(delivery)) {
                routes.put(delivery, true);
                return delivery;
            }*/
        }
        return null;
    }

    public static void removeRoute(WebHelper.WebDelivery route) {
        routes.remove(route);
    }
}
