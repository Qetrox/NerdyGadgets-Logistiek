package nl.nerdygadgets.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebHelper {

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

    public static class WebProduct {
        public String naam;
        public int aantal;
        public int id;
        public WebProduct(String naam, int aantal, int id) {
            this.naam = naam;
            this.aantal = aantal;
            this.id = id;
        }
    }

    public static class WebOrder {
        public ArrayList<WebProduct> producten = new ArrayList<>();
        public int id;
        public WebOrder(int id, WebProduct[] product) {
            producten.addAll(Arrays.asList(product));
        }
    }

}
