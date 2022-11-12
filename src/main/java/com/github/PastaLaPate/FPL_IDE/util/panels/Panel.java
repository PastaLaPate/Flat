package com.github.PastaLaPate.FPL_IDE.util.panels;

import com.github.PastaLaPate.FPL_IDE.Constants;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;

public abstract class Panel implements IPanel {

    protected final Group layout = new Group();
    protected String panelName;

    protected PanelManager panelManager;

    public Panel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }

    @Override
    public void onShow() {
        final FadeTransition transition = new FadeTransition(Duration.seconds(1), this.layout);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.setAutoReverse(true);
        transition.play();
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public Scene getScene() {
        Scene scene = new Scene(layout);
        scene.setFill(Constants.BACKGROUND);
        return scene;
    }
}
