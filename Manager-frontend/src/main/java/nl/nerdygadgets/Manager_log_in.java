package nl.nerdygadgets;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class Manager_log_in{
    public static void main(String[] args) {
        // Maak een JFrame aan
        JFrame frame = new JFrame("Manager ui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Maak een JPanel aan
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        // Zet het frame zichtbaar
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Label voor wachtwoord
        JLabel logIn = new JLabel("LOG IN");
        logIn.setBounds(80,20,80,25);
        panel.add(logIn);


        // Email veld

        JTextField email = new JTextField("EMAIL");
        email.setBounds(10,50, 200, 30);
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

        // Voegfocus listeners toe om de placeholders functionaliteit te implementeren
        email.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (email.getText().equals("EMAIL")) {
                    email.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (email.getText().equals("")){
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
        // Stukje hardcode
        // Correct email
        String correctEmail = "Email";
        // Correct password
        String correctPassword = "Password";

        // Actie bij het klikken van de knop
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verkrijg het ingevoerde wachtwoord
                String enteredPassword = new String(password.getPassword());
                String enteredEmail = new String(email.getText());

                // Controleer of het wachtwoord correct is
                if (enteredPassword.equals(correctPassword) || enteredEmail.equals(correctEmail)){
                    JOptionPane.showMessageDialog(panel, "Toegang verleend.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Toegang geweigerd");
                }
            }
        });
    }
}
