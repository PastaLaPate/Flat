package com.github.PastaLaPate.FPL_IDE.util.syntax;

import java.awt.*;

public class Word {

    private final String word;
    private final Color color;

    public Word(String word, Color color) {
        this.word = word;
        this.color = color;
    }

    public String getWord() {
        return word;
    }

    public Color getColor() {
        return color;
    }
}
