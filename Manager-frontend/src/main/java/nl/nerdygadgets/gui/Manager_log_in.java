package nl.nerdygadgets.gui;

import com.google.gson.GsonBuilder;
import nl.nerdygadgets.util.CacheManager;
import nl.nerdygadgets.util.HttpUtil;
import nl.nerdygadgets.util.WebHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Manager_log_in {

    public Manager_log_in() {
        // Maak een JFrame aan
        JFrame frame = new JFrame("Manager ui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Maak een JPanel aan
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(frame, panel);

        // Zet het frame zichtbaar
        frame.setVisible(true);
    }

    private static void placeComponents(JFrame frame, JPanel panel) {
        panel.setLayout(null);

        // Label voor login
        JLabel logIn = new JLabel("LOG IN");
        logIn.setBounds(80, 20, 80, 25);
        panel.add(logIn);

        // Email veld
        JTextField email = new JTextField("EMAIL");
        email.setBounds(10, 50, 200, 30);
        panel.add(email);

        // Wachtwoord veld
        JPasswordField password = new JPasswordField("PASSWORD");
        password.setBounds(10, 90, 200, 30);
        panel.add(password);

        // Login knop
        JButton login = new JButton("LOG IN");
        login.setBounds(110, 130, 100, 30);
        panel.add(login);

        JButton forgot = new JButton("FORGOT?");
        forgot.setBounds(10, 130, 100, 30);
        panel.add(forgot);

        // Standaard echo character instellen
        char defaultEchoChar = password.getEchoChar();

        // Begin zonder echo character zodat de placeholder zichtbaar is
        password.setEchoChar((char) 0);

        // Voeg focus listeners toe om de placeholders functionaliteit te implementeren
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (email.getText().equals("EMAIL")) {
                    email.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (email.getText().equals("")) {
                    email.setText("EMAIL");
                }
            }
        });

        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(password.getPassword()).equals("PASSWORD")) {
                    password.setText("");
                    password.setEchoChar(defaultEchoChar);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(password.getPassword()).isEmpty()) {
                    password.setText("PASSWORD");
                    password.setEchoChar((char) 0);
                }
            }
        });




        // Actie bij het klikken van de knop
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String enteredPassword = new String(password.getPassword());
                    String enteredEmail = email.getText();
                    URL url = new URL("https://api.nerdy-gadgets.nl/login?username="+ enteredEmail +"&password="+ enteredPassword +"&manager=true");
                    String response = HttpUtil.getRequest(url);
                    if (response == null) {
                        // fout
                        JOptionPane.showMessageDialog(panel, "Toegang geweigerd");
                       return;
                    }
                    showNewPanel(frame, enteredEmail, enteredPassword);
                    return;
                    //


                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        });
    }

    private static void showNewPanel(JFrame frame, String username, String password) {
        // Maak een nieuw panel
        JPanel panel1 = new JPanel(new FlowLayout());
        // Verwijder het huidige panel en voeg het nieuwe panel toe
        frame.getContentPane().removeAll();
        frame.add(panel1);
        frame.revalidate();
        frame.repaint();

        JTextField twoFcode = new JTextField("Enter OTP");
        twoFcode.setBounds(50,50,100,30);
        panel1.add(twoFcode);


        JButton verify = new JButton("Verify");
        verify.setBounds(110, 130, 100, 30);
        panel1.add(verify);

        JButton resend = new JButton("resend");
        resend.setBounds(110,170,100,30);
        panel1.add(resend);

        JButton cancel = new JButton("cancel");
        cancel.setBounds(110, 210, 100, 30);
        panel1.add(cancel);

        twoFcode.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (twoFcode.getText().equals("Enter OTP")) {
                    twoFcode.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (twoFcode.getText().equals("")) {
                    twoFcode.setText("Enter OTP");
                }
            }
        });

        twoFcode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // negeer het teken
                }
            }
        });
        // Actie bij het klikken van de knop
        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verkrijg het ingevoerde wachtwoord en email
                int enteredCode = Integer.parseInt(twoFcode.getText());

                try {
                    URL url = new URL("https://api.nerdy-gadgets.nl/login?username="+ username +"&password="+ password +"&manager=true&code=" + enteredCode);
                    String response = HttpUtil.getRequest(url);
                    if (response == null) {
                        JOptionPane.showMessageDialog(null, "Incorrecte inloggegevens", "Fout", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    GsonBuilder builder = new GsonBuilder();
                    builder.setPrettyPrinting();
                    WebHelper.WebToken token = builder.create().fromJson(response, WebHelper.WebToken.class);
                    CacheManager.setToken(token);

                    System.out.println(token.id);
                    frame.dispose();

                    new Manger_overzicht_list();

                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
            }
        });
        resend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    URL url = new URL("https://api.nerdy-gadgets.nl/login?username="+ username +"&password="+ password +"&manager=true");
                    String response = HttpUtil.getRequest(url);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                JFrame frame = new JFrame("Manager ui");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);

                // Maak een JPanel aan
                JPanel panel = new JPanel();
                frame.add(panel);
                placeComponents(frame, panel);

                // Zet het frame zichtbaar
                frame.setVisible(true);

            }
        });
    }
}
