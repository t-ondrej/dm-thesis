package sk.upjs.ics.autocomplete;

import java.util.Collection;
import java.util.Map;

public abstract class LanguageModel {

    protected Collection<String> inputSentences;

    public LanguageModel(Collection<String> sentences) {
        this.inputSentences = sentences;
    }

    abstract Map<Integer, String> getProbabilities(String token);
}
