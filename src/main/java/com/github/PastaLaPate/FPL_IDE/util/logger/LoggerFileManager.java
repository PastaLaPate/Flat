package com.github.PastaLaPate.FPL_IDE.util.logger;

import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import java.io.IOException;

public class LoggerFileManager {

    private static final Saver SAVER = new Saver();

    public static void log(String message) throws IOException {
        String path = Downloader.getPathFolder() + "log.txt";
        SAVER.saveFile(path, "log.txt \n");
        String content = SAVER.getFile(path) + "\n" + message;
        SAVER.saveFile(path, content);
    }
}
