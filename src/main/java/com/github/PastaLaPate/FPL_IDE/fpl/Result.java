package com.github.PastaLaPate.FPL_IDE.fpl;

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
    }

    public void addLine(String line) {
        label.setText(label.getText() + "<br>" + line);
    }
}
