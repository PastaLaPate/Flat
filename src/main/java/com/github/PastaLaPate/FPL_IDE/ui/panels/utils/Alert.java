package com.github.PastaLaPate.FPL_IDE.ui.panels.utils;

import com.github.PastaLaPate.FPL_IDE.interfaces.defaults.DefaultAlertResponse;
import com.github.PastaLaPate.FPL_IDE.interfaces.listeners.IAlertListener;

import javax.swing.*;

public class Alert {
    public Alert(String text, IAlertListener listener) {
        int res = JOptionPane.showConfirmDialog(null, text, "File",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(res == 0) {
            listener.buttonPressed(new DefaultAlertResponse(true));
        }
        else if (res == 1){
            listener.buttonPressed(new DefaultAlertResponse(false));
        }
    }
}
