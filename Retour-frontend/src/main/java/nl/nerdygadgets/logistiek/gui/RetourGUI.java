package nl.nerdygadgets.logistiek.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nl.nerdygadgets.logistiek.gui.panels.Headpanel;
import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.HttpUtil;
import nl.nerdygadgets.logistiek.util.WebHelper;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class RetourGUI extends DefaultJFrame {

    public void close() {
        this.dispose();
    }

    public RetourGUI() throws IOException {
        super("Retour");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());

        RetourInfoPanel retourInfoPanel = new RetourInfoPanel();
        mainPanel.add(retourInfoPanel, BorderLayout.EAST);

        RMATable rmaTable = new RMATable(retourInfoPanel);
        mainPanel.add(rmaTable, BorderLayout.CENTER);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        Headpanel headpanel = new Headpanel(this);
        getContentPane().add(headpanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                WebHelper.WebToken token = new WebHelper.WebToken(1, true);
                CacheManager.setToken(token);

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
    private RetourInfoPanel retourInfoPanel;

    public RMATable(RetourInfoPanel retourInfoPanel) throws IOException {
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

        String[] columnNames = {"Retour ID", "Order ID", "Customer Name", "Create Date", "Resolution Type", "Return Reason", "Handled"};

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 6) {
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
            if (searchText.trim().isEmpty()) {
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
                    int retourId = (int) model.getValueAt(selectedRow, 0);
                    int orderId = (int) model.getValueAt(selectedRow, 1);
                    String customerName = (String) model.getValueAt(selectedRow, 2);
                    long createDate = (long) model.getValueAt(selectedRow, 3);
                    String resolutionType = (String) model.getValueAt(selectedRow, 4);
                    String returnReason = (String) model.getValueAt(selectedRow, 5);
                    boolean handled = (boolean) model.getValueAt(selectedRow, 6);
                    retourInfoPanel.updateInfo(retourId, orderId, customerName, createDate, resolutionType, returnReason, handled);
                    retourInfoPanel.setSelectedRowIndex(selectedRow);
                    retourInfoPanel.setTableModel(model);
                }
            }
        });

        fetchRetourData();
    }

    private void fetchRetourData() throws IOException {
        if (!CacheManager.hasToken()) {
            throw new IOException("User is not authenticated.");
        }

        URL url = new URL("https://api.nerdy-gadgets.nl/retour");
        System.out.println("Fetching data from URL: " + url); // Debug log

        try {
            String response = HttpUtil.getRequest(url);
            System.out.println("Response: " + response); // Debug log

            if (response != null && !response.isEmpty()) {
                Gson gson = new GsonBuilder().create();
                List<WebHelper.WebRetour> retours = gson.fromJson(response, new TypeToken<List<WebHelper.WebRetour>>(){}.getType());
                System.out.println("Parsed retours: " + retours); // Debug log

                for (WebHelper.WebRetour retour : retours) {
                    model.addRow(new Object[]{
                            retour.retourId, retour.orderId, retour.name, retour.created, retour.resolutionType, retour.returnReason, retour.handled
                    });
                }
            } else {
                System.out.println("No data received from API."); // Debug log
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error fetching data: " + e.getMessage()); // Debug log
        }

        selectFirstRow();
    }

    private void selectFirstRow() {
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0);
        }
    }
}