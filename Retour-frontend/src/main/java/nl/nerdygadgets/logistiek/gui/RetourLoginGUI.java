package nl.nerdygadgets.logistiek.gui;

import com.google.gson.GsonBuilder;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import nl.nerdygadgets.logistiek.util.HttpUtil;
import nl.nerdygadgets.logistiek.util.WebHelper;
import nl.nerdygadgets.logistiek.util.CacheManager;
import nl.nerdygadgets.logistiek.gui.panels.LoadPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RetourLoginGUI extends DefaultJFrame {

    private static JTextField usernameField;
    private static JPasswordField passwordField;

    private JDialog dialog;
    private LoadPanel loadPanel;

    public RetourLoginGUI(JFrame _parentFrame, LoadPanel _loadPanel) {
        dialog = new JDialog(_parentFrame, "Inloggen", true);
        loadPanel = _loadPanel;

        JButton loginButton = new JButton("Inloggen");
        loginButton.addActionListener(createLoginButtonListener());

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        dialog.setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        dialog.add(addMargin(new JLabel("Log in om verder te gaan", SwingConstants.CENTER), emptyBorder));
        dialog.add(addMargin(usernameField, emptyBorder));
        dialog.add(addMargin(passwordField, emptyBorder));
        dialog.add(addMargin(loginButton, emptyBorder));

        dialog.setSize(200, 300);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private ActionListener createLoginButtonListener() {
        return e -> {
            System.out.println("Login button clicked");
            System.out.println("Username: " + usernameField.getText());
            System.out.println("Password: " + new String(passwordField.getPassword()));

            if(usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vul alle velden in", "Fout", JOptionPane.ERROR_MESSAGE);
                return;
            }

            URL url;
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

                RetourGUI retourGUI = new RetourGUI();
                retourGUI.setVisible(true);
                dialog.dispose();

                if (loadPanel != null) {
                    Window window = SwingUtilities.getWindowAncestor(loadPanel);
                    if (window != null) {
                        window.dispose();
                    }
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private Component addMargin(Component component, Border border) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(border);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}