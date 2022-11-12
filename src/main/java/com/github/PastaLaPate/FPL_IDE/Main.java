package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.downloader.DownloadScreen;
import com.github.PastaLaPate.FPL_IDE.util.panels.PanelManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    public static final String VERSION = "v0.3.1-alpha";

    public static void main(String[] args) throws IOException {
        try {
            Class.forName("javafx.application.Application");
            Application.launch(Launcher.class, args);
        } catch (ClassNotFoundException e) {
            /*JOptionPane.showMessageDialog(
                    null,
                    "Erreur:\n" + e.getMessage() + " not found",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );*/
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SettingsManager manager = new SettingsManager();
        manager.init();
        PanelManager pm = new PanelManager(primaryStage);
        DownloadScreen downloadScreen = new DownloadScreen(pm);
        downloadScreen.initComponents();
        downloadScreen.download();
    }
}
