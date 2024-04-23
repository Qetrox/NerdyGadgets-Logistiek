package nl.nerdygadgets.logistiek.gui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import nl.nerdygadgets.logistiek.util.DefaultJFrame;
import javafx.scene.web.WebView;

import java.io.IOException;

public class LoadGUI extends DefaultJFrame {

    private final JFXPanel jfxPanel = new JFXPanel();

    public LoadGUI() throws IOException {
        super("Logistiek");
        setSize(800, 600);
        setResizable(false);
        //getContentPane().add(new LoadPanel());
        getContentPane().add(jfxPanel);
        setVisible(true);

        Platform.runLater(() -> {
                    WebView webView = new WebView();
                    jfxPanel.setScene(new Scene(webView));
                    WebEngine webEngine = webView.getEngine();
                    webEngine.load("https://leafletjs.com/examples/quick-start/example.html");
                    webEngine.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
                });


    }

}
