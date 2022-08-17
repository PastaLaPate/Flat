package com.github.PastaLaPate.FPL_IDE.fpl;

import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Runner {
    public Runner() {

    }

    public void Run() throws IOException {
        System.out.println("[FPL_IDE] [RUNNER] launcher main.fpl");
        String program_File;
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            program_File = "French_Programming_Language.exe";
        } else {
            program_File = "French_Programming_Language.out";
        }
        String command = Downloader.getPathFolder() + program_File + Downloader.getPathFolder() + "main.fpl" ;
        System.out.println("[FPL_IDE] [RUNNER] launching command : " + command);
        Result result = new Result();
        result.init();
        try {
            // Use a ProcessBuilder
            ProcessBuilder pb = new ProcessBuilder(Downloader.getPathFolder() + program_File, Downloader.getPathFolder() + "main.fpl");

            Process p = pb.start();
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                result.addLine(line);
            }
            int r = p.waitFor(); // Let the process finish.
            result.addLine("Finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
