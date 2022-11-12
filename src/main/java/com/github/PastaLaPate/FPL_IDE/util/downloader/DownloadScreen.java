package com.github.PastaLaPate.FPL_IDE.util.downloader;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.MainPanel;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.panels.Panel;
import com.github.PastaLaPate.FPL_IDE.util.panels.PanelManager;
import com.github.PastaLaPate.FPL_IDE.util.panels.TopBar;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public class DownloadScreen extends Panel {

    private final ProgressBar progressBar = new ProgressBar();
    private final Label label = new Label();

    public DownloadScreen(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Downloading...";
    }

    public void download() {
        try {
            downloadIDE();
            downloadFPL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //panelManager.show(new MainPanel(panelManager));
    }

    private void downloadIDE() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadIDE(e -> {
            label.setText("Downloading " + e.getFileName() + " " + e.getFileNumber() + "/" + e.getMaxfiles());
            progressBar.setProgress(e.getFileNumber() / e.getMaxfiles());
            if (e.getFileNumber() == e.getMaxfiles()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.log(ex);
                }
            }
        });
    }

    private void downloadFPL() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadFPL(e -> {
            label.setText("Downloading " + e.getFileName() + " " + e.getFileNumber() + "/" + e.getMaxfiles());
            progressBar.setProgress(e.getFileNumber() / e.getMaxfiles());
            if (e.getFileNumber() == e.getMaxfiles()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.log(ex);
                }
            }
        });
    }

    @Override
    public void initComponents() {
        TilePane tp = new TilePane();
        tp.setAlignment(Pos.CENTER);
        label.setTextFill(Constants.TEXT);
        tp.getChildren().addAll(label, progressBar);
        layout.getChildren().add(tp);
    }
}
