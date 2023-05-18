package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.EditorViewer;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

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
        listViewReference.setEditable(true);

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
                    ContextMenu menu = new ContextMenu();
                    MenuItem rename = new MenuItem("Rename");
                    rename.setOnAction(event -> {
                        TextInputDialog dialog = new TextInputDialog(item.getFileName());
                        dialog.setTitle("Rename File");
                        dialog.setHeaderText("Enter a new name for the file:");
                        dialog.setContentText("New Name:");
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(newName -> {
                            File newFile = new File(item.getFile().getParent() + File.separator + newName);
                            if (item.getFile().renameTo(newFile)) {
                                item.setFileName(newName);
                                item.update(newFile);
                                setText(item.toString());
                                getListView().refresh();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error Renaming File");
                                alert.setHeaderText("Could not rename file:");
                                alert.setContentText("The file may be in use or you may not have permission to modify it.");
                                alert.showAndWait();
                            }
                        });
                    });
                    MenuItem newFile = new MenuItem("New file/directory");
                    newFile.setOnAction(event -> {
                        if (item.getFile().isDirectory()) {
                            TextInputDialog dialog = new TextInputDialog(item.getFileName());
                            dialog.setTitle("Create file/directory");
                            dialog.setHeaderText("Enter a name for the file/directory:");
                            dialog.setContentText("Name:");

                            ToggleGroup fileTypeGroup = new ToggleGroup();
                            RadioButton fileRadioButton = new RadioButton("File");
                            RadioButton directoryRadioButton = new RadioButton("Directory");
                            fileRadioButton.setToggleGroup(fileTypeGroup);
                            directoryRadioButton.setToggleGroup(fileTypeGroup);

                            GridPane gridPane = new GridPane();
                            gridPane.setHgap(10);
                            gridPane.setHgap(10);
                            gridPane.setPadding(new Insets(20, 150, 10, 10));

                            gridPane.add(new Label("File type : "), 0, 0);
                            gridPane.add(fileRadioButton, 1, 0);
                            gridPane.add(directoryRadioButton, 2, 0);
                            gridPane.add(new Label("Name:"), 0, 1);
                            gridPane.add(dialog.getEditor(), 1, 1, 2, 1);

                            dialog.getDialogPane().setContent(gridPane);
                            dialog.getEditor().requestFocus();

                            Optional<String> result = dialog.showAndWait();
                            result.ifPresent(newName -> {
                                Toggle toggle = fileTypeGroup.getSelectedToggle();
                                File addedFile = new File(item.getFile().getPath() + File.separator + newName);
                                FileView.FileType fileType;

                                boolean Cresult;
                                if (toggle.equals(fileRadioButton)) {
                                    fileType = FileView.FileType.FILE;
                                    try {
                                        Cresult = addedFile.createNewFile();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    fileType =  FileView.FileType.FOLDER;
                                    Cresult = addedFile.mkdir();
                                }
                                if (!Cresult) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error creating folder/file");
                                    alert.setHeaderText("Could not create folder/file:");
                                    alert.setContentText("The file may be in use or you may not have permission to modify it.");
                                    alert.showAndWait();
                                }

                                getListView().getItems().add(
                                        new FileView(newName, fileType, addedFile, item.arbo + 1)
                                );
                                getListView().refresh();
                            });
                        }
                    });
                    MenuItem removeFile = new MenuItem("Remove");
                    removeFile.setOnAction(event -> {
                        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmation.setTitle("Confirmation of delete");
                        confirmation.setHeaderText("Confirm delete of " + item.getFile().getName());
                        confirmation.setContentText("Are you sure to delete this file ?");

                        Optional<ButtonType> result = confirmation.showAndWait();
                        if (result.get() == ButtonType.OK) {
                            if (!item.getFile().delete()) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Error deleting file");
                                alert.setHeaderText("Cannot delete the file:");
                                alert.setContentText("The file may be in use or you may not have permission to modify it.");
                                alert.showAndWait();
                            } else {
                                getListView().refresh();
                                getListView().getItems().remove(item);
                            }
                        }
                    });
                    if (item.getFile().isDirectory()) {
                        menu.getItems().add(newFile);
                    }
                    menu.getItems().addAll(rename, removeFile);
                    setContextMenu(menu);
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2 && !event.isConsumed() && event.getButton() == MouseButton.PRIMARY) {
                            if (!item.getFile().isDirectory()) {
                                editorViewer.addEditor(item);
                            }
                        }
                    });
                    setText(item.toString());
                    setGraphic(imageView);
                    setFont(Constants.JetBrainsMono);
                }
                setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
            }
        });
        setCanTakeAllSize(listViewReference);
        try {
            File defaultDirectory = new File(Downloader.getPathFolder() + "/workspace");
            if (!defaultDirectory.exists()) {
                defaultDirectory.mkdir();
            }
            seeFolder(defaultDirectory, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.layout.add(listViewReference, 0, 0);
    }
}
