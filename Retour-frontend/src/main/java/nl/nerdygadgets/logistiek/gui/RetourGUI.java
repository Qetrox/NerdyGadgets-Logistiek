package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.Headpanel;
import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class RetourGUI extends DefaultJFrame {

    public RetourGUI() throws IOException {
        super("Retour");

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
            setSize(1200, 800);
        } else {
            setSize(1200, 800);
        }
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Headpanel Headpanel = new Headpanel();
        getContentPane().add(Headpanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());

        RetourInfoPanel retourInfoPanel = new RetourInfoPanel();
        mainPanel.add(retourInfoPanel, BorderLayout.EAST);

        RMATable rmaTable = new RMATable();
        mainPanel.add(rmaTable, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
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
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public RMATable() {
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
                return column == 5 || column == 7;
            }
        };

        table = new JTable(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
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
    }
}
