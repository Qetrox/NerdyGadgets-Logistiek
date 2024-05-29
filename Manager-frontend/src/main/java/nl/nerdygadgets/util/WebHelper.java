package nl.nerdygadgets.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
