package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Files extends Panel {

    public Files(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Files";
    }

    @Override
    public void initComponents() {
        super.initComponents();
        ListView<String> listViewReference = new ListView<>();
        listViewReference.getItems().add("Test");
        listViewReference.getItems().add("Test2");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");
        listViewReference.getItems().add("Test232");


        setCanTakeAllSize(listViewReference);
        VBox box = new VBox(listViewReference);
        setCanTakeAllSize(box);
        this.layout.add(box, 0, 0);
    }
}
