package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class RMATable extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private RetourInfoPanel retourInfoPanel;

    public RMATable(RetourInfoPanel retourInfoPanel) {
        this.retourInfoPanel = retourInfoPanel;
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"Retour ID", "Order ID", "Customer Name", "Created on Date", "Products", "Resolution Type", "Returns Reason", "Handled"};

        Object[][] data = {
                {"#408", "#1003", "Karen kik", "Nov 21, 2017", "Fortnite battle pass", "Refund", "Wrong Color", true},
                {"#407", "#1003", "Bob bob", "Oct 20, 2017", "Fortnite golden scar", "Refund", "Wrong Color", false}
        };

        model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 7) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText();
            if (searchText.trim().length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0, 2));
            }
        });

        clearButton.addActionListener(e -> {
            searchField.setText("");
            sorter.setRowFilter(null);
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                    String retourID = (String) model.getValueAt(selectedRow, 0);
                    String orderID = (String) model.getValueAt(selectedRow, 1);
                    String customerName = (String) model.getValueAt(selectedRow, 2);
                    String date = (String) model.getValueAt(selectedRow, 3);
                    String products = (String) model.getValueAt(selectedRow, 4);
                    String resolutionType = (String) model.getValueAt(selectedRow, 5);
                    String returnReason = (String) model.getValueAt(selectedRow, 6);
                    boolean handled = (boolean) model.getValueAt(selectedRow, 7);
                    retourInfoPanel.updateInfo(retourID, orderID, customerName, date, products, resolutionType, returnReason, handled);
                    retourInfoPanel.setSelectedRowIndex(selectedRow);
                    retourInfoPanel.setTableModel(model);
                }
            }
        });
    }
}
