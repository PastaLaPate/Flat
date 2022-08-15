package com.github.PastaLaPate.FPL_IDE;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SettingsManager {

    private Downloader downloader = new Downloader();

    public void init() throws IOException {
        getSettingsFile();
    }

    public void setSettingsFile(JsonObject content) throws IOException {
        String contentS = content.toString();
        FileWriter writer = new FileWriter(getSettingsFile());
        writer.write(contentS);
        writer.close();
    }

    public File getSettingsFile() throws IOException {
        String path = downloader.getPathFolder() + "settings.txt";
        boolean exists = Files.exists(Path.of(path));
        if (!exists) {
            File settings = new File(path);
            settings.createNewFile();
            FileWriter writer = new FileWriter(settings);
            writer.write("{FPL_Version=\"V0\"}");
            writer.close();
            return settings;
        }
        return  new File(path);
    }

    public JsonElement getSettingsJson() throws IOException {
        File file = getSettingsFile();
        FileInputStream input = new FileInputStream(file);
        String r = "";
        int code;
        while((code = input.read()) != -1) {
            char ch = (char) code;
            r = r + ch;
        }
        input.close();
        return JsonParser.parseString(r);
    }
}
