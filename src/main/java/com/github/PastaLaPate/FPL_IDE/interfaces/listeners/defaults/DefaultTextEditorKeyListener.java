package com.github.PastaLaPate.FPL_IDE.interfaces.listeners.defaults;

import com.github.PastaLaPate.FPL_IDE.ui.panels.TextEditor;
import com.github.PastaLaPate.FPL_IDE.util.AutoCompletion.Autocompleter;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

public class DefaultTextEditorKeyListener implements KeyListener {

    private final TextEditor tPane;
    private final Autocompleter autocompleter = new Autocompleter();

    public DefaultTextEditorKeyListener(TextEditor textEditor) {
        this.tPane = textEditor;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tPane.getCaretPosition(); i++) {
            stringBuilder.append(tPane.getText().charAt(i));
        }
        String string = stringBuilder.toString();
        String delim = "[ ]+";
        String[] result = string.split(delim);
        autocompleter.setListener(completion -> {
            String lastResult = result[result.length - 1];
            try {
                tPane.getDocument().remove(string.length() - lastResult.length(), lastResult.length() + 1);
                tPane.getDocument().insertString(tPane.getCaretPosition(), completion, null);
            } catch (BadLocationException ex) {
                Logger.log(ex);
            }
            tPane.updateText();
        });
        if (result.length != 0) {
            try {
                if (result.length < 2) {
                    Rectangle2D rectangle = ((JTextPane)(e.getSource())).modelToView2D(tPane.getCaretPosition());
                    autocompleter.autocomplete(result[result.length-1], (int) rectangle.getX(), 15 + (int) rectangle.getY(), (Component) e.getSource());
                } else {
                    Rectangle2D rectangle = ((JTextPane)(e.getSource())).modelToView2D(tPane.getCaretPosition());
                    autocompleter.autocomplete(result[result.length-1], (int) rectangle.getX(), 15 + (int) rectangle.getY(), (Component) e.getSource(), result[result.length-2].toLowerCase());
                }
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        }
        tPane.updateText();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == '{') {
            tPane.insertChar("}");
        } else if (e.getKeyChar() == '(') {
            tPane.insertChar(")");
        } else if (e.getKeyChar() == '\"') {
            tPane.insertChar("\"");
        }
    }

}
