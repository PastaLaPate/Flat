package com.github.PastaLaPate.FPL_IDE.util.autoCompleter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoCompleter {

    private final ObservableList<Completion> completions;

    public AutoCompleter() {

        this.completions = FXCollections.observableArrayList();
        this.completions.addAll(
                // TYPES
                new Completion(Completion.CompleteType.BASE, "vide", false),
                new Completion(Completion.CompleteType.BASE, "entier", false),
                new Completion(Completion.CompleteType.BASE, "texte", false),
                new Completion(Completion.CompleteType.BASE, "auto", false),
                new Completion(Completion.CompleteType.BASE, "decimal", false),
                // BASICS FUNCTION
                new Completion(Completion.CompleteType.BASE, "envoyer", false)
                        .addCompletion(new Completion(Completion.CompleteType.VALUE, "", true)),
                // FICHIERS
                new Completion(Completion.CompleteType.BASE, "fichier", false)
                        .addCompletion(new Completion(Completion.CompleteType.ENUM, "", true)
                                .addToEnum("lire")
                                .addToEnum("ecrire"))
        );
    }

    public List<Completion> complete(String text) {
        List<Completion> response = new ArrayList<>();

        String[] words = text.split(" ");
        String lastword = words[words.length - 1];
        String beforeLastWord;
        if (words.length > 1) {
            beforeLastWord = words[words.length - 2];
        } else {
            beforeLastWord = "";
        }
        completions.forEach(completion -> {
            if (Objects.equals(completion.completion, beforeLastWord)) {
                completion.completions.forEach(completion1 -> {
                    if (completion1.isFutur()) {
                        if (completion1.type == Completion.CompleteType.ENUM) {
                            completion1.enumList.forEach(enumV -> {
                                if (enumV.startsWith(lastword)) {
                                    response.add(0, new Completion(Completion.CompleteType.ENUM, enumV, true));
                                }
                            });
                        }
                    }
                });
            } else if (completion.completion.startsWith(lastword)) {
                response.add(completion);
            }
        });

        return response;
    }
}
