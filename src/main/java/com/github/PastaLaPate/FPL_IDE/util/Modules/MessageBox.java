package com.github.PastaLaPate.FPL_IDE.util.Modules;

import com.github.PastaLaPate.FPL_IDE.util.Downloader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageBox {
    private final String downloadURL;
    private final String path;

    private final String filename = "msgbox.exe";

    {
        try {
            downloadURL = "https://pastalapate.github.io/assets/msgbox.exe";
            path = Downloader.getPathFolder();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MessageBox() {
        try {
            download();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(List<String> argsL) {
        StringBuilder args = new StringBuilder();
        for (String arg:argsL) {
            args.append(" ").append(arg);
        }
        String command = path + filename + " " + args;
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addViewMessage(String name) {
        ArrayList<String> args = new ArrayList<>();
        args.add(name);
        execute(args);
    }

    public void download() throws IOException {
        if (!isDownloaded()) {
            new Downloader().downloadFile(downloadURL, filename, path);
        }
    }

    public boolean isDownloaded() {
        return new File(path + filename).exists();
    }
}

