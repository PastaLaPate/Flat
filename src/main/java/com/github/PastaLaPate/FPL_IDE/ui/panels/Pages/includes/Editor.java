package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.Main;
import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.util.Logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.Syntax.SyntaxHighLighter;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Editor extends Panel {

    boolean isSelected;
    SyntaxHighLighter syntaxHighLighter = new SyntaxHighLighter();

    TextArea area;

    public Editor(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "Editor";
    }

    @Override
    public void initComponents() {
        area = new TextArea();
        Color color = Constants.BACKGROUND;
        double red = color.getRed()*255;
        double blue = color.getBlue()*255;
        double green = color.getGreen()*255;
        String rgbColor = "rgb(" + red + ", " + blue + " , " + green + ")";

        setCanTakeAllSize(area);
        AtomicReference<KeyCode> previousKey = new AtomicReference<>();
        area.setOnKeyTyped(event -> {
            if (Objects.equals(event.getCharacter(), "{")) {
                area.insertText(area.getCaretPosition(), "}");
                area.positionCaret(area.getCaretPosition() - 1);
            }
        });
        area.setOnKeyPressed(event -> {
            Logger.log(event.getCode());
            if (event.getCode() == KeyCode.ENTER) {
                if (Objects.equals(previousKey, KeyCode.BRACELEFT)) {
                    area.setWrapText(true);
                    Logger.log("detected");
                    area.insertText(area.getCaretPosition(), System.lineSeparator() + System.lineSeparator() + "}");
                    area.positionCaret(area.getCaretPosition() - 1);
                }
            }
            previousKey.set(event.getCode());
            highlight();
        });
        area.setOnScrollFinished(event -> highlight());
        area.setStyle(
                "-fx-control-inner-background: " + rgbColor + ";" );
        area.setFont(Font.loadFont(
                Objects.requireNonNull(getClass().getClassLoader().getResource("fonts/EditorFont.ttf")).toExternalForm(),
                20
        ));
        //Logger.log(Font.loadFont("file:resources/fonts/EditorFont.ttf", 120));
        layout.add(area, 0, 0);
    }

    private void highlight() {
        Platform.runLater(() -> syntaxHighLighter.generateSyntax(area));
    }
}
