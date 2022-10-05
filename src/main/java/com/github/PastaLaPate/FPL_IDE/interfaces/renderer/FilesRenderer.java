package com.github.PastaLaPate.FPL_IDE.interfaces.renderer;

import com.github.PastaLaPate.FPL_IDE.MainPanel;
import com.github.PastaLaPate.FPL_IDE.interfaces.listeners.FilePaneListener;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files.CustomFile;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files.FileType;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files.Files;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilesRenderer extends JLabel implements ListCellRenderer<CustomFile>{
    private final FilePaneListener filePaneListener;

    public FilesRenderer(FilePaneListener filePaneListener) {
        this.filePaneListener = filePaneListener;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends CustomFile> list, CustomFile value, int index, boolean isSelected, boolean cellHasFocus) {

        FileType fileType = value.getFileType();
        setText(value.toString());
        if (fileType == FileType.FILE) {
            setIcon(Files.file_icon);
        } else {
            setIcon(Files.folder_icon);
        }

        if (isSelected) {
            filePaneListener.fileClicked(value.getName());
        }

        return this;
    }
}
