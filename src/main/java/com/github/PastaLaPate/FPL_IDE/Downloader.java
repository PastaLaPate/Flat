package com.github.PastaLaPate.FPL_IDE;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Downloader {
    public Downloader() {

    }

    public void initDownload(DownloadHandler downloadHandler) throws IOException {
        System.out.println("[FPL_IDE] Changed state to DOWNLOADING_FPL");
        JsonElement jsonObject = getResulfOfUrl("https://api.github.com/repos/Program132/French-Programming-Language/releases/latest");
        if (isLastedVersion(jsonObject.getAsJsonObject())) {
            System.out.println("[FPL_IDE] Lasted version are already installed");
            downloadHandler.fileDownloaded(new DownloadEvent("Finished", 1, 1));
            return;
        }
        System.out.println("[FPL_IDE] [DOWNLOADER] Downloading new version");
        String assetsUrl = jsonObject.getAsJsonObject().get("assets_url").getAsString();
        JsonArray assets = getResulfOfUrl(assetsUrl).getAsJsonArray();
        int i = 0;
        for (JsonElement jsonElement : assets) {
            String browserDownloadUrl = jsonElement.getAsJsonObject().get("browser_download_url").getAsString();
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            downloadFile(browserDownloadUrl, name);
            i++;
            downloadHandler.fileDownloaded(new DownloadEvent(name, i, assets.size()));
        }
        SettingsManager settingsManager = new SettingsManager();
        JsonObject finalSettings = new JsonObject();
        finalSettings.add("FPL_Version", jsonObject.getAsJsonObject().get("tag_name"));
        settingsManager.setSettingsFile(finalSettings);
    }

    public JsonElement getResulfOfUrl(String urla) throws IOException {
        URL url = new URL(urla);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        http.disconnect();
        return JsonParser.parseString(content.toString());
    }

    public boolean isLastedVersion(JsonObject lastedVersionJO) throws IOException {
        SettingsManager settingsManager = new SettingsManager();
        JsonObject settings = settingsManager.getSettingsJson().getAsJsonObject();
        String lastedversion = lastedVersionJO.get("tag_name").getAsString();
        if (settings.get("FPL_Version").getAsString().contains(lastedversion)) {
            return true;
        }
        return false;
    }

    public String getPathFolder() {
        File file = new File(System.getenv("APPDATA") + "/.fpl_ide");
        file.mkdir();
        return System.getenv("APPDATA") + "/.fpl_ide/";
    }

    public void downloadFile(String url, String fileName) throws IOException {
        URL fetchWebsite = new URL(url);

        File file = new File(getPathFolder() + fileName);

        System.out.println("[FPL_IDE] [DOWNLOADER] Downloading " + url + " to " + getPathFolder() + fileName);

        FileUtils.copyURLToFile(fetchWebsite, file);
    }
}
