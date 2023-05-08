package com.github.PastaLaPate.FPL_IDE.util.autoCompleter;

import java.util.ArrayList;
import java.util.List;

public class Completion {

    public final CompleteType type;
    public final List<Completion> completions;

    public final List<String> enumList;

    public final String completion;


    public Completion(CompleteType type, String completion) {
        this.type = type;
        this.completion = completion;
        this.enumList = new ArrayList<>();
        this.completions = new ArrayList<>();
    }

    public Completion addToEnum(String value) {
        this.enumList.add(value);
        return this;
    }


    public Completion addCompletion(Completion completion) {
        completions.add(completion);
        return this;
    }

    enum CompleteType {
        VALUE, ENUM, NOTHING
    }
}
