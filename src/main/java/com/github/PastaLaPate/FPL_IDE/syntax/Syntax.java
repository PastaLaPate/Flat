package com.github.PastaLaPate.FPL_IDE.syntax;

import java.util.ArrayList;
import java.util.Arrays;

public class Syntax {

    public Syntax() {

    }

    public String getFinalString(String text) {
        ArrayList<Word> words = generateSyntax(text);
        String r = "";
        for (Word word : words) {
            r = r + "<span style='color: " + word.getColor() + "'>" + word.getWord() + "</span>";
        }
        return r;
    }

    public ArrayList<Word> generateSyntax(String text) {
        String[] types = {"vide", "entier", "decimal", "texte", "auto"};
        String[] function = {"envoyer", "definir", "appeler", "renvoyer"};
        String[] variables = {"variable", "changer", "saisir"};

        ArrayList<Word> r = new ArrayList<>();

        String delimSpace = "[ ;\"]+";
        String[] arr1  = text.split(delimSpace);
        for (String word : arr1) {
            if (Arrays.asList(types).contains(word)) {
                r.add(new Word(word, Color.BLUE));
            } else if (Arrays.asList(function).contains(word)) {
                r.add(new Word(word, Color.YELLOW));
            } else if (Arrays.asList(variables).contains(variables)) {
                r.add(new Word(word, Color.GREEN));
            } else {
                r.add(new Word(word,Color.BLACK));
            }
        }
        return r;
    }

}
