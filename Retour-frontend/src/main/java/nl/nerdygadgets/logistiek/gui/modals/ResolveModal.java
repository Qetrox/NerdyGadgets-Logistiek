package nl.nerdygadgets.logistiek.gui.modals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResolveModal extends JDialog {
    private JTextField retourIdField;
    private JTextField orderIdField;
    private JTextField customerNameField;
    private JTextField dateField;
    private JTextField productsField;
    private JComboBox<String> ResolutionType;
    private JTextField returnReasonField;
    private JCheckBox handledCheckBox;
    private boolean resolved;

    public ResolveModal(JFrame parent, String retourId, String orderId, String customerName, String date, String products, String resolutionType, String returnReason, boolean handled) {
        super(parent, "Resolve Return", true);

        setLayout(new GridLayout(9, 2));

        add(new JLabel("Retour ID:"));
        retourIdField = new JTextField(retourId);
        retourIdField.setEditable(false); // Niet te editen
        add(retourIdField);

        add(new JLabel("Order ID:"));
        orderIdField = new JTextField(orderId);
        orderIdField.setEditable(false); // Niet te editen
        add(orderIdField);

        add(new JLabel("Customer Name:"));
        customerNameField = new JTextField(customerName);
        customerNameField.setEditable(false); // Niet te editen
        add(customerNameField);

        add(new JLabel("Date:"));
        dateField = new JTextField(date);
        add(dateField);

        add(new JLabel("Products:"));
        productsField = new JTextField(products);
        productsField.setEditable(false); // Niet te editen
        add(productsField);

        add(new JLabel("Resolution Type:"));
        ResolutionType = new JComboBox<>(new String[]{"Not decided", "Refund", "Refund (Store Credit)", "Exchange Product", "No Refund"});
        ResolutionType.setSelectedItem(resolutionType);
        add(ResolutionType);

        add(new JLabel("Return Reason:"));
        returnReasonField = new JTextField(returnReason);
        returnReasonField.setEditable(false); // Niet te editen
        add(returnReasonField);

        add(new JLabel("Handled:"));
        handledCheckBox = new JCheckBox();
        handledCheckBox.setSelected(handled);
        add(handledCheckBox);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolved = true;
                setVisible(false);
            }
        });
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resolved = false;
                setVisible(false);
            }
        });
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isResolved() {
        return resolved;
    }

    public String getRetourId() {
        return retourIdField.getText();
    }

    public String getOrderId() {
        return orderIdField.getText();
    }

    public String getCustomerName() {
        return customerNameField.getText();
    }

    public String getDate() {
        return dateField.getText();
    }

    public String getProducts() {
        return productsField.getText();
    }

    public String getResolutionType() {
        return (String) ResolutionType.getSelectedItem();
    }

    public String getReturnReason() {
        return returnReasonField.getText();
    }

    public boolean isHandled() {
        return handledCheckBox.isSelected();
    }
}
