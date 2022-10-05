package com.github.PastaLaPate.FPL_IDE.ui.panels.Files;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.interfaces.listeners.FilePaneListener;
import com.github.PastaLaPate.FPL_IDE.interfaces.renderer.FilesRenderer;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Files {

    private FilePaneListener listener;
    private ScrollPane sPane;
    private DefaultListModel<CustomFile> list;
    private JList<CustomFile> jList;

    public static final Icon folder_icon;

    public static final Icon file_icon;

    static {
        try {
            folder_icon = new ImageIcon(ImageIO.read(Objects.requireNonNull(Files.class.getClassLoader().getResource("img/folder.png"))).getScaledInstance(16,16, Image.SCALE_SMOOTH));
            file_icon = new ImageIcon(ImageIO.read(Objects.requireNonNull(Files.class.getClassLoader().getResource("img/file.png"))).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setListener(FilePaneListener listener) {
        this.listener = listener;
    }

    public ScrollPane init(JFrame f) {
        sPane = new ScrollPane();
        sPane.setSize(new Dimension(100, f.getHeight()));
        jList = new JList<>();
        list = new DefaultListModel<>();
        jList.setCellRenderer(new FilesRenderer(listener));
        try {
            list.addElement(new CustomFile(new File(Downloader.getPathFolder() + "main.fpl")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        jList.setModel(list);
        sPane.add(jList);
        return sPane;
    }

    public void setFolder(String path) {
        list.clear();
        addAllOfFolder(path, true, 0);
        jList.updateUI();
    }

    public void addAllOfFolder(String path, boolean pathOfFolders, int numOfFolder0) {
        File directory = new File(path);
        if (pathOfFolders) {
            addFile(new CustomFile(new File(directory.getPath())));
        } else {
            addFile(new CustomFile(new File(directory.getName())));
        }
        List<File> files = getAllFilesOfDirectory(directory);
        for (File file : files) {
            if (file.isDirectory()) {
                addAllOfFolder(file.getPath(), false, numOfFolder0 + 1);
            } else {
                if (numOfFolder0 != 0) {
                    addFile(new CustomFile(file, numOfFolder0));
                } else {
                    addFile(new CustomFile(file));
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

    public void addFile(CustomFile f) {
        list.addElement(f);
    }

    public List<CustomFile> getFiles() {
        List<CustomFile> r = new ArrayList<>();
        for (int i = 0; i < list.getSize(); i++) {
            r.add(list.getElementAt(i));
        }
        return r;
    }
}
