package com.github.PastaLaPate.FPL_IDE.util.syntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Syntax {

    // GENERATE SYNTAX
    public void generateSyntax(JTextPane pane) {
        // ALL TYPES PATTERN
        String[] types = {"vide", "entier", "decimal", "texte", "auto"};
        // ALL FUNCTIONS PATTERN
        String[] function = {"envoyer", "definir", "appeler", "renvoyer", "fichier", ";", "(", ")", "{", "}"};
        // ALL VARIABLES PATTERN
        String[] variables = {"variable", "changer", "saisir", "ecrire", "lire", "\""};
        // ALL CHAR PATTER
        String[] chars = {"\"", ";", "'", "->", "=", "+", "-", "{", "}", "(", ")"};

        pane.getHighlighter().removeAllHighlights();

        //HIGHLIGHT TYPES PATTERNS WITH COLOR BLUE
        for (String type : types) {
            highlight(pane, type, new DefaultHighlighter.DefaultHighlightPainter(new Color(0, 139, 139)));
        }
        //HIGHLIGHT FUNCTIONS PATTERNS WITH COLOR YELLOW
        for (String functiona : function) {
            highlight(pane, functiona, new DefaultHighlighter.DefaultHighlightPainter(new Color(184, 134, 11)));
        }
        //HIGHLIGHT VARIABLES PATTERNS WITH COLOR GREEN
        for (String variable : variables) {
            highlight(pane, variable, new DefaultHighlighter.DefaultHighlightPainter(new Color(34, 139, 34)));
        }
        //HIGHLIGHT CHARS PATTERS WITH COLOR RED
        for (String chars1 : chars) {
            highlight(pane,chars1, new DefaultHighlighter.DefaultHighlightPainter(new Color(178, 34, 34)));
        }
    }

    public static void highlight(JTextPane t, String tth, Color c) {
        Style defaultStyle = StyleContext.
                getDefaultStyleContext().
                getStyle(StyleContext.DEFAULT_STYLE);
        t.getStyledDocument().setCharacterAttributes(0, t.getStyledDocument().getLength(), defaultStyle, true);

        String allT;
        try {
            allT = t.getStyledDocument().getText(0, t.getStyledDocument().getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
        //MATCH PATTERN
        Matcher m = compileWildcard(tth).matcher(allT);
        while (m.find()) {
            t.getStyledDocument().setCharacterAttributes(m.start(), m.end(), aset, true);
        }
    }

    public static void highlight(JTextPane textarea, String textToHighlight,
                                 DefaultHighlighter.DefaultHighlightPainter painter) {
        /*String text = textarea.getText();
        Highlighter highlighter = textarea.getHighlighter();

        if (!textToHighlight.isEmpty()) {
            //MATCH PATTERN
            Matcher m = compileWildcard(textToHighlight).matcher(text);
            while (m.find()) {
                try {
                    highlighter.addHighlight(m.start(), m.end(), painter);
                } catch (BadLocationException e) {
                    throw new RuntimeException(e);
                }
            }
        }*/
        highlight(textarea, textToHighlight, painter.getColor());
    }

    public static Pattern compileWildcard(String wildcard) {
        StringBuilder sb = new StringBuilder("\\b"); /* word boundary */
        /* the following replaceAll is just for performance */
        for (char c : wildcard.replaceAll("\\*+", "*").toCharArray()) {
            if (c == '*') {
                sb.append("\\S*"); /*- arbitrary non-space characters */
            } else {
                sb.append(Pattern.quote(String.valueOf(c)));
            }
        }
        sb.append("\\b"); /* word boundary */
        return Pattern.compile(sb.toString());
    }

}