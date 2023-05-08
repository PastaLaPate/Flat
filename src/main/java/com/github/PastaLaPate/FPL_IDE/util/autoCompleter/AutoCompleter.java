package com.github.PastaLaPate.FPL_IDE.util.autoCompleter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fxmisc.richtext.CodeArea;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleter {

    private ObservableList<Completion> completions;
    private CodeArea area;

    public AutoCompleter(CodeArea area) {
        this.area = area;

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
                        .addCompletion(new Completion(Completion.CompleteType.VALUE, ""))
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
            if (completion.completion.startsWith(lastword)) {
                response.add(completion);
            } else if (completion.completion.startsWith(beforeLastWord)) {
                completion.completions.forEach(completion1 -> {
                    if (completion1.completion.startsWith(lastword)) {
                        response.add(completion1);
                    }
                });
            }
        });

        return response;
    }
}
