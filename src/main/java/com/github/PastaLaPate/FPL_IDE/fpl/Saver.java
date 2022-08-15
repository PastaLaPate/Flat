package com.github.PastaLaPate.FPL_IDE.fpl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public class Saver {
    public void saveFile(String path, String content) {
        byte[] bytes = content.getBytes();
        try (OutputStream out = new FileOutputStream(new File(path))) {
            out.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("[FPL_IDE] [SAVER] Saved " + path + " with " + content);
    }

    public String getFile(String path) throws IOException {
        FileInputStream input = new FileInputStream(new File(path));
        String r = "";
        int code;
        while((code = input.read()) != -1) {
            char ch = (char) code;
            r = r + ch;
        }
        input.close();
        return r;
    }
}
