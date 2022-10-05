package com.github.PastaLaPate.FPL_IDE.ui.panels.editor;

import com.github.PastaLaPate.FPL_IDE.Constants;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditorTabsPanel extends JTabbedPane {

    public EditorTabsPanel() {
      super();
      UIManager.put("TabbedPane.selected", Constants.ELEMENT1);
      UIManager.put("TabbedPane.selectedForeground", Constants.TEXT);
      setBackground(Constants.BACKGROUND);
      setForeground(Constants.TEXT);
      SwingUtilities.updateComponentTreeUI(this);
    }

    public String getCurrentFileContent() {
        TextEditor currenttab = (TextEditor) getComponentAt(getSelectedIndex());
        return currenttab.getText();
    }

    public void openFile(File file) {
        if (!opened(file) && !file.getName().contains("\ud83d\udcc1")) {
            TextEditor tab = new TextEditor();
            tab.loadFile(file);
            addTab(file.getName(), tab);
        }
    }

    public boolean opened(File file) {
        for (TextEditor editor : getTabs()) {
            if (editor.getCurrentfile().getName().equals(file.getName())) {
                return true;
            }
        }
        return false;
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
