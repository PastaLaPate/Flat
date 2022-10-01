package com.github.PastaLaPate.FPL_IDE.ui.panels.editor;

import com.github.PastaLaPate.FPL_IDE.Constants;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorTabsPanel extends JTabbedPane {

    public EditorTabsPanel() {
      super();
      setBackground(Constants.BACKGROUND);
      setForeground(Constants.TEXT);
    }

    public String getCurrentFileContent() {
        TextEditor currenttab = (TextEditor) getTabComponentAt(getSelectedIndex());
        return currenttab.getText();
    }

    public void openFile(File file) {
        TextEditor tab = new TextEditor();
        tab.loadFile(file);
        addTab(file.getName(), tab);
    }

    public List<TextEditor> getTabs() {
        List<TextEditor> r = new ArrayList<>();
        int count = getTabCount();
        for (int i = 0; i < count; i++) {
            TextEditor textEditor = (TextEditor) getComponentAt(i);
            r.add(textEditor);
        }
        return r;
    }

}
