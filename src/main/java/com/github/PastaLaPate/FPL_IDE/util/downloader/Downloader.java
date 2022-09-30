package com.github.PastaLaPate.FPL_IDE.util.downloader;

import com.github.PastaLaPate.FPL_IDE.Main;
import com.github.PastaLaPate.FPL_IDE.SettingsManager;
import com.github.PastaLaPate.FPL_IDE.util.logger.Level;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downloader {
    public Downloader() {

    }

    public void initDownloadIDE(DownloadHandler downloadHandler) throws IOException {
        Logger.log("Changet stat to DOWNLOAD_IDE", this.getClass(), Level.INFO);
        JsonElement jsonObject = getResulfOfUrl("https://api.github.com/repos/PastaLaPate/FPL_IDE/releases/latest");
        if (isLastedVersionIDE(jsonObject.getAsJsonObject())) {
            Logger.log("Lasted version are already installed", this.getClass(), Level.INFO);
            downloadHandler.fileDownloaded(new DownloadEvent("Finished", 1, 1));
            return;
        }
        Logger.log("Downloading new version", this.getClass(), Level.INFO);
        String assetsUrl = jsonObject.getAsJsonObject().get("assets_url").getAsString();
        JsonArray assets = getResulfOfUrl(assetsUrl).getAsJsonArray();
        int i = 0;
        for (JsonElement jsonElement : assets) {
            String browserDownloadUrl = jsonElement.getAsJsonObject().get("browser_download_url").getAsString();
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            downloadFile(browserDownloadUrl, name, getPathOfJar());
            i++;
            downloadHandler.fileDownloaded(new DownloadEvent(name, i, assets.size()));
        }
        SettingsManager settingsManager = new SettingsManager();
        JsonObject finalSettings = settingsManager.getSettingsJson().getAsJsonObject();
        finalSettings.add("IDE_Version", jsonObject.getAsJsonObject().get("tag_name"));
        settingsManager.setSettingsFile(finalSettings);
    }

    public void initDownloadFPL(DownloadHandler downloadHandler) throws IOException {
        Logger.log("Changed stat to DOWNLOADING_FPL", this.getClass(), Level.INFO);
        JsonElement jsonObject = getResulfOfUrl("https://api.github.com/repos/Program132/French-Programming-Language/releases/latest");
        if (isLastedVersionFPL(jsonObject.getAsJsonObject())) {
            Logger.log("Lasted version are already installed", this.getClass(), Level.INFO);
            downloadHandler.fileDownloaded(new DownloadEvent("Finished", 1, 1));
            return;
        }
        Logger.log("Downloading new version", this.getClass(), Level.INFO);
        String assetsUrl = jsonObject.getAsJsonObject().get("assets_url").getAsString();
        JsonArray assets = getResulfOfUrl(assetsUrl).getAsJsonArray();
        int i = 0;
        for (JsonElement jsonElement : assets) {
            String browserDownloadUrl = jsonElement.getAsJsonObject().get("browser_download_url").getAsString();
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            downloadFile(browserDownloadUrl, name, getPathFolder());
            i++;
            downloadHandler.fileDownloaded(new DownloadEvent(name, i, assets.size()));
        }
        SettingsManager settingsManager = new SettingsManager();
        JsonObject finalSettings = settingsManager.getSettingsJson().getAsJsonObject();
        finalSettings.add("FPL_Version", jsonObject.getAsJsonObject().get("tag_name"));
        settingsManager.setSettingsFile(finalSettings);
    }

    public JsonElement getResulfOfUrl(String urla) throws IOException {
        URL url = new URL(urla);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        http.disconnect();
        return JsonParser.parseString(content.toString());
    }

    public boolean isLastedVersionFPL(JsonObject lastedVersionJO) throws IOException {
        SettingsManager settingsManager = new SettingsManager();
        JsonObject settings = settingsManager.getSettingsJson().getAsJsonObject();
        String lastedversion = lastedVersionJO.get("tag_name").getAsString();
        return settings.get("FPL_Version").getAsString().contains(lastedversion);
    }

    public boolean isLastedVersionIDE(JsonObject lastedVersionJO) {
        String lastedversion = lastedVersionJO.get("tag_name").getAsString();
        return Main.VERSION.contains(lastedversion);
    }

    public static String getPathFolder() throws IOException {
        File file;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win"))
            file = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.fpl_ide");
        else if (os.contains("mac"))
            file = new File(System.getProperty("user.home") + "/Library/Application Support/fpl_ide");
        else
            file = new File(System.getProperty("user.home") + "/.fpl_ide");
        if (!file.exists()) {
            boolean r = file.mkdir();
            if (r) {
                Logger.log("Succesfully created dir", Downloader.class, Level.INFO);
            }
        }
        return file.getCanonicalPath() + "\\";
    }

    public void downloadFile(String url, String fileName, String path) throws IOException {
        URL fetchWebsite = new URL(url);

        File file = new File(getPathFolder() + fileName);

        Logger.log("Downloading " + url + " to " + path + fileName, this.getClass(), Level.INFO);

        try {
            FileUtils.copyURLToFile(fetchWebsite, file);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error during downloading : " + fileName, "ERROR DOWNLOADING", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public String getPathOfJar() {
        URL url = getClass().getClassLoader().getResource("com/");
        assert url != null;
        String path = url.getPath();
        path = path.substring("file:/".length());
        path = path.substring(0, path.length() - 6);
        return path;
    }
}
