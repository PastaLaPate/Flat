package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Console extends Panel {

    public Console(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Console";
    }

    //TODO Execute commands

    @Override
    public void initComponents() {
        super.initComponents();
        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setStyle("-fx-text-fill: white");
        Color color = Constants.ELEMENT1;
        double red = color.getRed()*255;
        double blue = color.getBlue()*255;
        double green = color.getGreen()*255;
        String rgbColor = "rgb(" + red + ", " + blue + " , " + green + ")";
        textArea.setStyle(
                "-fx-control-inner-background: " + rgbColor + ";" );
        textArea.setFont(Constants.JetBrainsMono);

        TextField commandField = new TextField();
        commandField.setBackground(new Background(new BackgroundFill(Constants.ELEMENT1,CornerRadii.EMPTY, Insets.EMPTY)));
        commandField.setMaxHeight(50.0D);
        commandField.setStyle("-fx-text-fill: white");
        commandField.setFont(Constants.JetBrainsMono);
        setCanTakeAllSize(textArea);
        this.layout.add(textArea, 0, 0);
        this.layout.add(commandField, 0, 1);
    }
}
