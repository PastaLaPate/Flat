package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages;

import com.github.PastaLaPate.FPL_IDE.ui.Dimension;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Files;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;

public class MainPanel extends Panel {

    public MainPanel(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Editor";
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(720, 1080);
    }

    @Override
    public void initComponents() {
        super.initComponents();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.LEFT);
        columnConstraints.setMinWidth(350);
        columnConstraints.setMaxWidth(400);
        this.layout.getColumnConstraints().addAll(columnConstraints, new ColumnConstraints());

        layout.setGridLinesVisible(true);
        layout.setHgap(10);

        EditorViewer editorViewer = new EditorViewer(panelManager);
        Files files = new Files(panelManager,  editorViewer);
        layout.add(files.getLayout(), 0, 0);
        layout.add(editorViewer.getLayout(), 1, 0);
        setCanTakeAllSize(files.getLayout());
        setCanTakeAllSize(editorViewer.getLayout());
    }
}
