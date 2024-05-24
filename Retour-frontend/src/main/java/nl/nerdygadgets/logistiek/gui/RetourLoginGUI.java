package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.util.DefaultJFrame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RetourLoginGUI extends DefaultJFrame {

    private static JTextField usernameField;
    private static JPasswordField passwordField;

    private static JFrame parentFrame;

    public RetourLoginGUI(JFrame _parentFrame) {
        JDialog frame = new JDialog(_parentFrame, "Inloggen", true);
        parentFrame = _parentFrame;

        JButton loginButton = new JButton("Inloggen");
        loginButton.addActionListener(ActionListener());

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        frame.setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        frame.add(addMargin(new JLabel("Log in om verder te gaan", SwingConstants.CENTER), emptyBorder));
        frame.add(addMargin(usernameField, emptyBorder));
        frame.add(addMargin(passwordField, emptyBorder));
        frame.add(addMargin(loginButton, emptyBorder));

        frame.setSize(200, 300);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private ActionListener ActionListener() {
        return e -> {
            System.out.println("Login button clicked");
            System.out.println("Username: " + usernameField.getText());
            System.out.println("Password: " + new String(passwordField.getPassword()));

            try {
                new RetourPreviewGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            parentFrame.dispose();
        };
    }

    // Method to add margin to a component
    private Component addMargin(Component component, Border border) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(border);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

}
