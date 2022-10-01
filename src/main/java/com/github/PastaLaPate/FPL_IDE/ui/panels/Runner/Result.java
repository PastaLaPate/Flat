package com.github.PastaLaPate.FPL_IDE.ui.panels.Runner;

import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

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
        f.setUndecorated(true);
        f.setJMenuBar(new TopBar(f, null, null).createMenuBar(false));
        f.setVisible(true);
        Logger.log("test");
    }

    public void addLine(String line) {
        label.setText(label.getText() + "<br>" + line);
    }
}
