package sk.upjs.ics.autocomplete.markovautocompleter;

import sk.upjs.ics.autocomplete.LanguageModel;

import java.util.Map;
import java.util.stream.Collectors;

public class MarkovLanguageModel implements LanguageModel {
    private final Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency;
    private final Map<Integer, Integer> ngramsizeToTotalFrequency;

    public MarkovLanguageModel(Map<Integer, Map<String, Integer>> nToNgramFrequency) {
        this.ngramsizeToNGramToFrequency = nToNgramFrequency;
        ngramsizeToTotalFrequency = nToNgramFrequency.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().values().stream()
                                .mapToInt(value -> value)
                                .sum()));
    }

    public Map<Integer, Map<String, Integer>> getNgramsizeToNGramToFrequency() {
        return ngramsizeToNGramToFrequency;
    }

    public Map<Integer, Integer> getNgramsizeToTotalFrequency() {
        return ngramsizeToTotalFrequency;
    }

    public Integer getTotalNGramFrequencies(int ngram) {
        return getNgramsizeToTotalFrequency().get(ngram);
    }
}
