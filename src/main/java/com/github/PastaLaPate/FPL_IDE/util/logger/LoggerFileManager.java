package com.github.PastaLaPate.FPL_IDE.util.logger;

import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import java.io.IOException;

public class LoggerFileManager {

    private static final Saver SAVER = new Saver();
    private static String PATH;

    static {
        try {
            PATH = Downloader.getPathFolder() + "log.txt";
        } catch (IOException e) {
            Logger.log(e);
            System.exit(0);
        }
    }

    public LoggerFileManager() {
        SAVER.saveFile(PATH, "Logger loaded \n");
    }

    public void log(String message) throws IOException {
        String content = SAVER.getFile(PATH) + "\n" + message;
        SAVER.saveFile(PATH, content);
    }
}
