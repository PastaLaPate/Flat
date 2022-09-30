package com.github.PastaLaPate.FPL_IDE.util.panels;

import javax.swing.*;
import java.awt.*;

public class Alert {
    public Alert(String text, IAlertListener listener) {
        ImageIcon icon = new ImageIcon("E −\\new.PNG");
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(250, 100));
        panel.setLayout(null);
        JLabel label2 = new JLabel(text);
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(20, 80, 200, 20);
        panel.add(label2);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 200));
        int res = JOptionPane.showConfirmDialog(null, panel, "File",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);
        if(res == 0) {
            listener.yesPressed();
        }
        else if (res == 1){
            listener.noPressed();
        }
    }
}
