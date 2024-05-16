package nl.nerdygadgets.logistiek.gui.panels;

import nl.nerdygadgets.logistiek.gui.modals.SupportModal;
import nl.nerdygadgets.logistiek.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DeliverInfoPanel extends JPanel {

    private static final int buttonWidth = 160;
    private static final int buttonHeight = 50;
    private JPanel smallerButton(JButton button) throws IOException {
        JPanel smallPanel = new JPanel();
        smallPanel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        smallPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        smallPanel.add(button);
        smallPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        smallPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        return smallPanel;
    }

    public DeliverInfoPanel() throws IOException {

        setLayout(new BorderLayout(0,0));
        setBackground(ColorUtil.BACKGROUND_COLOR);
        setPreferredSize(new Dimension(400, 800));

        JLabel title = new JLabel("Order #12345");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(ColorUtil.TEXT_COLOR);
        title.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel address = new JLabel("Idk laan 2");
        address.setFont(new Font("Arial", Font.PLAIN, 18));
        address.setForeground(ColorUtil.TEXT_COLOR);
        address.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel name = new JLabel("Persoon Achternaam");
        name.setFont(new Font("Arial", Font.PLAIN, 18));
        name.setForeground(ColorUtil.TEXT_COLOR);
        name.setHorizontalAlignment(SwingConstants.LEFT);


        JPanel topPanel = new JPanel();

        topPanel.setLayout(new GridLayout(3, 1));
        topPanel.add(title);
        topPanel.add(address);
        topPanel.add(name);
        topPanel.setBackground(ColorUtil.BACKGROUND_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 10, 10));

        add(topPanel, BorderLayout.NORTH);

        JButton complete = new JButton("Complete Order");
        complete.setBackground(ColorUtil.TEXT_COLOR);
        complete.setForeground(ColorUtil.BACKGROUND_COLOR);
        complete.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        complete.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #12345 is completed", "Order Completed", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton notHome = new JButton("Not Home");
        notHome.setBackground(ColorUtil.TEXT_COLOR);
        notHome.setForeground(ColorUtil.BACKGROUND_COLOR);
        notHome.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        notHome.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #12345 is marked as not home", "Not Home", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton cancel = new JButton("Cancel Order");
        cancel.setBackground(ColorUtil.TEXT_COLOR);
        cancel.setForeground(ColorUtil.BACKGROUND_COLOR);
        cancel.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        cancel.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Order #12345 is cancelled", "Order Cancelled", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton support = new JButton("Support");
        support.setBackground(ColorUtil.TEXT_COLOR);
        support.setForeground(ColorUtil.BACKGROUND_COLOR);
        support.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        support.addActionListener(e -> {
            new SupportModal();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 140));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.add(smallerButton(complete));
        buttonPanel.add(smallerButton(notHome));
        buttonPanel.add(smallerButton(cancel));
        buttonPanel.add(smallerButton(support));
        buttonPanel.setBackground(ColorUtil.BACKGROUND_COLOR);

        add(buttonPanel, BorderLayout.SOUTH);


    }

}
