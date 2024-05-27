package nl.nerdygadgets.logistiek.util.web;

import java.util.ArrayList;

public class WebHelper {

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

    public static class WebDelivery {
        public long id;
        public int driverId;
        public ArrayList<WebPackage> packages= new ArrayList<>();
        public double startLatitude;
        public double startLongitude;
        public double endLatitude;
        public double endLongitude;

        public WebDelivery(long id, int driverId, double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
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
        public double latitude;
        public double longitude;
        public String address;

        public WebPackage(int id, String name, double latitude, double longitude, String adress) {
            this.id = id;
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.address = adress;
        }

    }

}
