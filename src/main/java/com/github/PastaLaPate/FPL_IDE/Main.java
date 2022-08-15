package com.github.PastaLaPate.FPL_IDE;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {

        Downloader downloader = new Downloader();
        downloader.initDownload();

        MainPanel panel = new MainPanel();
        panel.init();
    }
}
