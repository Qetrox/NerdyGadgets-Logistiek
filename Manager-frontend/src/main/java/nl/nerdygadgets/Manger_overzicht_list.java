package nl.nerdygadgets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;

public class Manger_overzicht_list {

    JFrame frame = new JFrame("Storage");
    JList<Order> list = new JList<>();
    DefaultListModel<Order> model = new DefaultListModel<>();

    JTable table = new JTable();
    DefaultTableModel tableModel = new DefaultTableModel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();

    public Manger_overzicht_list() {

        list.setModel(model);

        model.addElement(new Order("Bestelling 123123", "Tim Kok", "Kokstraat 3", "Helmut Kazan"));
        model.addElement(new Order("Bestelling 234234", "Jan Jansen", "Hoofdstraat 5", "Alex de Jong"));
        model.addElement(new Order("Bestelling 345345", "Piet Pieters", "Eikenlaan 12", "Chris van Dam"));
        model.addElement(new Order("Bestelling 456456", "Kees Klaassen", "Dennendreef 9", "David de Vries"));
        model.addElement(new Order("Bestelling 567567", "Annie Bakker", "Lindenstraat 7", "Freddie Mulder"));
        model.addElement(new Order("Bestelling 678678", "Henk de Boer", "Cederweg 4", "Mick Jansen"));
        model.addElement(new Order("Bestelling 789789", "Emma de Graaf", "Berkenlaan 6", "Elvis van Dijk"));
        model.addElement(new Order("Bestelling 890890", "Sophie Visser", "Esdoornstraat 8", "Paul van Leeuwen"));
        model.addElement(new Order("Bestelling 901901", "Lucas de Wit", "Sparrenlaan 2", "Jim Bakker"));
        model.addElement(new Order("Bestelling 101010", "Julia Smits", "Wilgenlaan 10", "Robert van der Meer"));
        ;

        list.getSelectionModel().addListSelectionListener(e -> {
            Order o = list.getSelectedValue();
            if (o != null) {
                tableModel.setRowCount(0); // Clear the table
                tableModel.addRow(new Object[]{o.getOrder(), o.getKlant_naam(),o.getKlant_adres(),o.getBezorger()});

            }
        });

        tableModel.addColumn("order");
        tableModel.addColumn("klant naam");
        tableModel.addColumn("klant adres");
        tableModel.addColumn("bezorger");
        table.setModel(tableModel);

        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(new JScrollPane(table));
        splitPane.setRightComponent(panel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(splitPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Manger_overzicht_list::new);
    }

    private class Order {
        String order;
        String klant_naam;
        String klant_adres;
        String bezorger;

        public Order(String order, String klant_naam, String klant_adres,String bezorger) {
            this.order = order;
            this.klant_naam = klant_naam;
            this.klant_adres = klant_adres;
            this.bezorger = bezorger;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getKlant_naam() {
            return klant_naam;
        }

        public void setKlant_naam(String klant_naam) {
            this.klant_naam = klant_naam;
        }

        public String getKlant_adres() {
            return klant_adres;
        }

        public void setKlant_adres(String klant_adres) {
            this.klant_adres = klant_adres;
        }

        public String getBezorger() {
            return bezorger;
        }

        public void setBezorger(String bezorger) {
            this.bezorger = bezorger;
        }

        @Override
        public String toString() {
            return order;
        }
    }
}