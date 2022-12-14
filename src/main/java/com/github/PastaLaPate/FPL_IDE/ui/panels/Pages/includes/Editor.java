package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.Syntax.SyntaxHighLighter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Editor extends Panel {

    private final SyntaxHighLighter syntaxHighLighter = new SyntaxHighLighter();
    private final File file;

    TextArea area;

    public Editor(PanelManager panelManager, File file) {
        super(panelManager);
        this.panelName = file.getName();
        this.file = file;
        Saver saver = new Saver();
        try {
            area.setText(saver.getFile(file.getPath()));
            Platform.runLater(this::highlight);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        AtomicReference<String> previousChar = new AtomicReference<>();
        area.setOnKeyTyped(event -> {
            if (Objects.equals(event.getCharacter(), "{")) {
                area.insertText(area.getCaretPosition(), "}");
                area.positionCaret(area.getCaretPosition() - 1);
            } else if (Objects.equals(event.getCharacter(), "(")) {
                area.insertText(area.getCaretPosition(), ")");
                area.positionCaret(area.getCaretPosition() - 1);
            }
            previousChar.set(event.getCharacter());
            highlight();
        });
        area.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (Objects.equals(previousChar.get(), "{")) {
                    area.setWrapText(true);
                    area.insertText(area.getCaretPosition(), "    " + System.lineSeparator());
                    area.positionCaret(area.getCaretPosition() - 1);
                }
            } else if (event.getCode() == KeyCode.TAB) {
                String s = "    ";
                area.deleteText(area.getCaretPosition() - 1, area.getCaretPosition());
                area.insertText(area.getCaretPosition(), s);
                event.consume();
            }
        });
        area.setOnScrollFinished(event -> highlight());
        area.setStyle(
                "-fx-control-inner-background: " + rgbColor + ";" );
        area.setFont(Constants.JetBrainsMono);
        layout.add(area, 0, 0);
    }

    public String getText() {
        return area.getText();
    }

    public File getFile() {
        return file;
    }

    private void highlight() {
        syntaxHighLighter.generateSyntax(area);
    }
}
