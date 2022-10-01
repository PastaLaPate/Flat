package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Level;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
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
        String path = Downloader.getPathFolder() + "settings.txt";
        boolean exists = Files.exists(Path.of(path));
        if (!exists) {
            File settings = new File(path);
            boolean r = settings.createNewFile();
            if (!r) {
                Logger.log("CREATING SETTINGS FILE", this.getClass(), Level.ERROR);
                System.exit(0);
            }
            FileWriter writer = new FileWriter(settings);
            writer.write("{FPL_Version=\"V0\",IDE_Version=\"V0\"}");
            writer.close();
            return settings;
        }
        return  new File(path);
    }

    public JsonElement getSettingsJson() throws IOException {
        File file = getSettingsFile();
        FileInputStream input = new FileInputStream(file);
        StringBuilder r = new StringBuilder();
        int code;
        while((code = input.read()) != -1) {
            char ch = (char) code;
            r.append(ch);
        }
        input.close();
        return JsonParser.parseString(r.toString());
    }
}
