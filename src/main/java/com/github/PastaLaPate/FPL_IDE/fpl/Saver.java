package com.github.PastaLaPate.FPL_IDE.fpl;

import com.github.PastaLaPate.FPL_IDE.util.logger.Level;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import java.io.*;

public class Saver {
    public void saveFile(String path, String content) {
        byte[] bytes = content.getBytes();
        try (OutputStream out = new FileOutputStream(path)) {
            out.write(bytes);
        } catch (IOException e) {
            Logger.log(e);
        }
    }

    public String getFile(String path) throws IOException {
        FileInputStream input = new FileInputStream(path);
        StringBuilder r = new StringBuilder();
        int code;
        while((code = input.read()) != -1) {
            char ch = (char) code;
            r.append(ch);
        }
        input.close();
        return r.toString();
    }
}
