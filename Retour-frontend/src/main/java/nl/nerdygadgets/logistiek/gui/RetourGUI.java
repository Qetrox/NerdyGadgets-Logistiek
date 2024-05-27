package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RetourGUI extends DefaultJFrame {

    public RetourGUI() throws IOException {
        super("Retour");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            setSize(1200, 800);
        }
        setResizable(false);

        RetourInfoPanel retourInfoPanel = new RetourInfoPanel();
        getContentPane().add(retourInfoPanel, BorderLayout.EAST);

        RMATable rmaTable = new RMATable();
        getContentPane().add(rmaTable, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RetourGUI frame = new RetourGUI();
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

class RMATable extends JPanel {

    public RMATable() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Retour ID", "Order ID", "Customer Name", "Created on Date", "Products", "Resolution Type", "Returns Reason", "Handled"};

        Object[][] data = {
                {"#408", "#1003", "Karen kik", "Nov 21, 2017", "Fortnite battle pass", "Refund", "Wrong Color", false},
                {"#407", "#1003", "Bob bob", "Oct 20, 2017", "Fortnite golden scar", "Refund", "Wrong Color", false}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 7) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 7; // Make only "Resolution Type" and "Request Status" columns editable
            }
        };

        JTable table = new JTable(model);

        String[] resolutionOptions = {"Refund", "Refund (Store Credit)", "Exchange Product", "No Refund"};
        JComboBox<String> resolutionComboBox = new JComboBox<>(resolutionOptions);
        table.getColumn("Resolution Type").setCellEditor(new DefaultCellEditor(resolutionComboBox));

        table.getColumn("Handled").setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumn("Handled").setCellEditor(table.getDefaultEditor(Boolean.class));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
