package nl.nerdygadgets;

import javax.swing.*;
public class Manager_overzicht {
    // frame
    JFrame f;
    // Table
    JTable j;

    // Constructor
    Manager_overzicht()
    {
        // Frame initialization
        f = new JFrame();

        // Frame Title
        f.setTitle("JTable Example");

        // Data to be displayed in the JTable
        String[][] data = {
                { "ORDER 4343234 ","Alberto " },
                { "ORDER 4343235","Henk"}
        };

        // Column Names
        String[] columnNames = { "Order", "Bezorger"};

        // Initializing the JTable
        j = new JTable(data, columnNames);
        j.setBounds(30, 40, 200, 300);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
        f.add(sp);
        // Frame Size
        f.setSize(500, 200);
        // Frame Visible = true
        f.setVisible(true);
    }

    // Driver  method
    public static void main(String[] args)
    {
        new Manager_overzicht();
    }
}