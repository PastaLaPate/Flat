package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Console;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Editor;
import javafx.geometry.VPos;
import javafx.scene.layout.RowConstraints;

public class EditorViewer extends Panel {

    public EditorViewer(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "EditorViewer";
    }

    @Override
    public void initComponents() {
        super.initComponents();
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(500);
        rowConstraints.setMaxHeight(600);
        this.layout.getRowConstraints().addAll(rowConstraints, new RowConstraints());

        Editor editor = new Editor(this.panelManager);
        Console console = new Console(panelManager);
        layout.add(editor.getLayout(), 0, 0);
        layout.add(console.getLayout(), 0, 1);
        setCanTakeAllSize(editor.getLayout());
    }
}
