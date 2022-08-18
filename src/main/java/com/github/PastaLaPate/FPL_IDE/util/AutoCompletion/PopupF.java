package com.github.PastaLaPate.FPL_IDE.util.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopupF {

    private final JPopupMenu popup;
    private final List<String> completions;
    private AutoCompleteListener listener = null;

    public PopupF() {
        popup = new JPopupMenu();
        completions = new ArrayList<>();
    }

    public void addCompletion(String completion) {
        completions.add(completion);
    }

    public void addCompletions(List<String> list) {
        for (String completion : list) {
            addCompletion(completion);
        }
    }

    public void setListener(AutoCompleteListener listener) {
        this.listener = listener;
    }

    public void show(Component invoker,int x, int y) {
        for (String name : completions) {
            JMenuItem menuItem = new JMenuItem(name);
            menuItem.addActionListener(e -> {
                listener.completionClicked(name);
                popup.setVisible(false);
            });
            popup.add(menuItem);
        }
        popup.setFocusable(false);
        popup.setRequestFocusEnabled(false);
        popup.show(invoker, x, y);
    }

    public void hide() {
        popup.setVisible(false);
    }
}
