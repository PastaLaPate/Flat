package com.github.PastaLaPate.FPL_IDE.ui.panels;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;

public class TextEditor extends JTextPane {

    private final Syntax syntax = new Syntax();
    private final Saver saver = new Saver();

    public TextEditor() {
        super();
        setEditable(true);
        setBackground(Constants.BACKGROUND);
        setCaretColor(Constants.TEXT);
        setForeground(Constants.TEXT);
    }

    public void loadFile(String path) {
        try {
            setText(saver.getFile(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        updateText();
    }

    public void insertChar(String chara) {
        try {
            getDocument().insertString(getCaretPosition(),chara, null);
        } catch (BadLocationException ex) {
            Logger.log(ex);
        }
        setCaretPosition(getCaretPosition() - 1);
        updateText();
    }

    public void updateText() {
        generateSyntax();
        updateTabs();
    }

    private void generateSyntax() {
        syntax.generateSyntax(this);
    }

    private void updateTabs() {
        FontMetrics fm = getFontMetrics( getFont() );
        int charWidth = fm.charWidth( ' ' );
        int tabWidth = charWidth * 4;

        TabStop[] tabs = new TabStop[5];

        for (int j = 0; j < tabs.length; j++)
        {
            int tab = j + 1;
            tabs[j] = new TabStop( tab * tabWidth );
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = getDocument().getLength();
        getStyledDocument().setParagraphAttributes(0, length, attributes, false);
    }
}
