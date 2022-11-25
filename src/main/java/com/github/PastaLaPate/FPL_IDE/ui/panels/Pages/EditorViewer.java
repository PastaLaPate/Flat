package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Console;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Editor;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.FileView;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import javafx.geometry.VPos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditorViewer extends Panel {

    TabPane tabPane;

    List<Editor> editors;
    static Editor selectedEditor;

    public EditorViewer(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "EditorViewer";
    }

    public static void saveCurrentFile() {
        Saver saver = new Saver();
        String textToSave = selectedEditor.getText();
        String path = selectedEditor.getFile().getPath();
        saver.saveFile(path, textToSave);
    }


    public void addEditor(FileView file) {
        selectedEditor = new Editor(this.panelManager, file.getFile());
        editors.add(selectedEditor);
        tabPane.getTabs().add(new Tab(file.getFile().getName(), selectedEditor.getLayout()));
    }

    public Editor getCurrentEditor() {
        return selectedEditor;
    }

    @Override
    public void initComponents() {
        super.initComponents();
        editors = new ArrayList<>();
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(VPos.BOTTOM);
        rowConstraints.setMinHeight(500);
        rowConstraints.setMaxHeight(600);
        this.layout.getRowConstraints().addAll(rowConstraints, new RowConstraints());

        try {
            String path = Downloader.getPathFolder() + "main.fpl";
            File file = new File(path);
            selectedEditor = new Editor(this.panelManager, file);
            editors.add(selectedEditor);
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
        tabPane.getTabs().add(new Tab("main.fpl", selectedEditor.getLayout()));
        layout.add(tabPane, 0, 0);
        layout.add(console.getLayout(), 0, 1);
        setCanTakeAllSize(selectedEditor.getLayout());
    }
}
