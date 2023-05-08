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
                new Completion(Completion.CompleteType.NOTHING, "vide"),
                new Completion(Completion.CompleteType.NOTHING, "entier"),
                new Completion(Completion.CompleteType.NOTHING, "texte"),
                new Completion(Completion.CompleteType.NOTHING, "auto"),
                new Completion(Completion.CompleteType.NOTHING, "decimal"),
                // BASICS FUNCTION
                new Completion(Completion.CompleteType.NOTHING, "envoyer")
                        .addCompletion(new Completion(Completion.CompleteType.VALUE, "")),
                // FICHIERS
                new Completion(Completion.CompleteType.NOTHING, "fichier")
                        .addCompletion(new Completion(Completion.CompleteType.ENUM, "")
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
                System.out.println(completion.completion);
                completion.completions.forEach(completion1 -> {
                    if (completion1.type == Completion.CompleteType.ENUM) {
                        completion1.enumList.forEach(s -> {
                            if (s.startsWith(lastword)) {
                                response.add(new Completion(Completion.CompleteType.VALUE, s));
                            }
                        });
                    } else {
                        if (completion1.completion.startsWith(lastword)) {
                            response.add(completion1);
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
