package com.github.PastaLaPate.FPL_IDE.ui.panels.Files;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.interfaces.listeners.FilePaneListener;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Files {

    private FilePaneListener listener;
    private ScrollPane sPane;
    private JList<Object> list;
    private List<String> strings;
    private int selection;

    public void setListener(FilePaneListener listener) {
        this.listener = listener;
    }

    public ScrollPane init(JFrame f) {
        sPane = new ScrollPane();
        sPane.setSize(new Dimension(100, f.getHeight()));
        list = new JList<>();
        strings = new ArrayList<>();
        f.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                sPane.setSize(100, f.getHeight());
            }

            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        list.setForeground(Constants.TEXT);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setDragEnabled(false);
        list.setSelectedIndex(0);
        list.setSize(100, f.getHeight());
        list.addListSelectionListener(e -> {
            if (selection == list.getSelectedIndex()) {
                listener.fileClicked((String) list.getSelectedValue());
            } else {
                selection = list.getSelectedIndex();
            }
        });
        list.setBackground(Constants.BACKGROUND);
        try {
            setFolder(Downloader.getPathFolder());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        list.setListData(strings.toArray());
        list.setFont(new Font("Monospaced",Font.BOLD,9));
        sPane.add(list);
        sPane.setBackground(Constants.BACKGROUND);
        sPane.setVisible(true);
        return sPane;
    }

    public void setFolder(String path) {
        strings.clear();
        addAllOfFolder(path, true, 0);
        list.setListData(strings.toArray());
    }

    public void addAllOfFolder(String path, boolean pathOfFolders, int numOfFolder0) {
        File directory = new File(path);
        if (pathOfFolders) {
            strings.add("📁" + directory.getPath());
        } else {
            strings.add("📁" + directory.getName());
        }
        List<File> files = getAllFilesOfDirectory(directory);
        for (File file : files) {
            if (file.isDirectory()) {
                addAllOfFolder(file.getPath(), false, numOfFolder0 + 1);
            } else {
                if (numOfFolder0 != 0) {
                    strings.add("  ".repeat(Math.max(0, numOfFolder0)) + "📄" + file.getName());
                } else {
                    strings.add("📄" + file.getName());
                }
            }
        }
    }

    public List<File> getAllFilesOfDirectory(File directory) {
        List<File> files = new ArrayList<>();
        String[] contents = directory.list();
        assert contents != null;
        for (String content : contents) {
            String path = directory.getPath() + File.separator + content;
            Logger.log(path);
            files.add(new File(path));
        }
        return files;
    }

    public void addFile(String fileName) {
        strings.add(fileName);
        list.setListData(strings.toArray());
    }

}
