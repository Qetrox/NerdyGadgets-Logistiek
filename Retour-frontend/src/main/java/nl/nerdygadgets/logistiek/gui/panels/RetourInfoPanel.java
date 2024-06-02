package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.gui.modals.ResolveModal;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetourInfoPanel extends JPanel {

    private JLabel retourIdLabel;
    private JLabel orderIdLabel;
    private JLabel customerNameLabel;
    private JLabel createDateLabel;
    private JLabel resolutionTypeLabel;
    private JLabel returnReasonLabel;
    private JLabel handledLabel;
    private int selectedRowIndex;
    private DefaultTableModel tableModel;

    public RetourInfoPanel() {
        setLayout(new GridLayout(7, 2));

        retourIdLabel = new JLabel();
        orderIdLabel = new JLabel();
        customerNameLabel = new JLabel();
        createDateLabel = new JLabel();
        resolutionTypeLabel = new JLabel();
        returnReasonLabel = new JLabel();
        handledLabel = new JLabel();

        add(new JLabel("Retour ID:"));
        add(retourIdLabel);
        add(new JLabel("Order ID:"));
        add(orderIdLabel);
        add(new JLabel("Customer Name:"));
        add(customerNameLabel);
        add(new JLabel("Create Date:"));
        add(createDateLabel);
        add(new JLabel("Resolution Type:"));
        add(resolutionTypeLabel);
        add(new JLabel("Return Reason:"));
        add(returnReasonLabel);
        add(new JLabel("Handled:"));
        add(handledLabel);

        JButton resolve = new JButton("Resolve");
        resolve.setForeground(Color.BLACK);
        resolve.addActionListener(e -> openResolveModal());
        add(resolve);
    }

    public void updateInfo(int retourId, int orderId, String customerName, long createDate, String resolutionType, String returnReason, boolean handled) {
        retourIdLabel.setText(String.valueOf(retourId));
        orderIdLabel.setText(String.valueOf(orderId));
        customerNameLabel.setText(customerName);
        createDateLabel.setText(String.valueOf(createDate));
        resolutionTypeLabel.setText(resolutionType);
        returnReasonLabel.setText(returnReason);
        handledLabel.setText(String.valueOf(handled));
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    private void openResolveModal() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        ResolveModal resolveModal = new ResolveModal(parent, retourIdLabel.getText(), orderIdLabel.getText(), customerNameLabel.getText(), createDateLabel.getText(), "", resolutionTypeLabel.getText(), returnReasonLabel.getText(), Boolean.parseBoolean(handledLabel.getText()));
        resolveModal.setVisible(true);

        if (resolveModal.isResolved()) {
            int rowIndex = selectedRowIndex;
            tableModel.setValueAt(resolveModal.getRetourId(), rowIndex, 0);
            tableModel.setValueAt(resolveModal.getOrderId(), rowIndex, 1);
            tableModel.setValueAt(resolveModal.getCustomerName(), rowIndex, 2);
            tableModel.setValueAt(resolveModal.getDate(), rowIndex, 3);
            tableModel.setValueAt(resolveModal.getResolutionType(), rowIndex, 4);
            tableModel.setValueAt(resolveModal.getReturnReason(), rowIndex, 5);
            tableModel.setValueAt(resolveModal.isHandled(), rowIndex, 6);

            sendResolvedDataToServer(resolveModal.getRetourId(), resolveModal.getOrderId(), resolveModal.getCustomerName(), resolveModal.getDate(), resolveModal.getProducts(), resolveModal.getResolutionType(), resolveModal.getReturnReason(), resolveModal.isHandled());
        }
    }

    private void sendResolvedDataToServer(String retourId, String orderId, String customerName, String date, String products, String resolutionType, String returnReason, boolean handled) {
        Gson gson = new Gson();
        ResolvedData resolvedData = new ResolvedData(retourId, orderId, customerName, date, products, resolutionType, returnReason, handled);
        String json = gson.toJson(resolvedData);

        try {
            URL url = new URL("https://api.nerdy-gadgets.nl/retour/update");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            } else {
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ResolvedData {
        private String retourId;
        private String orderId;
        private String customerName;
        private String date;
        private String products;
        private String resolutionType;
        private String returnReason;
        private boolean handled;

        public ResolvedData(String retourId, String orderId, String customerName, String date, String products, String resolutionType, String returnReason, boolean handled) {
            this.retourId = retourId;
            this.orderId = orderId;
            this.customerName = customerName;
            this.date = date;
            this.products = products;
            this.resolutionType = resolutionType;
            this.returnReason = returnReason;
            this.handled = handled;
        }
    }
}
