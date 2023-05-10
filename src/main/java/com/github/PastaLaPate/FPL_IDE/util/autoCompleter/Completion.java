package com.github.PastaLaPate.FPL_IDE.util.autoCompleter;

import java.util.ArrayList;
import java.util.List;

public class Completion {

    public final CompleteType type;
    public final List<Completion> completions;

    public final List<String> enumList;

    public final String completion;
    private final boolean futur;


    public Completion(CompleteType type, String completion, boolean futur) {
        this.type = type;
        this.futur = futur;
        this.completion = completion;
        this.enumList = new ArrayList<>();
        this.completions = new ArrayList<>();
    }

    public Completion addToEnum(String value) {
        this.enumList.add(value);
        return this;
    }

    public boolean isFutur() {
        return futur;
    }

    public Completion addCompletion(Completion completion) {
        completions.add(completion);
        return this;
    }

    enum CompleteType {
        VALUE, ENUM, BASE
    }
}
