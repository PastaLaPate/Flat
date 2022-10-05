package com.github.PastaLaPate.FPL_IDE.ui.panels.Files;

import java.io.File;
import java.nio.file.Path;

public class CustomFile {

    private final File file;
    private final String name;

    private final FileType fileType;

    private final String viewName;

    public CustomFile(File f) {
        this.file = f;
        this.name = f.getName();
        this.viewName = f.getName();
        if (f.isDirectory()) {
            this.fileType = FileType.FOLDER;
        } else {
            this.fileType = FileType.FILE;
        }
    }

    public CustomFile(File f, int numOfFolder) {
        this.file = f;
        this.name = f.getName();
        this.viewName = "  ".repeat(Math.max(0, numOfFolder)) + file.getName();
        if (f.isDirectory()) {
            this.fileType = FileType.FOLDER;
        } else {
            this.fileType = FileType.FILE;
        }
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public FileType getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return viewName;
    }
}
