package com.github.PastaLaPate.FPL_IDE.ui.panels.Download;

import com.github.PastaLaPate.FPL_IDE.MainPanel;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.TopBar;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DownloadScreen {

    private JFrame j;
    private final JProgressBar progressBar = new JProgressBar(0, 1);

    public DownloadScreen() {

    }

    public void init() {
        j = new JFrame("DOWNLOADING");
        progressBar.setString("Downloading 0/N/A");
        progressBar.setBounds(40,40,160,30);
        progressBar.setStringPainted(true);
        j.add(progressBar);
        j.setSize(new Dimension(400, 400));
        j.setLocationRelativeTo(null);
        j.setUndecorated(true);
        j.setJMenuBar(new TopBar(j, null, null).createMenuBar(false));
        j.setVisible(true);
    }

    public void downloadIDE() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadIDE(e -> {
            progressBar.setString("Downloading " + e.getFileName() + " " + e.getFileNumber() + "/" + e.getMaxfiles());
            progressBar.setValue(e.getFileNumber() / e.getMaxfiles());
            if (e.getFileNumber() == e.getMaxfiles()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.log(ex);
                }
            }
        });
    }

    public void downloadFPL() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadFPL(e -> {
            progressBar.setString("Downloading " + e.getFileName() + " " + e.getFileNumber() + "/" + e.getMaxfiles());
            progressBar.setValue(e.getFileNumber() / e.getMaxfiles());
            if (e.getFileNumber() == e.getMaxfiles()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.log(ex);
                }
                j.setVisible(false);
                MainPanel panel = new MainPanel();
                panel.init();
            }
        });
    }
}
