package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.EditorViewer;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Files extends Panel {

    private final EditorViewer editorViewer;
    private ListView<FileView> listViewReference;

    public Files(PanelManager panelManager, EditorViewer editorViewer) {
        super(panelManager);
        this.panelName = "Files";
        this.editorViewer = editorViewer;
    }

    public void seeFolder(File folder, int arbo) {
        listViewReference.getItems().add(new FileView(folder.getName(), FileView.FileType.FOLDER, folder, arbo));
        for (File child: Objects.requireNonNull(folder.listFiles())) {
            if (child.isDirectory()) {
                seeFolder(child, arbo + 1);
            } else {
                listViewReference.getItems().add(new FileView(child.getName(), FileView.FileType.FILE, child, arbo + 1));
            }
        }
    }

    @Override
    public void initComponents() {
        super.initComponents();
        listViewReference = new ListView<>();
        listViewReference.setBackground(new Background(new BackgroundFill(Constants.BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY)));
        listViewReference.setOrientation(Orientation.VERTICAL);
        listViewReference.setCellFactory(param -> new ListCell<>(){
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(FileView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.setImage(item.getIcon());
                    imageView.setFitHeight(32);
                    imageView.setFitWidth(32);
                    setText(item.toString());
                    setGraphic(imageView);
                    setFont(Constants.JetBrainsMono);
                }
                setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        });
        listViewReference.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !event.isConsumed()) {
                FileView file = listViewReference.getSelectionModel().getSelectedItem();
                if (file != null && !file.getFile().isDirectory()) {
                    editorViewer.addEditor(file);
                }
            }
        });
        setCanTakeAllSize(listViewReference);
        try {
            seeFolder(new File(Downloader.getPathFolder()), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.layout.add(listViewReference, 0, 0);
    }
}
