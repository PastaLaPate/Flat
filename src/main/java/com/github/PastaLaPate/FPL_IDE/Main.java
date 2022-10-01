package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.ui.panels.Download.DownloadScreen;

import java.io.*;

public class Main {

    public static final String VERSION = "v0.3.1-alpha";

    public static void main(String[] args) throws IOException {
        SettingsManager manager = new SettingsManager();
        manager.init();
        DownloadScreen downloadScreen = new DownloadScreen();
        downloadScreen.init();
        downloadScreen.downloadIDE();
        downloadScreen.downloadFPL();
    }

}
