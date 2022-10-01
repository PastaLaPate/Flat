package com.github.PastaLaPate.FPL_IDE.fpl;

import com.github.PastaLaPate.FPL_IDE.ui.panels.TopBar;

import javax.swing.*;

public class Result{

    private JLabel label;

    public Result() {

    }

    public void init() {
        JFrame f = new JFrame("Result");
        label = new JLabel("<html><body>Running <br>");
        f.add(label);
        f.setSize(500, 800);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setJMenuBar(new TopBar(f, null, null, null).createMenuBar(false));
    }

    public void addLine(String line) {
        label.setText(label.getText() + "<br>" + line);
    }
}
