package com.github.PastaLaPate.FPL_IDE;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {

        Downloader downloader = new Downloader();
        SettingsManager manager = new SettingsManager();
        manager.init();
        downloader.initDownload();

        MainPanel panel = new MainPanel();
        panel.init();
    }

}
