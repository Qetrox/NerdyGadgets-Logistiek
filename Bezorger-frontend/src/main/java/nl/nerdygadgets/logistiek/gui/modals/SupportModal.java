package nl.nerdygadgets.logistiek.gui.modals;

import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SupportModal extends JDialog {

    public SupportModal() {
        super();
        setTitle("Support");
        setSize(400, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0,0));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 450));
        panel.setBackground(ColorUtil.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Contact Support");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(ColorUtil.TEXT_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setPreferredSize(new Dimension(380, 300));
        textArea.setText("I need help with...");
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton Submit = new JButton("Submit");
        Submit.setPreferredSize(new Dimension(100, 50));
        Submit.setBackground(ColorUtil.TEXT_COLOR);
        Submit.setForeground(ColorUtil.BACKGROUND_COLOR);

        Submit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Support request submitted!");
            try {
                CacheManager.skipPackage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            dispose();
        });

        JButton Cancel = new JButton("Cancel");
        Cancel.setPreferredSize(new Dimension(100, 50));
        Cancel.setBackground(ColorUtil.TEXT_COLOR);
        Cancel.setForeground(ColorUtil.BACKGROUND_COLOR);

        Cancel.addActionListener(e -> {
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
        buttonPanel.add(Submit);
        buttonPanel.add(Cancel);

        buttonPanel.setBackground(ColorUtil.BACKGROUND_COLOR);

        panel.add(title, BorderLayout.NORTH);
        panel.add(textArea, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

}
