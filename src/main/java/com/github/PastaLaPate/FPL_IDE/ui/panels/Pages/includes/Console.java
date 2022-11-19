package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import javafx.scene.text.Text;

public class Console extends Panel {

    public Console(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Console";
    }

    @Override
    public void initComponents() {
        super.initComponents();
        Text text = new Text("Console");
        this.layout.add(text, 0, 0);
    }
}
