package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import javafx.scene.image.Image;

import java.io.File;

public class FileView {
    public final int arbo;
    private Image icon;
    private String fileName;

    private File file;

    public FileView(String fileName, FileType fileType, File file, int arbo) {
        this.file = file;
        this.fileName = fileName;
        this.arbo =arbo;
        if (fileType == FileType.FILE) {
            icon = new Image("images/file.png");
        } else if (fileType == FileType.FOLDER) {
            icon = new Image("images/folder.png");
        }
    }

    public void update(File newFile) {
        this.file = newFile;
        this.fileName = newFile.getName();
        if (newFile.isDirectory()) {
            icon = new Image("images/folder.png");
        } else {
            icon = new Image("images/file.png");
        }
    }


    public String getFileName() {
        return fileName;
    }

    public Image getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "   ".repeat(Math.max(0, arbo)) + fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    enum FileType {
        FOLDER, FILE
    }
}
