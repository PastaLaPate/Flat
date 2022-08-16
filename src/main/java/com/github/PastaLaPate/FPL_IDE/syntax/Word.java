package com.github.PastaLaPate.FPL_IDE.syntax;

import java.awt.*;

public class Word {

    private String word;
    private Color color;

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
