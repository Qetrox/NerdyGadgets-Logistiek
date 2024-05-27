package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.panels.RetourInfoPanel;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        String[] columnNames = {"RMA ID", "Order ID", "Customer Name", "Created on Date", "Products", "Resolution Type", "Returns Reason", "Request Status", "Edit"};

        Object[][] data = {
                {"#408", "#1003", "Spicegems", "Nov 21, 2017", "Timex Analog Off-White Dial Men's Watch - Silver", "Refund", "Wrong Color", false, "Edit"},
                {"#407", "#1003", "Spicegems", "Oct 20, 2017", "Timex Analog Off-White Dial Men's Watch - White", "Refund", "Wrong Color", false, "Edit"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 7) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        JTable table = new JTable(model);
        table.getColumn("Request Status").setCellRenderer(table.getDefaultRenderer(Boolean.class));
        table.getColumn("Request Status").setCellEditor(table.getDefaultEditor(Boolean.class));

        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "Edit"));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        private JButton button;

        public ButtonEditor(JCheckBox checkBox, String action) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    JOptionPane.showMessageDialog(button, action + " clicked for row " + label);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }
    }
}
