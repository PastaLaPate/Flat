package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.DownloadScreen;
import com.github.PastaLaPate.FPL_IDE.util.SettingsManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SettingsManager settingsManager = new SettingsManager();
        settingsManager.init();
        PanelManager panelManager = new PanelManager(primaryStage);
        DownloadScreen downloadScreen = new DownloadScreen(panelManager);
        panelManager.show(downloadScreen);
        downloadScreen.download();
    }
}
