package nl.nerdygadgets.logistiek.gui.panels;

import javax.swing.*;
import java.awt.*;
import nl.nerdygadgets.logistiek.gui.modals.ResolveModal;
import nl.nerdygadgets.logistiek.gui.modals.SupportModal;
import javax.swing.table.DefaultTableModel;

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

    private DefaultTableModel tableModel;
    private int selectedRowIndex = -1;

    public RetourInfoPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(400, 340));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1));

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

        JButton resolve = new JButton("Resolve");
        resolve.setForeground(Color.BLACK);
        resolve.addActionListener(e -> openResolveModal());
        buttonPanel.add(resolve);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setForeground(Color.BLACK);
        return label;
    }

    private void openResolveModal() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        ResolveModal resolveModal = new ResolveModal(parentFrame,
                retourId.getText().replace("Retour ID: ", ""),
                orderId.getText().replace("Order ID: ", ""),
                customerName.getText().replace("Customer Name: ", ""),
                date.getText().replace("Date: ", ""),
                products.getText().replace("Products: ", ""),
                resolutionType.getText().replace("Resolution Type: ", ""),
                returnReason.getText().replace("Return Reason: ", ""),
                handled.getText().replace("Handled: ", "").equals("Yes"));

        resolveModal.setVisible(true);

        if (resolveModal.isResolved()) {
            updateInfo(resolveModal.getRetourId(),
                    resolveModal.getOrderId(),
                    resolveModal.getCustomerName(),
                    resolveModal.getDate(),
                    resolveModal.getProducts(),
                    resolveModal.getResolutionType(),
                    resolveModal.getReturnReason(),
                    resolveModal.isHandled());

            if (selectedRowIndex != -1 && tableModel != null) {
                tableModel.setValueAt(resolveModal.getRetourId(), selectedRowIndex, 0);
                tableModel.setValueAt(resolveModal.getOrderId(), selectedRowIndex, 1);
                tableModel.setValueAt(resolveModal.getCustomerName(), selectedRowIndex, 2);
                tableModel.setValueAt(resolveModal.getDate(), selectedRowIndex, 3);
                tableModel.setValueAt(resolveModal.getProducts(), selectedRowIndex, 4);
                tableModel.setValueAt(resolveModal.getResolutionType(), selectedRowIndex, 5);
                tableModel.setValueAt(resolveModal.getReturnReason(), selectedRowIndex, 6);
                tableModel.setValueAt(resolveModal.isHandled(), selectedRowIndex, 7);
            }
        }
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

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void setSelectedRowIndex(int selectedRowIndex) {
        this.selectedRowIndex = selectedRowIndex;
    }
}
