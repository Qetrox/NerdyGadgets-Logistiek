package nl.nerdygadgets.logistiek.gui;

import nl.nerdygadgets.logistiek.gui.modals.SupportModal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HomeScreen extends JFrame {

    public HomeScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.BLACK);
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel("Welcome, ");
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 150));
        navigationPanel.add(userLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2, 20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] mainButtons = {"Returns"/*, "Reports", "Settings"*/, "Support"};
        for (String buttonLabel : mainButtons) {
            JButton button = new JButton(buttonLabel);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            mainPanel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        switch (buttonLabel) {
                            case "Returns":
                                new RetourGUI().setVisible(true);
                                HomeScreen.this.dispose();
                                break;
                           /* case "Reports":
                                new RetourLoadGUI().setVisible(true);
                                HomeScreen.this.dispose();
                                break;
                            case "Settings":
                                new RetourLoadGUI().setVisible(true);
                                HomeScreen.this.dispose();
                                break; */
                            case "Support":
                                new SupportModal().setVisible(true);
                                break;
                            default:
                                break;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(HomeScreen.this, "Error opening window: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(navigationPanel, BorderLayout.WEST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeScreen dashboard = new HomeScreen();
            dashboard.setVisible(true);
        });
    }
}
