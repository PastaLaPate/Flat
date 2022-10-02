package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.EditorTabsPanel;
import com.github.PastaLaPate.FPL_IDE.ui.panels.utils.FrameUtil;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files.Files;
import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JFrame{

    public EditorTabsPanel editorTabsPanel;
    private JFrame f;
    private TopBar topBar;

    private Files files;


    public void init() {
        SwingUtilities.invokeLater(() -> {
            //BASE
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            f = this;
            setUndecorated(true);
            setResizable(true);
            getContentPane().setBackground(Constants.BACKGROUND);
            setTitle("FPL_IDE - Main.fpl");

            //CONTENT
            //TEXT EDITOR
            editorTabsPanel = new EditorTabsPanel();
            try {
                File mainFile = new File(Downloader.getPathFolder() + "main.fpl");
                Logger.log("ABSOLUTE PATH : " + mainFile.getAbsolutePath());
                editorTabsPanel.openFile(mainFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //FILES
            files = new Files();

            // TOP BAR
            topBar = new TopBar(f, files, this);
            setJMenuBar(topBar.createMenuBar(true));

            //LOAD FILE
            ScrollPane sPane = files.init(this);
            files.setListener(fileName -> {
                //LOAD NEW FILE
                File file1;
                fileName = fileName.substring(2);
                Logger.log("File name : " + fileName);
                try {
                    file1 = new File(Downloader.getPathFolder() + fileName);
                    editorTabsPanel.openFile(file1);
                    topBar.currentFileName = fileName;
                } catch (IOException e) {
                    Logger.log(e);
                }
            });
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sPane, editorTabsPanel);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(100);
            topBar.setSplitPane(splitPane);

            sPane.setMinimumSize(new Dimension(100, 100));
            sPane.setMaximumSize(new Dimension(500, 300));

            editorTabsPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

            setContentPane(splitPane);
            pack();
            FrameUtil.makeMaximized(this);
            setVisible(true);
        });
    }

}
