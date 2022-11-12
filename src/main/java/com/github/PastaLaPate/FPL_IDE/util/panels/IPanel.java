package com.github.PastaLaPate.FPL_IDE.util.panels;

import javafx.scene.Scene;

public interface IPanel {
    String getPanelName();
    Scene getScene();
    void initComponents();
    void onShow();
}
