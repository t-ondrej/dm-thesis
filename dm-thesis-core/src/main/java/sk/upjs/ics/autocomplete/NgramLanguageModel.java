package sk.upjs.ics.autocomplete;

import sk.upjs.ics.helpers.NGramGetter;
import sk.upjs.ics.helpers.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NgramLanguageModel extends LanguageModel {
    private Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency;
    private Map<Integer, Integer> ngramsizeToTotalFrequency;
    private int ngramSizeMin = 1;
    private int ngramSizeMax = 6;

    public static NgramLanguageModel create(Collection<String> sentences, NGramGetter ngramGetter) {
        NgramLanguageModel languageModel = new NgramLanguageModel(sentences);
        languageModel.init(ngramGetter);

        return languageModel;
    }

    private NgramLanguageModel(Collection<String> sentences) {
        super(sentences);
        // Placeholder
    }

    private void init(NGramGetter ngramGetter) {
        Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency = new HashMap<>();

        IntStream.rangeClosed(ngramSizeMin, ngramSizeMax)
            .forEach(ngramSize -> {
                List<Map.Entry<String, Integer>> test = this.inputSentences.stream()
                    .map(sentence ->
                        new ArrayList<>(Utils.getWordFrequencies(ngramGetter.getNGrams(sentence, ngramSize)).entrySet()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

                ngramsizeToNGramToFrequency.put(ngramSize, test.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum)));
            });

        this.ngramsizeToNGramToFrequency = ngramsizeToNGramToFrequency;
    }

    public void setNgramSizeMin(int ngramSizeMin) {
        this.ngramSizeMin = ngramSizeMin;
    }

    public void setNgramSizeMax(int ngramSizeMax) {
        this.ngramSizeMax = ngramSizeMax;
    }

    public Map<Integer, Map<String, Integer>> getNgramsizeToNGramToFrequency() {
        return ngramsizeToNGramToFrequency;
    }

    public Map<Integer, Integer> getNgramsizeToTotalFrequency() {
        if (ngramsizeToNGramToFrequency == null)
            ngramsizeToTotalFrequency = ngramsizeToNGramToFrequency.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().values().stream()
                        .mapToInt(value -> value)
                        .sum()));

        return ngramsizeToTotalFrequency;
    }

    public Integer getTotalNGramFrequencies(int ngram) {
        return getNgramsizeToTotalFrequency().get(ngram);
    }

    @Override
    public Map<Integer, String> getProbabilities(String token) {
        return null;
    }
}
