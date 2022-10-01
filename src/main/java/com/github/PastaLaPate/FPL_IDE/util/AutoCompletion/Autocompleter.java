package com.github.PastaLaPate.FPL_IDE.util.AutoCompletion;

import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Autocompleter {
    final List<String> completes;
    AutoCompleteListener listener;

    private static PopupF Ppopup = null;

    public Autocompleter() {
        // ALL TYPES PATTERN
        String[] types = {"vide", "entier", "decimal", "texte", "auto"};
        // ALL FUNCTIONS PATTERN
        String[] function = {"envoyer", "definir", "appeler", "renvoyer", "fichier", "importer"};
        // ALL VARIABLES PATTERN
        String[] variables = {"variable", "changer", "saisir", "ecrire", "lire"};
        completes = new ArrayList<>();
        completes.addAll(List.of(types));
        completes.addAll(List.of(function));
        completes.addAll(List.of(variables));
    }

    public void autocomplete(String word, double x, double y, Component invoker) {
        List<String> completions = getAllCompletion(word);
        if (Ppopup != null) {
            Ppopup.hide();
        }
        PopupF popupF = new PopupF();
        popupF.addCompletions(completions);
        popupF.setListener(listener);
        popupF.show(invoker, (int) x, (int) y);
        Ppopup = popupF;
    }

        public void autocomplete(String word, double x, double y, Component invoker, String previous) {
            List<String> completions;
            if (previous.equals("importer")) {
                completions = getAllFileCompletion();
            } else {
              completions = getAllCompletion(word);
            }
            if (Ppopup != null) {
                Ppopup.hide();
            }
            PopupF popupF = new PopupF();
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

    public List<String> getAllFileCompletion() {
        List<String> r = new ArrayList<>();
        try {
            for (String string : new Saver().getFilesInFolder(Downloader.getPathFolder())) {
                r.add("\"" + string + "\"");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return r;
    }
}
