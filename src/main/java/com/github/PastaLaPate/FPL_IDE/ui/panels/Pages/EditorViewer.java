package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Console;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Editor;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.FileView;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import javafx.geometry.VPos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.io.IOException;

public class EditorViewer extends Panel {

    TabPane tabPane;

    public EditorViewer(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "EditorViewer";
    }

    public void addEditor(FileView file) {
        tabPane.getTabs().add(new Tab(file.getFile().getName(), new Editor(this.panelManager, file.getFile()).getLayout()));
    }

    @Override
    public void initComponents() {
        super.initComponents();
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(500);
        rowConstraints.setMaxHeight(600);
        this.layout.getRowConstraints().addAll(rowConstraints, new RowConstraints());

        Editor editor;
        try {
            String path = Downloader.getPathFolder() + "main.fpl";
            File file = new File(path);
            editor = new Editor(this.panelManager, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Console console;
        try {
            console = new Console(panelManager);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tabPane = new TabPane();
        tabPane.getTabs().add(new Tab("main.fpl", editor.getLayout()));
        layout.add(tabPane, 0, 0);
        layout.add(console.getLayout(), 0, 1);
        setCanTakeAllSize(editor.getLayout());
    }
}
