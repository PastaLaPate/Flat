package com.github.PastaLaPate.FPL_IDE.ui;

import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public abstract class Panel implements IPanel {

    protected final GridPane layout = new GridPane();
    protected String panelName;
    protected PanelManager panelManager;

    protected Dimension dimension;

    public Panel(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.initComponents();
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
    public Dimension getDimension() {
        return new Dimension(720.0D, 360.0D);
    }

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public GridPane getLayout() {
        return layout;
    }

    @Override
    public void initComponents() {
        setCanTakeAllSize(layout);
    }

}
