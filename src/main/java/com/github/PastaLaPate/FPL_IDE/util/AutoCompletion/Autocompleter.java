package com.github.PastaLaPate.FPL_IDE.util.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Autocompleter {
    List<String> completes;
    AutoCompleteListener listener;

    private static PopupF Ppopup = null;

    public Autocompleter() {
        // ALL TYPES PATTERN
        String[] types = {"vide", "entier", "decimal", "texte", "auto"};
        // ALL FUNCTIONS PATTERN
        String[] function = {"envoyer", "definir", "appeler", "renvoyer", "fichier"};
        // ALL VARIABLES PATTERN
        String[] variables = {"variable", "changer", "saisir", "ecrire", "lire"};
        completes = new ArrayList<>();
        completes.addAll(List.of(types));
        completes.addAll(List.of(function));
        completes.addAll(List.of(variables));
    }

    public void autocomplete(JFrame frame, String word, double x, double y, Component invoker) {
        List<String> completions = getAllCompletion(word);
        if (Ppopup != null) {
            Ppopup.hide();
        }
        PopupF popupF = new PopupF(frame);
        popupF.addCompletions(completions);
        popupF.setListener(listener);
        popupF.show(invoker, (int) x, (int) y);
        Ppopup = popupF;
    }

    public void setListener(AutoCompleteListener listener) {
        this.listener = listener;
    }

    public List<String> getAllCompletion(String word) {
        List<String> r = new ArrayList<>();
        for (String complete : completes) {
            if (complete.startsWith(word)) {
                r.add(complete);
            }
        }
        return r;
    }
}
