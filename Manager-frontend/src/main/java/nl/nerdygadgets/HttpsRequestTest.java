package nl.nerdygadgets;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.nerdygadgets.util.CacheManager;
import nl.nerdygadgets.util.WebHelper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpsRequestTest {

    public static void main(String[] args) {
        try {

            URL url = null;

            try {
                url = new URL("https://api.nerdy-gadgets.nl/login?username=testuser&password=cool");
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

            try {
                String response = getRequest(url);
                if(response == null) {
                    JOptionPane.showMessageDialog(null, "Incorrecte inloggegevens", "Fout", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                WebHelper.WebToken token = builder.create().fromJson(response, WebHelper.WebToken.class);
                CacheManager.setToken(token);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String response = getRequest(new URL("https://api.nerdy-gadgets.nl/manager/bestellingen https://api.nerdy-gadgets.nl/manager/bestellingen?token=" + CacheManager.getToken().token));
            if (response != null) {
                System.out.println("Response: " + response);

                // Parse JSON response using Gson
                JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();
                System.out.println("Parsed JSON: " + jsonResponse.toString());
            } else {
                System.out.println("Failed to get a valid response");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRequest(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        if (con.getResponseCode() != 200) {
            return null;
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }




}

