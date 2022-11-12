package com.github.PastaLaPate.FPL_IDE.util.panels;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.Main;
import javafx.stage.Stage;

public class PanelManager {
    private final Stage stage;

    public PanelManager(Stage stage) {
        this.stage = stage;
        this.stage.setMinWidth(1280.0D);
        this.stage.setWidth(1280.0D);
        this.stage.setMinHeight(720.0D);
        this.stage.setHeight(720.0D);
        this.stage.centerOnScreen();
        this.stage.setOnCloseRequest(e -> System.exit(0));
    }

    public void show(IPanel panel) {
        panel.initComponents();
        stage.setScene(panel.getScene());
        stage.setTitle("FPL IDE " + Main.VERSION + " - " + panel.getPanelName());
        stage.show();
        panel.onShow();
    }
}
