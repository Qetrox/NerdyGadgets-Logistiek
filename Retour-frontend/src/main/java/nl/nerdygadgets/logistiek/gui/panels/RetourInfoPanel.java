package nl.nerdygadgets.logistiek.gui.panels;

import javax.swing.*;
import java.awt.*;
import nl.nerdygadgets.logistiek.gui.modals.SupportModal;

public class RetourInfoPanel extends JPanel {

    private static final int buttonWidth = 160;
    private static final int buttonHeight = 50;

    private JLabel retourId;
    private JLabel orderId;
    private JLabel customerName;
    private JLabel date;
    private JLabel products;
    private JLabel resolutionType;
    private JLabel returnReason;
    private JLabel handled;

    public RetourInfoPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 340));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1));
        infoPanel.setBackground(Color.WHITE);

        retourId = createLabel("");
        infoPanel.add(retourId);

        orderId = createLabel("");
        infoPanel.add(orderId);

        customerName = createLabel("");
        infoPanel.add(customerName);

        date = createLabel("");
        infoPanel.add(date);

        products = createLabel("");
        infoPanel.add(products);

        resolutionType = createLabel("");
        infoPanel.add(resolutionType);

        returnReason = createLabel("");
        infoPanel.add(returnReason);

        handled = createLabel("");
        infoPanel.add(handled);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 140));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBackground(Color.WHITE);

        JButton resolve = new JButton("Resolve");
        resolve.setBackground(Color.BLACK);
        resolve.setForeground(Color.WHITE);
        resolve.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        buttonPanel.add(resolve);

        JButton support = new JButton("Support");
        support.setBackground(Color.BLACK);
        support.setForeground(Color.WHITE);
        support.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        support.addActionListener(e -> {
            new SupportModal();
        });
        buttonPanel.add(support);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.BLACK);
        return label;
    }

    public void updateInfo(String retourID, String orderID, String customerName, String date, String products, String resolutionType, String returnReason, boolean handled) {
        this.retourId.setText("Retour ID: " + retourID);
        this.orderId.setText("Order ID: " + orderID);
        this.customerName.setText("Customer Name: " + customerName);
        this.date.setText("Date: " + date);
        this.products.setText("Products: " + products);
        this.resolutionType.setText("Resolution Type: " + resolutionType);
        this.returnReason.setText("Return Reason: " + returnReason);
        this.handled.setText("Handled: " + (handled ? "Yes" : "No"));
    }
}
