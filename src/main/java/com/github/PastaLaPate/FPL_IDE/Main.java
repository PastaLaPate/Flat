package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.downloader.DownloadScreen;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        SettingsManager manager = new SettingsManager();
        manager.init();
        DownloadScreen downloadScreen = new DownloadScreen();
        downloadScreen.init();
        downloadScreen.download();
    }

}
