package com.github.PastaLaPate.FPL_IDE.util.panels;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.util.listener.FilePaneListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;

public class Files {

    private FilePaneListener listener;
    private ScrollPane sPane;
    private JList<Object> list;
    private List<String> strings;

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
        list.addListSelectionListener(e -> listener.fileClicked((String) list.getSelectedValue()));
        list.setBackground(Constants.BACKGROUND);
        sPane.add(list);
        sPane.setBackground(Constants.BACKGROUND);
        sPane.setVisible(true);
        return sPane;
    }

    public void addFile(String fileName) {
        strings.add(fileName);
        list.setListData(strings.toArray());
    }

}
