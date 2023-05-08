package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.Logger.Logger;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class DownloadScreen extends Panel {

    private ProgressBar progressBar;
    private Label label;

    public DownloadScreen(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Downloading...";
    }

    public void download() {
        /*try {
            //downloadIDE();
            //downloadFPL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        checkStep(new Downloader.DownloadEvent(Downloader.DOWNLOAD_STEP.FINISHED, "FINISHED", 1, 1));
    }

    private void downloadIDE() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadIDE(this::checkStep);
    }

    private void downloadFPL() throws IOException {
        Downloader downloader = new Downloader();
        downloader.initDownloadFPL(this::checkStep);
    }

    private void checkStep(Downloader.DownloadEvent e) {
        if (e.getDownload_step() == Downloader.DOWNLOAD_STEP.FETCHING_JSON) {
            label.setText("fetching json...".toUpperCase());
        }else if (e.getDownload_step() == Downloader.DOWNLOAD_STEP.CHECKING) {
            label.setText("checking installation...".toUpperCase());
        } else if (e.getDownload_step() == Downloader.DOWNLOAD_STEP.FINISHED) {
            label.setText("finished ...".toUpperCase());
            panelManager.show(new MainPanel(panelManager));
        } else if (e.getDownload_step() == Downloader.DOWNLOAD_STEP.DOWNLOADING) {
            label.setText("Downloading " + e.getFileName() + " " + e.getFileNumber() + "/" + e.getMaxfiles());
            progressBar.setProgress((double) e.getFileNumber() / e.getMaxfiles());
            if (e.getFileNumber() == e.getMaxfiles()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.log(ex);
                }
            }
        }
    }

    @Override
    public void initComponents() {
        super.initComponents();
        label = new Label("Downloading ...");
        label.setTextFill(Constants.TEXT);
        label.setMinHeight(100.0D);
        label.setStyle("-fx-font: 50 arial");
        progressBar = new ProgressBar();
        progressBar.setPrefSize(300.0D, 50.0D);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(progressBar, HPos.CENTER);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Constants.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.addRow(0, label);
        layout.addRow(1, progressBar);
    }


}
