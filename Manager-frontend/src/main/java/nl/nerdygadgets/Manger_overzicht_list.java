package nl.nerdygadgets;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.nerdygadgets.util.CacheManager;
import nl.nerdygadgets.util.WebHelper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class Manger_overzicht_list {

    private JFrame frame;
    private JList<Order> list;
    private DefaultListModel<Order> model;
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel panel;
    private JSplitPane splitPane;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Login and fetch data before launching the main UI
            if (login()) {
                String ordersJson = fetchOrders();
                if (ordersJson != null) {
                    new Manger_overzicht_list(ordersJson);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to fetch orders", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Manger_overzicht_list(String jsonResponse) {
        frame = new JFrame("Storage");
        list = new JList<>();
        model = new DefaultListModel<>();

        table = new JTable();
        tableModel = new DefaultTableModel();
        panel = new JPanel();
        splitPane = new JSplitPane();

        list.setModel(model);

        // Parse JSON and populate the model
        populateOrdersFromJson(jsonResponse);

        list.getSelectionModel().addListSelectionListener(e -> {
            Order o = list.getSelectedValue();
            if (o != null) {
                tableModel.setRowCount(0); // Clear the table
                tableModel.addRow(new Object[]{o.getOrder(), o.getKlantNaam(), o.getKlantAdres(), o.getBezorger()});
            }
        });

        tableModel.addColumn("Order");
        tableModel.addColumn("Klant Naam");
        tableModel.addColumn("Klant Adres");
        tableModel.addColumn("Bezorger");
        table.setModel(tableModel);

        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(new JScrollPane(table));
        splitPane.setRightComponent(panel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(splitPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateOrdersFromJson(String jsonResponse) {
        // Parse JSON string
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // TreeMap to store orders sorted by ID
        TreeMap<Integer, Order> ordersMap = new TreeMap<>();

        // Iterate over the JSON object keys
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            int id = Integer.parseInt(entry.getKey());
            JsonObject orderJson = entry.getValue().getAsJsonObject();

            // Create an Order object
            Order order = new Order(
                    "Bestelling " + id,
                    orderJson.get("firstName").getAsString() + " " + orderJson.get("lastName").getAsString(),
                    orderJson.get("streetName").getAsString() + " " + orderJson.get("apartmentNumber").getAsString() + ", " + orderJson.get("postalCode").getAsString() + " " + orderJson.get("city").getAsString(),
                    "Bezorger" // Replace with actual bezorger field if available
            );

            // Add the order to the TreeMap
            ordersMap.put(id, order);
        }

        // Add orders to the DefaultListModel
        for (Order order : ordersMap.values()) {
            model.addElement(order);
        }
    }

    private static boolean login() {
        try {
            URL url = new URL("https://api.nerdy-gadgets.nl/login?username=testuser&password=cool");
            String response = getRequest(url);
            if (response == null) {
                JOptionPane.showMessageDialog(null, "Incorrecte inloggegevens", "Fout", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            WebHelper.WebToken token = builder.create().fromJson(response, WebHelper.WebToken.class);
            CacheManager.setToken(token);
            return true;
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String fetchOrders() {
        try {
            String urlStr = "https://api.nerdy-gadgets.nl/manager/bestellingen?token=" + CacheManager.getToken().token;
            URL url = new URL(urlStr);
            return getRequest(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

    private class Order {
        String order;
        String klantNaam;
        String klantAdres;
        String bezorger;

        public Order(String order, String klantNaam, String klantAdres, String bezorger) {
            this.order = order;
            this.klantNaam = klantNaam;
            this.klantAdres = klantAdres;
            this.bezorger = bezorger;
        }

        public String getOrder() {
            return order;
        }

        public String getKlantNaam() {
            return klantNaam;
        }

        public String getKlantAdres() {
            return klantAdres;
        }

        public String getBezorger() {
            return bezorger;
        }

        @Override
        public String toString() {
            return order;
        }
    }
}
