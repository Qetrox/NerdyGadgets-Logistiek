package nl.nerdygadgets.logistiek.gui;

import com.google.gson.GsonBuilder;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.util.ColorUtil;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.web.HttpUtil;
import nl.nerdygadgets.logistiek.util.web.WebHelper;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginGUI extends DefaultJFrame {

    private static JTextField usernameField;
    private static JPasswordField passwordField;

    private static JFrame parentFrame;

    public LoginGUI(JFrame _parentFrame) {
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

            if(usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vul alle velden in", "Fout", JOptionPane.ERROR_MESSAGE);
                return;
            }

            URL url = null;
            try {
                url = new URL("https://api.nerdy-gadgets.nl/login?username=" + usernameField.getText() + "&password=" + new String(passwordField.getPassword()));
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }

            try {
                String response = HttpUtil.getRequest(url);
                if(response == null) {
                    JOptionPane.showMessageDialog(null, "Incorrecte inloggegevens", "Fout", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                WebHelper.WebToken token = builder.create().fromJson(response, WebHelper.WebToken.class);
                CacheManager.setToken(token);

                // Gebruik dit om de token te krijgen voor requests etc.
                // CacheManager.getToken() - returnt de huidige token klasse.

                new DeliverPreviewGUI();
                this.dispose();
                parentFrame.dispose();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
