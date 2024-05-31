package nl.nerdygadgets.logistiek.gui.panels;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
}
