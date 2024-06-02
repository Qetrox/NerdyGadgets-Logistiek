package nl.nerdygadgets.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nl.nerdygadgets.util.CacheManager;
import nl.nerdygadgets.util.HttpUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

public class Manger_overzicht_list {

    private JFrame frame;
    private JList<Order> orderList;
    private DefaultListModel<Order> listModel;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JPanel panel;
    private JSplitPane splitPane;

    public Manger_overzicht_list() {
        initializeComponents();
        setupLayout();
        fetchAndPopulateOrders();
        addEventListeners();
        displayFrame();
    }

    private void initializeComponents() {
        frame = new JFrame("Storage");
        orderList = new JList<>();
        listModel = new DefaultListModel<>();
        orderTable = new JTable();
        tableModel = new DefaultTableModel();
        panel = new JPanel(new BorderLayout(10, 10));
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        orderList.setModel(listModel);
        tableModel.addColumn("Bestelling");
        tableModel.addColumn("Klant Naam");
        tableModel.addColumn("Klant Adres");
        tableModel.addColumn("Bezorger");
        orderTable.setModel(tableModel);

        // Styling the table
        orderTable.setFillsViewportHeight(true);
        orderTable.setGridColor(Color.LIGHT_GRAY);
        orderTable.setRowHeight(25);
        TableColumnModel columnModel = orderTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(100);
    }

    private void setupLayout() {
        splitPane.setLeftComponent(new JScrollPane(orderList));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        splitPane.setRightComponent(panel);
        splitPane.setDividerLocation(250);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
    }

    private void fetchAndPopulateOrders() {
        String jsonResponse = fetchOrders();
        if (jsonResponse == null) {
            showErrorDialog("Failed to fetch orders");
            return;
        }

        TreeMap<Integer, Order> ordersMap = parseOrdersFromJson(jsonResponse);
        for (Order order : ordersMap.values()) {
            listModel.addElement(order);
        }
    }

    private void addEventListeners() {
        orderList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Order selectedOrder = orderList.getSelectedValue();
                if (selectedOrder != null) {
                    updateTable(selectedOrder);
                }
            }
        });
    }

    private void displayFrame() {
        frame.setVisible(true);
    }

    private String fetchOrders() {
        try {
            String urlStr = "https://api.nerdy-gadgets.nl/manager/bestellingen?token=" + CacheManager.getToken().token;
            URL url = new URL(urlStr);
            return HttpUtil.getRequest(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private TreeMap<Integer, Order> parseOrdersFromJson(String jsonResponse) {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        TreeMap<Integer, Order> ordersMap = new TreeMap<>();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            int id = Integer.parseInt(entry.getKey());
            JsonObject orderJson = entry.getValue().getAsJsonObject();

            String firstName = orderJson.has("firstName") ? orderJson.get("firstName").getAsString() : "Unknown";
            String lastName = orderJson.has("lastName") ? orderJson.get("lastName").getAsString() : "Unknown";
            String streetName = orderJson.has("streetName") ? orderJson.get("streetName").getAsString() : "Unknown";
            String apartmentNumber = orderJson.has("apartmentNumber") ? orderJson.get("apartmentNumber").getAsString() : "";
            String postalCode = orderJson.has("postalCode") ? orderJson.get("postalCode").getAsString() : "Unknown";
            String city = orderJson.has("city") ? orderJson.get("city").getAsString() : "Unknown";
            String bezorger = orderJson.has("bezorger") ? orderJson.get("bezorger").getAsString() : "Unknown";

            Order order = new Order(
                    "Bestelling " + id,
                    firstName + " " + lastName,
                    streetName + " " + apartmentNumber + ", " + postalCode + " " + city,
                    bezorger
            );

            ordersMap.put(id, order);
        }
        return ordersMap;
    }

    private void updateTable(Order order) {
        tableModel.setRowCount(0); // Clear the table
        tableModel.addRow(new Object[]{order.getOrder(), order.getKlantNaam(), order.getKlantAdres(), order.getBezorger()});
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class Order {
        private final String order;
        private final String klantNaam;
        private final String klantAdres;
        private final String bezorger;

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