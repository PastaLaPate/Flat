package com.github.PastaLaPate.FPL_IDE.util.AutoCompletion;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PopupF {

    private Popup popup;
    private final PopupFactory popupFactory;
    private final JFrame f;
    private final JPanel p;
    private final List<String> completions;
    private AutoCompleteListener listener = null;

    public PopupF(JFrame jFrame) {
        this.f = jFrame;
        this.p = new JPanel();
        popupFactory = new PopupFactory();
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

    public void show(double x, double y) {
        for (String name : completions) {
            JButton button = new JButton(name);
            button.setBorder(null);
            if (listener != null) {
                button.addActionListener(e -> listener.completionClicked(name));
            }
            button.addActionListener(e -> popup.hide());
            p.add(button);
        }
        popup = popupFactory.getPopup(f, p, (int) x, (int) y);
        popup.show();
    }

    public void hide() {
        popup.hide();
    }
}
