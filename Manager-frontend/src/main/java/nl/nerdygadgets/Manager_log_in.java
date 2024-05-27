package nl.nerdygadgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Manager_log_in {
    // Correct email en password
    private static String correctEmail = "Email";
    private static String correctPassword = "Password";
    private static int code;
    public static void main(String[] args) {
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
    public static int code(){
        StringBuilder randomNumberString = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int randomDigit = (int) (Math.random() * 10); // Willekeurig cijfer tussen 0 en 9
            randomNumberString.append(randomDigit);
        }

        // Converteer de StringBuilder naar een int
        code = Integer.parseInt(randomNumberString.toString());
        System.out.println(code);
        return code;
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
                // Verkrijg het ingevoerde wachtwoord en email
                String enteredPassword = new String(password.getPassword());
                String enteredEmail = email.getText();

                // Controleer of ze correct zijn
                if (enteredPassword.equals(correctPassword) && enteredEmail.equals(correctEmail)) {
                    JOptionPane.showMessageDialog(panel, "Toegang verleend.");
                    showNewPanel(frame);
//                    Code voor het sturen van de email. werkt alleen nog niet.
                    int generatedCode = code();
//                    sendEmail(generatedCode, email.getText());
                } else {
                    JOptionPane.showMessageDialog(panel, "Toegang geweigerd");
                }
            }
        });
        forgot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredemail = email.getText();
                if (enteredemail.equals(correctEmail)){
                    System.out.println("email gestuurd naar: " + enteredemail);
//                send_Forgot_Email(enteredemail);
                }

            }
        });
    }

    private static void showNewPanel(JFrame frame) {
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

                // Controleer of ze correct zijn
                if (enteredCode == code) {
                    JOptionPane.showMessageDialog(panel1, "Toegang verleend.");
//                    Openen_Scherm_Log_in();
                } else {
                    JOptionPane.showMessageDialog(panel1, "Toegang geweigerd");
                }
            }
        });

    }
    private static void sendEmail2fcode(int generatedCode, String enteredEmail) {
        final String host = "smtp.gmail.com";
        final String port = "587";
        final String username = "username";
        final String password = "Password";

        // Onderwerp van de e-mail
        final String subject = "Uw verificatiecode";
        final String body = "Uw verificatiecode is: " + generatedCode;

        // Instellen van SMTP server eigenschappen
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Authenticeren en verkrijgen van de sessie
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Creëren van de e-mail boodschap
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enteredEmail));
            message.setSubject(subject);
            message.setText(body);

            // Versturen van de e-mail
            Transport.send(message);

            System.out.println("E-mail is succesvol verstuurd!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    private static void send_Forgot_Email(String enteredEmail) {
        final String host = "smtp.gmail.com";
        final String port = "587";
        final String username = "username";
        final String password = "Password";

        // Onderwerp van de e-mail
        final String subject = "Vergeten wachtwoord";
        final String body = "Uw wachtwoord is: " + correctPassword;

        // Instellen van SMTP server eigenschappen
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Authenticeren en verkrijgen van de sessie
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Creëren van de e-mail boodschap
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enteredEmail));
            message.setSubject(subject);
            message.setText(body);

            // Versturen van de e-mail
            Transport.send(message);

            System.out.println("E-mail is succesvol verstuurd!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
