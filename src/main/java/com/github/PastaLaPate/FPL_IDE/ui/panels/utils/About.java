package com.github.PastaLaPate.FPL_IDE.ui.panels.utils;

import com.github.PastaLaPate.FPL_IDE.SettingsManager;

import javax.swing.*;
import java.io.IOException;

public class About extends JFrame {
    public About() throws IOException {
        setTitle("About");
        JPanel jPanel = new JPanel();
        SettingsManager settingsManager = new SettingsManager();
        JLabel jLabel = new JLabel("FPL_VERSION : " + settingsManager.getSettingsJson().getAsJsonObject().get("FPL_Version"));
        JLabel jLabel1 = new JLabel("Create by : PastaLaPate");
        jPanel.add(jLabel);
        jPanel.add(jLabel1);
        setLayout(null);
        setContentPane(jPanel);
        setLocationRelativeTo(null);
        setSize(800, 400);
        setVisible(true);
    }
}
