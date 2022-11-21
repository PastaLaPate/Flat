package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.Modules.MessageBox;
import javafx.application.Application;

public class Main {

    public static final String VERSION = "v0.3.1-alpha";

    public static void main(String[] args) {
        try {
            Class.forName("javafxx.application.Application");
            Application.launch(Launcher.class, args);
        } catch (ClassNotFoundException e) {
            new MessageBox().addViewMessage("JAVAFX not installed");
        }
    }
}
