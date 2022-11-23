package com.github.PastaLaPate.FPL_IDE.util.Syntax;

import com.jfoenix.utils.JFXHighlighter;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class SyntaxHighLighter {
    private JFXHighlighter highlighter;

    // GENERATE SYNTAX
    public void generateSyntax(TextArea pane) {
        if (highlighter != null) {
            highlighter.clear();
        }
        // ALL TYPES PATTERN
        String[] types = {"vide", "entier", "decimal", "texte", "auto"};
        // ALL FUNCTIONS PATTERN
        String[] functions = {"envoyer", "definir", "appeler", "renvoyer", "fichier", ";", "(", ")", "{", "}"};
        // ALL VARIABLES PATTERN
        String[] variables = {"variable", "changer", "saisir", "ecrire", "lire"};
        // ALL CHAR PATTER
        String[] chars = {"\"", ";", "'", "->", "=", "+", "-", "{", "}", "(", ")"};

        //HIGHLIGHT TYPES PATTERNS WITH COLOR BLUE
        for (String type : types) {
            highlight(pane, type, Color.rgb(0, 139, 139));
        }
        //HIGHLIGHT FUNCTIONS PATTERNS WITH COLOR YELLOW
        for (String function : functions) {
            highlight(pane, function,  Color.rgb(184, 134, 11));
        }
        //HIGHLIGHT CHARS PATTERS WITH COLOR RED
        for (String chars1 : chars) {
            highlight(pane,chars1, Color.rgb(178, 34, 34));
        }
        //HIGHLIGHT VARIABLES PATTERNS WITH COLOR GREEN
        for (String variable : variables) {
            highlight(pane, variable, Color.rgb(34, 139, 34));
        }
    }

    private void highlight(TextArea textArea, String text, Color color) {
        highlighter = new JFXHighlighter();
        highlighter.setPaint(color);
        highlighter.highlight(textArea, text);
    }
}
